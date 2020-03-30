package com.gam.phoenix.numberingformat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gam.phoenix.numberingformat.controller.NumberingFormatController;
import com.gam.phoenix.numberingformat.exception.BusinessException;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.service.NumberingFormatService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class NumberingFormatApplicationTest {

    @MockBean
    NumberingFormatService numberingFormatService;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoad() {
        assertNotNull(numberingFormatService);
        assertNotNull(restTemplate);
    }

    @Test
    @DisplayName("given getAllNumberingFormat when numberingFormat exists then return all numberingFormats")
    public void given_getAllNumberingFormat_when_numberingFormat_exists_then_return_all_numberingFormats() throws IOException {
        ResponseEntity<String> response = restTemplate.getForEntity(NumberingFormatController.NUMBERING_FORMAT_URL, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(response.getBody());

        assertFalse(responseJson.isMissingNode());
        assertEquals("[]", responseJson.toString());
    }

    @Test
    @DisplayName("given getNumberingFormatByFormatAndUsage when usage and format are valid then return numberingFormat")
    public void given_getNumberingFormatByFormatAndUsage_when_usage_and_format_are_valid_then_return_numberingFormat() throws BusinessException {

        doReturn(Optional.of(MotherObject.getAnyValidNumberingFormat())).when(numberingFormatService).findByUsageAndFormat("test1", "test1");

        ResponseEntity<NumberingFormat> response = restTemplate.getForEntity(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1", NumberingFormat.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        assertEquals(MotherObject.getAnyValidNumberingFormat().getClass(), Objects.requireNonNull(response.getBody()).getClass());
        verify(numberingFormatService, times(1)).findByUsageAndFormat(anyString(), anyString());
    }

    @Test
    @DisplayName("given getCurrentSerial when usage and format are valid then return current Serial")
    public void given_getCurrentSerial_when_usage_and_format_are_valid_then_return_currentSerial() throws BusinessException {

        doReturn(Optional.of(MotherObject.getAnyValidNumberingFormat())).when(numberingFormatService).findByUsageAndFormat("test1", "test1");

        ResponseEntity<Long> response = restTemplate.getForEntity(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1/current", Long.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial(), response.getBody());
        verify(numberingFormatService, times(1)).findByUsageAndFormat(anyString(), anyString());
    }

//    @Test
//    @DisplayName("given getNextSerial when usage and format are valid then return next Serial")
//    public void given_getNextSerial_when_usage_and_format_are_valid_then_return_nextSerial() throws BusinessException {
//
//        //doReturn(Optional.of(MotherObject.getAnyValidNumberingFormat())).when(numberingFormatService).findByUsageAndFormat("test1", "test1");
//        doReturn(anyLong()).when(numberingFormatService).getNextAllocatedSerial(any());
//
//        ResponseEntity<Long> response = restTemplate.getForEntity(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1/next", Long.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        assertEquals(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial(), response.getBody());
//        verify(numberingFormatService, times(1)).findByUsageAndFormat(anyString(), anyString());
//    }

    @Test
    @DisplayName("given saveNumberFormat when numberingFormat is valid then return numberingFormat")
    public void given_saveNumberFormat_when_numberingFormat_is_valid_then_return_numberingFormat() throws BusinessException {

        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatService).saveNumberFormat(any(NumberingFormat.class));
        //String expected = om.writeValueAsString(MotherObject.getAnyValidNumberingFormat());

        ResponseEntity<String> response = restTemplate.postForEntity(NumberingFormatController.NUMBERING_FORMAT_URL, MotherObject.getAnyValidNumberingFormat(), String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        //assertEquals(MotherObject.getAnyValidNumberingFormat(), response.getBody());

        verify(numberingFormatService, times(1)).saveNumberFormat(any(NumberingFormat.class));
    }

}
