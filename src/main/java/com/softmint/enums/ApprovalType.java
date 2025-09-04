package com.softmint.enums;

public enum ApprovalType {
    EMPLOYER,     // Employer confirmation step
    LENDER,       // Lender’s internal approval
    EXTERNAL,     // Could be external auditor/regulator if needed
    AUTO          // Auto-approve (for low-risk/low-amount loans)
}

