package com.teacher.teacherlearn.curl_14_5.jwt;

import com.alibaba.fastjson.JSONObject;

import java.util.Base64;

public class Jwt {

    public String decodeJwt(String uToken) {
        String[] jwtParts = uToken.split("\\."); // 以点号分隔JWT的三个部分
        String headerJson = new String(Base64.getDecoder().decode(jwtParts[0])); // 解码头部信息
        System.out.println("头部信息：" + headerJson);
        String payloadJson = new String(Base64.getDecoder().decode(jwtParts[1])); // 解码载荷信息
        System.out.println("载荷信息：" + payloadJson);
        JSONObject jsonObject = JSONObject.parseObject(payloadJson);
        String exp = jsonObject.getString("exp");
        return exp;
    }

    public static void main(String[] args) {
        Jwt j = new Jwt();
        j.decodeJwt("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhOTVmNTA3MC1kNWI1LTRmYmMtOWUxYi1mMTk3ZWZhZGM3NWYiLCJpYXQiOjE2OTc0NDkzOTYsInN1YiI6IjczNjcxODQyOTUxMzYwMTAyNCIsImlzcyI6Imd1b3JlbnQiLCJhdHRlc3RTdGF0ZSI6MCwic3JjIjoid2ViIiwiYWN0aXZlU3RhdGUiOjEsIm1vYmlsZSI6IjE4MzAxMTkyMTcwIiwicGxhdGZvcm1JZCI6IjEzMTQ1ODU0OTgzMzExIiwiYWNjb3VudCI6IjE4MzAxMTkyMTcwIiwiZXhwIjoxNjk3NDg1Mzk2fQ.Je9NeqDuYyVPQIb8FswkoQsXJg-DYnEGazrZt3RQ43U");
    }
}
