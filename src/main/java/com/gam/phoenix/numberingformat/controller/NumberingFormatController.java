package com.gam.phoenix.numberingformat.controller;

import com.gam.phoenix.numberingformat.exception.BusinessException;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.service.NumberingFormatService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "NumberingFormat Service!!!")
@RequestMapping(NumberingFormatController.NUMBERING_FORMAT_URL)
public class NumberingFormatController {
    public static final String NUMBERING_FORMAT_URL = "/numbering-format/api/v1/numbering-formats";

    private NumberingFormatService numberingFormatService;

    @Autowired
    public NumberingFormatController(NumberingFormatService numberingFormatService) {
        this.numberingFormatService = numberingFormatService;
    }

    @PostMapping
    public ResponseEntity<NumberingFormat> saveNumberFormat(@Valid @RequestBody NumberingFormat numberingFormat) throws BusinessException {
        this.numberingFormatService.saveNumberFormat(numberingFormat);
        return ResponseEntity.status(HttpStatus.CREATED).body(numberingFormat);
    }

    @GetMapping
    public ResponseEntity<List<NumberingFormat>> getAllNumberingFormats() {
        return ResponseEntity.ok(numberingFormatService.findAllNumberFormats());
    }

    @GetMapping("/{usage}/{format}")
    public ResponseEntity<NumberingFormat> getNumberFormatByUsageAndFormat(@PathVariable String usage, @PathVariable String format) throws BusinessException {
        Optional<NumberingFormat> numberingFormat = Optional.of(this.numberingFormatService.findByUsageAndFormat(usage, format).orElseThrow(() -> new BusinessException("not exist!")));
        return ResponseEntity.status(HttpStatus.OK).body(numberingFormat.get());
    }

    @GetMapping("/{usage}/{format}/current")
    public ResponseEntity<Long> getCurrentSerial(@PathVariable String usage, @PathVariable String format) throws BusinessException {
        Optional<NumberingFormat> numberingFormat = Optional.of(this.numberingFormatService.findByUsageAndFormat(usage, format).orElseThrow(() -> new BusinessException("not exist!")));
        return ResponseEntity.status(HttpStatus.OK).body(numberingFormat.get().getLastAllocatedSerial());
    }

    @GetMapping("/{usage}/{format}/next")
    public ResponseEntity<Long> getNextSerial(@PathVariable String usage, @PathVariable String format) throws BusinessException {
        Optional<NumberingFormat> numberingFormat = Optional.of(this.numberingFormatService.findByUsageAndFormat(usage, format).orElseThrow(() -> new BusinessException("not exist!")));
        return ResponseEntity.status(HttpStatus.OK).body(numberingFormatService.getNextAllocatedSerial(numberingFormat.get()));
    }

    @PatchMapping("/{usage}/{format}/serial/increase")
    public ResponseEntity<Long> increaseSerial(@PathVariable String usage, @PathVariable String format) throws BusinessException {
        NumberingFormat numberingFormat = numberingFormatService.increaseLastAllocatedSerialByOne(usage, format);
        return ResponseEntity.status(HttpStatus.OK).body(numberingFormat.getLastAllocatedSerial());
    }

    @DeleteMapping("/{usage}/{format}")
    public ResponseEntity deleteNumberFormat(@PathVariable String usage, @PathVariable String format) throws BusinessException {
        this.numberingFormatService.deleteNumberingFormat(usage, format);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}