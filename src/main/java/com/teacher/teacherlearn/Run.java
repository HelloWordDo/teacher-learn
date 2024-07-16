package com.teacher.teacherlearn;

import com.teacher.teacherlearn.service.AiLearnService;
import com.teacher.teacherlearn.config.Account;
import com.teacher.teacherlearn.config.User;
import com.teacher.teacherlearn.utils.LocalCache;
import com.teacher.teacherlearn.config.FourteenFive;
import com.teacher.teacherlearn.service.FourteenFiveLearnService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class Run implements CommandLineRunner {

    LocalCache cache;
    Account account;
    AiLearnService aiLearnService;
    FourteenFiveLearnService fourteenFiveLearnService;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {

        List<User> aiUsers = (List<User>) cache.getValue("AIUSER");
        List<User> users = (List<User>) cache.getValue("USER");

        if (!aiUsers.isEmpty()) {
            log.info("开始执行AI学习");
            log.info(aiUsers.toString());
            for (User user : aiUsers) {
                aiLearnService.learn(user);
            }
        }

        if (!users.isEmpty()) {
            log.info("开始执行十四五学习");
            log.info(users.toString());
            fourteenFiveLearnService.Exam();
            FourteenFive fourteenFive = account.getFourteenFive();

            for (User u : users) {
                fourteenFiveLearnService.learn(u, fourteenFive);
            }
        }

    }
}
