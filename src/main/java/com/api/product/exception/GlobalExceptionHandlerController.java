package com.api.product.exception;

import com.api.product.model.ApiErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * @author usama
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandlerController {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ApiErrorMessage handleProductNotFoundException(ProductNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ApiErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ProductSkuAlreadyExistException.class, ProductBarcodeAlreadyExistException.class, ProductBarcodeIsMissingException.class,
            ProductBarcodeIsRepeatedException.class, ProductSkuIsMissingException.class, ProductTitleIsMissingException.class,
            ProductAttributeNameIsMissingException.class, ProductAttributeIsRepeatedException.class})
    public ApiErrorMessage handleProductSkuExceptions(RuntimeException e) {
        log.error(e.getMessage(), e);
        return new ApiErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ConversionFailedException.class, IllegalArgumentException.class, MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class})
    ApiErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return new ApiErrorMessage(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ApiErrorMessage handleGeneralExceptions(Exception e) {
        log.error(e.getMessage(), e);
        return new ApiErrorMessage("Internal Server Error. Please, try again later");
    }
}
