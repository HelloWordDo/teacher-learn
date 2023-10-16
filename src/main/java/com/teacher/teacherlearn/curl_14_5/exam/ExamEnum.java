package com.teacher.teacherlearn.curl_14_5.exam;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExamEnum {

    SINGLE("0", "单选题"),
    MULTIPLE("1", "多选题"),
    TRUEORFALSE("2", "是非题");

    String types;
    String name;
}
