package com.gam.phoenix.numberingformat.service;

import com.gam.phoenix.numberingformat.constants.ExceptionMessage;
import com.gam.phoenix.numberingformat.model.IncreaseRequestModel;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import com.gam.phoenix.numberingformat.repository.NumberingFormatIntervalRepository;
import com.gam.phoenix.numberingformat.repository.NumberingFormatRepository;
import com.gam.phoenix.spring.commons.dal.DalException;
import org.flywaydb.core.internal.util.StringUtils;
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

    public NumberingFormat saveNumberingFormat(NumberingFormat numberingFormat) throws DalException {
        try {
            numberingFormat.setLastAllocatedSerial(this.decreaseStartAtByOneForLastAllocatedSerial(numberingFormat.getStartAt()));
            return this.numberingFormatRepository.save(numberingFormat);
        } catch (DataIntegrityViolationException e) {
            throw new DalException(ExceptionMessage.NUMBERING_FORMAT_DUPLICATION_EXCEPTION, ExceptionMessage.NUMBERING_FORMAT_DUPLICATION_EXCEPTION_MSG);
        }
    }

    public NumberingFormat findByUsageAndFormat(String usage, String format) throws DalException {
        NumberingFormat numberingFormat = this.numberingFormatRepository.findByNumberUsageAndNumberFormat(usage, format);
        if (numberingFormat == null)
            throw new DalException(ExceptionMessage.NUMBERING_FORMAT_NOT_FOUND_EXCEPTION, ExceptionMessage.NUMBERING_FORMAT_NOT_FOUND_EXCEPTION_MSG);
        else
            return numberingFormat;
    }


    public List<NumberingFormat> findAllNumberingFormats() {
        return this.numberingFormatRepository.findAll();
    }

    public Long deleteNumberingFormat(String usage, String format) throws DalException {
        Long deletedRows = this.numberingFormatRepository.deleteNumberingFormatByNumberUsageAndNumberFormat(usage, format);
        if (deletedRows == 0)
            throw new DalException(ExceptionMessage.NUMBERING_FORMAT_NOT_FOUND_EXCEPTION, ExceptionMessage.NUMBERING_FORMAT_NOT_FOUND_EXCEPTION_MSG);
        else
            return deletedRows;
    }

    public String increaseLastAllocatedSerialByOne(String usage, String format, IncreaseRequestModel
            increaseRequestModel) throws DalException {

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
            assert returnType != null;
            return specifyResultWithCertainLength(returnType, format, newSerialWithProperFormat, newSerial);
        }
    }

    private String specifyResultWithCertainLength(String returnType, String format, String newSerialWithProperFormat, Long newSerial) {
        String result;
        if (returnType.equals("Full"))
            result = format + newSerialWithProperFormat;
        else
            result = newSerial.toString();
        return result;
    }

    private void updateLastAllocatedSerial(Long newSerial, String usage, String format) throws DalException {
        int updatedRows = this.numberingFormatRepository.updateLastAllocatedSerial(newSerial, usage, format);
        if (updatedRows <= 0)
            throw new DalException(ExceptionMessage.NUMBERING_FORMAT_NOT_FOUND_EXCEPTION, ExceptionMessage.NUMBERING_FORMAT_NOT_FOUND_EXCEPTION_MSG);
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
        String serialWithZeroAtBeginning = StringUtils.leftPad("" + lastAllocatedSerial, serialLength.intValue(), '0');

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
                newSerial = numberingFormatInterval.getReservedEnd() + 1L;
        }
        return newSerial;
    }
}
