package com.teacher.teacherlearn.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.teacher.teacherlearn.ai_education.entity.Resp;
import com.teacher.teacherlearn.ai_education.entity.Watch;
import com.teacher.teacherlearn.config.Account;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor
public class WatchVideo {

    Account account;

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
        builder.add("eventType", "study");
        FormBody body = builder.build();

        Request request = new Request.Builder()
                .url("https://www.ttcdw.cn/p/course/services/member/study/progress?orgId=" + account.getOrgId())
                .method("POST", body)
                .addHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("cookie", "u-token=" + uToken)
                .addHeader("encryptionvalue", courseId)
                .addHeader("isencryption", "true")
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        log.info("刷课请求返回：{}", responseBody);
        Resp<Watch> res = JSONObject.parseObject(responseBody, new TypeReference<Resp<Watch>>() {
        }.getType());
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
