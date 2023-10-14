package com.teacher.teacherlearn.curl_14_5.pojo;

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
    }
}
