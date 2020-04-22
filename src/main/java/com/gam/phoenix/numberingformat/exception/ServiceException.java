package com.gam.phoenix.numberingformat.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 **/

@Setter
@Getter
@NoArgsConstructor
@ToString
public class ServiceException extends Exception {
    private static final long serialVersionUID = 5270039361418646509L;
    private final Object[] args = new Object[0];
    private String exceptionCode;

    public ServiceException(String message) {
        super(message);
    }
}
