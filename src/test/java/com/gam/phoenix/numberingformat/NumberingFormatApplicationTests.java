package com.gam.phoenix.numberingformat;

import com.gam.phoenix.numberingformat.service.NumberingFormatService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class NumberingFormatApplicationTests {

    @MockBean
    NumberingFormatService numberingFormatService;
    @Autowired
    private TestRestTemplate restTemplate;

//    @Test
//    @DisplayName("given getAllNumberingFormat when numberingFormat exists then return all numberingFormats")
//    public void given_getAllNumberingFormat_when_numberingFormat_exists_then_return_all_numberingFormats() throws IOException {
//        ResponseEntity<List<NumberingFormat>> response = restTemplate.getForEntity(eq(NumberingFormatController.NUMBERING_FORMAT_URL), any());
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());

//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode responseJson = objectMapper.readTree(response.getBody());
//
//        assertFalse(responseJson.isMissingNode());
//        assertEquals("[]", responseJson.toString());
}
