package com.gam.phoenix.numberingformat.controller;


import com.gam.phoenix.numberingformat.exception.BusinessException;
import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import com.gam.phoenix.numberingformat.service.NumberingFormatIntervalService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = "NumberingFormatInterval Service!!!")
@RequestMapping(value = {NumberingFormatIntervalController.NUMBERING_FORMAT_INTERVAL_URL, NumberingFormatController.NUMBERING_FORMAT_URL})
public class NumberingFormatIntervalController {
    public static final String NUMBERING_FORMAT_INTERVAL_URL = "/numbering-format/api/v1/reserved-intervals";


    private NumberingFormatIntervalService numberingFormatIntervalService;

    @Autowired
    public NumberingFormatIntervalController(NumberingFormatIntervalService numberingFormatIntervalService) {
        this.numberingFormatIntervalService = numberingFormatIntervalService;
    }

    @PostMapping("/{numberingFormatId}/reserved-intervals")
    public ResponseEntity<NumberingFormatInterval> saveNumberFormatInterval(@PathVariable Long numberingFormatId, @Valid @RequestBody NumberingFormatInterval numberingFormatInterval) throws BusinessException {
        this.numberingFormatIntervalService.saveNumberingFormatInterval(numberingFormatId, numberingFormatInterval);
        return ResponseEntity.status(HttpStatus.CREATED).body(numberingFormatInterval);
    }

    @GetMapping
    public ResponseEntity<List<NumberingFormatInterval>> getAllNumberingFormatInterval() throws BusinessException {
        return ResponseEntity.ok(numberingFormatIntervalService.findAllNumberingFormatInterval());
    }

    @GetMapping("/{numberingFormatId}")
    public ResponseEntity<List<NumberingFormatInterval>> getNumberingFormatIntervalByUsageAndFormat(@PathVariable Long numberingFormatId, @RequestBody(required = false) boolean justApplicable, @RequestBody(required = false) Long serial) throws BusinessException {
        return ResponseEntity.ok(this.numberingFormatIntervalService.findByNumberingFormatId(numberingFormatId, justApplicable, serial));
    }

    @DeleteMapping("/{reservedIntervalId}")
    public ResponseEntity deleteNumberingFormatInterval(@PathVariable Long reservedIntervalId) throws BusinessException {
        this.numberingFormatIntervalService.deleteNumberingFormatInterval(reservedIntervalId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
