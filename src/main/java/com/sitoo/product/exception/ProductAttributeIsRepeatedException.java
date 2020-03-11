package com.sitoo.product.exception;

import lombok.Data;

/**
 * @author usama
 */
@Data
public class ProductAttributeIsRepeatedException extends RuntimeException {


    private static final long serialVersionUID = -6467033639075255397L;

    public ProductAttributeIsRepeatedException(String attributeName) {
        super(String.format("Attribute '%s' is repeated", attributeName));
    }

}
