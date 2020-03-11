package com.sitoo.product.controller;

/**
 * @author usama
 */

import com.sitoo.product.configuration.ProductFilter;
import com.sitoo.product.model.ApiErrorMessage;
import com.sitoo.product.model.Product;
import com.sitoo.product.model.SearchResult;
import com.sitoo.product.service.ProductService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Map;

@Api(value = "products", tags = {"products"})
@RestController
@Slf4j
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductFilter productFilter;

    @ApiOperation(value = "Get all products", nickname = "getProducts", notes = "Returns all products", response = SearchResult.class, tags = {"products",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SearchResult.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiErrorMessage.class)})
    @RequestMapping(value = "/products", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<MappingJacksonValue> getProducts(
            @ApiParam(value = "The start index of items") @Valid @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
            @ApiParam(value = "The number of items returned") @Valid @RequestParam(value = "num", required = false, defaultValue = "10") Integer num,
            @ApiParam(value = "Filter on sku") @Valid @RequestParam(value = "sku", required = false, defaultValue = "") String sku,
            @ApiParam(value = "Filter on barcode") @Valid @RequestParam(value = "barcode", required = false, defaultValue = "") String barcode,
            @ApiParam(value = "Fields of the products to return in the response") @Pattern(regexp = "[a-zA-Z]+(,[a-zA-Z]).*") @Valid @RequestParam(value = "fields", required = false, defaultValue = "") String fields) {

        SearchResult searchResult = productService.getAllProducts(start, num, sku, barcode);

        return ResponseEntity.status(HttpStatus.OK).body(productFilter.filterResult(searchResult, fields));
    }

    @ApiOperation(value = "Find product by ID", nickname = "getProductById", notes = "Returns a single product", response = Product.class, tags = {"products",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Product.class),
            @ApiResponse(code = 404, message = "Not Found", response = ApiErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiErrorMessage.class)})
    @RequestMapping(value = "/products/{productId}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<MappingJacksonValue> getProductById(
            @ApiParam(value = "ID of product to return", required = true) @PathVariable("productId") Integer productId,
            @ApiParam(value = "Fields of the products to return in the response") @Pattern(regexp = "[a-zA-Z]+(,[a-zA-Z]).*") @Valid @RequestParam(value = "fields", required = false) String fields) {

        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(productFilter.filterProduct(product, fields));
    }

    @ApiOperation(value = "Add a new product to the store", nickname = "addProduct", notes = "", response = Integer.class, tags = {"products",})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Integer.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiErrorMessage.class)})
    @RequestMapping(value = "/products", method = RequestMethod.POST, produces = {"application/text", "application/json"}, consumes = {"application/json"})
    public ResponseEntity<Integer> addProduct(
            @ApiParam(value = "Product object that needs to be added to the stored", required = true) @Valid @RequestBody Product product) {

        Integer productId = productService.saveNewProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }

    @ApiOperation(value = "Update an existing product", nickname = "updateProduct", notes = "", response = Boolean.class, tags = {"products",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Boolean.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiErrorMessage.class),
            @ApiResponse(code = 404, message = "Not Found", response = ApiErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiErrorMessage.class)})
    @RequestMapping(value = "/products/{productId}", method = RequestMethod.PUT, produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<Boolean> updateProduct(
            @ApiParam(value = "Product object that needs to be updated", required = true) @Valid @RequestBody Map<String, Object> product,
            @ApiParam(value = "ID of product to update", required = true) @PathVariable("productId") Integer productId) {
        
        productService.updateProduct(productId, product);
        return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
    }

    @ApiOperation(value = "Deletes a product", nickname = "deleteProduct", notes = "", response = Boolean.class, tags = {"products",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Boolean.class),
            @ApiResponse(code = 404, message = "Not Found", response = ApiErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiErrorMessage.class)})
    @RequestMapping(value = "/products/{productId}", method = RequestMethod.DELETE, produces = {"application/text", "application/json"})
    public ResponseEntity<Boolean> deleteProduct(
            @ApiParam(value = "ID of product to delete", required = true) @PathVariable("productId") @Validated @Min(1) Integer productId) {

        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
    }
}
