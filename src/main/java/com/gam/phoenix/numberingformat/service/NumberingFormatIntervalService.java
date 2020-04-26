package com.gam.phoenix.numberingformat.service;

import com.gam.phoenix.numberingformat.constants.ExceptionMessage;
import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import com.gam.phoenix.numberingformat.repository.NumberingFormatIntervalRepository;
import com.gam.phoenix.numberingformat.repository.NumberingFormatRepository;
import com.gam.phoenix.spring.commons.dal.DalException;
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


    public NumberingFormatInterval saveNumberingFormatInterval(Long numberingFormatId, NumberingFormatInterval numberingFormatInterval) throws DalException {
        return this.numberingFormatRepository.findById(numberingFormatId).map(numberingFormat -> {
            numberingFormatInterval.setNumberingFormat(numberingFormat);
            return numberingFormatIntervalRepository.save(numberingFormatInterval);
        }).orElseThrow(() -> new DalException(ExceptionMessage.NUMBERING_FORMAT_NOT_FOUND_EXCEPTION, ExceptionMessage.NUMBERING_FORMAT_NOT_FOUND_EXCEPTION_MSG));
    }

    public List<NumberingFormatInterval> getAllReservedIntervalsByNumberingFormatId(Long numberingFormatId, Boolean justApplicableIntervals, Long serial) throws DalException {
        List<NumberingFormatInterval> numberingFormatIntervals = this.retrieveNumberingFormatIntervalById(numberingFormatId, justApplicableIntervals, serial);
        if (numberingFormatIntervals == null)
            throw new DalException(ExceptionMessage.NUMBERING_FORMAT_NOT_FOUND_EXCEPTION, ExceptionMessage.NUMBERING_FORMAT_NOT_FOUND_EXCEPTION_MSG);
        else return numberingFormatIntervals;
    }

    public NumberingFormatInterval deleteNumberingFormatInterval(Long numberingFormatId, Long reservedIntervalId) throws DalException {
        return this.numberingFormatIntervalRepository.findByIdAndNumberingFormatId(reservedIntervalId, numberingFormatId).map(numberingFormatInterval -> {
            numberingFormatIntervalRepository.delete(numberingFormatInterval);
            return numberingFormatInterval;
        }).orElseThrow(() -> new DalException(ExceptionMessage.NUMBERING_FORMAT_NOT_FOUND_EXCEPTION, ExceptionMessage.NUMBERING_FORMAT_NOT_FOUND_EXCEPTION_MSG));
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
