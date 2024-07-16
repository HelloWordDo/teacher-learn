package com.teacher.teacherlearn.controller;

import com.teacher.teacherlearn.utils.LocalCache;
import com.teacher.teacherlearn.config.Account;
import com.teacher.teacherlearn.config.User;
import com.teacher.teacherlearn.service.FourteenFiveLearnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
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
    @Autowired
    FourteenFiveLearnService fourteenFiveLearnService;

    @PostConstruct
    public void postConstruct() {
        List<User> users = account.getUser().stream().filter(user -> !user.isFinish()).collect(Collectors.toList());
        localCache.put("USER", users);
        List<User> aiUsers = account.getUser().stream()
                .filter(user -> user.getAiFinish() != null && user.getAiFinish().equals(Boolean.FALSE))
                .collect(Collectors.toList());
        localCache.put("AIUSER", aiUsers);
    }

    @PostMapping("/add")
    public List<User> addUser(@RequestBody User user) throws IOException, InterruptedException {
        List<User> users = (List<User>) localCache.getValue("USER");
        users.add(user);
        localCache.put("USER", users);
        fourteenFiveLearnService.learn(user, account.getFourteenFive());
        return users;
    }
}
