package com.sitoo.product.service;

import com.sitoo.product.exception.*;
import com.sitoo.product.model.Product;
import com.sitoo.product.model.SearchResult;
import com.sitoo.product.repository.ProductJPARepository;
import com.sitoo.product.repository.ProductSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
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

    public Integer updateProduct(Product product) {

        Product existProduct = productRepository.findById(product.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product with productId (%s) does not exist", product.getProductId())));

        if (product.getTitle() != null)
            existProduct.setTitle(product.getTitle());

        if (product.getSku() != null && !existProduct.getSku().equalsIgnoreCase(product.getSku())) {
            if (productRepository.existsBySku(product.getSku()))
                throw new ProductSkuAlreadyExistException(product.getSku());
            existProduct.setSku(product.getSku());
        }

        if (Optional.ofNullable(product.getBarcodes()).isPresent()) {

            product.getBarcodes().stream().filter(barcode -> !barcode.isBlank())
                    .filter(barcode -> !existProduct.getBarcodes().contains(barcode))
                    .filter(barcode -> productRepository.existsByBarcodes(barcode))
                    .forEach(barcode -> {
                        if (productRepository.existsByBarcodes(barcode)) {
                            throw new ProductBarcodeAlreadyExistException(barcode);
                        }
                    });

            existProduct.setBarcodes(product.getBarcodes());
        }


        if (product.getDescription() != null)
            existProduct.setDescription(product.getDescription());

        if (product.getAttributes() != null)
            existProduct.setAttributes(product.getAttributes());

        if (product.getPrice() != null)
            existProduct.setPrice(product.getPrice());

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
