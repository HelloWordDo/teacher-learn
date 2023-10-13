package com.teacher.teacherlearn.curl_14_5;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CourseResp {

    private String success;
    private Integer resultCode;
    private List<Data> data;

    @Getter
    @Setter
    @ToString
    public static class Data {
        private String videoId;
        private String name;
    }
}
