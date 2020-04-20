package com.gam.phoenix.numberingformat.service;

import com.gam.phoenix.numberingformat.constants.ErrorMessages;
import com.gam.phoenix.numberingformat.exception.RecordNotFoundException;
import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import com.gam.phoenix.numberingformat.repository.NumberingFormatIntervalRepository;
import com.gam.phoenix.numberingformat.repository.NumberingFormatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 **/

@Service
public class NumberingFormatIntervalService {

    private NumberingFormatIntervalRepository numberingFormatIntervalRepository;
    private NumberingFormatRepository numberingFormatRepository;

    @Autowired
    public NumberingFormatIntervalService(NumberingFormatIntervalRepository numberingFormatIntervalRepository, NumberingFormatRepository numberingFormatRepository) {
        this.numberingFormatIntervalRepository = numberingFormatIntervalRepository;
        this.numberingFormatRepository = numberingFormatRepository;
    }


    public NumberingFormatInterval saveNumberingFormatInterval(Long numberingFormatId, NumberingFormatInterval numberingFormatInterval) {
        return this.numberingFormatRepository.findById(numberingFormatId).map(numberingFormat -> {
            numberingFormatInterval.setNumberingFormat(numberingFormat);
            return numberingFormatIntervalRepository.save(numberingFormatInterval);
        }).orElseThrow(() -> new RecordNotFoundException(ErrorMessages.NOT_EXIST));
    }

    public List<NumberingFormatInterval> getAllReservedIntervalsByNumberingFormatId(Long numberingFormatId, Boolean justApplicableIntervals, Long serial) {
        List<NumberingFormatInterval> numberingFormatIntervals = this.retrieveNumberingFormatIntervalById(numberingFormatId, justApplicableIntervals, serial);
        if (numberingFormatIntervals == null || numberingFormatIntervals.isEmpty())
            throw new RecordNotFoundException(ErrorMessages.NOT_EXIST);
        else return numberingFormatIntervals;
    }

    public NumberingFormatInterval deleteNumberingFormatInterval(Long numberingFormatId, Long reservedIntervalId) {
        return this.numberingFormatIntervalRepository.findByIdAndNumberingFormatId(reservedIntervalId, numberingFormatId).map(numberingFormatInterval -> {
            numberingFormatIntervalRepository.delete(numberingFormatInterval);
            return numberingFormatInterval;
        }).orElseThrow(() -> new RecordNotFoundException(ErrorMessages.NOT_EXIST));
    }

    private List<NumberingFormatInterval> retrieveNumberingFormatIntervalById(Long numberingFormatId, Boolean justApplicableIntervals, Long serial) {
        if (justApplicableIntervals.equals(false)) {
            return this.numberingFormatIntervalRepository.findByNumberingFormatId(numberingFormatId);
        } else if (serial != null) {
            return this.numberingFormatIntervalRepository.findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(numberingFormatId, serial);
        } else {
            serial = 1L;
            return this.numberingFormatIntervalRepository.findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(numberingFormatId, serial);
        }
    }
}
