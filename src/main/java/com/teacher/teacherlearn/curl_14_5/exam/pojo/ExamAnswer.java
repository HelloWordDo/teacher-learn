package com.teacher.teacherlearn.curl_14_5.exam.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExamAnswer {
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
            private String name;
            private String memPaperId;
            private List<Assess> assessList;

            @Getter
            @Setter
            public static class Assess {

                String id;
                String types;
                List<Question> questionList;

                @Getter
                @Setter
                public static class Question {
                    String id;
                    String questionsId;
                    List<Answer> answers;

                    @Getter
                    @Setter
                    public static class Answer {
                        String id;
                        String questionsId;
                        String isAnswer;
                    }
                }
            }
        }
    }
}
