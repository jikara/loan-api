package com.softmint.service.impl;

import com.softmint.entity.*;
import com.softmint.enums.LoanPerformanceStatus;
import com.softmint.exception.LoanApplicationException;
import com.softmint.repo.LoanRepo;
import com.softmint.service.*;
import com.softmint.specification.LoanSpecification;
import com.softmint.util.ReferenceGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepo loanRepo;
    private final UserService userService;
    private final EmployeeUserService employeeUserService;
    private final InterestService interestService;
    private final ProductService productService;

    @Override
    public Page<Loan> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return loanRepo.findAll(pageable);
    }

    @Override
    public Page<Loan> findByFilters(Authentication authentication, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Specification<Loan> specification;
        User user = userService.getUserById((UUID) authentication.getPrincipal());
        if (user instanceof EmployeeUser employeeUser) {
            specification = Specification
                    .allOf(LoanSpecification.hasBorrowerId(employeeUser.getEmployee().getId())
                            .and(LoanSpecification.hasSearchKey(searchKey))
                            .and(LoanSpecification.createdBetween(startDate, endDate))
                    );
        } else if (user instanceof EmployerUser employerUser) {
            specification = Specification
                    .allOf(LoanSpecification.hasEmployerId(employerUser.getEmployer().getId())
                            .and(LoanSpecification.hasSearchKey(searchKey))
                            .and(LoanSpecification.createdBetween(startDate, endDate))
                    );
        } else { //is admin
            specification = Specification
                    .allOf(LoanSpecification.hasSearchKey(searchKey))
                    .and(LoanSpecification.createdBetween(startDate, endDate)
                    );
        }
        return loanRepo.findAll(specification, pageable);
    }

    @Override
    public Loan create(Authentication authentication, Loan model) {
        UUID authId = (UUID) authentication.getPrincipal();
        User authUser = userService.getUserById(authId);
        if (!(authUser instanceof EmployeeUser)) {
            throw new LoanApplicationException("This operation is not allowed");
        }
        UUID userId = (UUID) authentication.getPrincipal();
        EmployeeUser employeeUser = employeeUserService.findById(userId);
        assert employeeUser != null;
        Employee borrower = employeeUser.getEmployee();
        //Check if borrower has an existing pending loan
        Loan existingLoan = getEmployeeExistingUnsettledLoan(borrower.getId());
        if (existingLoan != null) {
            throw new LoanApplicationException("Loan reference: " + existingLoan.getReference() + " must be settled first");
        }
        model.setBorrower(borrower);
        //Get interest rate
        Interest interest = interestService.getEmployerProductInterest(employeeUser.getEmployee().getEmployer().getId(), model.getProduct().getId());
        if (interest != null) {
            model.setEffectiveInterestRate(interest.getInterestRate());
        } else {
            // set default interest
            Product product = productService.findById(model.getProduct().getId());
            model.setEffectiveInterestRate(product.getDefaultInterestRate());
        }
        model.setReference(ReferenceGeneratorUtil.generateReference(UUID.randomUUID()));
        return loanRepo.save(model);
    }

    @Override
    public Loan getEmployeeExistingUnsettledLoan(UUID employeeId) {
        Specification<Loan> spec = Specification
                .allOf(LoanSpecification.hasBorrowerId(employeeId))
                .and(LoanSpecification.performanceStatusNotEqual(LoanPerformanceStatus.SETTLED));
        return loanRepo.findAll(spec, PageRequest.of(0, 1))
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Loan findById(Authentication authentication, UUID id) {
        Specification<Loan> specification;
        UUID authId = (UUID) authentication.getPrincipal();
        User authUser = userService.getUserById(authId);
        assert authUser != null;
        if (authUser instanceof EmployeeUser) {
            return loanRepo.findById(id).orElse(null);
        } else if (authUser instanceof EmployerUser) {
            return loanRepo.findById(id).orElse(null);
        }
        throw new RuntimeException("This operation is not allowed");
    }
}
