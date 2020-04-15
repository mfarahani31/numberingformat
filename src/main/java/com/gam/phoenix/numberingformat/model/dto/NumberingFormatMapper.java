package com.gam.phoenix.numberingformat.model.dto;

import com.gam.phoenix.numberingformat.model.NumberingFormat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 */
@Mapper(componentModel = "spring", uses = NumberingFormat.class)
public interface NumberingFormatMapper {

    NumberingFormatMapper INSTANCE = Mappers.getMapper(NumberingFormatMapper.class);

    NumberingFormatDto entityToDto(NumberingFormat numberingFormat);

    NumberingFormat dtoToEntity(NumberingFormatDto numberingFormatDto);

    List<NumberingFormatDto> toNumberingFormatDTOs(List<NumberingFormat> numberingFormats);

    List<NumberingFormat> toNumberingFormats(List<NumberingFormatDto> numberingFormatDtos);
}
