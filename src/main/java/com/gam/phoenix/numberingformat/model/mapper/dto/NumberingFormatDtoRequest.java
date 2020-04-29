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
public class NumberingFormatDtoRequest implements RESTResponse {

    private String numberUsage;

    private String numberFormat;

    private Long startAt;
}
