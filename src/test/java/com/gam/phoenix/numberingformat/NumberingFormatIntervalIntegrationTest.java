//package com.gam.phoenix.numberingformat;
//
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.gam.phoenix.numberingformat.controller.NumberingFormatController;
//import com.gam.phoenix.numberingformat.controller.NumberingFormatIntervalController;
//import com.gam.phoenix.numberingformat.exception.BusinessException;
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
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.IOException;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
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
//    HttpEntity<String> entity = new HttpEntity<>(null);
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
//    @DisplayName("given getAllNumberingFormatInterval when numberingFormatInterval exists then return all numberingFormatIntervals")
//    public void given_getAllNumberingFormat_when_numberingFormat_exists_then_return_all_numberingFormats() throws IOException {
//        ResponseEntity<String> response = restTemplate.getForEntity(NumberingFormatIntervalController.NUMBERING_FORMAT_INTERVAL_URL, String.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode responseJson = objectMapper.readTree(response.getBody());
//
//        assertFalse(responseJson.isMissingNode());
//        assertEquals("[]", responseJson.toString());
//    }
//
//    @Test
//    @DisplayName("given saveNumberFormatInterval when numberingFormatInterval is valid then return numberingFormatInterval")
//    public void given_saveNumberFormatInterval_when_numberingFormatInterval_is_valid_then_return_numberingFormatInterval() throws BusinessException {
//
//        doReturn(MotherObject.getAnyValidNumberingFormatInterval()).when(numberingFormatIntervalService).saveNumberingFormatInterval(any(NumberingFormatInterval.class));
//
//        ResponseEntity<String> response = restTemplate.postForEntity(NumberingFormatIntervalController.NUMBERING_FORMAT_INTERVAL_URL, MotherObject.getAnyValidNumberingFormatInterval(), String.class);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//
//        verify(numberingFormatIntervalService, times(1)).saveNumberingFormatInterval(any(NumberingFormatInterval.class));
//    }
//
//    @Test
//    @DisplayName("given deleteNumberFormatInterval when usage and format are valid then delete numberingFormatInterval")
//    public void given_deleteNumberFormat_when_usage_and_format_are_valid_then_delete_numbering_format() throws BusinessException {
//
//        doNothing().when(numberingFormatIntervalService).deleteNumberingFormatInterval(anyString(), anyString(), anyLong(), anyLong());
//
//        ResponseEntity responseEntity = restTemplate.exchange(NumberingFormatIntervalController.NUMBERING_FORMAT_INTERVAL_URL + "/test1/test1/300/400", HttpMethod.DELETE, entity, ResponseEntity.class);
//
//        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
//
//        verify(numberingFormatIntervalService, times(1)).deleteNumberingFormatInterval(anyString(), anyString(), anyLong(), anyLong());
//    }
//
//    @Test
//    @DisplayName("given deleteNumberFormatInterval when numberingFormatInterval not exist then throws exception")
//    public void given_deleteNumberFormat_when_numberingFormat_not_exist_then_throws_exception() throws BusinessException {
//        doThrow(BusinessException.class).when(numberingFormatIntervalService).deleteNumberingFormatInterval(anyString(), anyString(), anyLong(), anyLong());
//
//        restTemplate.delete(NumberingFormatController.NUMBERING_FORMAT_URL + "/invalidUsage/InvalidFormat/invalid/invalid");
//        assertThrows(BusinessException.class, () -> numberingFormatIntervalService.deleteNumberingFormatInterval(anyString(), anyString(), anyLong(), anyLong()));
//    }
//
//    @Test
//    @DisplayName("given deleteNumberFormatInterval when numberingFormatInterval not exist then throws exception")
//    public void given_getAllNumberFormatIntervals_when_numberingFormat_not_exist_then_throws_exception() throws BusinessException {
//        doThrow(BusinessException.class).when(numberingFormatIntervalService).findAllNumberingFormatInterval();
//
//        restTemplate.getForEntity(NumberingFormatIntervalController.NUMBERING_FORMAT_INTERVAL_URL, List.class);
//        assertThrows(BusinessException.class, () -> numberingFormatIntervalService.findAllNumberingFormatInterval());
//    }
//
//}
