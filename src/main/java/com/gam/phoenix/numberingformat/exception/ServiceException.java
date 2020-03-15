package com.gam.phoenix.numberingformat.exception;

public class ServiceException extends Exception {
    private static final long serialVersionUID = 5270039361418646509L;
    private Object[] args = new Object[0];
    private String exceptionCode;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String exceptionCode, String message) {
        super(message);
        this.setExceptionCode(exceptionCode);
    }

    public ServiceException(String exceptionCode, String message, Object[] args) {
        super(message);
        this.setExceptionCode(exceptionCode);
        this.setArgs(args);
    }

    public ServiceException(String exceptionCode, String message, Throwable cause) {
        super(message, cause);
        this.setExceptionCode(exceptionCode);
    }

    public ServiceException(String exceptionCode, String message, Throwable cause, Object[] args) {
        super(message, cause);
        this.setExceptionCode(exceptionCode);
        this.setArgs(args);
    }

    public ServiceException(String exceptionCode, Throwable cause) {
        super(exceptionCode, cause);
        this.setExceptionCode(exceptionCode);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public Object[] getArgs() {
        return this.args;
    }

    protected void setArgs(Object[] args) {
        this.args = args;
    }

    public String getExceptionCode() {
        return this.exceptionCode;
    }

    protected void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
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
