package com.gam.phoenix.numberingformat.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 **/

public class EndGreaterThanStartValidator implements ConstraintValidator<CheckEndGreaterThanStart, NumberingFormatInterval> {
    @Override
    public void initialize(CheckEndGreaterThanStart constraintAnnotation) {
        //nothing here
    }

    @Override
    public boolean isValid(NumberingFormatInterval numberingFormatInterval, ConstraintValidatorContext constraintValidatorContext) {

        return numberingFormatInterval.getReservedEnd() > numberingFormatInterval.getReservedStart();
    }
}
