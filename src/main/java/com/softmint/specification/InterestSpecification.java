package com.softmint.specification;

import com.softmint.entity.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

public class InterestSpecification {

    public static Specification<Interest> hasEmployerAndProduct(UUID employerId, UUID productId) {
        return (root, query, cb) -> {
            Join<Interest, Employer> employerJoin = root.join(Interest_.employer, JoinType.INNER);
            Join<Interest, Product> productJoin = root.join(Interest_.product, JoinType.INNER);

            return cb.and(
                    cb.equal(employerJoin.get(Employer_.id).as(UUID.class), employerId),
                    cb.equal(productJoin.get(Product_.id).as(UUID.class), productId)
            );
        };
    }

    public static Specification<Interest> hasSearchKey(String searchKey) {
        return (root, query, cb) -> {
            if (searchKey == null || searchKey.trim().isEmpty()) {
                return cb.conjunction();
            }
            String likeKey = "%" + searchKey.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get(String.valueOf(Interest_.id))), likeKey)
            );
        };
    }

    public static Specification<Interest> createdBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            if (startDate == null || endDate == null) {
                return cb.conjunction();
            }
            return cb.between(root.get(Interest_.created), startDate, endDate);
        };
    }
}
