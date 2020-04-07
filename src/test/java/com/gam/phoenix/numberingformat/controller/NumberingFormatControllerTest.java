//package com.gam.phoenix.numberingformat.controller;
//
//import com.gam.phoenix.numberingformat.MotherObject;
//import com.gam.phoenix.numberingformat.model.NumberingFormat;
//import com.gam.phoenix.numberingformat.service.NumberingFormatService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.web.client.HttpServerErrorException;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//class NumberingFormatControllerTest {
//    @MockBean
//    NumberingFormatService numberingFormatService;
//    TestMapperUtil testMapperUtil = new TestMapperUtil();
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void contextLoads() {
//        assertNotNull(numberingFormatService);
//        assertNotNull(mockMvc);
//    }
//
//    @Test
//    @DisplayName("given getAllNumberingFormats when numberingFormat exist then returns numberingFormats")
//    public void given_getAllNumberingFormats_when_numberingFormat_exist_then_returns_numberingFormats() throws Exception {
//        List<NumberingFormat> numberingFormat = Collections.singletonList(MotherObject.getAnyValidNumberingFormat());
//
//        doReturn(new ArrayList<NumberingFormat>()).when(numberingFormatService).findAllNumberFormats();
//
//        // when + then
//        this.mockMvc.perform(get(NumberingFormatController.NUMBERING_FORMAT_URL)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//        //.andExpect(jsonPath("$[0].numberUsage", is(MotherObject.getAnyValidNumberingFormat().getNumberUsage())))
//        //.andExpect(jsonPath("$[0].numberFormat", is(MotherObject.getAnyValidNumberingFormat().getNumberFormat())))
//        //.andExpect(jsonPath("$[0].startAt", is(MotherObject.getAnyValidNumberingFormat().getStartAt())))
//        //.andExpect(jsonPath("$[0].lastAllocatedSerial", is(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial())));
//
//        verify(numberingFormatService, times(1)).findAllNumberFormats();
//        verifyNoMoreInteractions(numberingFormatService);
//    }
//
//    @Test
//    @DisplayName("given getNumberFormatByUsageAndFormat when usage and format are valid then returns numberingFormat")
//    public void given_getNumberFormatByUsageAndFormat_when_usage_and_format_are_valid_then_returns_numberingFormats() throws Exception {
//        doReturn(MotherObject.getAnyOptionalOfValidNumberingFormat()).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());
//        // when + then
//        this.mockMvc.perform(get(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1"))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.numberUsage").value("test1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.numberFormat").value("test1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.startAt").value(300))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.lastAllocatedSerial").value(301));
//    }
//
//    @Test
//    @DisplayName("given getNumberFormatByUsageAndFormat when usage and format are invalid then throws exception")
//    public void given_getNumberFormatByUsageAndFormat_when_usage_and_format_are_invalid_then_throws_exception() throws Exception {
//        // given
//        doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());
//        // when + then
//        this.mockMvc.perform(get(NumberingFormatController.NUMBERING_FORMAT_URL + "/invalidUsage/invalidFormat"))
//                .andExpect(status().is5xxServerError());
//    }
//
//    @Test
//    @DisplayName("given getAllNumberingFormats when not exist then throws exception")
//    public void given_getAllNumberingFormats_when_not_exist_then_throws_exception() throws Exception {
//        // given
//        doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(numberingFormatService).findAllNumberFormats();
//        // when + then
//        this.mockMvc.perform(get(NumberingFormatController.NUMBERING_FORMAT_URL))
//                .andExpect(status().is5xxServerError());
//    }
//
//    @Test
//    @DisplayName("given saveNumberFormat when numberingFormat is valid then returns numberingFormat")
//    public void given_saveNumberFormat_when_numberingFormat_is_valid_then_returns_numberingFormat() throws Exception {
//        // given
//        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatService).saveNumberFormat(any(NumberingFormat.class));
//
//        String inputJson = testMapperUtil.mapToJson(MotherObject.getAnyValidNumberingFormat());
//
//        this.mockMvc.perform(post(NumberingFormatController.NUMBERING_FORMAT_URL)
//                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
//                .andExpect(status().is2xxSuccessful()).andReturn();
//    }
//
////    @Test
////    @DisplayName("given saveNumberFormat when input is invalid then throws exception")
////    public void given_saveNumberFormat_when_input_is_invalid_then_throws_exception() throws Exception {
////        // given
////        doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(numberingFormatService).saveNumberFormat(any(NumberingFormat.class));
////        // when + then
////        this.mockMvc.perform(get(NumberingFormatController.NUMBERING_FORMAT_URL))
////                .andExpect(status().is5xxServerError());
////    }
//
//    @Test
//    @DisplayName("given getCurrentSerial when usage and format are valid then returns serial")
//    public void given_getCurrentSerial_when_usage_and_format_are_valid_then_returns_serial() throws Exception {
//        doReturn(MotherObject.getAnyOptionalOfValidNumberingFormat()).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());
//        // when + then
//        this.mockMvc.perform(get(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1/current"))
//                .andExpect(status().is2xxSuccessful());
//    }
//
//    @Test
//    @DisplayName("given getCurrentSerial when usage and format are invalid then throws exception")
//    public void given_getCurrentSerial_when_usage_and_format_are_invalid_then_throws_exception() throws Exception {
//        // given
//        doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());
//        // when + then
//        this.mockMvc.perform(get(NumberingFormatController.NUMBERING_FORMAT_URL + "/invalidUsage/invalidFormat/current"))
//                .andExpect(status().is5xxServerError());
//    }
//
//    @Test
//    @DisplayName("given getNextSerial when usage and format are valid then returns serial")
//    public void given_getNextSerial_when_usage_and_format_are_valid_then_returns_serial() throws Exception {
//        doReturn(MotherObject.getAnyOptionalOfValidNumberingFormat()).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());
//        doReturn(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial() + 1).when(numberingFormatService).getNextAllocatedSerial(any(NumberingFormat.class));
//
//        // when + then
//        this.mockMvc.perform(get(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1/next"))
//                .andExpect(status().is2xxSuccessful());
//    }
//
//    @Test
//    @DisplayName("given deleteNumberFormat when usage and format are valid then success")
//    public void given_deleteNumberFormat_when_usage_and_format_are_valid_then_success() throws Exception {
//        doNothing().when(numberingFormatService).deleteNumberingFormat(anyString(), anyString());
//        // when + then
//        this.mockMvc.perform(delete(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1"))
//                .andExpect(status().isNoContent());
//    }
//
////    @Test
////    @DisplayName("given deleteNumberFormat when usage and format are invalid then throws exception")
////    public void given_deleteNumberFormat_when_usage_and_format_are_inValid_then_throws_exception() throws Exception {
////        doThrow(BusinessException.class).when(numberingFormatService).deleteNumberingFormat(anyString(), anyString());
////        // when + then
////        this.mockMvc.perform(delete(NumberingFormatController.NUMBERING_FORMAT_URL + "/invalidUsage/invalidFormat"))
////                .andExpect(status().is5xxServerError());
////    }
//
//    @Test
//    @DisplayName("given increaseSerial when usage and format are valid then returns serial")
//    public void given_increaseSerial_when_usage_and_format_are_valid_then_returns_serial() throws Exception {
//        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatService).increaseLastAllocatedSerialByOne(anyString(), anyString());
//
//        // when + then
//        this.mockMvc.perform(patch(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1/serial/increase"))
//                .andExpect(status().isOk());
//    }
//
////    @Test
////    @DisplayName("given increaseSerial when usage and format are valid then throws exception")
////    public void given_increaseSerial_when_usage_and_format_are_inValid_then_throws_exception() throws Exception {
////        doThrow(BusinessException.class).when(numberingFormatService).increaseLastAllocatedSerialByOne(anyString(), anyString());
////
////        // when + then
////        this.mockMvc.perform(patch(NumberingFormatController.NUMBERING_FORMAT_URL + "/invalidUsage/invalidFormat/serial/increase"))
////                .andExpect(status().is5xxServerError());
////    }
//}