package com.gam.phoenix.numberingformat.model.dto;

import com.gam.phoenix.numberingformat.model.NumberingFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumberingFormatIntervalDto {

    private Long reservedStart;

    private Long reservedEnd;


    private NumberingFormat numberingFormat;
}
