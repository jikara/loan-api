package com.softmint.specification;

import com.softmint.entity.*;
import com.softmint.enums.LoanLifecycleStatus;
import com.softmint.enums.LoanPerformanceStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

public class LoanSpecification {

    public static Specification<Loan> performanceStatusNotEqual(LoanPerformanceStatus status) {
        return (root, query, cb) ->
                status == null
                        ? cb.conjunction()
                        : cb.notEqual(root.get(Loan_.performanceStatus), status);
    }

    public static Specification<Loan> lifecycleStatusNotEqual(LoanLifecycleStatus status) {
        return (root, query, cb) ->
                status == null
                        ? cb.conjunction()
                        : cb.notEqual(root.get(Loan_.lifecycleStatus), status);
    }

    public static Specification<Loan> hasBorrowerId(@NotNull UUID borrowerId) {
        return (root, query, cb) -> {
            if (borrowerId == null) {
                return cb.conjunction();
            }
            // Join Document -> ProductDocument -> Product
            Join<Loan, Employee> loanEmployeeJoin = root.join(Loan_.borrower, JoinType.INNER);

            // Filter by Product ID
            return cb.equal(loanEmployeeJoin.get(Employee_.id), borrowerId);
        };
    }

    public static Specification<Loan> hasSearchKey(String searchKey) {
        return (root, query, cb) -> {
            if (searchKey == null || searchKey.trim().isEmpty()) {
                return cb.conjunction();
            }
            String likeKey = "%" + searchKey.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get(Loan_.reference)), likeKey)
            );
        };
    }

    public static Specification<Loan> createdBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            if (startDate == null || endDate == null) {
                return cb.conjunction();
            }
            return cb.between(root.get(Loan_.created), startDate, endDate);
        };
    }

    public static Specification<Loan> hasEmployerId(UUID employerId) {
        return (root, query, cb) -> {
            if (employerId == null) {
                return cb.conjunction(); // no filter if null
            }
            Join<Loan, Employee> borrower = root.join(Loan_.borrower, JoinType.INNER);
            Join<Employee, Employer> employer = borrower.join(Employee_.employer, JoinType.INNER);

            return cb.equal(employer.get(Employer_.id), employerId);
        };
    }


}
