package com.teacher.teacherlearn.curl_14_5.course.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CourseResp implements Serializable {

    private String success;
    private Integer resultCode;
    private CourseData data;

    @Setter
    @Getter
    public static class CourseData implements Serializable {
        Cuorses courseList;

        @Setter
        @Getter
        public static class Cuorses implements Serializable {

            List<Detail> list;

            @Setter
            @Getter
            public static class Detail implements Serializable {
                String courseId;
                String courseName;
                String courseProgress;
                String examId;
                String examProgress;
                String period;
                String itemExamId;
            }
        }
    }

}
