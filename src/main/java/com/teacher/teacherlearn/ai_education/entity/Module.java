package com.teacher.teacherlearn.ai_education.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Module {

    private List<ModuleListBean> moduleList;

    @NoArgsConstructor
    @Data
    public static class ModuleListBean {
        /**
         * id : 875937105444773888
         * name : 理论学习
         * courseCount : 7
         */

        private String id;
        private String name;
        private int courseCount;
    }
}
