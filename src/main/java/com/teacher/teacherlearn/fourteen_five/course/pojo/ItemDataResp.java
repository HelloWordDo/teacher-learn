package com.teacher.teacherlearn.fourteen_five.course.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDataResp implements Serializable {

    private String success;
    private Integer resultCode;
    private ItemData data;

    @Setter
    @Getter
    public static class ItemData implements Serializable {
        private Item data;

        @Setter
        @Getter
        public static class Item implements Serializable {

            private List<Detail> itemList;

            @Setter
            @Getter
            public static class Detail implements Serializable {
                private String id;
            }
        }

    }
}
