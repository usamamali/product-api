package com.api.product.exception;

import lombok.Data;

/**
 * @author usama
 */
@Data
public class ProductTitleIsMissingException extends RuntimeException {

    private static final long serialVersionUID = 5524591064874205143L;

    public ProductTitleIsMissingException() {
        super("Title is missing. Title must be provided.");
    }

}
