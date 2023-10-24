package com.teacher.teacherlearn.curl_14_5.course;

import com.alibaba.fastjson.JSONObject;
import com.teacher.teacherlearn.curl_14_5.Login;
import com.teacher.teacherlearn.curl_14_5.common.SunData;
import com.teacher.teacherlearn.curl_14_5.course.pojo.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class GetData {

    public static void main(String[] args) throws IOException {

        Login login = new Login();
        String uToken = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ZWI4NmVjNi1lMmViLTRhZGUtYWFiNS0xMDdlYWMyMDZjNDgiLCJpYXQiOjE2OTcyNzk1NzcsInN1YiI6IjczNjcxODQyOTg4Njg5NDA4MCIsImlzcyI6Imd1b3JlbnQiLCJhdHRlc3RTdGF0ZSI6MCwic3JjIjoid2ViIiwiYWN0aXZlU3RhdGUiOjEsIm1vYmlsZSI6IjE4MjMxNzM4NTI5IiwicGxhdGZvcm1JZCI6IjEzMTQ1ODU0OTgzMzExIiwiYWNjb3VudCI6IjE4MjMxNzM4NTI5IiwiZXhwIjoxNjk3MzE1NTc3fQ.zJ5COdQkoMwMpw7-cRsn-nMbnj56TIipqRIPl5hE1zE;domain=.ttcdw.cn;path=/;Secure=true;SameSite=None";
//        String uToken = login.login();
        GetData g = new GetData();

        UserInfoResp.Project.ProjectClass projectClass = g.getUserinfo(uToken, SunData.classId);
        log.info("获取User返回：{}", projectClass);
        List<LearnMessage> learnMessages = g.getSegIdAndItemId(uToken, projectClass.getProjectId(), SunData.classId);
        for (LearnMessage learn : learnMessages) {
            List<ModuleResp.Module.Detail> moudules = g.getModelIds(uToken, learn.getItemId());
            for (ModuleResp.Module.Detail m : moudules) {
                log.info("moduleId:{}，MouduleName:{}", m.getId(), m.getName());
                List<CourseResp.CourseData.Cuorses.Detail> courses = g.getCourse(uToken, learn.getSegId(), learn.getItemId(), m.getId());
                for (CourseResp.CourseData.Cuorses.Detail c : courses) {
                    log.info("CourseId:{}，CourseName:{},进度：{}", c.getCourseId(), c.getCourseName(), c.getCourseProgress());
                }
            }
        }
    }

    public UserInfoResp.Project.ProjectClass getUserinfo(String uToken, String classId) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://www.ttcdw.cn/p/uc/services/member/project/header?classId=" + classId)
                .addHeader("cookie", "u-token=" + uToken)
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        log.info("获取userInfo返回：{}", responseBody);
        if (response.isSuccessful()) {
            UserInfoResp res = JSONObject.parseObject(responseBody, UserInfoResp.class);
            UserInfoResp.Project.ProjectClass projectClass = res.getData().getProjectClass();
            return projectClass;
        }
        return null;
    }

    //Step1
    public List<LearnMessage> getSegIdAndItemId(String uToken, String projectId, String classId) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://www.ttcdw.cn/p/uc/services/member/project/projectIndex/" + projectId +
                        "/progressData?classId=" +
                        classId)
                .addHeader("cookie", "u-token=" + uToken)
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        log.info("获取segId返回：{}", responseBody);

        List<LearnMessage> learnMessages = new ArrayList<>();
        if (response.isSuccessful()) {
            SegmentResp res = JSONObject.parseObject(responseBody, SegmentResp.class);
            SegmentResp.Segment seg = res.getData();
            List<SegmentResp.Segment.Seg> segs = seg.getSegments();
            for (SegmentResp.Segment.Seg s : segs) {
                LearnMessage learnMessage = new LearnMessage();
                learnMessage.setSegId(s.getId());
                learnMessage.setSegName(s.getName());
                learnMessage.setItemId(s.getItemList().get(0).getId());
                learnMessage.setTotalHour(s.getTotalHour());
                learnMessages.add(learnMessage);
            }
        }
        return learnMessages;
    }

    public String getItemsData(String uToken, String segId, String projectId) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://www.ttcdw.cn/p/uc/services/member/project/myClassroom/segmentData/" + projectId +
                        "/segment/" + segId)
                .addHeader("cookie", "u-token=" + uToken)
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        log.info("获取ItemId返回：{}", responseBody);

        if (response.isSuccessful()) {
            ItemDataResp res = JSONObject.parseObject(responseBody, ItemDataResp.class);
            List<ItemDataResp.ItemData.Item.Detail> item = res.getData().getData().getItemList();
            String itemId = item.get(0).getId();
            return itemId;
        }

        return "";
    }

    //Step2
    public List<ModuleResp.Module.Detail> getModelIds(String uToken, String itemId) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://www.ttcdw.cn/p/uc/services/member/project/memberCourse/requiredCourseModule?itemId=" +
                        itemId)
                .addHeader("cookie", "u-token=" + uToken)
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        log.info("获取ModelIds返回：{}", responseBody);

        if (response.isSuccessful()) {
            ModuleResp res = JSONObject.parseObject(responseBody, ModuleResp.class);
            ModuleResp.Module m = res.getData();
            List<ModuleResp.Module.Detail> items = m.getModuleList();
            return items;
        }

        return Collections.emptyList();
    }

    //step3
    public List<CourseResp.CourseData.Cuorses.Detail> getCourse(String uToken, String segId, String itemId, String moduleId) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://www.ttcdw.cn/p/uc/services/member/project/memberCourse/requiredCourseExam?pageSize=10&pageNum=1&itemId=" +
                        itemId + "&segmentId=" + segId + "&moduleId=" + moduleId)
                .addHeader("cookie", "u-token=" + uToken)
                .build();
        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();
        log.info("获取Course返回：{}", responseBody);
        if (response.isSuccessful()) {
            CourseResp res = JSONObject.parseObject(responseBody, CourseResp.class);
            List<CourseResp.CourseData.Cuorses.Detail> details = res.getData().getCourseList().getList();
            return details;
        }
        return Collections.EMPTY_LIST;
    }

    //step4
    public List<VideoResp.Data> getVideo(String segId, String itemId, String courseId, String uToken) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://www.ttcdw.cn/p/course/services/course/public/course/lesson/" + courseId + "?" +
                        "itemId=" + itemId +
                        "&segId=" + segId +
                        "&projectId=" + SunData.projectId +
                        "&orgId=" + SunData.orgId +
                        "&type=1&isContent=false" +
                        "&courseId=" + courseId +
                        "&id=" + courseId +
                        "&sourceType=1" +
                        "&_=" + System.currentTimeMillis() +
                        "&orgId=" + SunData.orgId)
                .addHeader("accept", "application/json, text/javascript, */*; q=0.01")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("cookie", "u-token=" + uToken)
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        log.info("获取课程中的视频返回：{}", responseBody);

        if (response.isSuccessful()) {
            VideoResp res = JSONObject.parseObject(responseBody, VideoResp.class);
            List<VideoResp.Data> data = res.getData();
            return data;
        }
        return Collections.EMPTY_LIST;
    }

    public String watch(String segId, String itemId, String courseId, String videoId, String playProgress, String uToken, String projectId, String orgId) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .writeTimeout(10000, TimeUnit.MILLISECONDS)
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
        builder.add("sourceId", projectId);
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
        LearnResp res = JSONObject.parseObject(responseBody, LearnResp.class);
        if (response.isSuccessful()) {
            if (res.getResultCode() == 0) {
                if (res.getData().getProgress().equals("-1")) {
                    return "-1";
                } else if (res.getData().getProgress().equals("1.0")) {
                    //完成
                    return "1";
                } else {
                    //继续刷课
                    return res.getData().getVideoProgress();
                }
            }
        }
        if (res.getResultCode() == 1001) {
            return "1001";
        }
        if (res.getResultCode() == 3003) {
            return "3003";
        }
        return "-1";
    }
}
