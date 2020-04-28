package com.gam.phoenix.numberingformat.controller;

import com.gam.phoenix.numberingformat.MotherObject;
import com.gam.phoenix.numberingformat.exception.RecordNotFoundException;
import com.gam.phoenix.numberingformat.model.IncreaseRequestModel;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.service.NumberingFormatService;
import com.gam.phoenix.spring.commons.dal.DalException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class NumberingFormatControllerTest {
    @MockBean
    NumberingFormatService numberingFormatService;
    TestMapperUtil testMapperUtil = new TestMapperUtil();
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        assertNotNull(numberingFormatService);
        assertNotNull(mockMvc);
    }

    @Test
    @DisplayName("given getAllNumberingFormats when numberingFormat exist then returns numberingFormats")
    public void given_getAllNumberingFormats_when_numberingFormat_exist_then_returns_numberingFormats() throws Exception {
        List<NumberingFormat> numberingFormat = Collections.singletonList(MotherObject.getAnyValidNumberingFormat());

        doReturn(numberingFormat).when(numberingFormatService).findAllNumberingFormats();

        // when + then
        this.mockMvc.perform(get(NumberingFormatController.NUMBERING_FORMAT_URL).header("username", "username")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(numberingFormatService, times(1)).findAllNumberingFormats();
        verifyNoMoreInteractions(numberingFormatService);
    }


    @Test
    @DisplayName("given getNumberFormatByUsageAndFormat when usage and format are valid then returns numberingFormat")
    public void given_getNumberFormatByUsageAndFormat_when_usage_and_format_are_valid_then_returns_numberingFormats() throws Exception {
        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());
        // when + then
        this.mockMvc.perform(get(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1")
                .header("username", "username"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.numberUsage").value("test1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.numberFormat").value("test1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.startAt").value(300));
    }

    @Test
    @DisplayName("given getNumberFormatByUsageAndFormat when usage and format are invalid then throws exception")
    public void given_getNumberFormatByUsageAndFormat_when_usage_and_format_are_invalid_then_throws_exception() throws Exception {
        // given
        doThrow(RecordNotFoundException.class).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());
        // when + then
        this.mockMvc.perform(get(NumberingFormatController.NUMBERING_FORMAT_URL + "/invalidUsage/invalidFormat").header("username", "username"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("given getAllNumberingFormats when not exist then throws exception")
    public void given_getAllNumberingFormats_when_not_exist_then_throws_exception() throws Exception {
        // given
        doThrow(RecordNotFoundException.class).when(numberingFormatService).findAllNumberingFormats();
        // when + then
        this.mockMvc.perform(get(NumberingFormatController.NUMBERING_FORMAT_URL))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("given saveNumberFormat when numberingFormat is valid then returns numberingFormat")
    public void given_saveNumberFormat_when_numberingFormat_is_valid_then_returns_numberingFormat() throws Exception {
        // given
        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatService).saveNumberingFormat(any(NumberingFormat.class));

        String inputJson = testMapperUtil.mapToJson(MotherObject.getAnyValidNumberingFormat());

        this.mockMvc.perform(post(NumberingFormatController.NUMBERING_FORMAT_URL).header("username", "username")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.numberUsage").value(MotherObject.getAnyValidNumberingFormat().getNumberUsage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.numberFormat").value(MotherObject.getAnyValidNumberingFormat().getNumberFormat()))
                .andReturn();

    }

    @Test
    @DisplayName("given saveNumberFormat when input is invalid then throws exception")
    public void given_saveNumberFormat_when_input_is_invalid_then_throws_exception() throws Exception {
        // given
        doThrow(DalException.class).when(numberingFormatService).saveNumberingFormat(any(NumberingFormat.class));
        // when + then
        this.mockMvc.perform(post(NumberingFormatController.NUMBERING_FORMAT_URL, MotherObject.getValidHttpEntityWithHeaderUsernameAndBodyNumberingFormatDto())
                .header("username", "username"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("given getCurrentSerial when usage and format are valid then returns serial")
    public void given_getCurrentSerial_when_usage_and_format_are_valid_then_returns_serial() throws Exception {
        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());
        // when + then
        this.mockMvc.perform(get(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1/current").header("username", "username"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("given getCurrentSerial when usage and format are invalid then throws exception")
    public void given_getCurrentSerial_when_usage_and_format_are_invalid_then_throws_exception() throws Exception {
        // given
        doThrow(RecordNotFoundException.class).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());
        // when + then
        this.mockMvc.perform(get(NumberingFormatController.NUMBERING_FORMAT_URL + "/invalidUsage/invalidFormat/current")
                .header("username", "username"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("given getNextSerial when usage and format are valid then returns serial")
    public void given_getNextSerial_when_usage_and_format_are_valid_then_returns_serial() throws Exception {
        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());
        doReturn(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial() + 1).when(numberingFormatService).getNextValidAllocatedSerial(any(NumberingFormat.class));

        // when + then
        this.mockMvc.perform(get(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1/next").header("username", "username"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("given deleteNumberFormat when usage and format are valid then success")
    public void given_deleteNumberFormat_when_usage_and_format_are_valid_then_success() throws Exception {
        doReturn(1L).when(numberingFormatService).deleteNumberingFormat(anyString(), anyString());
        // when + then
        this.mockMvc.perform(delete(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1").header("username", "username"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("given deleteNumberFormat when usage and format are invalid then throws exception")
    public void given_deleteNumberFormat_when_usage_and_format_are_inValid_then_throws_exception() throws Exception {
        doThrow(RecordNotFoundException.class).when(numberingFormatService).deleteNumberingFormat(anyString(), anyString());
        // when + then
        this.mockMvc.perform(delete(NumberingFormatController.NUMBERING_FORMAT_URL + "/invalidUsage/invalidFormat").header("username", "username"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("given increaseSerial when usage and format are valid then returns serial")
    public void given_increaseSerial_when_usage_and_format_are_valid_then_returns_serial() throws Exception {
        doReturn(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial().toString()).when(numberingFormatService).increaseLastAllocatedSerialByOne(anyString(), anyString(), any(IncreaseRequestModel.class));

        // when + then
        this.mockMvc.perform(patch(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1/serial/increase").header("username", "username"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("given increaseSerial when usage and format are valid then throws exception")
    public void given_increaseSerial_when_usage_and_format_are_inValid_then_throws_exception() throws Exception {
        doThrow(DalException.class).when(numberingFormatService).increaseLastAllocatedSerialByOne(anyString(), anyString(), any(IncreaseRequestModel.class));

        // when + then
        this.mockMvc.perform(patch(NumberingFormatController.NUMBERING_FORMAT_URL + "/invalidUsage/invalidFormat/serial/increase"))
                .andExpect(status().is2xxSuccessful());
    }
}