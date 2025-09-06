package com.softmint.service.impl;

import com.softmint.entity.EmployerUser;
import com.softmint.entity.Loan;
import com.softmint.entity.LoanApproval;
import com.softmint.entity.User;
import com.softmint.enums.LoanApprovalStatus;
import com.softmint.enums.LoanLifecycleStatus;
import com.softmint.exception.LoanApplicationException;
import com.softmint.repo.LoanApprovalRepo;
import com.softmint.service.LoanApprovalService;
import com.softmint.service.LoanService;
import com.softmint.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LoanApprovalServiceImpl implements LoanApprovalService {
    private final LoanApprovalRepo loanApprovalRepo;
    private final UserService userService;
    private final LoanService loanService;

    @Override
    public Page<LoanApproval> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return null;
    }

    @Override
    public Page<LoanApproval> findByFilters(Authentication authentication, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return null;
    }

    @Override
    public LoanApproval create(Authentication authentication, LoanApproval model) {
        UUID authId = (UUID) authentication.getPrincipal();
        User authUser = userService.getUserById(authId);
        assert authUser != null;
        Loan loan = loanService.findById(authentication, model.getLoan().getId());
        assert loan != null;
        if (authUser instanceof EmployerUser) {
            loan.setApprovalStatus(LoanApprovalStatus.EMPLOYER_APPROVED);
            //Set step 1 of Approval Policy
            loan.setCurrentStep(loan.getApprovalPolicy().getFirstStep());
        } else {
            if (loan.getLifecycleStatus() != LoanLifecycleStatus.PENDING) {
                throw new LoanApplicationException("Loan already approved");
            }
            model.setStep(loan.getCurrentStep());
            if (model.getStatus() == LoanApprovalStatus.APPROVED) {
                if (loan.getCurrentStep().getNextStep() == null) {  //Loan has been approved on all steps
                    loan.setCurrentStep(null);  //Set current step to null,no more pending steps
                    loan.setApprovalStatus(model.getStatus());
                    //Set loan status to approved for disbursement by disbursement engine.
                    loan.setLifecycleStatus(LoanLifecycleStatus.APPROVED);
                    //Set effective date
                    loan.setStartDate(LocalDate.now());
                    int months = loan.getTenureMonths();
                    loan.setEndDate(LocalDate.now().plusMonths(months));
                    //Generate loan schedule

                } else {
                    //Move loan to next approval Step
                    loan.setCurrentStep(loan.getCurrentStep().getNextStep());
                }
            } else {
                loan.setApprovalStatus(model.getStatus());  //Mostly rejected
            }

        }
        loanService.save(loan);
        return loanApprovalRepo.save(model);
    }

    @Override
    public LoanApproval findById(Authentication authentication, UUID loanApprovalId) {
        return null;
    }

    @Override
    public Page<LoanApproval> findLoanApprovals(UUID loanId, Pageable pageable) {
        return loanApprovalRepo.findAll(pageable);
    }
}
