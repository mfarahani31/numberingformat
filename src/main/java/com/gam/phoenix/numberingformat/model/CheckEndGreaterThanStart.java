package com.gam.phoenix.numberingformat.model;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EndGreaterThanStartValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckEndGreaterThanStart {
    String message() default "end must be greater than start";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
