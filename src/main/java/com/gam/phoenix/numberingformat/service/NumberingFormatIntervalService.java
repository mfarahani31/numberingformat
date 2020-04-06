package com.gam.phoenix.numberingformat.service;

import com.gam.phoenix.numberingformat.constants.ErrorMessages;
import com.gam.phoenix.numberingformat.exception.BusinessException;
import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import com.gam.phoenix.numberingformat.repository.NumberingFormatIntervalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NumberingFormatIntervalService {

    private NumberingFormatIntervalRepository numberingFormatIntervalRepository;

    @Autowired
    public NumberingFormatIntervalService(NumberingFormatIntervalRepository numberingFormatIntervalRepository) {
        this.numberingFormatIntervalRepository = numberingFormatIntervalRepository;
    }

    public NumberingFormatInterval saveNumberingFormatInterval(NumberingFormatInterval numberingFormatInterval) throws BusinessException {
        try {
            return this.numberingFormatIntervalRepository.save(numberingFormatInterval);
        } catch (Exception e) {
            throw new BusinessException(ErrorMessages.DUPLICATE_NUMBERFORMAT);
        }
    }

    public List<NumberingFormatInterval> findAllNumberingFormatInterval() throws BusinessException {
        try {
            return this.numberingFormatIntervalRepository.findAll();
        } catch (Exception e) {
            throw new BusinessException(ErrorMessages.NOT_EXIST);
        }
    }

    public List<NumberingFormatInterval> findByUsageAndFormat(String usage, String format, boolean justApplicable, Long serial) throws BusinessException {
        try {
            return this.retrieveNumberingFormatInterval(usage, format, justApplicable, serial);
        } catch (Exception e) {
            throw new BusinessException(ErrorMessages.NOT_EXIST);
        }
    }

    public void deleteNumberingFormatInterval(String usage, String format, Long start, Long end) throws BusinessException {
        try {
            this.numberingFormatIntervalRepository.deleteNumberingFormatIntervalByNumberUsageAndNumberFormatAndReservedStartAndReservedEnd(usage, format, start, end);
        } catch (Exception e) {
            throw new BusinessException(ErrorMessages.NOT_EXIST);
        }
    }

    public List<NumberingFormatInterval> retrieveNumberingFormatInterval(String usage, String format, boolean justApplicable, Long serial) {
        if (!justApplicable) {
            return this.numberingFormatIntervalRepository.findAllByNumberUsageAndNumberFormat(usage, format);
        } else if (serial != null) {
            return this.numberingFormatIntervalRepository.findAllByNumberUsageAndNumberFormatAndReservedIsGreaterThenSerial(usage, format, serial);
        } else {
            serial = 1L;
            return this.numberingFormatIntervalRepository.findAllByNumberUsageAndNumberFormatAndReservedIsGreaterThenSerial(usage, format, serial);
        }
    }
}
