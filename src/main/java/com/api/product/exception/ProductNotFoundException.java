package com.api.product.exception;

import lombok.Data;

/**
 * @author usama
 */
@Data
public class ProductNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 5888255938034502191L;

    public ProductNotFoundException(String message) {
        super(message);
    }

}
