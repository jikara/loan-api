package com.softmint.specification;

import com.softmint.entity.User;
import com.softmint.entity.User_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class UserSpecification {
    public static Specification<User> hasSearchKey(String searchKey) {
        return (root, query, cb) -> {
            if (searchKey == null || searchKey.trim().isEmpty()) {
                return cb.conjunction();
            }
            String likeKey = "%" + searchKey.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get(User_.firstName)), likeKey),
                    cb.like(cb.lower(root.get(User_.lastName)), likeKey),
                    cb.like(cb.lower(root.get(User_.email)), likeKey),
                    cb.like(cb.lower(root.get(User_.phone)), likeKey)
            );
        };
    }

    public static Specification<User> createdBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            if (startDate == null || endDate == null) {
                return cb.conjunction();
            }
            return cb.between(root.get(User_.created), startDate, endDate);
        };
    }
}
