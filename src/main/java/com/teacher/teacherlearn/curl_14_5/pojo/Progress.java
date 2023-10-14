package com.teacher.teacherlearn.curl_14_5.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Progress  implements Serializable {

    private Integer validProgress;
    private String progress;
    private String videoProgress;
}
