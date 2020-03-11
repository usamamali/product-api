package com.sitoo.product.exception;

import lombok.Data;

/**
 * @author usama
 */
@Data
public class ProductAttributeNameIsMissingException extends RuntimeException {

    private static final long serialVersionUID = 5524591064874205143L;

    public ProductAttributeNameIsMissingException() {
        super("Attribute name is missing. Name can't be empty.");
    }

}
