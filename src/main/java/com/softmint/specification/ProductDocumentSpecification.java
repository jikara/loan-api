package com.softmint.specification;

import com.softmint.entity.ProductDocument;
import com.softmint.entity.ProductDocument_;
import com.softmint.entity.Product_;
import com.softmint.entity.User_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProductDocumentSpecification {
    public static Specification<ProductDocument> hasProductId(UUID productId) {
        return (root, query, cb) -> {
            if (productId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get(ProductDocument_.product).get(Product_.id), productId);
        };
    }


    public static Specification<ProductDocument> createdBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            if (startDate == null || endDate == null) {
                return cb.conjunction();
            }
            return cb.between(root.get(ProductDocument_.created), startDate, endDate);
        };
    }
}
