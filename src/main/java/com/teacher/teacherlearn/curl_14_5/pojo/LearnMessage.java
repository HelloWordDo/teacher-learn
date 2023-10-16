package com.teacher.teacherlearn.curl_14_5.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LearnMessage implements Serializable {

    /**
     * 目录
     */
    private String segId;

    /**
     * 目录
     */
    private String itemId;
    private String segName;
    private String totalHour;
}
