package com.gam.phoenix.numberingformat.dto;

import com.gam.phoenix.numberingformat.MotherObject;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.model.mapper.dto.NumberingFormatDtoRequest;
import com.gam.phoenix.numberingformat.model.mapper.dto.NumberingFormatMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 */

@SpringBootTest
class NumberingFormatMapperTest {
    NumberingFormat anyNumberingFormat;
    NumberingFormatDtoRequest anyNumberingFormatDtoRequest;

    @BeforeEach
    void setup() {
        anyNumberingFormat = MotherObject.getAnyValidNumberingFormat();
        anyNumberingFormatDtoRequest = MotherObject.getAnyValidNumberingFormatDto();
    }

    @Test
    void shouldMapNumberingFormatToDto() {
        //when
        NumberingFormatDtoRequest numberingFormatDtoRequest = NumberingFormatMapper.INSTANCE.entityToDtoRequest(anyNumberingFormat);
        //then
        assertNotNull(numberingFormatDtoRequest);
        assertEquals(numberingFormatDtoRequest.getNumberFormat(), anyNumberingFormat.getNumberFormat());
        assertEquals(numberingFormatDtoRequest.getNumberUsage(), anyNumberingFormat.getNumberUsage());
        assertEquals(numberingFormatDtoRequest.getStartAt(), anyNumberingFormat.getStartAt());
    }

    @Test
    void shouldMapNumberingFormatDtoToEntity() {
        //when
        NumberingFormat numberingFormat = NumberingFormatMapper.INSTANCE.dtoToEntity(anyNumberingFormatDtoRequest);
        //then
        assertNotNull(numberingFormat);
        assertEquals(numberingFormat.getNumberFormat(), anyNumberingFormatDtoRequest.getNumberFormat());
        assertEquals(numberingFormat.getNumberUsage(), anyNumberingFormatDtoRequest.getNumberUsage());
        assertEquals(numberingFormat.getStartAt(), anyNumberingFormatDtoRequest.getStartAt());
    }
}
