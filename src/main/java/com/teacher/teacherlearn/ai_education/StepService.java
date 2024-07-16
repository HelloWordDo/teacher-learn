package com.teacher.teacherlearn.ai_education;

import com.alibaba.fastjson2.TypeReference;
import com.teacher.teacherlearn.ai_education.entity.*;
import com.teacher.teacherlearn.config.Account;
import com.teacher.teacherlearn.utils.OkHttpRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class StepService {

    Account account;
    static AiAccount aiAccount;

    @PostConstruct
    public void postConstruct() {
        aiAccount = account.getAiAccount();
    }

    //Step1.获取左侧 course
    public List<Segment> getSegmentList(String token) {
        String url = aiAccount.getUrl() + "/member/project/myClassroom/listSegment?pageSize=7&pageNum=1&projectId=" + aiAccount.getProjectId() + "&classId=" + aiAccount.getClassId();
        String cookie = "u-token=" + token;

        try {
            Resp<List<Segment>> response = OkHttpRequest.sendGetRequest(url, cookie, new TypeReference<Resp<List<Segment>>>() {
            }.getType());
            return response.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Step2 获取中间course模块数据
     * 模块与课程
     */
    public List<Item> getSegmentData(String token, String segmentId) {
        String url = aiAccount.getUrl() + "/member/project/myClassroom/segmentData/" + aiAccount.getProjectId() + "/segment/" + segmentId;
        String cookie = "u-token=" + token;

        try {
            Resp<SegmentData> response = OkHttpRequest.sendGetRequest(url, cookie, new TypeReference<Resp<SegmentData>>() {
            }.getType());
            return response.getData().getData().getItemList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Step3 获取课sheet大类
     * 理论学习等
     */
    public List<Module.ModuleListBean> getCourseModule(String token, String itemId) {
        String url = aiAccount.getUrl() + "/member/project/memberCourse/requiredCourseModule?itemId=" + itemId;
        String cookie = "u-token=" + token;

        try {
            Resp<Module> response = OkHttpRequest.sendGetRequest(url, cookie, new TypeReference<Resp<Module>>() {
            }.getType());
            return response.getData().getModuleList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Step4 获取课单list
     * 所有信息都有
     */
    public Catalog getCatalog(String token, String itemId, String segmentId, String moduleId) {
        String url = aiAccount.getUrl() + "/member/project/memberCourse/requiredCourseExam?pageSize=10&pageNum=1" +
                "&itemId=" + itemId + "&segmentId=" + segmentId + "&moduleId=" + moduleId;
        String cookie = "u-token=" + token;

        try {
            Resp<Catalog> response = OkHttpRequest.sendGetRequest(url, cookie, new TypeReference<Resp<Catalog>>() {
            }.getType());
            return response.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Step5 获取具体视频列表
     */
    public List<Video> getVideo(String token, String courseId, String segmentId, String itemId) {

        String url = aiAccount.getUrl() + "/course/public/course/lesson/" + courseId + "?" +
                "itemId=" + itemId +
                "&segId=" + segmentId +
                "&projectId=" + aiAccount.getProjectId() +
                "&orgId=" + account.getOrgId() +
                "&type=1&isContent=false" +
                "&courseId=" + courseId +
                "&id=" + courseId +
                "&sourceType=1" +
                "&_=" + System.currentTimeMillis() +
                "&orgId=" + account.getOrgId();

        String cookie = "u-token=" + token;

        try {
            Resp<List<Video>> response = OkHttpRequest.sendGetRequest(url, cookie, new TypeReference<Resp<List<Video>>>() {
            }.getType());
            return response.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
