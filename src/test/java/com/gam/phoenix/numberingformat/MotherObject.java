package com.gam.phoenix.numberingformat;

import com.gam.phoenix.numberingformat.model.NumberingFormat;

import java.util.Date;

public class MotherObject {
    public static NumberingFormat getAnyValidNumberingFormat() {

        Date date = new Date();
        return new NumberingFormat(1L,
                "test1",
                "test2",
                300L,
                299L,
                "admin",
                date,
                "admin",
                date);
    }
}
