package com.teacher.teacherlearn.service;

import com.teacher.teacherlearn.ai_education.StepService;
import com.teacher.teacherlearn.ai_education.entity.*;
import com.teacher.teacherlearn.config.Account;
import com.teacher.teacherlearn.config.User;
import com.teacher.teacherlearn.utils.Jwt;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AiLearnService {

    Account account;
    LoginService loginService;
    WatchVideo watch;
    Jwt jwt;
    StepService stepService;
    static AiAccount aiAccount;

    @PostConstruct
    public void postConstruct() {
        aiAccount = account.getAiAccount();
    }

    @Async("taskExecutor")
    public void learn(User user) throws IOException, InterruptedException {
        String uToken = loginService.login(user.getUserName(), user.getPassWord(), account.getPlatformId(), account.getFourteenFive().getService());
        String exp = "";
        try {
            exp = jwt.decodeJwt(uToken);
        } catch (Exception e) {
            log.error("【{}】账号密码错误", user.getName());
            return;
        }

        List<Segment> segments = stepService.getSegmentList(uToken);

        for (Segment segment : segments) {
            String segmentId = segment.getId();

            List<Item> items = stepService.getSegmentData(uToken, segmentId);

            for (Item item : items) {
                String itemId = item.getId();
                List<Module.ModuleListBean> moduleListBeans = stepService.getCourseModule(uToken, itemId);

                for (Module.ModuleListBean module : moduleListBeans) {
                    String moduleId = module.getId();

                    Catalog catalog = stepService.getCatalog(uToken, itemId, segmentId, moduleId);
                    Catalog.CourseListBean courseList = catalog.getCourseList();
                    List<Course> courses = courseList.getList();

                    for (Course course : courses) {
                        String courseId = course.getCourseId();

                        if (course.getCourseProgress().equals("100")) {
                            log.info("【{}】：课程：{}，进度：{}，学习完跳过", user.getName(), course.getCourseName(), course.getCourseProgress());
                            continue;
                        }
                        List<Video> videos = stepService.getVideo(uToken, courseId, segmentId, itemId);

                        for (Video video : videos) {
                            log.info("=========【{}】开始刷视频=========", user.getName());

                            String videoId = video.getVideoId();
                            String secondName = module.getName();
                            String courseName = course.getCourseName();
                            String videoName = video.getName();
                            int playProgress = 60;
                            String res;
                            while (true) {
                                long now = System.currentTimeMillis() / 1000;
                                if (Long.parseLong(exp) - now < 60 * 60) {
                                    log.info("【{}】Token距离过期时间不足一小时,开始刷新Token", user.getName());
                                    uToken = loginService.login(user.getUserName(), user.getPassWord(), account.getPlatformId(), account.getFourteenFive().getService());
                                    exp = jwt.decodeJwt(uToken);
                                }
                                log.info("【{}】开始刷课，总进度：{}，进度：{}，Topic：{}，课程：{}，视频：{}", user.getName(), video.getDuration(), playProgress, secondName, courseName, videoName);
                                try {
                                    res = watch.watch(segmentId, itemId, courseId, videoId, String.valueOf(playProgress), uToken, aiAccount.getProjectId(), account.getOrgId());
                                } catch (Exception e) {
                                    log.error(e.getMessage());
                                    Thread.sleep(10000);
                                    continue;
                                }
                                log.info("【{}】开始刷课结束，返回进度：{}", user.getName(), res);
                                if (res.equals("-1")) {
                                    Thread.sleep(1000L * 65);
                                    continue;
                                }
                                if (res.equals("1")) {
                                    Thread.sleep(1000L * 65);
                                    log.info("=====================【{}】学完了，跳出，进行下一个视频===================", user.getName());
                                    break;
                                }
                                if (res.equals("3003")) {
                                    Thread.sleep(1000L * 65);
                                    stepService.getVideo(uToken, courseId, segmentId, itemId);
                                    continue;
                                }
                                playProgress = Integer.valueOf(res) + 60;
                                Thread.sleep(60000);
                            }
                        }
                    }
                }
            }
        }
        log.info("=============================");
        log.info("=     【{}】课程刷完     =", user.getName());
        log.info("=============================");
    }
}
