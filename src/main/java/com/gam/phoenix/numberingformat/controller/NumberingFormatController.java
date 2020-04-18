package com.gam.phoenix.numberingformat.controller;

import com.gam.phoenix.numberingformat.constants.ErrorMessages;
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
import java.util.Optional;


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
    public ResponseEntity<NumberingFormat> saveNumberFormat(@Valid @RequestBody NumberingFormatDto numberingFormatDto) throws BusinessException {
        NumberingFormat numberingFormat = this.numberingFormatService.saveNumberingFormat(numberingFormatMapper.dtoToEntity(numberingFormatDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(numberingFormat);
    }

    @GetMapping
    public ResponseEntity<List<NumberingFormat>> getAllNumberingFormats() throws BusinessException {
        List<NumberingFormat> allNumberFormats = numberingFormatService.findAllNumberingFormats();
        return ResponseEntity.ok(allNumberFormats);
    }

    @GetMapping("/{usage}/{format}")
    public ResponseEntity<NumberingFormat> getNumberFormatByUsageAndFormat(@PathVariable String usage, @PathVariable String format) throws BusinessException {
        Optional<NumberingFormat> numberingFormat = Optional.of(this.numberingFormatService.findByUsageAndFormat(usage, format).orElseThrow(() -> new BusinessException(ErrorMessages.NOT_EXIST)));
        return ResponseEntity.status(HttpStatus.OK).body(numberingFormat.get());
    }

    @GetMapping("/{usage}/{format}/current")
    public ResponseEntity<Long> getCurrentSerial(@PathVariable String usage, @PathVariable String format) throws BusinessException {
        Optional<NumberingFormat> numberingFormat = Optional.of(this.numberingFormatService.findByUsageAndFormat(usage, format).orElseThrow(() -> new BusinessException(ErrorMessages.NOT_EXIST)));
        return ResponseEntity.status(HttpStatus.OK).body(numberingFormat.get().getLastAllocatedSerial());
    }

    @GetMapping("/{usage}/{format}/next")
    public ResponseEntity<Long> getNextSerial(@PathVariable String usage, @PathVariable String format) throws BusinessException {
        Optional<NumberingFormat> numberingFormat = Optional.of(this.numberingFormatService.findByUsageAndFormat(usage, format).orElseThrow(() -> new BusinessException(ErrorMessages.NOT_EXIST)));
        return ResponseEntity.status(HttpStatus.OK).body(numberingFormatService.getNextValidAllocatedSerial(numberingFormat.get()));
    }

    @PatchMapping("/{usage}/{format}/serial/increase")
    public ResponseEntity<String> increaseSerial(@PathVariable String usage, @PathVariable String format, @Valid @RequestBody(required = false) IncreaseRequestModel increaseRequestModel) throws BusinessException {
        String serial = numberingFormatService.increaseLastAllocatedSerialByOne(usage, format, increaseRequestModel);
        return ResponseEntity.status(HttpStatus.OK).body(serial);
    }

    @DeleteMapping("/{usage}/{format}")
    public ResponseEntity<Long> deleteNumberFormat(@PathVariable String usage, @PathVariable String format) throws BusinessException {
        return ResponseEntity.status(HttpStatus.OK).body(this.numberingFormatService.deleteNumberingFormat(usage, format));
    }
}