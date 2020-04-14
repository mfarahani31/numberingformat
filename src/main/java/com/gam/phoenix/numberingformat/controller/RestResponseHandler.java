package com.gam.phoenix.numberingformat.controller;

import org.slf4j.MDC;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 **/

@EnableWebMvc
@ControllerAdvice(annotations = RestController.class)
public class RestResponseHandler {

    @ModelAttribute
    public void injectHeader(@RequestHeader(value = "USERNAME") String username) {
        MDC.put("USERNAME", username);
    }
}
