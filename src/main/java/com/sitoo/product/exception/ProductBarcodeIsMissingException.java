package com.sitoo.product.exception;

import lombok.Data;

/**
 * @author usama
 */
@Data
public class ProductBarcodeIsMissingException extends RuntimeException {

    private static final long serialVersionUID = 5524591064874205143L;

    public ProductBarcodeIsMissingException() {
        super("Barcode is missing. Barcode can't be empty.");
    }

}
