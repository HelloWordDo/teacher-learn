package com.teacher.teacherlearn.curl_14_5;

import com.teacher.teacherlearn.curl_14_5.common.HaiXiaData;
import com.teacher.teacherlearn.curl_14_5.course.GetData;
import com.teacher.teacherlearn.curl_14_5.course.pojo.CourseResp;
import com.teacher.teacherlearn.curl_14_5.course.pojo.LearnMessage;
import com.teacher.teacherlearn.curl_14_5.course.pojo.ModuleResp;
import com.teacher.teacherlearn.curl_14_5.course.pojo.VideoResp;
import com.teacher.teacherlearn.curl_14_5.exam.Exam;
import com.teacher.teacherlearn.curl_14_5.jwt.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * 十四五
 */
@Slf4j
@Component
public class Run14_5_HaiXia {

    public static void main(String[] args) throws IOException, InterruptedException {


        //目录->页->课程->video
        Login login = new Login();
        GetData g = new GetData();
        Jwt j = new Jwt();
        String uToken = login.login(HaiXiaData.userName, HaiXiaData.passWord, HaiXiaData.platformId, HaiXiaData.service);
        String exp = j.decodeJwt(uToken);
        List<LearnMessage> learnMessages = g.getSegIdAndItemId(uToken, HaiXiaData.projectId, HaiXiaData.classId);
        for (LearnMessage learn : learnMessages) {
            List<ModuleResp.Module.Detail> moudules = g.getModelIds(uToken, learn.getItemId());
            String totalHour = learn.getTotalHour();
            int period = 0;
            for (ModuleResp.Module.Detail m : moudules) {
                log.info("moduleId:{}，MouduleName:{}", m.getId(), m.getName());
                List<CourseResp.CourseData.Cuorses.Detail> courses = g.getCourse(uToken, learn.getSegId(), learn.getItemId(), m.getId());
                for (CourseResp.CourseData.Cuorses.Detail c : courses) {
                    log.info("CourseId:{}，CourseName:{},进度：{}", c.getCourseId(), c.getCourseName(), c.getCourseProgress());
                    if (c.getCourseProgress().equals("100")) {
                        period += Double.valueOf(c.getPeriod()).intValue();
                        log.info("课程：{}，进度：{}，总学习时长：{}，学习完跳过", c.getCourseName(), c.getCourseProgress(), period);
                        if (!c.getExamProgress().equals("100")) {
                            log.info("课程：{},考试进度：{},开始考试", c.getCourseName(), c.getExamProgress());
                            Exam exam = new Exam();
                            exam.excamChain(uToken, HaiXiaData.projectId, HaiXiaData.classId, learn.getItemId(), c.getItemExamId(), learn.getSegId());
                        }
                        continue;
                    }
                    if (Double.valueOf(totalHour).intValue() <= period) {
                        log.info("{}已看够{}小时无需再看", learn.getSegName(), period);
                        continue;
                    }
                    log.info("=========新课程 刷新冲突校验开始=========");
                    List<VideoResp.Data> videos = g.getVideo(learn.getSegId(), learn.getItemId(), c.getCourseId(), uToken);
                    log.info("=========新课程 刷新冲突校验结束=========");

                    for (VideoResp.Data v : videos) {
                        log.info("=========开始刷视频=========");
                        String videoId = v.getVideoId();
                        String topName = learn.getSegName();
                        String secondName = m.getName();
                        String courseName = c.getCourseName();
                        String videoName = v.getName();
                        int playProgress = 60;
                        String res;
                        while (true) {
                            Calendar cal = Calendar.getInstance();
                            int h = cal.get(Calendar.HOUR_OF_DAY);
//                            log.info("当前时间：{}", h);
//                            if (h <= 9 || h >= 22) {
//                                log.info("休息1小时");
//                                Thread.sleep(60 * 60 * 1000);
//                                continue;
//                            }
                            long now = System.currentTimeMillis() / 1000;
                            if (Long.parseLong(exp) - now < 60 * 60) {
                                log.info("距离过期时间不足一小时,开始刷新Token");
                                uToken = login.login(HaiXiaData.userName, HaiXiaData.passWord, HaiXiaData.platformId, HaiXiaData.service);
                                exp = j.decodeJwt(uToken);
                            }
                            log.info("开始刷课!!!!总进度：{}，进度：{}，Topic1：{}，Topic2：{}，课程：{}，视频：{}", v.getDuration(), playProgress, topName, secondName, courseName, videoName);
                            res = g.watch(learn.getSegId(), learn.getItemId(), c.getCourseId(), videoId, String.valueOf(playProgress), uToken, HaiXiaData.projectId, HaiXiaData.orgId);
                            log.info("开始刷课结束!!!返回进度：{}", res);
                            if (res.equals("-1")) {
                                Thread.sleep(10000);
                                continue;
                            }

                            if (res.equals("1")) {
                                log.info("================================================================");
                                log.info("=====================学完了，跳出，进行下一个视频===================");
                                log.info("================================================================");
                                break;
                            }

                            if (res.equals("3003")) {
                                log.info("新课程 刷新冲突校验开始");
                                g.getVideo(learn.getSegId(), learn.getItemId(), c.getCourseId(), uToken);
                                log.info("新课程 刷新冲突校验结束");
                                continue;
                            }
                            if (res.equals("1001")) {
                                log.info("登录失效");
                                uToken = login.login(HaiXiaData.userName, HaiXiaData.passWord, HaiXiaData.platformId, HaiXiaData.service);
                                continue;
                            }
                            playProgress = Integer.valueOf(res) + 60;
                            Thread.sleep(60000);
                        }
                    }
                    period += Double.valueOf(c.getPeriod()).intValue();
                    log.info("期望时长：{}，总学习时长：{}", totalHour, period);
                    if (!c.getExamProgress().equals("100")) {
                        log.info("课程：{},考试进度：{},开始考试", c.getCourseName(), c.getExamProgress());
                        Exam exam = new Exam();
                        Thread.sleep(60000 * 8);
                        exam.excamChain(uToken, HaiXiaData.projectId, HaiXiaData.classId, learn.getItemId(), c.getItemExamId(), learn.getSegId());
                    }
                }
            }
        }
    }
}
