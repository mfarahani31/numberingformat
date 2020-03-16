package com.gam.phoenix.numberingformat.controller;

import com.gam.phoenix.numberingformat.exception.BusinessException;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.service.NumberingFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/numbering-format/api/v1/numbering-formats")
public class NumberingFormatController {
    private NumberingFormatService numberingFormatService;

    @Autowired
    public NumberingFormatController(NumberingFormatService numberingFormatService) {
        this.numberingFormatService = numberingFormatService;
    }

    @PostMapping
    public ResponseEntity<NumberingFormat> saveNumberFormat(@Valid @RequestBody NumberingFormat numberingFormat) throws BusinessException {
        numberingFormat.setLastAllocatedSerial(numberingFormat.getStartAt() - 1);
        this.numberingFormatService.saveNumberFormat(numberingFormat);
        return ResponseEntity.status(HttpStatus.CREATED).body(numberingFormat);
    }

    @GetMapping
    public ResponseEntity<List<NumberingFormat>> getAllNumberingFormat() {
        return ResponseEntity.ok(numberingFormatService.findAllNumberFormats());
    }

    @GetMapping("/{usage}/{format}")
    public ResponseEntity<NumberingFormat> getNumberFormatByUsageAndFormat(@PathVariable String usage, @PathVariable String format) throws BusinessException {
        NumberingFormat numberingFormat = this.numberingFormatService.findByUsageAndFormat(usage, format);
        return ResponseEntity.status(HttpStatus.OK).body(numberingFormat);
    }

    @GetMapping("/{usage}/{format}/current")
    public ResponseEntity<Long> getCurrentSerial(@PathVariable String usage, @PathVariable String format) throws BusinessException {
        NumberingFormat numberingFormat = numberingFormatService.findByUsageAndFormat(usage, format);
        return ResponseEntity.status(HttpStatus.OK).body(numberingFormat.getLastAllocatedSerial());
    }

    @GetMapping("/{usage}/{format}/next")
    public ResponseEntity<Long> getNextSerial(@PathVariable String usage, @PathVariable String format) throws BusinessException {
        NumberingFormat numberingFormat = numberingFormatService.findByUsageAndFormat(usage, format);
        return ResponseEntity.status(HttpStatus.OK).body(numberingFormat.getLastAllocatedSerial() + 1);
    }

    @PatchMapping("/{usage}/{format}/serial/increase")
    public ResponseEntity<Long> increaseSerial(@PathVariable String usage, @PathVariable String format) throws BusinessException {
        NumberingFormat numberingFormat = numberingFormatService.findByUsageAndFormat(usage, format);
        numberingFormatService.increaseLastAllocatedSerialByOne(usage, format);
        return ResponseEntity.status(HttpStatus.OK).body(numberingFormat.getLastAllocatedSerial() + 1);
    }

    @DeleteMapping("/{usage}/{format}")
    public ResponseEntity deleteNumberFormat(@PathVariable String usage, @PathVariable String format) throws BusinessException {
        this.numberingFormatService.deleteNumberingFormat(usage, format);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}