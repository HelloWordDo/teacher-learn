package com.teacher.teacherlearn.curl_14_5.exam.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@ToString
public class ExamRequest implements Comparable<ExamRequest> {

    /**
     * 默认延迟5分钟
     */
    private static final long DELAY = 1000L * 60 * 5;

    private String uToken;
    private String projectId;
    private String classId;
    private String itemId;
    private String itemExamId;
    private String segId;
    private long executeTime;

    public ExamRequest() {
        this.executeTime = System.currentTimeMillis() + DELAY;
    }

    public ExamRequest(long waitTime) {
        this.executeTime = System.currentTimeMillis() + waitTime * 1000L;
    }

    @Override
    public int compareTo(@NotNull ExamRequest o) {
        return (int) (this.getExecuteTime() - o.getExecuteTime());
    }
}
