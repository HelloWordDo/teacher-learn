package com.teacher.teacherlearn.curl_14_5;

import com.teacher.teacherlearn.curl_14_5.common.LocalCache;
import com.teacher.teacherlearn.curl_14_5.config.Account;
import com.teacher.teacherlearn.curl_14_5.config.FourteenFive;
import com.teacher.teacherlearn.curl_14_5.config.User;
import com.teacher.teacherlearn.curl_14_5.service.LearnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class Run14_5 implements CommandLineRunner {

    @Autowired
    LocalCache cache;
    @Autowired
    Account account;
    @Autowired
    LearnService learnService;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {

        log.info("开始执行");
        learnService.Exam();
        FourteenFive fourteenFive = account.getFourteenFive();
        List<User> users = (List<User>) cache.getValue("USER");

        for (User u : users) {
            learnService.learn(u, fourteenFive);
        }
    }
}
