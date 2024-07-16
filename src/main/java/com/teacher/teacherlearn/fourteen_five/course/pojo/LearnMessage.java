package com.teacher.teacherlearn.fourteen_five.course.pojo;

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
