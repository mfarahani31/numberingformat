package com.gam.phoenix.numberingformat.service;

import com.gam.phoenix.numberingformat.constants.ErrorMessages;
import com.gam.phoenix.numberingformat.exception.BusinessException;
import com.gam.phoenix.numberingformat.model.IncreaseRequestModel;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import com.gam.phoenix.numberingformat.repository.NumberingFormatIntervalRepository;
import com.gam.phoenix.numberingformat.repository.NumberingFormatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;


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

    public NumberingFormat saveNumberFormat(NumberingFormat numberFormat) throws BusinessException {
        try {
            numberFormat.setLastAllocatedSerial(decreaseStartAtByOneForLastAllocatedSerial(numberFormat.getStartAt()));
            return this.numberingFormatRepository.save(numberFormat);
        } catch (HttpServerErrorException e) {
            throw new BusinessException(ErrorMessages.DUPLICATE_NUMBERFORMAT);
        }
    }

    public Optional<NumberingFormat> findByUsageAndFormat(String usage, String format) throws BusinessException {
        try {
            return Optional.of(this.numberingFormatRepository.findByNumberUsageAndNumberFormat(usage, format));
        } catch (Exception e) {
            throw new BusinessException(ErrorMessages.NOT_EXIST);
        }
    }


    public List<NumberingFormat> findAllNumberFormats() throws BusinessException {
        try {
            return this.numberingFormatRepository.findAll();
        } catch (Exception e) {
            throw new BusinessException(ErrorMessages.NOT_EXIST);
        }

    }

    public void deleteNumberingFormat(String usage, String format) throws BusinessException {
        try {
            this.numberingFormatRepository.deleteNumberingFormatByNumberUsageAndNumberFormat(usage, format);
        } catch (Exception e) {
            throw new BusinessException(ErrorMessages.NOT_EXIST);
        }
    }

    public String increaseLastAllocatedSerialByOne(String usage, String format, IncreaseRequestModel increaseRequestModel) {
        try {
            // increase serial by one in db
            NumberingFormat numberingFormat = this.numberingFormatRepository.findByNumberUsageAndNumberFormat(usage, format);
            this.numberingFormatRepository.updateLastAllocatedSerial(numberingFormat.getLastAllocatedSerial() + 1, usage, format);

            //=================================

            Long newSerial = this.findByUsageAndFormat(usage, format).orElseThrow(() -> new BusinessException(ErrorMessages.NOT_EXIST)).getLastAllocatedSerial();
            List<NumberingFormatInterval> numberingFormatIntervals = numberingFormatIntervalRepository.findByNumberingFormatId(numberingFormat.getId());
            for (NumberingFormatInterval numberingFormatInterval : numberingFormatIntervals) {
                if (numberingFormatInterval.getReservedStart() <= numberingFormat.getLastAllocatedSerial() && numberingFormatInterval.getReservedEnd() >= numberingFormat.getLastAllocatedSerial()) {
                    newSerial = numberingFormatInterval.getReservedEnd() + 1;
                    this.numberingFormatRepository.updateLastAllocatedSerial(newSerial, usage, format);
                }
            }

            String newSerialWithProperFormat = generateSerialWithProperFormat(increaseRequestModel.getSerialLength(), newSerial);

            // load serial from reserved serial in interval table

            if (increaseRequestModel.getReturnType().equals("Full"))
                return format + newSerialWithProperFormat;
            else
                return newSerial.toString();

        } catch (Exception e) {
            NumberingFormat numberingFormat = this.insertNewNumberingFormatWithNewValues(usage, format);
            return numberingFormat.getLastAllocatedSerial().toString();
        }
    }

    private NumberingFormat insertNewNumberingFormatWithNewValues(String usage, String format) {
        NumberingFormat inputNumberingFormat = new NumberingFormat();
        inputNumberingFormat.setLastAllocatedSerial(1L);
        inputNumberingFormat.setStartAt(1L);
        inputNumberingFormat.setNumberUsage(usage);
        inputNumberingFormat.setNumberFormat(format);
        return this.numberingFormatRepository.save(inputNumberingFormat);
    }

    private String generateSerialWithProperFormat(Long serialLength, Long newSerial) {
        if (serialLength > 0) {
            return addZeroAtBeginningOfSerial(serialLength, newSerial);
        } else
            return newSerial.toString();
    }

    private String addZeroAtBeginningOfSerial(Long serialLength, Long lastAllocatedSerial) {
        String serialWithZeroAtBeginning = String.format("%0" + serialLength + "d", lastAllocatedSerial); // 0009
        if (serialWithZeroAtBeginning.length() > serialLength)
            return lastAllocatedSerial.toString();
        else return serialWithZeroAtBeginning;
    }

    public Long decreaseStartAtByOneForLastAllocatedSerial(Long startAt) {
        return startAt - 1;
    }

    public Long getNextAllocatedSerial(NumberingFormat numberingFormat) {
        Long nextSerial = numberingFormat.getLastAllocatedSerial() + 1;
        List<NumberingFormatInterval> numberingFormatIntervals = numberingFormatIntervalRepository.findByNumberingFormatId(numberingFormat.getId());
        for (NumberingFormatInterval numberingFormatInterval : numberingFormatIntervals) {
            if (numberingFormatInterval.getReservedStart() <= nextSerial && numberingFormatInterval.getReservedEnd() >= nextSerial)
                nextSerial = numberingFormatInterval.getReservedEnd() + 1;
        }
        return nextSerial;
    }
}
