package com.gam.phoenix.numberingformat.controller;

import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.service.NumberingFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/numbering-format/api/v1/numbering-formats")
public class NumberingFormatController {
    private NumberingFormatService numberingFormatService;

    @Autowired
    public NumberingFormatController(NumberingFormatService numberingFormatService) {
        this.numberingFormatService = numberingFormatService;
    }

    @PostMapping
    public ResponseEntity<NumberingFormat> newNumberFormat(@Valid @RequestBody NumberingFormat numberingFormat) {
        this.numberingFormatService.saveNumberFormat(numberingFormat);
        return ResponseEntity.status(HttpStatus.CREATED).body(numberingFormat);
    }

    @GetMapping
    public ResponseEntity<List<NumberingFormat>> all() {
        return ResponseEntity.ok(numberingFormatService.findAllNumberFormats());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<NumberingFormat>> getNumberFormatById(@PathVariable Long id) {
        Optional<NumberingFormat> optionalNumberFormat = numberingFormatService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(optionalNumberFormat);
    }
}