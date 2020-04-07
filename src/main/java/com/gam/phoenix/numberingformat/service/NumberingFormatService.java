package com.gam.phoenix.numberingformat.service;

import com.gam.phoenix.numberingformat.constants.ErrorMessages;
import com.gam.phoenix.numberingformat.exception.BusinessException;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import com.gam.phoenix.numberingformat.repository.NumberingFormatIntervalRepository;
import com.gam.phoenix.numberingformat.repository.NumberingFormatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;

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

    public String increaseLastAllocatedSerialByOne(String usage, String format, Long serialLength, String returnType, NumberingFormat inputNumberingFormat) throws BusinessException {
        try {
            Long serial = manageSerialLength(serialLength, inputNumberingFormat.getLastAllocatedSerial());


            String type = manageSerialType(returnType);
            NumberingFormat numberingFormat = this.numberingFormatRepository.findByNumberUsageAndNumberFormat(usage, format);
            this.numberingFormatRepository.increaseLastAllocatedSerialByOne(usage, format);

            List<NumberingFormatInterval> numberingFormatIntervals = numberingFormatIntervalRepository.findAllByNumberingFormatIntervalByNumberingFormatId(numberingFormat.getId());
            for (NumberingFormatInterval numberingFormatInterval : numberingFormatIntervals) {
                if (numberingFormatInterval.getReservedStart() < numberingFormat.getLastAllocatedSerial() || numberingFormatInterval.getReservedEnd() > numberingFormat.getLastAllocatedSerial())
                    serial = serial + 1;
            }
            if (returnType == null || returnType.equals("Serial"))
                return serial.toString();
            else if (returnType.equals("Full"))
                return format + serial.toString();

            return numberingFormat.getLastAllocatedSerial().toString();

        } catch (Exception e) {
            inputNumberingFormat.setLastAllocatedSerial(1L);
            inputNumberingFormat.setStartAt(1L);
            inputNumberingFormat.setNumberUsage(usage);
            inputNumberingFormat.setNumberFormat(format);
            NumberingFormat numberingFormat = this.numberingFormatRepository.save(inputNumberingFormat);
            return numberingFormat.getLastAllocatedSerial().toString();
        }
    }

    private String manageSerialType(String returnType) {
        if (returnType == null || returnType.equals("Serial"))
            return "serial";
        else if (returnType.equals("Full"))
            return "Full";
        return null;
    }

    private Long manageSerialLength(Long serialLength, Long lastAllocatedSerial) {
        if (serialLength == null || serialLength == 0)
            return lastAllocatedSerial;
        else if (serialLength > 0) {
            return addZeroAtBeginningOfSerial(serialLength, lastAllocatedSerial);
        } else if (serialLength < lastAllocatedSerial.toString().length())
            return null;
        return null;
    }

    private Long addZeroAtBeginningOfSerial(Long serialLength, Long lastAllocatedSerial) {
        String serialWithZeroAtBeginning = String.format("%0" + serialLength + "d", lastAllocatedSerial); // 0009
        Long newAllocatedSerial = Long.valueOf(serialWithZeroAtBeginning);
        if (newAllocatedSerial.toString().length() > serialLength)
            return lastAllocatedSerial;
        else return newAllocatedSerial;
    }

    public Long decreaseStartAtByOneForLastAllocatedSerial(Long startAt) {
        return startAt - 1;
    }

    public Long getNextAllocatedSerial(NumberingFormat numberingFormat) {
        return numberingFormat.getLastAllocatedSerial() + 1;
    }
}
