package com.teacher.teacherlearn.utils;

import com.alibaba.fastjson2.JSON;
import com.teacher.teacherlearn.ai_education.entity.Resp;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;

@Slf4j
public class OkHttpRequest {

    private static final OkHttpClient client = new OkHttpClient();

    public static <T> Resp<T> sendGetRequest(String url, String cookie, Type responseType) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .addHeader("accept", "*/*")
                .addHeader("accept-language", "zh-CN,zh;q=0.9")
                .addHeader("cache-control", "no-cache")
                .addHeader("cookie", cookie)
                .addHeader("sec-ch-ua", "\"Google Chrome\";v=\"125\", \"Chromium\";v=\"125\", \"Not.A/Brand\";v=\"24\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"macOS\"")
                .addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            log.info("返回：{}", responseBody);
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return JSON.parseObject(responseBody, responseType);
        }
    }
}
