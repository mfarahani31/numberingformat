package com.gam.phoenix.numberingformat.model.dto;

import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import com.gam.phoenix.spring.commons.rest.model.response.RESTResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumberingFormatDto implements RESTResponse {

    private String numberUsage;

    private String numberFormat;

    private Long startAt;

    private Long lastAllocatedSerial;

    private List<NumberingFormatInterval> numberingFormatIntervalList;
}
