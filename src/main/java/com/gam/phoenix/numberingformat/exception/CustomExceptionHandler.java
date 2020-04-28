package com.gam.phoenix.numberingformat.exception;

import com.gam.phoenix.numberingformat.TimePicker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 **/

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(TimePicker.getCurrentTime(), ex.getMessage(), request.getDescription(false));
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Request: " + request.getContextPath() + " raised " + ex, ex);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(TimePicker.getCurrentTime(), ex.getMessage(), request.getDescription(false));
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Request: " + request.getContextPath() + " raised " + ex, ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}