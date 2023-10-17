package com.teacher.teacherlearn.curl_14_5.course.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LearnResp {

    private String success;
    private Integer resultCode;
    private Progress data;

    @Getter
    @Setter
    @ToString
    public static class Progress {
        private Integer validProgress;
        private String progress;
        private String videoProgress;
    }
}
