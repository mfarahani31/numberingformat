package com.gam.phoenix.numberingformat.service;

import com.gam.phoenix.numberingformat.constants.ErrorMessages;
import com.gam.phoenix.numberingformat.exception.BusinessException;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.repository.NumberingFormatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@Service
public class NumberingFormatService {

    private NumberingFormatRepository numberingFormatRepository;

    @Autowired
    public NumberingFormatService(NumberingFormatRepository numberingFormatRepository) {
        this.numberingFormatRepository = numberingFormatRepository;
    }

    public NumberingFormat saveNumberFormat(NumberingFormat numberFormat) throws BusinessException {
        try {
            numberFormat.setLastAllocatedSerial(decreaseStartAtByOneForLastAllocatedSerial(numberFormat.getStartAt()));
            return this.numberingFormatRepository.save(numberFormat);
        } catch (HttpServerErrorException e) {
            throw new BusinessException(ErrorMessages.DUPLICATE_NUMBERFORMAT);
        }
    }

    public NumberingFormat findByUsageAndFormat(String usage, String format) throws BusinessException {
        try {
            return this.numberingFormatRepository.findByNumberUsageAndNumberFormat(usage, format);
        } catch (Exception e) {
            throw new BusinessException(ErrorMessages.NOT_EXIST);
        }
    }


    public List<NumberingFormat> findAllNumberFormats() {
        return this.numberingFormatRepository.findAll();
    }

    public void deleteNumberingFormat(String usage, String format) throws BusinessException {
        try {
            this.numberingFormatRepository.deleteNumberingFormatByNumberUsageAndNumberFormat(usage, format);
        } catch (Exception e) {
            throw new BusinessException(ErrorMessages.NOT_EXIST);
        }
    }

    public NumberingFormat increaseLastAllocatedSerialByOne(String usage, String format) throws BusinessException {
        try {
            NumberingFormat numberingFormat = this.numberingFormatRepository.findByNumberUsageAndNumberFormat(usage, format);
            numberingFormat.setLastAllocatedSerial(numberingFormat.getLastAllocatedSerial() + 1);
            return numberingFormatRepository.save(numberingFormat);
        } catch (HttpServerErrorException e) {
            throw new BusinessException(ErrorMessages.NOT_EXIST);
        }
    }

    public Long decreaseStartAtByOneForLastAllocatedSerial(Long startAt) {
        return startAt - 1;
    }

    public Long getNextAllocatedSerial(NumberingFormat numberingFormat) {
        return numberingFormat.getLastAllocatedSerial() + 1;
    }
}
