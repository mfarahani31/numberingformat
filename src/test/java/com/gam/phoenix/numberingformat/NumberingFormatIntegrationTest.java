package com.gam.phoenix.numberingformat;

import com.gam.phoenix.numberingformat.service.NumberingFormatService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NumberingFormatIntegrationTest {

    @MockBean
    NumberingFormatService numberingFormatService;
    HttpEntity<String> entity = new HttpEntity<>(null);
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

//    @Test
//    @DisplayName("given getAllNumberingFormat when numberingFormat exists then return all numberingFormats")
//    public void given_getAllNumberingFormat_when_numberingFormat_exists_then_return_all_numberingFormats() throws IOException {
//        ResponseEntity<String> response = restTemplate.getForEntity(NumberingFormatController.NUMBERING_FORMAT_URL, String.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode responseJson = objectMapper.readTree(response.getBody());
//
//        assertFalse(responseJson.isMissingNode());
//        assertEquals("[]", responseJson.toString());
//    }

//    @Test
//    @DisplayName("given getCurrentSerial when usage and format are valid then return current Serial")
//    public void given_getCurrentSerial_when_usage_and_format_are_valid_then_return_currentSerial() throws BusinessException {
//
//        doReturn(Optional.of(MotherObject.getAnyValidNumberingFormat())).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());
//
//        ResponseEntity<Long> response = restTemplate.getForEntity(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1/current", Long.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        assertEquals(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial(), response.getBody());
//        verify(numberingFormatService, times(1)).findByUsageAndFormat(anyString(), anyString());
//    }
//
//    @Test
//    @DisplayName("given getNumberingFormatByFormatAndUsage when usage and format are valid then return numberingFormat")
//    public void given_getNumberingFormatByFormatAndUsage_when_usage_and_format_are_valid_then_return_numberingFormat() throws BusinessException {
//
//        doReturn(Optional.of(MotherObject.getAnyValidNumberingFormat())).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());
//
//        ResponseEntity<NumberingFormat> response = restTemplate.getForEntity(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1", NumberingFormat.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
//
//        assertEquals(MotherObject.getAnyValidNumberingFormat().getClass(), Objects.requireNonNull(response.getBody()).getClass());
//        verify(numberingFormatService, times(1)).findByUsageAndFormat(anyString(), anyString());
//    }
//
//    @Test
//    @DisplayName("given getNextSerial when usage and format are valid then return next Serial")
//    public void given_getNextSerial_when_usage_and_format_are_valid_then_return_nextSerial() throws BusinessException {
//
//        doReturn(Optional.of(MotherObject.getAnyValidNumberingFormat())).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());
//        doReturn(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial() + 1).when(numberingFormatService).getNextAllocatedSerial(any(NumberingFormat.class));
//
//        ResponseEntity<Long> response = restTemplate.getForEntity(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1/next", Long.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
////        assertEquals(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial(), response.getBody());
//        verify(numberingFormatService, times(1)).findByUsageAndFormat(anyString(), anyString());
//    }
//
//    @Test
//    @DisplayName("given saveNumberFormat when numberingFormat is valid then return numberingFormat")
//    public void given_saveNumberFormat_when_numberingFormat_is_valid_then_return_numberingFormat() throws BusinessException {
//
//        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatService).saveNumberFormat(any(NumberingFormat.class));
//        //String expected = om.writeValueAsString(MotherObject.getAnyValidNumberingFormat());
//
//        ResponseEntity<String> response = restTemplate.postForEntity(NumberingFormatController.NUMBERING_FORMAT_URL, MotherObject.getAnyValidNumberingFormat(), String.class);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        //assertEquals(MotherObject.getAnyValidNumberingFormat(), response.getBody());
//
//        verify(numberingFormatService, times(1)).saveNumberFormat(any(NumberingFormat.class));
//    }
//
//    @Test
//    @DisplayName("given increaseSerial when usage and format are valid then return nextValidSerial")
//    public void given_increaseSerial_when_usage_and_format_are_valid_then_return_nextValidSerial() throws BusinessException {
//
//        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatService).increaseLastAllocatedSerialByOne(anyString(), anyString());
//
//        ResponseEntity<Long> response = restTemplate.exchange(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1/serial/increase", HttpMethod.PATCH, entity, Long.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        assertEquals(MotherObject.getAnyValidNumberingFormat().getLastAllocatedSerial(), response.getBody());
//        verify(numberingFormatService, times(1)).increaseLastAllocatedSerialByOne(anyString(), anyString());
//    }
//
//    @Test
//    @DisplayName("given deleteNumberFormat when usage and format are valid then delete numbering format")
//    public void given_deleteNumberFormat_when_usage_and_format_are_valid_then_delete_numbering_format() throws BusinessException {
//
//        doNothing().when(numberingFormatService).deleteNumberingFormat("test1", "test1");
//
//        ResponseEntity responseEntity = restTemplate.exchange(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1", HttpMethod.DELETE, entity, ResponseEntity.class);
//
//        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
//
//        verify(numberingFormatService, times(1)).deleteNumberingFormat(anyString(), anyString());
//    }
//
//    @Test
//    @DisplayName("given getNumberingFormatByFormatAndUsage when numberingFormat not exist then return error")
//    public void given_getNumberingFormatByFormatAndUsage_when_numberingFormat_not_exist_then_return_error() throws BusinessException {
//        doThrow(BusinessException.class).when(numberingFormatService).findByUsageAndFormat(anyString(), anyString());
//
//        ResponseEntity<NumberingFormat> response = restTemplate.getForEntity(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test888", NumberingFormat.class);
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
//
//        verify(numberingFormatService, times(1)).findByUsageAndFormat(anyString(), anyString());
//    }
//
//    @Test
//    @DisplayName("given deleteNumberFormat when numberingFormat not exist then return error")
//    public void given_deleteNumberFormat_when_numberingFormat_not_exist_then_return_error() throws BusinessException {
//        doThrow(BusinessException.class).when(numberingFormatService).deleteNumberingFormat(anyString(), anyString());
//
//        restTemplate.delete(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test888");
//        verify(numberingFormatService, times(1)).deleteNumberingFormat(anyString(), anyString());
//    }
}
