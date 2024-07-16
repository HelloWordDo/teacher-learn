package com.teacher.teacherlearn.ai_education.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Item {
    /**
     * id : 871521859794501632
     * segmentId : 869086882352979968
     * type : 11
     * assessNumTimes : 16
     * ratio : -1
     * chooseNumTimes : 39693
     * title : 模块与课程
     * convertNum : 1
     * convertHour : 1
     * breakRule : null
     * sort : 1
     * fromItemId : 875937001728024576
     * courseType : 0
     * hourConfig : 0
     * cumulativeHour : 1
     * fixedHour : null
     * autoReview : 1
     * wordsNum : 0
     * fileNum : 0
     * creditRule : 0
     * centralizationExam : 0
     * examFaceState : null
     * checkSubmit : 0
     * checkAssessment : 1
     * progressPercentage : 80
     * totalPeriod : null
     * studentSocreFlag : null
     * scorePublishType : null
     * levelScoreCOnfigs : null
     * levelScoreConfigsStr : null
     * creditScore : null
     * publishAdminsOnlyFlag : null
     * remark : null
     * levelSocreFlag : null
     */

    private String id;
    private String segmentId;
    private String type;
    private int assessNumTimes;
    private int ratio;
    private int chooseNumTimes;
    private String title;
    private int convertNum;
    private int convertHour;
    private Object breakRule;
    private int sort;
    private String fromItemId;
    private int courseType;
    private int hourConfig;
    private int cumulativeHour;
    private Object fixedHour;
    private int autoReview;
    private int wordsNum;
    private int fileNum;
    private int creditRule;
    private int centralizationExam;
    private Object examFaceState;
    private int checkSubmit;
    private int checkAssessment;
    private int progressPercentage;
    private Object totalPeriod;
    private Object studentSocreFlag;
    private Object scorePublishType;
    private Object levelScoreCOnfigs;
    private Object levelScoreConfigsStr;
    private Object creditScore;
    private Object publishAdminsOnlyFlag;
    private Object remark;
    private Object levelSocreFlag;
}
