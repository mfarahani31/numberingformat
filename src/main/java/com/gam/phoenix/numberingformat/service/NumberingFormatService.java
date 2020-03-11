package com.gam.phoenix.numberingformat.service;

import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.repository.NumberingFormatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NumberingFormatService {

    private NumberingFormatRepository numberingFormatRepository;

    @Autowired
    public NumberingFormatService(NumberingFormatRepository numberingFormatRepository) {
        this.numberingFormatRepository = numberingFormatRepository;
    }

    public NumberingFormat saveNumberFormat(NumberingFormat numberFormat) {
        return this.numberingFormatRepository.save(numberFormat);
    }

    public Optional<NumberingFormat> findById(Long id) {
        return this.numberingFormatRepository.findById(id);
    }

    public List<NumberingFormat> findAllNumberFormats() {
        return this.numberingFormatRepository.findAll();
    }
}
