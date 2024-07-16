package com.teacher.teacherlearn.config;

import com.teacher.teacherlearn.ai_education.entity.AiAccount;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties("account")
public class Account {

    private String orgId;

    private String platformId;

    private List<User> user;

    private FourteenFive fourteenFive;

    private AiAccount aiAccount;
}
