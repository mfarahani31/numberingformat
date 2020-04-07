//package com.gam.phoenix.numberingformat;
//
//import com.gam.phoenix.numberingformat.model.NumberingFormat;
//import com.gam.phoenix.numberingformat.model.NumberingFormatInterval;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Optional;
//
//public class MotherObject {
//    public static NumberingFormat getAnyValidNumberingFormat() {
//
//        Date date = new Date();
//        return new NumberingFormat(1L,
//                "test1",
//                "test1",
//                300L,
//                301L,
//                new NumberingFormatInterval(1L,);
//                "admin",
//                date,
//                "admin",
//                date);
//    }
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
//
//    public static Long decreaseStartAtByOneForLastAllocatedSerial(Long startAt) {
//        return startAt - 1;
//    }
//}
