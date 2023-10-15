package com.teacher.teacherlearn.curl_14_5;

import com.alibaba.fastjson.JSONObject;
import com.teacher.teacherlearn.curl_14_5.common.SunData;
import com.teacher.teacherlearn.curl_14_5.pojo.LearnResp;
import com.teacher.teacherlearn.curl_14_5.pojo.LearnStruct;
import com.teacher.teacherlearn.curl_14_5.pojo.VideoResp;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

/**
 * 十四五
 */
@Slf4j
@Component
public class Run14_5_1 {

    public static void main(String[] args) throws IOException, InterruptedException {

        //目录->页->课程->video
        Run14_5_1 r = new Run14_5_1();
        Login login = new Login();
        GetData g = new GetData();

        String uToken = login.login(SunData.userName, SunData.passWord, SunData.platformId, SunData.service);

        Path path = Paths.get("src/main/java/com/teacher/teacherlearn/curl_14_5", "catalog_data/data2.json");
        String data = new String(Files.readAllBytes(path));
        log.info("读取到数据：{}", data);

        LearnStruct learnStruct = JSONObject.parseObject(data, LearnStruct.class);
        String segId = learnStruct.getSegId();
        String itemId = learnStruct.getItemId();
        List<LearnStruct.Course> courses = learnStruct.getCourses();

        for (LearnStruct.Course c : courses) {
            String courseId = c.getCourseId();
            log.info("=========新课程 刷新冲突校验开始=========");
            List<VideoResp.Data> videos = g.getVideo(segId, itemId, courseId, uToken);
            log.info("=========新课程 刷新冲突校验结束=========");
            for (VideoResp.Data v : videos) {
                log.info("=========开始刷视频=========");
                String videoId = v.getVideoId();
                String topName = learnStruct.getTopName();
                String itemName = c.getItemName();
                String courseName = c.getCourseName();
                String videoName = v.getName();
                int playProgress = 60;
                String res;
                while (true) {
                    Calendar cal = Calendar.getInstance();
                    int h = cal.get(Calendar.HOUR_OF_DAY);
                    log.info("当前时间：{}", h);
                    if (h <= 9 || h >= 22) {
                        log.info("休息1小时");
                        Thread.sleep(60 * 60 * 1000);
                        continue;
                    }

                    log.info("开始刷课!!!!信息：进度：{}，大类：{}，小类：{}，课程：{}，视频：{}", playProgress, topName, itemName, courseName, videoName);
                    res = r.run1(segId, itemId, courseId, videoId, String.valueOf(playProgress), uToken);
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
                        g.getVideo(segId, itemId, courseId, uToken);
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

    public String run1(String segId, String itemId, String courseId, String videoId, String playProgress, String uToken) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("courseId", courseId);
        builder.add("itemId", itemId);
        builder.add("videoId", videoId);
        builder.add("playProgress", playProgress);
        builder.add("segId", segId);
        builder.add("isFinish", "false");
        builder.add("type", "1");
        builder.add("tjzj", "1");
        builder.add("clockInDot", playProgress);
        builder.add("sourceId", SunData.sourceId);
        builder.add("clockInRule", "1");
        builder.add("timeLimit", "-1");
        FormBody body = builder.build();

        Request request = new Request.Builder()
                .url("https://www.ttcdw.cn/p/course/services/member/study/progress?orgId=" + SunData.orgId)
                .method("POST", body)
                .addHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("cookie", "u-token=" + uToken)
                .addHeader("encryptionvalue", courseId)
                .addHeader("isencryption", "true")
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        log.info("刷课请求返回：{}", responseBody);
        if (response.isSuccessful()) {

            LearnResp res = JSONObject.parseObject(responseBody, LearnResp.class);
            if (res.getResultCode() == 3003) {
                return "3003";
            } else if (res.getResultCode() == 0) {
                if (res.getData().getProgress().equals("-1")) {
                    return "-1";
                } else if (res.getData().getProgress().equals("1.0")) {
                    //完成
                    return "1";
                } else {
                    //继续刷课
                    return res.getData().getVideoProgress();
                }
            } else if (res.getResultCode() == 1001) {
                return "1001";
            }
        }
        return "-1";
    }

}
