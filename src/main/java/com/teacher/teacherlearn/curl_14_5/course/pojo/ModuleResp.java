package com.teacher.teacherlearn.curl_14_5.course.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class ModuleResp implements Serializable {

    private String success;
    private Integer resultCode;
    private Module data;

    @Getter
    @Setter
    @ToString
    public static class Module implements Serializable {
        private List<Detail> moduleList;

        @Getter
        @Setter
        @ToString
        public static class Detail implements Serializable {
            private Integer courseCount;
            private String id;
            private String name;
        }
    }
}
