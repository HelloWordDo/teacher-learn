package com.teacher.teacherlearn.curl_14_5.course.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LearnStruct implements Serializable {

    /**
     * 目录
     */
    private String segId;

    /**
     * 目录
     */
    private String itemId;
    private String topName;

    /**
     * 课程
     */
    private List<Course> courses;

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Course implements Serializable {
        /**
         * sheet页具体每个课程
         */
        private String courseId;
        private String itemName;
        private String courseName;

        /**
         * 具体每个视频
         */
        private List<Video> videos;

    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Video implements Serializable {
        /**
         * 具体每个视频
         */
        private String videoId;
        private String name4;
    }

}