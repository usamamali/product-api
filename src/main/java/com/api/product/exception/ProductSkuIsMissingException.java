package com.api.product.exception;

import lombok.Data;

/**
 * @author usama
 */
@Data
public class ProductSkuIsMissingException extends RuntimeException {

    private static final long serialVersionUID = -7118359354952149177L;

    public ProductSkuIsMissingException() {
        super("SKU is missing. SKU must be provided.");
    }

}
