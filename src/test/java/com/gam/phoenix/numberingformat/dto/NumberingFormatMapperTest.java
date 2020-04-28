package com.gam.phoenix.numberingformat.dto;

import com.gam.phoenix.numberingformat.MotherObject;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.model.dto.NumberingFormatDto;
import com.gam.phoenix.numberingformat.model.dto.NumberingFormatMapper;
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
        NumberingFormatDto numberingFormatDto = NumberingFormatMapper.INSTANCE.entityToDto(MotherObject.getAnyValidNumberingFormat());
        //then
        assertNotNull(numberingFormatDto);
        assertEquals(numberingFormatDto.getNumberFormat(), MotherObject.getAnyValidNumberingFormat().getNumberFormat());
        assertEquals(numberingFormatDto.getNumberUsage(), MotherObject.getAnyValidNumberingFormat().getNumberUsage());
        assertEquals(numberingFormatDto.getStartAt(), MotherObject.getAnyValidNumberingFormat().getStartAt());
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
