package com.softmint.specification;

import com.softmint.entity.Permission;
import com.softmint.entity.Permission_;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public class PermissionSpecification {
    public static Specification<Permission> hasSearchKey(String searchKey) {
        return (root, query, cb) -> {
            if (searchKey == null || searchKey.trim().isEmpty()) {
                return cb.conjunction();
            }
            String likeKey = "%" + searchKey.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get(Permission_.code)), likeKey),
                    cb.like(cb.lower(root.get(Permission_.name)), likeKey),
                    cb.like(cb.lower(root.get(Permission_.description)), likeKey)
            );
        };
    }

    public static Specification<Permission> isAssignable(boolean assignable) {
        return (root, query, cb) -> cb.equal(root.get(Permission_.assignable), assignable);
    }

    public static Specification<Permission> excludeIds(Set<String> excludedIds) {
        return (root, query, cb) -> {
            if (excludedIds == null || excludedIds.isEmpty()) {
                return cb.conjunction();
            }
            return cb.not(root.get(Permission_.code).in(excludedIds));
        };
    }


}
