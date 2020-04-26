//package com.gam.phoenix.numberingformat;
//
//
//import com.gam.phoenix.numberingformat.controller.NumberingFormatController;
//import com.gam.phoenix.numberingformat.exception.RecordNotFoundException;
//import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
//import com.gam.phoenix.numberingformat.service.NumberingFormatIntervalService;
//import org.junit.Before;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
///**
// * @author Mohammad Farahani (farahani@gamelectronics.com)
// **/
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class NumberingFormatIntervalIntegrationTest {
//
//    @MockBean
//    NumberingFormatIntervalService numberingFormatIntervalService;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Before
//    public void setup() {
//        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
//    }
//
//    @Test
//    void contextLoad() {
//        assertNotNull(numberingFormatIntervalService);
//        assertNotNull(restTemplate);
//    }
//
//    @Test
//    @DisplayName("given saveNumberFormatInterval when numberingFormatId and inputValues are valid then return numberingFormatInterval")
//    public void given_saveNumberFormatInterval_when_numberingFormatId_and_inputValues_are_valid_then_return_numberingFormatInterval() {
//
//        doReturn(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400()).when(numberingFormatIntervalService).saveNumberingFormatInterval(anyLong(), any(NumberingFormatInterval.class));
//
//        ResponseEntity<String> response = restTemplate.exchange(NumberingFormatController.NUMBERING_FORMAT_URL + "/id/1/reserved-intervals", HttpMethod.POST, MotherObject.getValidHttpEntityWithHeaderUsernameAndBodyNumberingFormatInterval(), String.class);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//
//        verify(numberingFormatIntervalService, times(1)).saveNumberingFormatInterval(anyLong(), any(NumberingFormatInterval.class));
//    }
//
//    @Test
//    @DisplayName("given deleteNumberFormatInterval when numberingFormatId is valid then delete numberingFormatInterval")
//    public void given_deleteNumberFormat_when_numberingFormatId_is_valid_then_delete_numberingFormatInterval() {
//
//        //doNothing().when(numberingFormatIntervalService).deleteNumberingFormatInterval(anyLong(), anyLong());
//
//        ResponseEntity<?> responseEntity = restTemplate.exchange(NumberingFormatController.NUMBERING_FORMAT_URL + "/id/1/reserved-intervals/1", HttpMethod.DELETE, MotherObject.getValidHttpEntityWithHeaderUsernameAdmin(), ResponseEntity.class);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//
//        verify(numberingFormatIntervalService, times(1)).deleteNumberingFormatInterval(anyLong(), anyLong());
//    }
//
//    @Test
//    @DisplayName("given deleteNumberFormatInterval when numberingFormatInterval not exist then throws exception")
//    public void given_deleteNumberFormat_when_numberingFormat_not_exist_then_throws_exception() {
//        doThrow(RecordNotFoundException.class).when(numberingFormatIntervalService).deleteNumberingFormatInterval(anyLong(), anyLong());
//
//        restTemplate.delete(NumberingFormatController.NUMBERING_FORMAT_URL + "/id/1/reserved-intervals/1", HttpMethod.DELETE, MotherObject.getValidHttpEntityWithHeaderUsernameAdmin(), ResponseEntity.class);
//        assertThrows(RecordNotFoundException.class, () -> numberingFormatIntervalService.deleteNumberingFormatInterval(anyLong(), anyLong()));
//    }
//
//    @Test
//    @DisplayName("given saveNumberFormatInterval when numberingFormatId and inputValues are inValid then throws Exception")
//    public void given_saveNumberFormatInterval_when_numberingFormatId_and_inputValues_are_inValid_then_throws_exception() {
//        doThrow(RecordNotFoundException.class).when(numberingFormatIntervalService).saveNumberingFormatInterval(anyLong(), any(NumberingFormatInterval.class));
//
//        ResponseEntity<String> response = restTemplate.exchange(NumberingFormatController.NUMBERING_FORMAT_URL + "/id/55/reserved-intervals", HttpMethod.POST, MotherObject.getValidHttpEntityWithHeaderUsernameAndBodyNumberingFormatInterval(), String.class);
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
//
//    @Test
//    @DisplayName("given getAllNumberingFormatIntervalsByNumberingFormatId when usage and format are valid then return numberingFormat")
//    public void given_getAllNumberingFormatIntervalsByNumberingFormatId_when_numberingFormatId_is_valid_then_return_numberingFormatInterval() {
//
//        List<NumberingFormatInterval> numberingFormatIntervals = Collections.singletonList(MotherObject.getAnyValidNumberingFormatIntervalBetween300And400());
//
//        doReturn(numberingFormatIntervals).when(numberingFormatIntervalService).getAllReservedIntervalsByNumberingFormatId(anyLong(), anyBoolean(), anyLong());
//
//        ResponseEntity<String> response = restTemplate.exchange(NumberingFormatController.NUMBERING_FORMAT_URL + "/id/1/reserved-intervals", HttpMethod.GET, MotherObject.getValidHttpEntityWithHeaderUsernameAdmin(), String.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
//        //assertEquals(MotherObject.getAnyValidNumberingFormatInterval().toString(), response.getBody());
//        verify(numberingFormatIntervalService, times(1)).getAllReservedIntervalsByNumberingFormatId(anyLong(), anyBoolean(), any());
//    }
//
//    @Test
//    @DisplayName("given getAllNumberingFormatIntervalsByNumberingFormatId when usage and format are valid then throws exception")
//    public void given_getAllNumberingFormatIntervalsByNumberingFormatId_when_numberingFormatId_is_inValid_then_throws_exception() {
//        doThrow(RecordNotFoundException.class).when(numberingFormatIntervalService).getAllReservedIntervalsByNumberingFormatId(anyLong(), anyBoolean(), any());
//
//        ResponseEntity<String> response = restTemplate.exchange(NumberingFormatController.NUMBERING_FORMAT_URL + "/id/55/reserved-intervals", HttpMethod.GET, MotherObject.getValidHttpEntityWithHeaderUsernameAdmin(), String.class);
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//
//        assertThrows(RecordNotFoundException.class, () -> numberingFormatIntervalService.getAllReservedIntervalsByNumberingFormatId(anyLong(), anyBoolean(), any()));
//    }
//}
