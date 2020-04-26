package com.gam.phoenix.numberingformat.controller.model.response;

import com.gam.phoenix.spring.commons.rest.model.response.RESTResponse;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "SerialResponse", description = "Represent the Serial with correct Format")
public class SerialResponse implements RESTResponse {
    private String serial;
}
