package com.sitoo.product.exception;

import lombok.Data;

/**
 * @author usama
 */
@Data
public class ProductSkuAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = -8098549323376177013L;

    public ProductSkuAlreadyExistException(String sku) {
        super(String.format("SKU '%s' already exists", sku));
    }

}
