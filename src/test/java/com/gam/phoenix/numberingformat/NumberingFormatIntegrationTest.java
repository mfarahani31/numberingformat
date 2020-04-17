package com.gam.phoenix.numberingformat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gam.phoenix.numberingformat.controller.NumberingFormatController;
import com.gam.phoenix.numberingformat.exception.BusinessException;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.service.NumberingFormatService;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NumberingFormatIntegrationTest {

    @MockBean
    NumberingFormatService numberingFormatService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    void contextLoad() {
        assertNotNull(numberingFormatService);
        assertNotNull(restTemplate);
    }

    @Test
    @DisplayName("given getAllNumberingFormat when numberingFormat exists then return all numberingFormats")
    public void given_getAllNumberingFormat_when_numberingFormat_exists_then_return_all_numberingFormats() throws IOException {
        ResponseEntity<String> response = restTemplate.exchange(NumberingFormatController.NUMBERING_FORMAT_URL, HttpMethod.GET, MotherObject.getValidHttpEntityWithHeaderUsername(), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(response.getBody());

        assertFalse(responseJson.isMissingNode());
        assertEquals("[]", responseJson.toString());
    }

    @Test
    @DisplayName("given getCurrentSerial when usage and format are valid then return current Serial")
    public void given_getCurrentSerial_when_usage_and_format_are_valid_then_return_currentSerial() throws BusinessException {

        doReturn(Optional.of(MotherObject.getAnyValidNumberingFormat())).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());

        ResponseEntity<Long> response = restTemplate.exchange(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1/current", HttpMethod.GET, MotherObject.getValidHttpEntityWithHeaderUsername(), Long.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial(), response.getBody());
        verify(numberingFormatService, times(1)).findByUsageAndFormat(anyString(), anyString());
    }

    @Test
    @DisplayName("given getNumberingFormatByFormatAndUsage when numberingFormat not exist then return error")
    public void given_getCurrentSerial_when_usage_and_format_are_invalid_throws_exception() throws BusinessException {
        doThrow(BusinessException.class).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());

        ResponseEntity<String> response = restTemplate.exchange(NumberingFormatController.NUMBERING_FORMAT_URL + "/invalidUsage/invalidFormat/current", HttpMethod.GET, MotherObject.getValidHttpEntityWithHeaderUsername(), String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        verify(numberingFormatService, times(1)).findByUsageAndFormat(anyString(), anyString());
    }

    @Test
    @DisplayName("given getNumberingFormatByFormatAndUsage when numberingFormat not exist then throws exception")
    public void given_getNextSerial_when_usage_and_format_are_inValid_then_throws_exception() throws BusinessException {

        doReturn(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial() + 1).when(numberingFormatService).getNextAllocatedSerial(any(NumberingFormat.class));

        doThrow(BusinessException.class).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());

        ResponseEntity<String> response = restTemplate.exchange(NumberingFormatController.NUMBERING_FORMAT_URL + "/invalidUsage/invalidFormat/next", HttpMethod.GET, MotherObject.getValidHttpEntityWithHeaderUsername(), String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        verify(numberingFormatService, times(1)).findByUsageAndFormat(anyString(), anyString());
    }

    @Test
    @DisplayName("given getNumberingFormatByFormatAndUsage when usage and format are valid then return numberingFormat")
    public void given_getNumberingFormatByFormatAndUsage_when_usage_and_format_are_valid_then_return_numberingFormat() throws BusinessException {

        doReturn(Optional.of(MotherObject.getAnyValidNumberingFormat())).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());

        ResponseEntity<NumberingFormat> response = restTemplate.exchange(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1", HttpMethod.GET, MotherObject.getValidHttpEntityWithHeaderUsername(), NumberingFormat.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        assertEquals(MotherObject.getAnyValidNumberingFormat().getClass(), Objects.requireNonNull(response.getBody()).getClass());
        verify(numberingFormatService, times(1)).findByUsageAndFormat(anyString(), anyString());
    }

    @Test
    @DisplayName("given getNextSerial when usage and format are valid then return next Serial")
    public void given_getNextSerial_when_usage_and_format_are_valid_then_return_nextSerial() throws BusinessException {

        doReturn(Optional.of(MotherObject.getAnyValidNumberingFormat())).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());
        doReturn(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial() + 1).when(numberingFormatService).getNextAllocatedSerial(any(NumberingFormat.class));

        ResponseEntity<Long> response = restTemplate.exchange(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1/next", HttpMethod.GET, MotherObject.getValidHttpEntityWithHeaderUsername(), Long.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial() + 1, response.getBody());
        verify(numberingFormatService, times(1)).findByUsageAndFormat(anyString(), anyString());
    }

    @Test
    @DisplayName("given saveNumberFormat when numberingFormat is valid then return numberingFormat")
    public void given_saveNumberFormat_when_numberingFormat_is_valid_then_return_numberingFormat() throws BusinessException {

        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatService).saveNumberFormat(any(NumberingFormat.class));
        //String expected = om.writeValueAsString(MotherObject.getAnyValidNumberingFormat());

        ResponseEntity<String> response = restTemplate.exchange(NumberingFormatController.NUMBERING_FORMAT_URL, HttpMethod.POST, MotherObject.getValidHttpEntityWithHeaderUsernameAndBodyNumberingFormat(), String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        //assertEquals(MotherObject.getAnyValidNumberingFormat(), response.getBody());

        verify(numberingFormatService, times(1)).saveNumberFormat(any(NumberingFormat.class));
    }

    // todo ; debug this test
    @Test
    @DisplayName("given increaseSerial when usage and format are valid then return nextValidSerial")
    public void given_increaseSerial_when_usage_and_format_are_valid_then_return_nextValidSerial() {
        ResponseEntity<Long> response = restTemplate.exchange(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1/serial/increase", HttpMethod.PATCH, MotherObject.getValidHttpEntityWithHeaderUsername(), Long.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        //assertEquals(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial(), response.getBody());
        //verify(numberingFormatService, times(1)).increaseLastAllocatedSerialByOne(anyString(), anyString(),anyLong(),anyString(),any(NumberingFormat.class));
    }

    @Test
    @DisplayName("given deleteNumberFormat when usage and format are valid then delete numbering format")
    public void given_deleteNumberFormat_when_usage_and_format_are_valid_then_delete_numbering_format() throws BusinessException {

        doNothing().when(numberingFormatService).deleteNumberingFormat("test1", "test1");

        ResponseEntity responseEntity = restTemplate.exchange(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1", HttpMethod.DELETE, MotherObject.getValidHttpEntityWithHeaderUsername(), ResponseEntity.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(numberingFormatService, times(1)).deleteNumberingFormat(anyString(), anyString());
    }

    @Test
    @DisplayName("given getNumberingFormatByFormatAndUsage when numberingFormat not exist then return error")
    public void given_getNumberingFormatByFormatAndUsage_when_numberingFormat_not_exist_then_return_error() throws BusinessException {
        doThrow(BusinessException.class).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());

        ResponseEntity<NumberingFormat> response = restTemplate.exchange(NumberingFormatController.NUMBERING_FORMAT_URL + "/invalidUsage/invalidFormat", HttpMethod.GET, MotherObject.getValidHttpEntityWithHeaderUsername(), NumberingFormat.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        verify(numberingFormatService, times(1)).findByUsageAndFormat(anyString(), anyString());
    }

    @Test
    @DisplayName("given deleteNumberFormat when numberingFormat not exist then throws exception")
    public void given_deleteNumberFormat_when_numberingFormat_not_exist_then_throws_exception() throws BusinessException {
        doThrow(BusinessException.class).when(numberingFormatService).deleteNumberingFormat(anyString(), anyString());

        ResponseEntity<String> responseEntity = restTemplate.exchange(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test888", HttpMethod.DELETE, MotherObject.getValidHttpEntityWithHeaderUsername(), String.class);
        assertTrue(responseEntity.getStatusCode().is5xxServerError());
        verify(numberingFormatService, times(1)).deleteNumberingFormat(anyString(), anyString());
    }
}
