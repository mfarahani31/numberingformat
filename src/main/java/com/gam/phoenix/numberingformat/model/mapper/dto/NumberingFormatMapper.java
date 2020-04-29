package com.gam.phoenix.numberingformat.model.mapper.dto;

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

    NumberingFormatDtoRequest entityToDtoRequest(NumberingFormat numberingFormat);

    NumberingFormatDtoResponse entityToDtoResponse(NumberingFormat numberingFormat);

    NumberingFormat dtoToEntity(NumberingFormatDtoRequest numberingFormatDtoRequest);

    List<NumberingFormatDtoRequest> toNumberingFormatDTOs(List<NumberingFormat> numberingFormats);

    List<NumberingFormat> toNumberingFormats(List<NumberingFormatDtoRequest> numberingFormatDtoRequests);
}
