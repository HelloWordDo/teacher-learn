package com.teacher.teacherlearn.curl_14_5.controller;

import com.teacher.teacherlearn.curl_14_5.common.LocalCache;
import com.teacher.teacherlearn.curl_14_5.config.Account;
import com.teacher.teacherlearn.curl_14_5.config.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    Account account;
    @Autowired
    LocalCache localCache;

    @PostConstruct
    public List<User> postConstruct() {
        List<User> users = account.getUser().stream().filter(user -> !user.isFinish()).collect(Collectors.toList());
        localCache.put("USER", users);
        return users;
    }

    @PostMapping("/add")
    public List<User> addUser(User user) {
        List<User> users = (List<User>) localCache.getValue("USER");
        users.add(user);
        localCache.put("USER", users);
        return users;
    }
}
