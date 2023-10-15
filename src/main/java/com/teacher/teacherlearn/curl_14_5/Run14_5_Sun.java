package com.teacher.teacherlearn.curl_14_5;

import com.teacher.teacherlearn.curl_14_5.common.SunData;
import com.teacher.teacherlearn.curl_14_5.pojo.CourseResp;
import com.teacher.teacherlearn.curl_14_5.pojo.LearnMessage;
import com.teacher.teacherlearn.curl_14_5.pojo.ModuleResp;
import com.teacher.teacherlearn.curl_14_5.pojo.VideoResp;
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
public class Run14_5_Sun {

    public static void main(String[] args) throws IOException, InterruptedException {


        //目录->页->课程->video
        Login login = new Login();
        GetData g = new GetData();

        String uToken = login.login(SunData.userName, SunData.passWord, SunData.platformId, SunData.service);

        List<LearnMessage> learnMessages = g.getSegIdAndItemId(uToken, SunData.projectId, SunData.classId);
        for (LearnMessage learn : learnMessages) {
            List<ModuleResp.Module.Detail> moudules = g.getModelIds(uToken, learn.getItemId());
            for (ModuleResp.Module.Detail m : moudules) {
                log.info("moduleId:{}，MouduleName:{}", m.getId(), m.getName());
                List<CourseResp.CourseData.Cuorses.Detail> courses = g.getCourse(uToken, learn.getSegId(), learn.getItemId(), m.getId());
                for (CourseResp.CourseData.Cuorses.Detail c : courses) {
                    log.info("CourseId:{}，CourseName:{},进度：{}", c.getCourseId(), c.getCourseName(), c.getCourseProgress());
                    if (c.getCourseProgress().equals("100")) {
                        log.info("课程：{}，进度：{}，学习完跳过", c.getCourseName(), c.getCourseProgress());
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
                            log.info("当前时间：{}", h);
//                            if (h <= 9 || h >= 22) {
//                                log.info("休息1小时");
//                                Thread.sleep(60 * 60 * 1000);
//                                continue;
//                            }

                            log.info("开始刷课!!!!信息：进度：{}，Topic1：{}，Topic2：{}，课程：{}，视频：{}", playProgress, topName, secondName, courseName, videoName);
                            res = g.watch(learn.getSegId(), learn.getItemId(), c.getCourseId(), videoId, String.valueOf(playProgress), uToken);
                            log.info("开始刷课结束!!!返回：{}", res);
                            if (res.equals("-1")) {
                                log.info("返回-1，刷的太快，时间不够，休息10s");
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
                                uToken = login.login(SunData.userName, SunData.passWord, SunData.platformId, SunData.service);
                                continue;
                            }
                            playProgress = Integer.valueOf(res) + 60;
                            log.info("60s休息开始");
                            Thread.sleep(60000);
                            log.info("60s休息结束");
                        }
                    }

                }
            }
        }
    }
}
