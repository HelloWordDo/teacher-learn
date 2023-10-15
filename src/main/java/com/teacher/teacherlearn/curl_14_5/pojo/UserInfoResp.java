package com.teacher.teacherlearn.curl_14_5.pojo;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfoResp implements Serializable {

    private String success;
    private Integer resultCode;
    private Project data;

    @Setter
    @Getter
    @ToString
    public static class Project implements Serializable {
        ProjectClass projectClass;

        @Setter
        @Getter
        @ToString
        public static class ProjectClass implements Serializable {

            String id;
            String orgId;
            String projectId;
        }
    }

}
