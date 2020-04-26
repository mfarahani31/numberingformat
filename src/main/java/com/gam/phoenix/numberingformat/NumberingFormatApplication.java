package com.gam.phoenix.numberingformat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 **/

@SpringBootApplication
@ComponentScan({"com.gam.phoenix.spring.commons.rest", "com.gam.phoenix.numberingformat"})
public class NumberingFormatApplication {
    public static void main(String[] args) {
        SpringApplication.run(NumberingFormatApplication.class, args);
    }
}
