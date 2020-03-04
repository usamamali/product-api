package com.sitoo.product.repository;

import com.sitoo.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author usama
 */
@Repository
public interface ProductJPARepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    @Override
    Page<Product> findAll(Specification<Product> productSpec, Pageable pageReq);

    boolean existsBySku(String sku);

    boolean existsByBarcodes(String barcode);
}
