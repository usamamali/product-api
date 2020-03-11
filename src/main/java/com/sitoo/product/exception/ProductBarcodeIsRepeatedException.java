package com.sitoo.product.exception;

import lombok.Data;

/**
 * @author usama
 */
@Data
public class ProductBarcodeIsRepeatedException extends RuntimeException {

    private static final long serialVersionUID = -8098549323376177013L;

    public ProductBarcodeIsRepeatedException(String barcode) {
        super(String.format("Barcode '%s' is repeated", barcode));
    }

}
