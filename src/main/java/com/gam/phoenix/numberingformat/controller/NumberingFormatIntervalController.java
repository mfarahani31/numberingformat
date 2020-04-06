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
@RequestMapping(NumberingFormatIntervalController.NUMBERING_FORMAT_INTERVAL_URL)
public class NumberingFormatIntervalController {
    public static final String NUMBERING_FORMAT_INTERVAL_URL = "/numbering-format/api/v1/reserved-intervals";

    private NumberingFormatIntervalService numberingFormatIntervalService;

    @Autowired
    public NumberingFormatIntervalController(NumberingFormatIntervalService numberingFormatIntervalService) {
        this.numberingFormatIntervalService = numberingFormatIntervalService;
    }

    @PostMapping
    public ResponseEntity<NumberingFormatInterval> saveNumberFormatInterval(@Valid @RequestBody NumberingFormatInterval numberingFormatInterval) throws BusinessException {
        this.numberingFormatIntervalService.saveNumberingFormatInterval(numberingFormatInterval);
        return ResponseEntity.status(HttpStatus.CREATED).body(numberingFormatInterval);
    }

    @GetMapping
    public ResponseEntity<List<NumberingFormatInterval>> getAllNumberingFormatInterval() throws BusinessException {
        return ResponseEntity.ok(numberingFormatIntervalService.findAllNumberingFormatInterval());
    }

    @GetMapping("/{usage}/{format}")
    public ResponseEntity<List<NumberingFormatInterval>> getNumberingFormatIntervalByUsageAndFormat(@PathVariable String usage, @PathVariable String format, @RequestBody(required = false) boolean justApplicable, @RequestBody(required = false) Long serial) throws BusinessException {
        return ResponseEntity.ok(this.numberingFormatIntervalService.findByUsageAndFormat(usage, format, justApplicable, serial));
    }

    @DeleteMapping("/{usage}/{format}/{start}/{end}")
    public ResponseEntity deleteNumberingFormatInterval(@PathVariable String usage, @PathVariable String format, @PathVariable Long start, @PathVariable Long end) throws BusinessException {
        this.numberingFormatIntervalService.deleteNumberingFormatInterval(usage, format, start, end);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
