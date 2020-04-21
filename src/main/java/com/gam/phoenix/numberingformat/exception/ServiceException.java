package com.gam.phoenix.numberingformat.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 **/

@EqualsAndHashCode(callSuper = true)
@Data
public class ServiceException extends Exception {
    private static final long serialVersionUID = 5270039361418646509L;
    private final Object[] args = new Object[0];
    private String exceptionCode;

    public ServiceException(String message) {
        super(message);
    }
}
