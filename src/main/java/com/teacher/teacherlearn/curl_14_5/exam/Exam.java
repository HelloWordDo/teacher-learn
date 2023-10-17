package com.teacher.teacherlearn.curl_14_5.exam;

import com.alibaba.fastjson.JSONObject;
import com.teacher.teacherlearn.curl_14_5.exam.pojo.ExamAnswer;
import com.teacher.teacherlearn.curl_14_5.exam.pojo.ExamInfo;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Exam {

    //Setp1:获取excamInfo
    public ExamInfo.Data.Exam getExamInfo(String uToken, String projectId, String classId, String itemId, String itemExamId) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://www.ttcdw.cn/p/uc/services/member/project/exams/v2/info?projectId=" + projectId +
                        "&classId=" + classId + "&itemId=" + itemId + "&itemExamId=" + itemExamId)
                .addHeader("cookie", "u-token=" + uToken)
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        log.info("getExamInfo返回：{}", responseBody);
        if (response.isSuccessful()) {

            ExamInfo res = JSONObject.parseObject(responseBody, ExamInfo.class);
            return res.getData().getExam();
        }
        return null;
    }


    //Step2:开始测验
    public ExamAnswer.Data.Exam toExam(String uToken, String itemId, String examResourceId, String segId, String projectId) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, "itemId=" + itemId +
                "&sourceExamPaperId=" + examResourceId + "&segmentId=" + segId + "&projectId=" + projectId);
        Request request = new Request.Builder()
                .url("https://www.ttcdw.cn/p/uExam/services/member/exams/v2/toExamination")
                .method("POST", body)
                .addHeader("accept", "application/json, text/plain, */*")
                .addHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("cookie", "u-token=" + uToken)
//                .addHeader("referer", "https://www.ttcdw.cn/p/uExam/goExam/" + itemId + "/" + examResourceId + "?projectId=" + projectId + "&segmentId=" + segId)
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        log.info("开始测验按钮返回：{}", responseBody);
        if (response.isSuccessful()) {
            ExamAnswer res = JSONObject.parseObject(responseBody, ExamAnswer.class);
            if (res.getData() == null) {
                return null;
            }
            return res.getData().getExam();
        }
        return null;
    }

    //Step3：点击答题
    public void clickAnswer(String uToken, String memExamId, String assessId, String questionId, List<String> answerIds) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, "questionId=" + questionId + "&assessId=" + assessId +
                "&memExamId=" + memExamId + "&answer=" + answerIds);
        Request request = new Request.Builder()
                .url("https://www.ttcdw.cn/p/uExam/services/member/app/v1/exam/questions")
                .method("POST", body)
                .addHeader("accept", "application/json, text/plain, */*")
                .addHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("cookie", "u-token=" + uToken)
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        log.info("clickAnswer答题返回：{}", responseBody);
    }


    public void submit(String uToken, String itemId, String memPaperId, String examPaperId, String projectId, String segmentId) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, "itemId=" + itemId + "&memPaperId=" + memPaperId +
                "&examPaperId=" + examPaperId + "&projectId=" + projectId + "&segmentId=" + segmentId);
        Request request = new Request.Builder()
                .url("https://www.ttcdw.cn/p/uExam/services/member/exams/v2/submit")
                .method("POST", body)
                .addHeader("accept", "application/json, text/plain, */*")
                .addHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("cookie", "u-token=" + uToken)
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        log.info("提交答题返回：{}", responseBody);
    }


    public void excamChain(String uToken, String projectId, String classId, String itemId, String itemExamId, String segId) throws IOException, InterruptedException {
        ExamInfo.Data.Exam exam = getExamInfo(uToken, projectId, classId, itemId, itemExamId);
        if (exam == null) {
            log.info("没有试卷跳过");
            return;
        }
        ExamAnswer.Data.Exam answerExam = toExam(uToken, itemId, exam.getExamResourceId(), segId, projectId);
        String examPaperId = answerExam.getId();
        String memExamId = answerExam.getMemPaperId();
        log.info("开始答题：{}", answerExam.getName());
        List<ExamAnswer.Data.Exam.Assess> assess = answerExam.getAssessList();
        for (ExamAnswer.Data.Exam.Assess a : assess) {
            String assessId = a.getId();
            List<ExamAnswer.Data.Exam.Assess.Question> questions = a.getQuestionList();

            List<String> answerIds = new ArrayList<>();
            for (ExamAnswer.Data.Exam.Assess.Question q : questions) {
                String questionId = q.getQuestionsId();
                List<ExamAnswer.Data.Exam.Assess.Question.Answer> answers = q.getAnswers();
                for (ExamAnswer.Data.Exam.Assess.Question.Answer an : answers) {
                    if (an.getIsAnswer().equals("0")) {
                        answerIds.add(an.getId());
                    }
                }
                log.info("选择答案");
                clickAnswer(uToken, memExamId, assessId, questionId, answerIds);
                Thread.sleep(1000);
            }
        }
        log.info("提交答案");
        submit(uToken, itemId, memExamId, examPaperId, projectId, segId);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Exam e = new Exam();
        String uToken = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwOGM4ODNhNC1iZGRkLTQ0ZmMtYjdkMi1lMGI1ZjAwZGQ0ZDgiLCJpYXQiOjE2OTc0MzYyMzMsInN1YiI6IjczNjcxODQyOTg4Njg5NDA4MCIsImlzcyI6Imd1b3JlbnQiLCJhdHRlc3RTdGF0ZSI6MCwic3JjIjoid2ViIiwiYWN0aXZlU3RhdGUiOjEsIm1vYmlsZSI6IjE4MjMxNzM4NTI5IiwicGxhdGZvcm1JZCI6IjEzMTQ1ODU0OTgzMzExIiwiYWNjb3VudCI6IjE4MjMxNzM4NTI5IiwiZXhwIjoxNjk3NDcyMjMzfQ.8JqbwDLKpuVn_39aNK24hwZXYs73eh3wQYMI5X-Tgow";
        //“四史”学习教育及育人方式的创新
        e.excamChain(uToken, "622297127378276352", "638519743941120000", "642880803644047360", "674114995023020032", "622297284333326336");


    }
}
