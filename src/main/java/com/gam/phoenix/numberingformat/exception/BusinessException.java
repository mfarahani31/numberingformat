package com.gam.phoenix.numberingformat.exception;


public class BusinessException extends ServiceException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String exceptionCode, String message) {
        super(exceptionCode, message);
    }

    public BusinessException(String exceptionCode, String message, Object[] args) {
        super(exceptionCode, message, args);
    }

    public BusinessException(String exceptionCode, String message, Throwable cause) {
        super(exceptionCode, message, cause);
    }

    public BusinessException(String exceptionCode, String message, Throwable cause, Object[] args) {
        super(exceptionCode, message, cause, args);
    }

    public BusinessException(String exceptionCode, Throwable cause) {
        super(exceptionCode, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}