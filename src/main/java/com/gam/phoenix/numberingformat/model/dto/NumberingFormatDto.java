package com.gam.phoenix.numberingformat.model.dto;

import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import lombok.Data;

import java.util.List;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 */
@Data
public class NumberingFormatDto {


    private String numberUsage;


    private String numberFormat;

    private Long startAt;

    private Long lastAllocatedSerial;

    private List<NumberingFormatInterval> numberingFormatIntervalList;
}
