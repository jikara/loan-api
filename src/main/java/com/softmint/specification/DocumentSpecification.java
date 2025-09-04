package com.softmint.specification;

import com.softmint.entity.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

public class DocumentSpecification {
    public static Specification<Document> hasSearchKey(String searchKey) {
        return (root, query, cb) -> {
            if (searchKey == null || searchKey.trim().isEmpty()) {
                return cb.conjunction();
            }
            String likeKey = "%" + searchKey.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get(Document_.name)), likeKey),
                    cb.like(cb.lower(root.get(Document_.code)), likeKey),
                    cb.like(cb.lower(root.get(Document_.description)), likeKey)
            );
        };
    }

    public static Specification<Document> createdBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            if (startDate == null || endDate == null) {
                return cb.conjunction();
            }
            return cb.between(root.get(Document_.created), startDate, endDate);
        };
    }

    public static Specification<Document> hasProductId(UUID productId) {
        return (root, query, cb) -> {
            if (productId == null) {
                return cb.conjunction();
            }
            // Join Document -> ProductDocument -> Product
            Join<Document, ProductDocument> productDocumentJoin = root.join(Document_.productDocuments, JoinType.INNER);
            Join<ProductDocument, Product> productJoin = productDocumentJoin.join(ProductDocument_.product, JoinType.INNER);

            // Filter by Product ID
            return cb.equal(productJoin.get(Product_.id), productId);
        };
    }

}
