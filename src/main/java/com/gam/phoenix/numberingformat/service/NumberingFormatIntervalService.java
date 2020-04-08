package com.gam.phoenix.numberingformat.service;

import com.gam.phoenix.numberingformat.constants.ErrorMessages;
import com.gam.phoenix.numberingformat.exception.BusinessException;
import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import com.gam.phoenix.numberingformat.repository.NumberingFormatIntervalRepository;
import com.gam.phoenix.numberingformat.repository.NumberingFormatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NumberingFormatIntervalService {

    private NumberingFormatIntervalRepository numberingFormatIntervalRepository;
    private NumberingFormatRepository numberingFormatRepository;

    @Autowired
    public NumberingFormatIntervalService(NumberingFormatIntervalRepository numberingFormatIntervalRepository, NumberingFormatRepository numberingFormatRepository) {
        this.numberingFormatIntervalRepository = numberingFormatIntervalRepository;
        this.numberingFormatRepository = numberingFormatRepository;
    }


    public NumberingFormatInterval saveNumberingFormatInterval(Long numberingFormatId, NumberingFormatInterval numberingFormatInterval) throws BusinessException {
        return this.numberingFormatRepository.findById(numberingFormatId).map(numberingFormat -> {
            numberingFormatInterval.setNumberingFormat(numberingFormat);
            return numberingFormatIntervalRepository.save(numberingFormatInterval);
        }).orElseThrow(() -> new BusinessException(ErrorMessages.DUPLICATE_NUMBERFORMAT));
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
            return this.numberingFormatIntervalRepository.findAllNumberingFormatIntervalByNumberingFormatId(numberingFormatId);
        } else if (serial != null) {
            return this.numberingFormatIntervalRepository.findAllByNumberUsageAndNumberFormatAndReservedIsGreaterThenSerial(numberingFormatId, serial);
        } else {
            serial = 1L;
            return this.numberingFormatIntervalRepository.findAllByNumberUsageAndNumberFormatAndReservedIsGreaterThenSerial(numberingFormatId, serial);
        }
    }
}
