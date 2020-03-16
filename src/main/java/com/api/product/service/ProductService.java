package com.api.product.service;

import com.api.product.exception.*;
import com.api.product.model.Attribute;
import com.api.product.model.Product;
import com.api.product.model.SearchResult;
import com.api.product.repository.ProductJPARepository;
import com.api.product.repository.ProductSpecs;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author usama
 */
@Service
public class ProductService {

    @Autowired
    private ProductJPARepository productRepository;

    public SearchResult getAllProducts(@Valid Integer start, @Valid Integer num, @Valid String sku, @Valid String barcode) {

        Specification<Product> productFilterCriteria = ProductSpecs.productIdStartFrom(start)
                .and(ProductSpecs.filterBySku(sku))
                .and(ProductSpecs.filterByBarcode(barcode));

        Long count = productRepository.count(productFilterCriteria);

        List<Product> productList = productRepository.findAll(productFilterCriteria,
                PageRequest.of(0, num, Sort.by("productId").ascending())).getContent();
        return SearchResult.builder().totalCount(count).items(productList).build();
    }

    public Integer saveNewProduct(Product product) {

        if (Optional.ofNullable(product.getSku()).isEmpty() || product.getSku().isBlank())
            throw new ProductSkuIsMissingException();

        if (Optional.ofNullable(product.getTitle()).isEmpty() || product.getTitle().isBlank())
            throw new ProductTitleIsMissingException();

        if (productRepository.existsBySku(product.getSku()))
            throw new ProductSkuAlreadyExistException(product.getSku());

        if (Optional.ofNullable(product.getBarcodes()).isPresent())
            product.getBarcodes().stream().forEach(barcode -> {
                if (!barcode.isBlank() && productRepository.existsByBarcodes(barcode)) {
                    throw new ProductBarcodeAlreadyExistException(barcode);
                }
            });
        return productRepository.save(product).getProductId();
    }

    public Integer updateProduct(Integer productId, Map<String, Object> productDetails) {

        Product existProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product with productId (%s) does not exist", productId)));

        if (productDetails.keySet().contains("title")) {
            if (Optional.ofNullable(productDetails.get("title")).isEmpty() || ((String) productDetails.get("title")).isBlank())
                throw new ProductTitleIsMissingException();

            existProduct.setTitle((String) productDetails.get("title"));
        }

        if (productDetails.keySet().contains("sku")) {
            if (Optional.ofNullable(productDetails.get("sku")).isEmpty() || ((String) productDetails.get("sku")).isBlank())
                throw new ProductSkuIsMissingException();
            else {
                String newSku = (String) productDetails.get("sku");

                if (productRepository.existsBySku(newSku))
                    throw new ProductSkuAlreadyExistException(newSku);

                existProduct.setSku(newSku);
            }
        }

        if (productDetails.keySet().contains("barcodes")) {
            List<String> newBarcodes = (ArrayList<String>) productDetails.get("barcodes");

            if (Optional.ofNullable(newBarcodes).isPresent()) {

                for (String barcode : newBarcodes) {

                    if (Optional.ofNullable(barcode).isEmpty() || barcode.isBlank())
                        throw new ProductBarcodeIsMissingException();

                    if (newBarcodes.stream().filter(bcode -> barcode.equalsIgnoreCase(bcode)).count() > 1)
                        throw new ProductBarcodeIsRepeatedException(barcode);

                    if ((Optional.ofNullable(existProduct.getBarcodes()).isEmpty() || !existProduct.getBarcodes().contains(barcode)) && productRepository.existsByBarcodes(barcode))
                        throw new ProductBarcodeAlreadyExistException(barcode);
                }
            }

            existProduct.setBarcodes(newBarcodes);
        }

        if (productDetails.keySet().contains("description"))
            existProduct.setDescription((String) productDetails.get("description"));

        if (productDetails.keySet().contains("attributes")) {

            List<Attribute> attributes = null;

            if (Optional.ofNullable(productDetails.get("attributes")).isPresent()) {

                attributes = (new ObjectMapper()).convertValue(productDetails.get("attributes"), new TypeReference<List<Attribute>>() {
                });

                for (Attribute attribute : attributes) {
                    if (Optional.ofNullable(attribute.getName()).isEmpty())
                        throw new ProductAttributeNameIsMissingException();
                    if (attributes.stream().filter(att -> attribute.equals(att)).count() > 1)
                        throw new ProductAttributeIsRepeatedException(attribute.getName());
                }
            }
            existProduct.setAttributes(attributes);
        }

        if (productDetails.keySet().contains("price")) {
            if (Optional.ofNullable(productDetails.get("price")).isEmpty())
                existProduct.setPrice(BigDecimal.ZERO);
            else
                existProduct.setPrice(BigDecimal.valueOf(Double.valueOf(productDetails.get("price") + "")));
        }

        return productRepository.save(existProduct).getProductId();
    }

    public void deleteProduct(Integer productId) {
        if (!productRepository.existsById(productId))
            throw new ProductNotFoundException(String.format("Product with productId (%s) does not exist", productId));
        productRepository.deleteById(productId);
    }

    public Product getProductById(Integer productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(String.format("Can’t find product (%s)", productId)));
    }
}
