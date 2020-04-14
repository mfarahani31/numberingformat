package com.gam.phoenix.numberingformat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mohammad
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncreaseRequestModel {

    private Long serialLength;

    private String returnType;
}
