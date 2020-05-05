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
public class NumberingFormatIntervalDto implements RESTResponse {

    @ApiModelProperty("Min value is 1")
    private Long reservedStart;

    @ApiModelProperty("Must be greater than reservedStart")
    private Long reservedEnd;

}
