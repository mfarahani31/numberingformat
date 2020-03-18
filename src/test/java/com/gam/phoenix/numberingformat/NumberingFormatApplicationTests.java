package com.gam.phoenix.numberingformat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gam.phoenix.numberingformat.controller.NumberingFormatController;
import com.gam.phoenix.numberingformat.exception.BusinessException;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.service.NumberingFormatService;
import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class NumberingFormatApplicationTests {

    @MockBean
    NumberingFormatService numberingFormatService;
    @Autowired
    private TestRestTemplate restTemplate;

    private static final ObjectMapper om = new ObjectMapper();

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

//    @Test
//    @DisplayName("given getNumberingFormatByFormatAndUsage when numberingFormat exists then return numberingFormat")
//    public void given_getNumberingFormatByFormatAndUsage_when_numberingFormat_exists_then_return_numberingFormat() throws JSONException, BusinessException {
//
//        doReturn(Optional.of(new NumberingFormat())).when(numberingFormatService).findByUsageAndFormat("test1","test1");
//
//        ResponseEntity<String> response = restTemplate.getForEntity(NumberingFormatController.NUMBERING_FORMAT_URL + "/test1/test1/", String.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
//
//        assertEquals(MotherObject.getAnyValidNumberingFormat().toString(), response.getBody());
//
//        verify(numberingFormatService, times(1)).findByUsageAndFormat(anyString(), anyString());
//
//    }

    @Test
    @DisplayName("given saveNumberFormat when numberingFormat is valid then return numberingFormat")
    public void given_saveNumberFormat_when_numberingFormat_is_valid_then_return_numberingFormat() throws BusinessException, JsonProcessingException, JSONException {

        doReturn(MotherObject.getAnyValidNumberingFormat()).when(numberingFormatService).saveNumberFormat(any(NumberingFormat.class));
        //String expected = om.writeValueAsString(MotherObject.getAnyValidNumberingFormat());

        ResponseEntity<String> response = restTemplate.postForEntity(NumberingFormatController.NUMBERING_FORMAT_URL, MotherObject.getAnyValidNumberingFormat(), String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        //assertEquals(MotherObject.getAnyValidNumberingFormat(), response.getBody());

        verify(numberingFormatService, times(1)).saveNumberFormat(any(NumberingFormat.class));
    }

}
