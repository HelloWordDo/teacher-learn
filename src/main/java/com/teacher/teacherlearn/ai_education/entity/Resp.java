package com.teacher.teacherlearn.ai_education.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Resp<DATA> implements Serializable {

    String success;
    Integer resultCode;
    DATA data;
}
