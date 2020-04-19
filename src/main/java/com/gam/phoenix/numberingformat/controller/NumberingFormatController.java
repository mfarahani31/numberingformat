package com.gam.phoenix.numberingformat.controller;

import com.gam.phoenix.numberingformat.exception.BusinessException;
import com.gam.phoenix.numberingformat.model.IncreaseRequestModel;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.model.dto.NumberingFormatDto;
import com.gam.phoenix.numberingformat.model.dto.NumberingFormatMapper;
import com.gam.phoenix.numberingformat.service.NumberingFormatService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 **/
@RestController
@Api(value = "NumberingFormat Service!!!")
@RequestMapping(NumberingFormatController.NUMBERING_FORMAT_URL)
public class NumberingFormatController {
    public static final String NUMBERING_FORMAT_URL = "/numbering-format/api/v1/numbering-formats";

    private NumberingFormatService numberingFormatService;
    private NumberingFormatMapper numberingFormatMapper;

    @Autowired
    public NumberingFormatController(NumberingFormatService numberingFormatService, NumberingFormatMapper numberingFormatMapper) {
        this.numberingFormatService = numberingFormatService;
        this.numberingFormatMapper = numberingFormatMapper;
    }

    @PostMapping
    public ResponseEntity<NumberingFormat> saveNumberingFormat(@Valid @RequestBody NumberingFormatDto numberingFormatDto) throws BusinessException {
        NumberingFormat numberingFormat = this.numberingFormatService.saveNumberingFormat(numberingFormatMapper.dtoToEntity(numberingFormatDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(numberingFormat);
    }

    @GetMapping
    public ResponseEntity<List<NumberingFormat>> getAllNumberingFormats() {
        List<NumberingFormat> allNumberFormats = this.numberingFormatService.findAllNumberingFormats();
        return ResponseEntity.ok(allNumberFormats);
    }

    @GetMapping("/{usage}/{format}")
    public ResponseEntity<NumberingFormat> getNumberingFormatByUsageAndFormat(@PathVariable String usage, @PathVariable String format) {
        NumberingFormat numberingFormat = this.numberingFormatService.findByUsageAndFormat(usage, format);
        return ResponseEntity.status(HttpStatus.OK).body(numberingFormat);
    }

    @GetMapping("/{usage}/{format}/current")
    public ResponseEntity<Long> getCurrentSerial(@PathVariable String usage, @PathVariable String format) {
        NumberingFormat numberingFormat = this.numberingFormatService.findByUsageAndFormat(usage, format);
        return ResponseEntity.status(HttpStatus.OK).body(numberingFormat.getLastAllocatedSerial());
    }

    @GetMapping("/{usage}/{format}/next")
    public ResponseEntity<Long> getNextSerial(@PathVariable String usage, @PathVariable String format) {
        NumberingFormat numberingFormat = this.numberingFormatService.findByUsageAndFormat(usage, format);
        return ResponseEntity.status(HttpStatus.OK).body(numberingFormatService.getNextValidAllocatedSerial(numberingFormat));
    }

    @PatchMapping("/{usage}/{format}/serial/increase")
    public ResponseEntity<String> increaseSerial(@PathVariable String usage, @PathVariable String format, @Valid @RequestBody(required = false) IncreaseRequestModel increaseRequestModel) throws BusinessException {
        String serial = numberingFormatService.increaseLastAllocatedSerialByOne(usage, format, increaseRequestModel);
        return ResponseEntity.status(HttpStatus.OK).body(serial);
    }

    @DeleteMapping("/{usage}/{format}")
    public ResponseEntity<Long> deleteNumberingFormat(@PathVariable String usage, @PathVariable String format) {
        return ResponseEntity.status(HttpStatus.OK).body(this.numberingFormatService.deleteNumberingFormat(usage, format));
    }
}