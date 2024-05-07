package com.study.study01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Study01Application {

    public static void main(String[] args) {
        SpringApplication.run(Study01Application.class, args);
    }

}
