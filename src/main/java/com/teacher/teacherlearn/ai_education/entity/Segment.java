package com.teacher.teacherlearn.ai_education.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Segment {

    /**
     * id : 869086882352979968
     * name : 实训课程（无测验）
     * startTime : 1720108800000
     * createTime : 1719390002000
     * endTime : 1732982399000
     * templateId : 869084482409979904
     * templateSesgemtId : 874779219500290048
     * type : 0
     * sort : 0
     * userScore : 0.0
     * userHour : 0.0
     * days : 139
     * progressState : 1
     * totalRatio : 0
     * totalHour : 0.0
     * itemList : null
     */

    private String id;
    private String name;
    private long startTime;
    private long createTime;
    private long endTime;
    private String templateId;
    private String templateSesgemtId;
    private String type;
    private int sort;
    private String userScore;
    private String userHour;
    private int days;
    private String progressState;
    private int totalRatio;
    private String totalHour;
    private List<Item> itemList;
}
