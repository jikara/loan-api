package com.softmint.specification;

import com.softmint.entity.Role;
import com.softmint.entity.Role_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class RoleSpecification {
    public static Specification<Role> isBaseRole() {
        return (root, query, cb) -> cb.equal(root.type(), Role.class);
    }

    public static Specification<Role> hasSearchKey(String searchKey) {
        return (root, query, cb) -> {
            if (searchKey == null || searchKey.trim().isEmpty()) {
                return cb.conjunction();
            }
            String likeKey = "%" + searchKey.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get(Role_.name)), likeKey)
            );
        };
    }

    public static Specification<Role> createdBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            if (startDate == null || endDate == null) {
                return cb.conjunction();
            }
            return cb.between(root.get(Role_.created), startDate, endDate);
        };
    }
}
