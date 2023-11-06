package com.teacher.teacherlearn.curl_14_5.service;

import com.teacher.teacherlearn.curl_14_5.Login;
import com.teacher.teacherlearn.curl_14_5.config.FourteenFive;
import com.teacher.teacherlearn.curl_14_5.config.User;
import com.teacher.teacherlearn.curl_14_5.course.GetData;
import com.teacher.teacherlearn.curl_14_5.course.pojo.CourseResp;
import com.teacher.teacherlearn.curl_14_5.course.pojo.LearnMessage;
import com.teacher.teacherlearn.curl_14_5.course.pojo.ModuleResp;
import com.teacher.teacherlearn.curl_14_5.course.pojo.VideoResp;
import com.teacher.teacherlearn.curl_14_5.exam.Exam;
import com.teacher.teacherlearn.curl_14_5.exam.pojo.ExamRequest;
import com.teacher.teacherlearn.curl_14_5.jwt.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.PriorityQueue;

@Slf4j
@Service
public class LearnService {

    @Autowired
    Login login;
    @Autowired
    GetData getData;
    @Autowired
    Exam exam;
    @Autowired
    Jwt jwt;
    PriorityQueue<ExamRequest> queue = new PriorityQueue<>();

    @Async
    public void learn(User u, FourteenFive fourteenFive) throws IOException, InterruptedException {

        String uToken = login.login(u.getUserName(), u.getPassWord(), fourteenFive.getPlatformId(), fourteenFive.getService());
        String exp = "";
        try {
            exp = jwt.decodeJwt(uToken);
        } catch (Exception e) {
            log.error("【{}】账号密码错误", u.getName());
            return;
        }

        List<LearnMessage> learnMessages = getData.getSegIdAndItemId(uToken, fourteenFive.getProjectId(), fourteenFive.getClassId());
        for (LearnMessage learn : learnMessages) {
            List<ModuleResp.Module.Detail> modules = getData.getModelIds(uToken, learn.getItemId());
            String totalHour = learn.getTotalHour();
            int period = 0;
            for (ModuleResp.Module.Detail m : modules) {
                List<CourseResp.CourseData.Cuorses.Detail> courses = getData.getCourse(uToken, learn.getSegId(), learn.getItemId(), m.getId());
                for (CourseResp.CourseData.Cuorses.Detail c : courses) {
                    if (c.getCourseProgress().equals("100")) {
                        period += Double.valueOf(c.getPeriod()).intValue();
                        log.info("【{}】：课程：{}，进度：{}，总学习时长：{}，学习完跳过", u.getName(), c.getCourseName(), c.getCourseProgress(), period);
                        if (!c.getExamProgress().equals("100")) {
                            log.info("【{}】提交考试队列：课程：{},考试进度：{},开始考试", u.getName(), c.getCourseName(), c.getExamProgress());
                            ExamRequest examRequest = new ExamRequest(0);
                            examRequest.setUToken(uToken);
                            examRequest.setProjectId(fourteenFive.getProjectId());
                            examRequest.setClassId(fourteenFive.getClassId());
                            examRequest.setItemId(learn.getItemId());
                            examRequest.setItemExamId(c.getItemExamId());
                            examRequest.setSegId(learn.getSegId());
                            queue.offer(examRequest);
                        }
                        continue;
                    }
                    if (Double.valueOf(totalHour).intValue() <= period) {
                        log.info("【{}】{}已看够{}小时无需再看", u.getName(), learn.getSegName(), period);
                        continue;
                    }
                    List<VideoResp.Data> videos = getData.getVideo(learn.getSegId(), learn.getItemId(), c.getCourseId(), uToken);

                    for (VideoResp.Data v : videos) {
                        log.info("=========【{}】开始刷视频=========", u.getName());
                        String videoId = v.getVideoId();
                        String topName = learn.getSegName();
                        String secondName = m.getName();
                        String courseName = c.getCourseName();
                        String videoName = v.getName();
                        int playProgress = 60;
                        String res;
                        while (true) {
//                            Calendar cal = Calendar.getInstance();
//                            int h = cal.get(Calendar.HOUR_OF_DAY);
//                            log.info("当前时间：{}", h);
//                            if (h <= 9 || h >= 22) {
//                                log.info("休息1小时");
//                                Thread.sleep(60 * 60 * 1000);
//                                continue;
//                            }
                            long now = System.currentTimeMillis() / 1000;
                            if (Long.parseLong(exp) - now < 60 * 60) {
                                log.info("【{}】Token距离过期时间不足一小时,开始刷新Token", u.getName());
                                uToken = login.login(u.getUserName(), u.getPassWord(), fourteenFive.getPlatformId(), fourteenFive.getService());
                                exp = jwt.decodeJwt(uToken);
                            }
                            log.info("【{}】开始刷课，总进度：{}，进度：{}，Topic1：{}，Topic2：{}，课程：{}，视频：{}", u.getName(), v.getDuration(), playProgress, topName, secondName, courseName, videoName);
                            try {
                                res = getData.watch(learn.getSegId(), learn.getItemId(), c.getCourseId(), videoId, String.valueOf(playProgress), uToken, fourteenFive.getProjectId(), fourteenFive.getOrgId());
                            } catch (Exception e) {
                                log.error(e.getMessage());
                                Thread.sleep(10000);
                                continue;
                            }
                            log.info("【{}】开始刷课结束，返回进度：{}", u.getName(), res);
                            if (res.equals("-1")) {
                                Thread.sleep(1000L * 30);
                                continue;
                            }
                            if (res.equals("1")) {
                                log.info("=====================【{}】学完了，跳出，进行下一个视频===================", u.getName());
                                break;
                            }
                            if (res.equals("3003")) {
                                getData.getVideo(learn.getSegId(), learn.getItemId(), c.getCourseId(), uToken);
                                continue;
                            }
                            playProgress = Integer.valueOf(res) + 60;
                            Thread.sleep(60000);
                        }
                    }
                    period += Double.valueOf(c.getPeriod()).intValue();
                    log.info("【{}】：期望时长：{}，总学习时长：{}", u.getName(), totalHour, period);
                    if (!c.getExamProgress().equals("100")) {
                        log.info("【{}】提交考试队列：课程：{},考试进度：{},开始考试", u.getName(), c.getCourseName(), c.getExamProgress());
                        ExamRequest examRequest = new ExamRequest();
                        examRequest.setUToken(uToken);
                        examRequest.setProjectId(fourteenFive.getProjectId());
                        examRequest.setClassId(fourteenFive.getClassId());
                        examRequest.setItemId(learn.getItemId());
                        examRequest.setItemExamId(c.getItemExamId());
                        examRequest.setSegId(learn.getSegId());
                        queue.offer(examRequest);
                    }
                }
            }
        }
        log.info("=============================");
        log.info("=     【{}】课程刷完     =", u.getName());
        log.info("=============================");
    }

    @Async
    public void Exam() throws InterruptedException {
        while (true) {
            log.info("Queue剩余任务：{}", queue.size());
            if (queue.size() == 0) {
                Thread.sleep(1000L * 60);
                continue;
            }
            if (queue.peek().getExecuteTime() > System.currentTimeMillis()) {
                Thread.sleep(1000L * 60);
                continue;
            }
            ExamRequest examRequest = queue.poll();
            log.info("获取到考试信息{}", examRequest);
            try {
                boolean success = exam.excamChain(examRequest);
                if (!success) {
                    examRequest.setExecuteTime(System.currentTimeMillis() + 1000L * 60);
                    log.info("重新放入考试队列{}", examRequest);
                    queue.offer(examRequest);
                }
            } catch (Exception e) {
                log.error("考试出错:{}", e.getMessage());
                examRequest.setExecuteTime(System.currentTimeMillis() + 1000L * 60);
                queue.offer(examRequest);
            }
        }
    }
}
