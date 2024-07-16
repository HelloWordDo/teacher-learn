package com.teacher.teacherlearn.ai_education.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Catalog {

    /**
     * item : {"id":"871521859794501632","segmentId":"869086882352979968","type":"11","assessNumTimes":16,"ratio":-1,"chooseNumTimes":39693,"title":"模块与课程","convertNum":1,"convertHour":1,"breakRule":null,"sort":1,"fromItemId":"875937001728024576","courseType":0,"hourConfig":0,"cumulativeHour":1,"fixedHour":null,"autoReview":1,"wordsNum":0,"fileNum":0,"creditRule":0,"centralizationExam":0,"examFaceState":null,"checkSubmit":0,"checkAssessment":1,"progressPercentage":80,"totalPeriod":null,"studentSocreFlag":null,"scorePublishType":null,"levelScoreCOnfigs":null,"levelScoreConfigsStr":null,"creditScore":null,"publishAdminsOnlyFlag":null,"remark":null,"levelSocreFlag":null}
     * courseList : {"total":"7","list":[{"id":"875937105465745408","finish":null,"courseId":"869089897528311808","courseName":"跨越人工智能教育应用的认知外包陷阱","durationSecond":"2456","originalId":"846871266508443648","thirdCourse":0,"totalStudyProgress":"0","period":"1.0","courseProgress":"0","itemExamId":null,"examId":null,"examName":null,"examProgress":"0","state":0,"count":"58527","examState":null,"imgUrl":"group1/M00/1B/DE/CgoAGGYrAO2AOHgPAAHLzWXOk_c779.jpg","itemId":null,"periodGeted":null,"itemIdAndItemExamId":"nullnull"},{"id":"875937105465745409","finish":null,"courseId":"869089897549283328","courseName":"基础教育大模型及其应用","durationSecond":"1614","originalId":"846871324644388864","thirdCourse":0,"totalStudyProgress":"0","period":"1.0","courseProgress":"0","itemExamId":null,"examId":null,"examName":null,"examProgress":"0","state":0,"count":"56310","examState":null,"imgUrl":"group1/M00/1B/DE/CgoAGGYq_3mASE2VABmUdckOkoY232.png","itemId":null,"periodGeted":null,"itemIdAndItemExamId":"nullnull"},{"id":"875937105465745410","finish":null,"courseId":"641866071297867776","courseName":"智能时代教育转型与变革","durationSecond":"4672","originalId":"-1","thirdCourse":0,"totalStudyProgress":"0","period":"3.0","courseProgress":"0","itemExamId":null,"examId":null,"examName":null,"examProgress":"0","state":0,"count":"55383","examState":null,"imgUrl":"group1/M00/09/94/CgoAGGNY8meATErrAAA0qfX1f3Y916.jpg","itemId":null,"periodGeted":null,"itemIdAndItemExamId":"nullnull"},{"id":"875937105465745411","finish":null,"courseId":"641866753375584256","courseName":"智能时代教师专业发展新路径探索及实践案例","durationSecond":"5369","originalId":"-1","thirdCourse":0,"totalStudyProgress":"0","period":"3.0","courseProgress":"0","itemExamId":null,"examId":null,"examName":null,"examProgress":"0","state":0,"count":"51420","examState":null,"imgUrl":"group1/M00/09/94/CgoAGGNY-8WAOO19AAAokpxPdh4183.jpg","itemId":null,"periodGeted":null,"itemIdAndItemExamId":"nullnull"},{"id":"875937105465745412","finish":null,"courseId":"638515222176784384","courseName":"数据驱动下的教育评价改革与创新","durationSecond":"4000","originalId":"-1","thirdCourse":0,"totalStudyProgress":"0","period":"2.0","courseProgress":"0","itemExamId":null,"examId":null,"examName":null,"examProgress":"0","state":0,"count":"46371","examState":null,"imgUrl":"group1/M00/08/4C/CgoAGGM1CPmAHMooAA2wYkytPz0253.png","itemId":null,"periodGeted":null,"itemIdAndItemExamId":"nullnull"},{"id":"875937105465745413","finish":null,"courseId":"871645656189726720","courseName":"教育数字化转型及智慧校园建设培训","durationSecond":"7192","originalId":"871537836287029248","thirdCourse":0,"totalStudyProgress":"0","period":"3.0","courseProgress":"0","itemExamId":null,"examId":null,"examName":null,"examProgress":"0","state":0,"count":"46076","examState":null,"imgUrl":"group1/M00/1D/D5/CgoAGGaEw2GAD73qAAA350i8-Tg070.jpg","itemId":null,"periodGeted":null,"itemIdAndItemExamId":"nullnull"},{"id":"875937105465745414","finish":null,"courseId":"871645656219086848","courseName":"积极天性的力量\u2014\u2014在AI时代探索数字健康和文化创造力","durationSecond":"2352","originalId":"871537749133586432","thirdCourse":0,"totalStudyProgress":"0","period":"1.0","courseProgress":"0","itemExamId":null,"examId":null,"examName":null,"examProgress":"0","state":0,"count":"42747","examState":null,"imgUrl":"group1/M00/1D/D5/CgoAGGaEw2GANG9NAAA1mPaenq4169.jpg","itemId":null,"periodGeted":null,"itemIdAndItemExamId":"nullnull"}],"pageNum":1,"pageSize":10,"size":7,"startRow":1,"endRow":7,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1}
     */

    private Item item;
    private CourseListBean courseList;

    @NoArgsConstructor
    @Data
    public static class CourseListBean {
        /**
         * total : 7
         * list : [{"id":"875937105465745408","finish":null,"courseId":"869089897528311808","courseName":"跨越人工智能教育应用的认知外包陷阱","durationSecond":"2456","originalId":"846871266508443648","thirdCourse":0,"totalStudyProgress":"0","period":"1.0","courseProgress":"0","itemExamId":null,"examId":null,"examName":null,"examProgress":"0","state":0,"count":"58527","examState":null,"imgUrl":"group1/M00/1B/DE/CgoAGGYrAO2AOHgPAAHLzWXOk_c779.jpg","itemId":null,"periodGeted":null,"itemIdAndItemExamId":"nullnull"},{"id":"875937105465745409","finish":null,"courseId":"869089897549283328","courseName":"基础教育大模型及其应用","durationSecond":"1614","originalId":"846871324644388864","thirdCourse":0,"totalStudyProgress":"0","period":"1.0","courseProgress":"0","itemExamId":null,"examId":null,"examName":null,"examProgress":"0","state":0,"count":"56310","examState":null,"imgUrl":"group1/M00/1B/DE/CgoAGGYq_3mASE2VABmUdckOkoY232.png","itemId":null,"periodGeted":null,"itemIdAndItemExamId":"nullnull"},{"id":"875937105465745410","finish":null,"courseId":"641866071297867776","courseName":"智能时代教育转型与变革","durationSecond":"4672","originalId":"-1","thirdCourse":0,"totalStudyProgress":"0","period":"3.0","courseProgress":"0","itemExamId":null,"examId":null,"examName":null,"examProgress":"0","state":0,"count":"55383","examState":null,"imgUrl":"group1/M00/09/94/CgoAGGNY8meATErrAAA0qfX1f3Y916.jpg","itemId":null,"periodGeted":null,"itemIdAndItemExamId":"nullnull"},{"id":"875937105465745411","finish":null,"courseId":"641866753375584256","courseName":"智能时代教师专业发展新路径探索及实践案例","durationSecond":"5369","originalId":"-1","thirdCourse":0,"totalStudyProgress":"0","period":"3.0","courseProgress":"0","itemExamId":null,"examId":null,"examName":null,"examProgress":"0","state":0,"count":"51420","examState":null,"imgUrl":"group1/M00/09/94/CgoAGGNY-8WAOO19AAAokpxPdh4183.jpg","itemId":null,"periodGeted":null,"itemIdAndItemExamId":"nullnull"},{"id":"875937105465745412","finish":null,"courseId":"638515222176784384","courseName":"数据驱动下的教育评价改革与创新","durationSecond":"4000","originalId":"-1","thirdCourse":0,"totalStudyProgress":"0","period":"2.0","courseProgress":"0","itemExamId":null,"examId":null,"examName":null,"examProgress":"0","state":0,"count":"46371","examState":null,"imgUrl":"group1/M00/08/4C/CgoAGGM1CPmAHMooAA2wYkytPz0253.png","itemId":null,"periodGeted":null,"itemIdAndItemExamId":"nullnull"},{"id":"875937105465745413","finish":null,"courseId":"871645656189726720","courseName":"教育数字化转型及智慧校园建设培训","durationSecond":"7192","originalId":"871537836287029248","thirdCourse":0,"totalStudyProgress":"0","period":"3.0","courseProgress":"0","itemExamId":null,"examId":null,"examName":null,"examProgress":"0","state":0,"count":"46076","examState":null,"imgUrl":"group1/M00/1D/D5/CgoAGGaEw2GAD73qAAA350i8-Tg070.jpg","itemId":null,"periodGeted":null,"itemIdAndItemExamId":"nullnull"},{"id":"875937105465745414","finish":null,"courseId":"871645656219086848","courseName":"积极天性的力量\u2014\u2014在AI时代探索数字健康和文化创造力","durationSecond":"2352","originalId":"871537749133586432","thirdCourse":0,"totalStudyProgress":"0","period":"1.0","courseProgress":"0","itemExamId":null,"examId":null,"examName":null,"examProgress":"0","state":0,"count":"42747","examState":null,"imgUrl":"group1/M00/1D/D5/CgoAGGaEw2GANG9NAAA1mPaenq4169.jpg","itemId":null,"periodGeted":null,"itemIdAndItemExamId":"nullnull"}]
         * pageNum : 1
         * pageSize : 10
         * size : 7
         * startRow : 1
         * endRow : 7
         * pages : 1
         * prePage : 0
         * nextPage : 0
         * isFirstPage : true
         * isLastPage : true
         * hasPreviousPage : false
         * hasNextPage : false
         * navigatePages : 8
         * navigatepageNums : [1]
         * navigateFirstPage : 1
         * navigateLastPage : 1
         */

        private String total;
        private int pageNum;
        private int pageSize;
        private int size;
        private int startRow;
        private int endRow;
        private int pages;
        private int prePage;
        private int nextPage;
        private boolean isFirstPage;
        private boolean isLastPage;
        private boolean hasPreviousPage;
        private boolean hasNextPage;
        private int navigatePages;
        private int navigateFirstPage;
        private int navigateLastPage;
        private List<Course> list;
        private List<Integer> navigatepageNums;
    }
}
