package com.gam.phoenix.numberingformat.controller;

import com.gam.phoenix.numberingformat.controller.model.response.SerialResponse;
import com.gam.phoenix.numberingformat.model.IncreaseRequestModel;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.model.mapper.dto.NumberingFormatDtoRequest;
import com.gam.phoenix.numberingformat.model.mapper.dto.NumberingFormatDtoResponse;
import com.gam.phoenix.numberingformat.model.mapper.dto.NumberingFormatMapper;
import com.gam.phoenix.numberingformat.service.NumberingFormatService;
import com.gam.phoenix.spring.commons.dal.DalException;
import com.gam.phoenix.spring.commons.rest.model.response.ListRESTResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 **/
@RestController
@Api(value = "NumberingFormat Service!!!")
@RequestMapping(NumberingFormatController.NUMBERING_FORMAT_URL)
@Slf4j
@CrossOrigin
public class NumberingFormatController {
    public static final String NUMBERING_FORMAT_URL = "/api/v1/numbering-formats";

    private NumberingFormatService numberingFormatService;
    private NumberingFormatMapper numberingFormatMapper;

    @Autowired
    public NumberingFormatController(NumberingFormatService numberingFormatService, NumberingFormatMapper numberingFormatMapper) {
        this.numberingFormatService = numberingFormatService;
        this.numberingFormatMapper = numberingFormatMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new NumberingFormat.")
    public NumberingFormatDtoRequest saveNumberingFormat(@Valid @RequestBody NumberingFormatDtoRequest numberingFormatDtoRequest) throws DalException {
        return numberingFormatMapper.entityToDtoRequest(this.numberingFormatService.saveNumberingFormat(numberingFormatMapper.dtoToEntity(numberingFormatDtoRequest)));
    }

    @GetMapping
    @ApiOperation(value = "Return all numberingFormats")
    @ResponseStatus(HttpStatus.OK)
    public ListRESTResponse<NumberingFormat> getAllNumberingFormats() {
        ListRESTResponse<NumberingFormat> numberingFormatListRESTResponse = new ListRESTResponse<>();
        numberingFormatListRESTResponse.setRecords(numberingFormatService.findAllNumberingFormats());
        return numberingFormatListRESTResponse;
    }

    @GetMapping("/{usage}/{format}")
    @ApiOperation(value = "Return the specified NumberingFormat")
    @ResponseStatus(HttpStatus.OK)
    public NumberingFormatDtoResponse getNumberingFormatByUsageAndFormat(@PathVariable String usage, @PathVariable String format) throws DalException {
        NumberingFormat numberingFormat = this.numberingFormatService.findByUsageAndFormat(usage, format);
        return numberingFormatMapper.entityToDtoResponse(numberingFormat);
    }

    @GetMapping("/{usage}/{format}/current")
    @ApiOperation(value = "Return current serial of the specified NumberingFormat")
    @ResponseStatus(HttpStatus.OK)
    public SerialResponse getCurrentSerial(@PathVariable String usage, @PathVariable String format) throws DalException {
        NumberingFormat numberingFormat = this.numberingFormatService.findByUsageAndFormat(usage, format);
        return new SerialResponse(numberingFormat.getLastAllocatedSerial().toString());
    }

    @GetMapping("/{usage}/{format}/next")
    @ApiOperation(value = "Return the next valid serial of specified NumberingFormat")
    @ResponseStatus(HttpStatus.OK)
    public SerialResponse getNextSerial(@PathVariable String usage, @PathVariable String format) throws DalException {
        NumberingFormat numberingFormat = this.numberingFormatService.findByUsageAndFormat(usage, format);
        return new SerialResponse(numberingFormatService.getNextValidAllocatedSerial(numberingFormat).toString());
    }

    @PatchMapping("/{usage}/{format}/serial/increase")
    @ApiOperation(value = "Increase the serial of specified NumberingFormat")
    @ResponseStatus(HttpStatus.OK)
    public SerialResponse increaseSerial(@PathVariable String usage, @PathVariable String format, @Valid @RequestBody(required = false) IncreaseRequestModel increaseRequestModel) throws DalException {
        return new SerialResponse(numberingFormatService.increaseLastAllocatedSerialByOne(usage, format, increaseRequestModel));
    }

    @DeleteMapping("/{usage}/{format}")
    @ApiOperation(value = "Delete the specified NumberingFormat")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNumberingFormat(@PathVariable String usage, @PathVariable String format) throws DalException {
        this.numberingFormatService.deleteNumberingFormat(usage, format);
    }
}