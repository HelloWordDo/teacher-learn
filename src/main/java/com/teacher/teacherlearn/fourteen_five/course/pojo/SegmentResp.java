package com.teacher.teacherlearn.fourteen_five.course.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class SegmentResp implements Serializable {

    private String success;
    private Integer resultCode;
    private Segment data;

    @Setter
    @Getter
    public static class Segment implements Serializable {
        List<Seg> segments;

        @Setter
        @Getter
        public static class Seg implements Serializable {
            private String id;
            private String name;
            private String totalHour;
            private List<Item> itemList;

            @Setter
            @Getter
            public static class Item implements Serializable {
                private String id;
            }
        }
    }

}
