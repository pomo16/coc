package com.pomo.coc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CocApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocApplication.class, args);
    }

}
