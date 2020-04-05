package com.gam.phoenix.numberingformat.exception;

import lombok.Data;

@Data
public class ServiceException extends Exception {
    private static final long serialVersionUID = 5270039361418646509L;
    private Object[] args = new Object[0];
    private String exceptionCode;

    public ServiceException(String message) {
        super(message);
    }

    private String augmentMessageWithArgs(String message) {
        String errorMessage = message;

        for (Object argument : this.args) {
            errorMessage = errorMessage.replaceFirst("\\{}", argument.toString());
        }

        return errorMessage;
    }

    @Override
    public String getMessage() {
        return this.augmentMessageWithArgs(super.getMessage());
    }
}
