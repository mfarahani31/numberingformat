package com.gam.phoenix.numberingformat.constants;


/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 **/

public class ExceptionMessage {
    public static final String NUMBERING_FORMAT_NOT_FOUND_EXCEPTION = "NBR-S-NFS-001";
    public static final String NUMBERING_FORMAT_NOT_FOUND_EXCEPTION_MSG = "NumberingFormat does not exists";


    public static final String NUMBERING_FORMAT_DUPLICATION_EXCEPTION = "NBR_S_NFS_002";
    public static final String NUMBERING_FORMAT_DUPLICATION_EXCEPTION_MSG = "Duplicate NumberingFormat !!!";

    public static final String NUMBERING_FORMAT_UPDATE_SERIAL_ERROR = "NBR_S_NFS_003";
    public static final String NUMBERING_FORMAT_UPDATE_SERIAL_ERROR_MSG = "Error in update";

    public static final String NUMBERING_FORMAT_ENCODE_URL_ERROR = "NBR_C_NFC_004";
    public static final String NUMBERING_FORMAT_ENCODE_URL_ERROR_MSG = "ERROR IN ENCODING URL!";

    private ExceptionMessage() {

    }
}
