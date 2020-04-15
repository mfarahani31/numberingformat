package com.gam.phoenix.numberingformat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * @author mohammad
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncreaseRequestModel {

    @Nullable
    private Long serialLength;

    @Nullable
    private String returnType;
}
