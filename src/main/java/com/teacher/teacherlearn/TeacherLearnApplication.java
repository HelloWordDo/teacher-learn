package com.teacher.teacherlearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TeacherLearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeacherLearnApplication.class, args);
	}

}
