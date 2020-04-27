package com.gam.phoenix.numberingformat.controller;

import com.gam.phoenix.numberingformat.MotherObject;
import com.gam.phoenix.numberingformat.exception.RecordNotFoundException;
import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import com.gam.phoenix.numberingformat.service.NumberingFormatIntervalService;
import com.gam.phoenix.numberingformat.service.NumberingFormatService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    @DisplayName("given getAllReservedIntervalsByNumberingFormatId when numberingFormatId is valid then returns 200")
    public void given_getAllReservedIntervalsByNumberingFormatId_when_numberingFormatId_is_valid_then_returns_200() throws Exception {
        List<NumberingFormatInterval> expectedNumberingFormatInterval = Collections.singletonList(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400());

        doReturn(expectedNumberingFormatInterval).when(numberingFormatIntervalService).getAllReservedIntervalsByNumberingFormatId(anyLong(), anyBoolean(), anyLong());

        // when + then
        this.mockMvc.perform(get(NumberingFormatController.NUMBERING_FORMAT_URL + "/id/1/reserved-intervals")
                .header("username", "username")
                //.param("justApplicableIntervals","false")
                //.param("serial","3L")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //verify(numberingFormatIntervalService, times(1)).getAllReservedIntervalsByNumberingFormatId(anyLong(),anyBoolean(),anyLong());
    }

    @Test
    @DisplayName("given getAllReservedIntervalsByNumberingFormatId when numberingFormatId is inValid then returns 400")
    public void given_getAllReservedIntervalsByNumberingFormatId_when_numberingFormatId_is_inValid_then_returns_400() throws Exception {

        doThrow(RecordNotFoundException.class).when(numberingFormatIntervalService).getAllReservedIntervalsByNumberingFormatId(anyLong(), anyBoolean(), anyLong());

        // when + then
        this.mockMvc.perform(get(NumberingFormatController.NUMBERING_FORMAT_URL + "/id/invalidId/reserved-intervals")
                .header("username", "username")
                //.param("justApplicableIntervals","false")
                //.param("serial","3L")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("given saveNumberingFormatInterval when numberingFormatId is valid then returns numberingFormatInterval")
    public void given_saveNumberingFormatInterval_when_numberingFormatId_is_valid_then_returns_numberingFormatInterval() throws Exception {
        // given
        doReturn(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400()).when(numberingFormatIntervalService).saveNumberingFormatInterval(anyLong(), any(NumberingFormatInterval.class));

        String inputJson = testMapperUtil.mapToJson(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400());

        this.mockMvc.perform(post(NumberingFormatController.NUMBERING_FORMAT_URL + "/id/1/reserved-intervals")
                .header("username", "username")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.reservedStart").value(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400().getReservedStart()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.reservedEnd").value(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400().getReservedEnd()))
                .andReturn();
    }

    @Test
    @DisplayName("given saveNumberingFormatInterval when numberingFormat is valid then returns numberingFormat")
    public void given_saveNumberFormat_when_numberingFormatId_is_inValid_then_returns_numberingFormat() throws Exception {
        // given
        doThrow(RecordNotFoundException.class).when(numberingFormatIntervalService).saveNumberingFormatInterval(anyLong(), any(NumberingFormatInterval.class));

        String inputJson = testMapperUtil.mapToJson(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400());

        this.mockMvc.perform(post(NumberingFormatController.NUMBERING_FORMAT_URL + "/id/invalidId/reserved-intervals")
                .header("username", "username")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @DisplayName("given deleteNumberingFormatInterval when numberingFormat is valid then delete numberingFormatInterval")
    public void given_deleteNumberingFormatInterval_when_numberingFormatId_is_valid_then_delete_numberingFormatInterval() throws Exception {
        // given
        doReturn(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400()).when(numberingFormatIntervalService).deleteNumberingFormatInterval(anyLong(), anyLong());

        String inputJson = testMapperUtil.mapToJson(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400());

        this.mockMvc.perform(delete(NumberingFormatController.NUMBERING_FORMAT_URL + "/id/1/reserved-intervals/1")
                .header("username", "username")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    @DisplayName("given deleteNumberingFormatInterval when numberingFormat is valid then return 404")
    public void given_deleteNumberingFormatInterval_when_numberingFormatId_is_inValid_then_return_404() throws Exception {
        // given
        doThrow(RecordNotFoundException.class).when(numberingFormatIntervalService).deleteNumberingFormatInterval(anyLong(), anyLong());

        this.mockMvc.perform(delete(NumberingFormatController.NUMBERING_FORMAT_URL + "/id/invalidId/reserved-intervals/nvalidId")
                .header("username", "username")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @DisplayName("given deleteNumberingFormatInterval when numberingFormat is inValid and numberingFormatIntervalId is valid then return 404")
    public void given_deleteNumberingFormatInterval_when_numberingFormatId_is_inValid_and_numberingFormatIntervalId_is_valid_then_return_404() throws Exception {
        // given
        doThrow(RecordNotFoundException.class).when(numberingFormatIntervalService).deleteNumberingFormatInterval(anyLong(), anyLong());

        this.mockMvc.perform(delete(NumberingFormatController.NUMBERING_FORMAT_URL + "/id/1/reserved-intervals/invalidId")
                .header("username", "username")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }
}