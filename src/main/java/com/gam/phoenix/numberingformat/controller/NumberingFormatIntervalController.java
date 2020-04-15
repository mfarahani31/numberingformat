package com.gam.phoenix.numberingformat.controller;


import com.gam.phoenix.numberingformat.exception.BusinessException;
import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import com.gam.phoenix.numberingformat.model.dto.NumberingFormatIntervalDto;
import com.gam.phoenix.numberingformat.model.dto.NumberingFormatIntervalMapper;
import com.gam.phoenix.numberingformat.service.NumberingFormatIntervalService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 **/
@RequiredArgsConstructor
@RestController
@Api(value = "NumberingFormatInterval Service!!!")
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
    public ResponseEntity<NumberingFormatInterval> saveNumberFormatInterval(@PathVariable Long numberingFormatId, @Valid @RequestBody NumberingFormatIntervalDto numberingFormatIntervalDto) throws BusinessException {
        NumberingFormatInterval numberingFormatInterval = this.numberingFormatIntervalService.saveNumberingFormatInterval(numberingFormatId, numberingFormatIntervalMapper.dtoToEntity(numberingFormatIntervalDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(numberingFormatInterval);
    }

    @GetMapping("/id/{numberingFormatId}/reserved-intervals")
    public ResponseEntity<List<NumberingFormatInterval>> getAllNumberingFormatIntervalsByNumberingFormatId(@PathVariable Long numberingFormatId, @RequestParam(required = false, defaultValue = "false") boolean justApplicable, @RequestParam(required = false) Long serial) throws BusinessException {
        return ResponseEntity.ok(this.numberingFormatIntervalService.getAllReservedIntervalsByNumberingFormatId(numberingFormatId, justApplicable, serial));
    }

    @DeleteMapping("/id/{numberingFormatId}/reserved-intervals/{reservedIntervalId}")
    public ResponseEntity deleteNumberingFormatInterval(@PathVariable Long numberingFormatId, @PathVariable Long reservedIntervalId) throws BusinessException {
        this.numberingFormatIntervalService.deleteNumberingFormatInterval(numberingFormatId, reservedIntervalId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
