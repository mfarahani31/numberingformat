package com.gam.phoenix.numberingformat.model.dto;

import com.gam.phoenix.numberingformat.model.NumberingFormat;
import lombok.Data;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 */
@Data
public class NumberingFormatIntervalDto {

    private Long reservedStart;

    private Long reservedEnd;


    private NumberingFormat numberingFormat;
}
