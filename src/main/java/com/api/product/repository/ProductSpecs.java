package com.api.product.repository;

import com.api.product.model.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author usama
 */
public class ProductSpecs {

    public static Specification<Product> productIdStartFrom(Integer start) {
        return new Specification<Product>() {
            private static final long serialVersionUID = 7078776175348014387L;

            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                return builder.ge(root.get("productId"), start);
            }
        };
    }

    public static Specification<Product> filterBySku(String sku) {
        return new Specification<Product>() {
            private static final long serialVersionUID = 7078776175348014387L;

            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                if (!sku.isBlank())
                    return builder.equal(root.get("sku"), sku);
                else
                    return builder.and();
            }
        };
    }

    public static Specification<Product> filterByBarcode(String barcode) {
        return new Specification<Product>() {
            private static final long serialVersionUID = 7078776175348014387L;

            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                if (!barcode.isBlank())
                    return builder.isMember(barcode, root.get("barcodes"));
                else
                    return builder.and();
            }
        };
    }
}
