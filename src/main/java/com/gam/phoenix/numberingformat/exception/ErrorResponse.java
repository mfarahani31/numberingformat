package com.gam.phoenix.numberingformat.exception;


import lombok.Getter;

@Getter
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
}
