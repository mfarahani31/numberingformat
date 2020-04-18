package com.gam.phoenix.numberingformat;

import com.gam.phoenix.numberingformat.model.IncreaseRequestModel;
import com.gam.phoenix.numberingformat.model.NumberingFormat;
import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
import com.gam.phoenix.numberingformat.model.dto.NumberingFormatDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 **/
public class MotherObject {
    public static NumberingFormat getAnyValidNumberingFormat() {

        return new NumberingFormat(1L,
                "test1",
                "test1",
                300L,
                301L,
                null);
    }

    public static IncreaseRequestModel getAnyValidIncreaseRequestModel() {
        return new IncreaseRequestModel(3L, "Serial");
    }

    public static NumberingFormatDto getAnyValidNumberingFormatDto() {
        return new NumberingFormatDto("test1",
                "test1",
                300L,
                301L,
                null);
    }

    public static NumberingFormatInterval getAnyValidNumberingFormatInterval() {

        return new NumberingFormatInterval(1L,
                300L,
                400L, null);
    }

    public static Optional<NumberingFormat> getAnyOptionalOfValidNumberingFormat() {

        return Optional.of(getAnyValidNumberingFormat());
    }

    public static Long decreaseStartAtByOneForLastAllocatedSerial(Long startAt) {
        return startAt - 1;
    }

    public static HttpEntity<String> getValidHttpEntityWithHeaderUsername() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("username", "admin");
        return new HttpEntity<>(null, headers);
    }

    public static HttpEntity<NumberingFormat> getValidHttpEntityWithHeaderUsernameAndBodyNumberingFormat() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("username", "username");
        return new HttpEntity<>(MotherObject.getAnyValidNumberingFormat(), headers);
    }

    public static HttpEntity<NumberingFormatInterval> getValidHttpEntityWithHeaderUsernameAndBodyNumberingFormatInterval() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("username", "username");
        return new HttpEntity<>(MotherObject.getAnyValidNumberingFormatInterval(), headers);
    }


}
