package com.test.kimhun_board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class KimhunBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(KimhunBoardApplication.class, args);
    }

}
