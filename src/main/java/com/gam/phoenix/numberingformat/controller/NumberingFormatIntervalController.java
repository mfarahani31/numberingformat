package com.gam.phoenix.numberingformat.controller;


import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import com.gam.phoenix.numberingformat.model.mapper.dto.NumberingFormatIntervalDto;
import com.gam.phoenix.numberingformat.model.mapper.dto.NumberingFormatIntervalMapper;
import com.gam.phoenix.numberingformat.service.NumberingFormatIntervalService;
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
@Api(value = "NumberingFormatInterval Service!!!")
@Slf4j
@CrossOrigin
@RequestMapping(NumberingFormatController.NUMBERING_FORMAT_URL)
public class NumberingFormatIntervalController {

    private NumberingFormatIntervalService numberingFormatIntervalService;
    private NumberingFormatIntervalMapper numberingFormatIntervalMapper;

    @Autowired
    public NumberingFormatIntervalController(NumberingFormatIntervalService numberingFormatIntervalService, NumberingFormatIntervalMapper numberingFormatIntervalMapper) {
        this.numberingFormatIntervalService = numberingFormatIntervalService;
        this.numberingFormatIntervalMapper = numberingFormatIntervalMapper;
    }

    @PostMapping("/id/{numberingFormatId}/reserved-intervals")
    @ApiOperation(value = "Create new NumberingFormatInterval.")
    @ResponseStatus(HttpStatus.CREATED)
    public NumberingFormatIntervalDto saveNumberingFormatInterval(@PathVariable Long numberingFormatId, @Valid @RequestBody NumberingFormatIntervalDto numberingFormatIntervalDto) throws DalException {
        return numberingFormatIntervalMapper.entityToDto(this.numberingFormatIntervalService.saveNumberingFormatInterval(numberingFormatId, numberingFormatIntervalMapper.dtoToEntity(numberingFormatIntervalDto)));
    }

    @GetMapping("/id/{numberingFormatId}/reserved-intervals")
    @ApiOperation(value = "Return the specified NumberingFormatInterval")
    @ResponseStatus(HttpStatus.OK)
    public ListRESTResponse<NumberingFormatInterval> getAllNumberingFormatIntervalsByNumberingFormatId(@PathVariable Long numberingFormatId, @RequestParam(required = false, defaultValue = "false") boolean justApplicableIntervals, @RequestParam(required = false) Long serial) throws DalException {
        ListRESTResponse<NumberingFormatInterval> numberingFormatIntervalListRESTResponse = new ListRESTResponse<>();
        numberingFormatIntervalListRESTResponse.setRecords(this.numberingFormatIntervalService.getAllReservedIntervalsByNumberingFormatId(numberingFormatId, justApplicableIntervals, serial));
        return numberingFormatIntervalListRESTResponse;
    }

    @DeleteMapping("/id/{numberingFormatId}/reserved-intervals/{reservedIntervalId}")
    @ApiOperation(value = "Delete the specified NumberingFormatInterval")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNumberingFormatInterval(@PathVariable Long numberingFormatId, @PathVariable Long reservedIntervalId) throws DalException {
        this.numberingFormatIntervalService.deleteNumberingFormatInterval(numberingFormatId, reservedIntervalId);
    }
}
