package com.teacher.teacherlearn.curl_14_5;

import com.alibaba.fastjson.JSONObject;
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

    private static String orgId = "622293883621437440";
    private static String projectId = "622297127378276352";
    private String sourceId = "622297127378276352";

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
        builder.add("sourceId", sourceId);
        builder.add("clockInRule", "1");
        builder.add("timeLimit", "-1");
        FormBody body = builder.build();

        Request request = new Request.Builder()
                .url("https://www.ttcdw.cn/p/course/services/member/study/progress?orgId=" + orgId)
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

    public void refresh(String segId, String itemId, String courseId, String uToken) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://www.ttcdw.cn/p/course/services/course/public/course/lesson/" + courseId + "?" +
                        "itemId=" + itemId +
                        "&segId=" + segId +
                        "&projectId=" + projectId +
                        "&orgId=" + orgId +
                        "&type=1&isContent=false" +
                        "&courseId=" + courseId +
                        "&id=" + courseId +
                        "&sourceType=1" +
                        "&_=" + System.currentTimeMillis() +
                        "&orgId=" + orgId)
                .addHeader("accept", "application/json, text/javascript, */*; q=0.01")
                .addHeader("accept-language", "zh-CN,zh;q=0.9")
                .addHeader("cache-control", "no-cache")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("cookie", "u-token=" + uToken)
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        log.info("刷新冲突校验返回：{}", responseBody);
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        //目录->页->课程->video
        String uToken = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5NDEyZTQ3Ny1jNjUwLTQ3NjgtYjkyOC00NDE0MGMyYzhiMGYiLCJpYXQiOjE2OTcxNzgwNjQsInN1YiI6IjczNjcxODQyOTg4Njg5NDA4MCIsImlzcyI6Imd1b3JlbnQiLCJhdHRlc3RTdGF0ZSI6MCwic3JjIjoid2ViIiwiYWN0aXZlU3RhdGUiOjEsIm1vYmlsZSI6IjE4MjMxNzM4NTI5IiwicGxhdGZvcm1JZCI6IjEzMTQ1ODU0OTgzMzExIiwiYWNjb3VudCI6IjE4MjMxNzM4NTI5IiwiZXhwIjoxNjk3MjE0MDY0fQ.VB9o7r-jfVqbSas6sJLa92uyygqvagzjijYzDNKczXY";

        Run14_5_1 r = new Run14_5_1();

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
            r.refresh(segId, itemId, courseId, uToken);
            log.info("=========新课程 刷新冲突校验结束=========");
            List<LearnStruct.Video> videos = c.getVideos();
            for (LearnStruct.Video v : videos) {
                log.info("=========开始刷视频=========");
                String videoId = v.getVideoId();
                String topName = learnStruct.getTopName();
                String itemName = c.getItemName();
                String courseName = c.getCourseName();
                String name4 = v.getName4();
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

                    log.info("开始刷课!!!!信息：进度：{}，大类：{}，小类：{}，课程：{}，视频：{}", playProgress, topName, itemName, courseName, name4);
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
                        r.refresh(segId, itemId, courseId, uToken);
                        log.info("新课程 刷新冲突校验结束");
                        continue;
                    }
                    if (res.equals("1001")) {
                        log.info("登录失效");
                        Login login = new Login();
                        uToken = login.login();
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
