package com.gam.phoenix.numberingformat.model.mapper.dto;

import com.gam.phoenix.spring.commons.rest.model.response.RESTResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumberingFormatIntervalDto implements RESTResponse {

    private Long reservedStart;

    private Long reservedEnd;

}
