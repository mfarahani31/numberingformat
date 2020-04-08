package com.gam.phoenix.numberingformat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NumberingFormatApplication {


    public static void main(String[] args) {
        SpringApplication.run(NumberingFormatApplication.class, args);
    }

}
