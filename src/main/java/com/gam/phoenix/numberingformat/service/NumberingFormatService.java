package com.gam.phoenix.numberingformat.service;

import com.gam.phoenix.numberingformat.constants.ErrorMessages;
import com.gam.phoenix.numberingformat.exception.BusinessException;
import com.gam.phoenix.numberingformat.exception.RecordNotFoundException;
import com.gam.phoenix.numberingformat.model.IncreaseRequestModel;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import com.gam.phoenix.numberingformat.repository.NumberingFormatIntervalRepository;
import com.gam.phoenix.numberingformat.repository.NumberingFormatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 **/

@Service
public class NumberingFormatService {

    private NumberingFormatRepository numberingFormatRepository;
    private NumberingFormatIntervalRepository numberingFormatIntervalRepository;

    @Autowired
    public NumberingFormatService(NumberingFormatRepository numberingFormatRepository, NumberingFormatIntervalRepository numberingFormatIntervalRepository) {
        this.numberingFormatRepository = numberingFormatRepository;
        this.numberingFormatIntervalRepository = numberingFormatIntervalRepository;
    }

    public NumberingFormat saveNumberingFormat(NumberingFormat numberingFormat) throws BusinessException {
        try {
            numberingFormat.setLastAllocatedSerial(this.decreaseStartAtByOneForLastAllocatedSerial(numberingFormat.getStartAt()));
            return this.numberingFormatRepository.save(numberingFormat);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(ErrorMessages.DUPLICATE_NUMBERFORMAT);
        }
    }

    public NumberingFormat findByUsageAndFormat(String usage, String format) {
        NumberingFormat numberingFormat = this.numberingFormatRepository.findByNumberUsageAndNumberFormat(usage, format);
        if (numberingFormat == null)
            throw new RecordNotFoundException(ErrorMessages.NOT_EXIST);
        else
            return numberingFormat;
    }


    public List<NumberingFormat> findAllNumberingFormats() {
        return this.numberingFormatRepository.findAll();
    }

    public Long deleteNumberingFormat(String usage, String format) {
        Long deletedRows = this.numberingFormatRepository.deleteNumberingFormatByNumberUsageAndNumberFormat(usage, format);
        if (deletedRows == 0)
            throw new RecordNotFoundException(ErrorMessages.NOT_EXIST);
        else
            return deletedRows;
    }

    public String increaseLastAllocatedSerialByOne(String usage, String format, IncreaseRequestModel
            increaseRequestModel) throws BusinessException {

        NumberingFormat numberingFormat = this.numberingFormatRepository.findByNumberUsageAndNumberFormat(usage, format);
        if (numberingFormat == null) {
            numberingFormat = this.initializeNumberingFormatWithNewValues(usage, format);
            numberingFormat = this.saveNumberingFormat(numberingFormat);
            return numberingFormat.getLastAllocatedSerial().toString();
        } else {
            Long newSerial = this.getNextValidAllocatedSerial(numberingFormat);
            this.updateLastAllocatedSerial(newSerial, usage, format);

            increaseRequestModel = initializeIncreaseRequestModel(increaseRequestModel);
            String newSerialWithProperFormat = generateSerialWithRequiredLength(increaseRequestModel.getSerialLength(), newSerial);
            String returnType = increaseRequestModel.getReturnType();
            String result;
            assert returnType != null;
            if (returnType.equals("Full"))
                result = format + newSerialWithProperFormat;
            else
                result = newSerial.toString();
            return result;
        }
    }

    private void updateLastAllocatedSerial(Long newSerial, String usage, String format) throws BusinessException {
        try {
            this.numberingFormatRepository.updateLastAllocatedSerial(newSerial, usage, format);
        } catch (Exception e) {
            throw new BusinessException(ErrorMessages.NOT_EXIST);
        }
    }

    private NumberingFormat initializeNumberingFormatWithNewValues(String usage, String format) {
        NumberingFormat inputNumberingFormat = new NumberingFormat();
        inputNumberingFormat.setLastAllocatedSerial(1L);
        inputNumberingFormat.setStartAt(1L);
        inputNumberingFormat.setNumberUsage(usage);
        inputNumberingFormat.setNumberFormat(format);
        return inputNumberingFormat;
    }

    private IncreaseRequestModel initializeIncreaseRequestModel(IncreaseRequestModel increaseRequestModel) {
        if (increaseRequestModel == null)
            increaseRequestModel = new IncreaseRequestModel();
        if (increaseRequestModel.getSerialLength() == null)
            increaseRequestModel.setSerialLength(0L);
        if (increaseRequestModel.getReturnType() == null)
            increaseRequestModel.setReturnType("Serial");
        return increaseRequestModel;
    }

    private String generateSerialWithRequiredLength(Long serialLength, Long newSerial) {
        if (serialLength > 0) {
            return addZeroAtBeginningOfSerial(serialLength, newSerial);
        } else
            return newSerial.toString();
    }

    private String addZeroAtBeginningOfSerial(Long serialLength, Long lastAllocatedSerial) {
        String serialWithZeroAtBeginning = String.format("%0" + serialLength + "d", lastAllocatedSerial); // Like : 0009
        if (serialWithZeroAtBeginning.length() > serialLength)
            return lastAllocatedSerial.toString();
        else return serialWithZeroAtBeginning;
    }

    private Long decreaseStartAtByOneForLastAllocatedSerial(Long startAt) {
        return startAt - 1;
    }

    public Long getNextValidAllocatedSerial(NumberingFormat numberingFormat) {
        Long newSerial = numberingFormat.getLastAllocatedSerial() + 1;
        List<NumberingFormatInterval> numberingFormatIntervals = this.numberingFormatIntervalRepository.findAllByNumberingFormatIdAndReservedEndIsGreaterThanSerial(numberingFormat.getId(), newSerial);
        for (NumberingFormatInterval numberingFormatInterval : numberingFormatIntervals) {
            if (numberingFormatInterval.getReservedStart() <= newSerial && numberingFormatInterval.getReservedEnd() >= newSerial)
                newSerial = numberingFormatInterval.getReservedEnd() + 1;
        }
        return newSerial;
    }
}
