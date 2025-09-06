package com.softmint.specification;

import com.softmint.entity.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

public class EmployerUserSpecification {
    public static Specification<EmployerUser> hasSearchKey(String searchKey) {
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

    public static Specification<EmployerUser> createdBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            if (startDate == null || endDate == null) {
                return cb.conjunction();
            }
            return cb.between(root.get(User_.created), startDate, endDate);
        };
    }

    public static Specification<EmployerUser> hasEmployer(UUID employerId) {
        return (root, query, cb) -> {
            if (employerId == null) {
                return cb.conjunction(); // no filter if employerId not provided
            }
            return cb.equal(root.get(EmployerUser_.employer).get(Employer_.id), employerId);
        };
    }




}
