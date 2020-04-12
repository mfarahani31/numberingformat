package com.gam.phoenix.numberingformat;

import com.gam.phoenix.numberingformat.model.NumberingFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.Date;

public class MotherObject {
    public static NumberingFormat getAnyValidNumberingFormat() {

        Date date = new Date();
        return new NumberingFormat(1L,
                "test1",
                "test1",
                300L,
                301L,
                null);
    }
//
//    public static NumberingFormatInterval getAnyValidNumberingFormatInterval() {
//
//        Date date = new Date();
//        return new NumberingFormatInterval(1L,
//                "test1",
//                "test1",
//                300L,
//                400L,
//                "admin",
//                date,
//                "admin",
//                date);
//    }
//
//    public static Optional<NumberingFormat> getAnyOptionalOfValidNumberingFormat() {
//
//        Date date = new Date();
//        NumberingFormat numberingFormat = new NumberingFormat(1L,
//                "test1",
//                "test1",
//                300L,
//                301L,
//                "admin",
//                date,
//                "admin",
//                date);
//        return Optional.of(numberingFormat);
//    }

    public static Long decreaseStartAtByOneForLastAllocatedSerial(Long startAt) {
        return startAt - 1;
    }

    public static HttpEntity<String> getValidHttpEntityWithHeaderUsername() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("username", "username");
        return new HttpEntity<>(null, headers);
    }

    public static HttpEntity<NumberingFormat> getValidHttpEntityWithHeaderUsernameAndBodyNumberingFormat() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("username", "username");
        return new HttpEntity<>(MotherObject.getAnyValidNumberingFormat(), headers);
    }

}
