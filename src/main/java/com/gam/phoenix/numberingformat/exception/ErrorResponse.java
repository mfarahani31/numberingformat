package com.gam.phoenix.numberingformat.exception;


public class ErrorResponse {

    private String timestamp;
    private String message;
    private String details;

    public ErrorResponse(String timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
