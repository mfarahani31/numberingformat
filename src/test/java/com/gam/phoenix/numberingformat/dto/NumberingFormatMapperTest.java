package com.gam.phoenix.numberingformat.dto;

import com.gam.phoenix.numberingformat.MotherObject;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.model.mapper.dto.NumberingFormatDtoRequest;
import com.gam.phoenix.numberingformat.model.mapper.dto.NumberingFormatMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 */

@SpringBootTest
class NumberingFormatMapperTest {
    @Test
    public void shouldMapNumberingFormatToDto() {
        //when
        NumberingFormatDtoRequest numberingFormatDtoRequest = NumberingFormatMapper.INSTANCE.entityToDtoRequest(MotherObject.getAnyValidNumberingFormat());
        //then
        assertNotNull(numberingFormatDtoRequest);
        assertEquals(numberingFormatDtoRequest.getNumberFormat(), MotherObject.getAnyValidNumberingFormat().getNumberFormat());
        assertEquals(numberingFormatDtoRequest.getNumberUsage(), MotherObject.getAnyValidNumberingFormat().getNumberUsage());
        assertEquals(numberingFormatDtoRequest.getStartAt(), MotherObject.getAnyValidNumberingFormat().getStartAt());
    }

    @Test
    public void shouldMapNumberingFormatDtoToEntity() {
        //when
        NumberingFormat numberingFormat = NumberingFormatMapper.INSTANCE.dtoToEntity(MotherObject.getAnyValidNumberingFormatDto());
        //then
        assertNotNull(numberingFormat);
        assertEquals(numberingFormat.getNumberFormat(), MotherObject.getAnyValidNumberingFormatDto().getNumberFormat());
        assertEquals(numberingFormat.getNumberUsage(), MotherObject.getAnyValidNumberingFormatDto().getNumberUsage());
        assertEquals(numberingFormat.getStartAt(), MotherObject.getAnyValidNumberingFormatDto().getStartAt());
    }
}
