package com.teacher.teacherlearn.curl_14_5;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

@Slf4j
public class Login {

    private String userName = "18231738529";
    private String passWord = "Wolove11997";
    private String service = "https://www.ttcdw.cn/p/uc/projectCenter/622297127378276352/index/638519743941120000?orgId=622293883621437440";
    private String platformId = "13145854983311";

    public String login() throws IOException {

        log.info("重新开始登录");
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, "username=" + userName + "&password=" + passWord + "&platformId=" + platformId + "&service=" + service);
        Request request = new Request.Builder()
                .url("https://www.ttcdw.cn/p/uc/userLogin?type=0&pageType=login&service=https%3A%2F%2Fwww.ttcdw.cn%2Fp%2Fuc%2FprojectCenter%2F622297127378276352%2Findex%2F638519743941120000%3ForgId%3D622293883621437440")
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

    public static void main(String[] args) throws IOException {

        Login login = new Login();
        log.info("token：{}", login.login());
    }
}
