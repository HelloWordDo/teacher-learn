package com.teacher.teacherlearn.curl_14_5.exam.pojo;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class ExamRequest implements Delayed {

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
    public long getDelay(@NotNull TimeUnit unit) {
        return unit.convert(this.executeTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(@NotNull Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }
}
