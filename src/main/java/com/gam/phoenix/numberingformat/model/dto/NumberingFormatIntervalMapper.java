package com.gam.phoenix.numberingformat.model.dto;

import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 */
@Mapper(componentModel = "spring", uses = NumberingFormatInterval.class)
public interface NumberingFormatIntervalMapper {
    NumberingFormatIntervalMapper INSTANCE = Mappers.getMapper(NumberingFormatIntervalMapper.class);

    NumberingFormatIntervalDto entityToDto(NumberingFormatInterval numberingFormatInterval);

    NumberingFormatInterval dtoToEntity(NumberingFormatIntervalDto numberingFormatIntervalDto);

    List<NumberingFormatIntervalDto> toNumberingFormatIntervalDTOs(List<NumberingFormatInterval> numberingFormatIntervals);

    List<NumberingFormatInterval> toNumberingFormatIntervals(List<NumberingFormatIntervalDto> numberingFormatIntervalDtos);
}
