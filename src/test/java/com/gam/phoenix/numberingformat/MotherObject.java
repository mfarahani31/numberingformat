package com.gam.phoenix.numberingformat;

import com.gam.phoenix.numberingformat.model.NumberingFormat;

import java.util.Date;

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
}
