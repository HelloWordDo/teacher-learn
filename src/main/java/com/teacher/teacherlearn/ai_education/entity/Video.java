package com.teacher.teacherlearn.ai_education.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Video {

    /**
     * id : 846876044612120577
     * parentId : 0
     * tag : 余胜泉--跨越人工智能教育应用的认知外包陷阱.mp4
     * name : 跨越人工智能教育应用的认知外包陷阱
     * durationText : 00:40:56
     * duration : 2456
     * freetime : 0
     * opencourseFreetime : 0
     * type : 3
     * studyProgress : 0
     * videoId : 846876044612120576
     * progressId : null
     * ccVideoId : null
     * ccSiteId : null
     * ccKey : null
     * progress : 0.0
     * coursewareType : 0
     * fileId : null
     * fileUrl : null
     * transState : 2
     * videoType : 0
     * playProgress : null
     * token : dG9IU3FYaEZLNG1TM1paUUhPcmpNZUVyYmxXQWE0N0VvWDdBakpLY24ybE9XN2I5WTk2c2tvOGNUOVhEZERabnFiJTJGSGxrSkcwR0pZdEFZcXZndzNYUSUzRCUzRA==
     * urls : [{"id":"846877144937443328","hwVedioId":"76dd89ae1a64aebcd7a8f3df878821b4","playType":"HLS","quality":"SD","metaData":"{audio_bit_rate=0, audio_channels=0, frame_rate=30, sample=0, quality=SD, duration=2456, codec=H.264, bit_rate=118, play_type=0, video_size=75728896, width=854, hight=480, height=480}","encrypted":1,"url":"https://vod.grtcloud.net/asset/76dd89ae1a64aebcd7a8f3df878821b4/play_video/bf347818e09bbb8ce31278dfe86cd524.m3u8","imageUrl":"https://vod.grtcloud.net/asset/76dd89ae1a64aebcd7a8f3df878821b4/snapshot/time/16.jpg"}]
     */

    private String id;
    private String parentId;
    private String tag;
    private String name;
    private String durationText;
    private String duration;
    private int freetime;
    private int opencourseFreetime;
    private String type;
    private int studyProgress;
    private String videoId;
    private Object progressId;
    private Object ccVideoId;
    private Object ccSiteId;
    private Object ccKey;
    private double progress;
    private int coursewareType;
    private Object fileId;
    private Object fileUrl;
    private String transState;
    private int videoType;
    private Object playProgress;
    private String token;
    private List<UrlsBean> urls;

    @NoArgsConstructor
    @Data
    public static class UrlsBean {
        /**
         * id : 846877144937443328
         * hwVedioId : 76dd89ae1a64aebcd7a8f3df878821b4
         * playType : HLS
         * quality : SD
         * metaData : {audio_bit_rate=0, audio_channels=0, frame_rate=30, sample=0, quality=SD, duration=2456, codec=H.264, bit_rate=118, play_type=0, video_size=75728896, width=854, hight=480, height=480}
         * encrypted : 1
         * url : https://vod.grtcloud.net/asset/76dd89ae1a64aebcd7a8f3df878821b4/play_video/bf347818e09bbb8ce31278dfe86cd524.m3u8
         * imageUrl : https://vod.grtcloud.net/asset/76dd89ae1a64aebcd7a8f3df878821b4/snapshot/time/16.jpg
         */

        private String id;
        private String hwVedioId;
        private String playType;
        private String quality;
        private String metaData;
        private int encrypted;
        private String url;
        private String imageUrl;
    }
}
