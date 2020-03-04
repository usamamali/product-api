package com.sitoo.product.exception;

import lombok.Data;

/**
 * @author usama
 */
@Data
public class ProductBarcodeAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = -8098549323376177013L;

    public ProductBarcodeAlreadyExistException(String barcode) {
        super(String.format("Barcode '%s' already exists", barcode));
    }

}
