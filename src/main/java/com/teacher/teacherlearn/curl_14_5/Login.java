package com.teacher.teacherlearn.curl_14_5;

import com.teacher.teacherlearn.curl_14_5.common.SunData;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.RequestBody;

import java.io.IOException;

@Slf4j
public class Login {

    public static void main(String[] args) throws IOException {

        Login login = new Login();
        log.info("token：{}", login.login(SunData.userName, SunData.passWord, SunData.platformId, SunData.service));
    }

    public String login(String userName, String passWord, String platformId, String service) throws IOException {

        log.info("重新开始登录");
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType,
                "username=" + userName + "&password=" + passWord + "&platformId=" + platformId + "&service=" + service);
        Request request = new Request.Builder()
                .url("https://www.ttcdw.cn/p/uc/userLogin?type=0&pageType=login&service=" + service)
                .method("POST", body)
                .addHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        log.info("登录请求返回：{}", responseBody);
        Headers responseHeaders = response.headers();
        String token = "";

        for (int i = 0; i < responseHeaders.size(); i++) {
            if (responseHeaders.value(i).contains("u-token=")) {
                log.info("登录请求获取到Token：{}", responseHeaders.value(i));
                token = responseHeaders.value(i).split("=")[1];
            }
        }
        return token;
    }
}
