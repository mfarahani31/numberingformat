package com.gam.phoenix.numberingformat.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncreaseRequestModel {

    @Nullable
    @ApiModelProperty("Min value is 0")
    private Long serialLength;

    @Nullable
    @ApiModelProperty("Can be 'Full', 'Serial' or empty")
    private String returnType;
}
