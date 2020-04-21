package com.gam.phoenix.numberingformat.controller;

import com.gam.phoenix.numberingformat.service.NumberingFormatIntervalService;
import com.gam.phoenix.numberingformat.service.NumberingFormatService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertNotNull;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class NumberingFormatIntervalControllerTest {
    @MockBean
    NumberingFormatService numberingFormatService;
    @MockBean
    NumberingFormatIntervalService numberingFormatIntervalService;

    TestMapperUtil testMapperUtil = new TestMapperUtil();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        assertNotNull(numberingFormatService);
        assertNotNull(numberingFormatIntervalService);
        assertNotNull(mockMvc);
    }


}