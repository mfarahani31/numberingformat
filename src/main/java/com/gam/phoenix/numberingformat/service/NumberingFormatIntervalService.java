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

    public List<NumberingFormatInterval> findByNumberingFormatId(Long numberingFormatId, Boolean justApplicableIntervals, Long serial) throws BusinessException {
        try {
            return this.retrieveNumberingFormatIntervalById(numberingFormatId, justApplicableIntervals, serial);
        } catch (Exception e) {
            throw new BusinessException(ErrorMessages.NOT_EXIST);
        }
    }

    public void deleteNumberingFormatInterval(Long reservedIntervalId) throws BusinessException {
        try {
            this.numberingFormatIntervalRepository.deleteById(reservedIntervalId);
        } catch (Exception e) {
            throw new BusinessException(ErrorMessages.NOT_EXIST);
        }
    }

    public List<NumberingFormatInterval> retrieveNumberingFormatIntervalById(Long numberingFormatId, Boolean justApplicableIntervals, Long serial) {
        if (justApplicableIntervals == false || justApplicableIntervals == null) {
            return this.numberingFormatIntervalRepository.findAllByNumberingFormatIntervalByNumberingFormatId(numberingFormatId);
        } else if (serial != null) {
            return this.numberingFormatIntervalRepository.findAllByNumberUsageAndNumberFormatAndReservedIsGreaterThenSerial(numberingFormatId, serial);
        } else {
            serial = 1L;
            return this.numberingFormatIntervalRepository.findAllByNumberUsageAndNumberFormatAndReservedIsGreaterThenSerial(numberingFormatId, serial);
        }
    }
}
