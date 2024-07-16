package com.teacher.teacherlearn.fourteen_five.exam.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamInfo {

    private String success;
    private Integer resultCode;
    private Data data;

    @Getter
    @Setter
    public static class Data {

        private Exam exam;

        @Getter
        @Setter
        public static class Exam {
            private String id;
            private String examResourceId;
            private String name;
            private String realExamPaperId;
        }

    }
}
