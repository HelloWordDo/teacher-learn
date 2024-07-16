package com.teacher.teacherlearn.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private String name;
    private String userName;
    private String passWord;
    private boolean finish;
    private Boolean aiFinish;
}
