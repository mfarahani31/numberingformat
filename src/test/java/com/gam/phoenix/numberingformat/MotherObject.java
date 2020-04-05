package com.gam.phoenix.numberingformat;

import com.gam.phoenix.numberingformat.model.NumberingFormat;

import java.util.Date;
import java.util.Optional;

public class MotherObject {
    public static NumberingFormat getAnyValidNumberingFormat() {

        Date date = new Date();
        return new NumberingFormat(1L,
                "test1",
                "test1",
                300L,
                301L,
                "admin",
                date,
                "admin",
                date);
    }

    public static Optional<NumberingFormat> getAnyOptionalOfValidNumberingFormat() {

        Date date = new Date();
        NumberingFormat numberingFormat = new NumberingFormat(1L,
                "test1",
                "test1",
                300L,
                301L,
                "admin",
                date,
                "admin",
                date);
        return Optional.of(numberingFormat);
    }

    public static Long decreaseStartAtByOneForLastAllocatedSerial(Long startAt) {
        return startAt - 1;
    }
}
