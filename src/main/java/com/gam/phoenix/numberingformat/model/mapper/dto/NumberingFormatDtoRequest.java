package com.gam.phoenix.numberingformat.model.mapper.dto;

import com.gam.phoenix.spring.commons.rest.model.response.RESTResponse;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("Must not start with '/'")
    private String numberUsage;

    @ApiModelProperty("Must not start with '/'")
    private String numberFormat;

    @ApiModelProperty("Min value is 1")
    private Long startAt;
}
