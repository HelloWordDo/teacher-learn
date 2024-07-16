package com.teacher.teacherlearn.fourteen_five.course.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VideoResp implements Serializable {

    private String success;
    private Integer resultCode;
    private List<Data> data;

    @Getter
    @Setter
    @ToString
    public static class Data implements Serializable {
        private String videoId;
        private String name;
        private String duration;
    }
}
