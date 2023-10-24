package com.teacher.teacherlearn.curl_14_5.config;

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

    private List<User> user;

    private FourteenFive fourteenFive;
}
