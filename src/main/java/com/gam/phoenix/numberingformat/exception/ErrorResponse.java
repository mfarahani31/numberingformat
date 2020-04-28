package com.gam.phoenix.numberingformat.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;


/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 **/

@Getter
public class ErrorResponse {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
