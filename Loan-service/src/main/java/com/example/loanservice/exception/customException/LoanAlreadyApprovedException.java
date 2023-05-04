package com.example.loanservice.exception.customException;

import java.io.Serial;

public class LoanAlreadyApprovedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public LoanAlreadyApprovedException(String msg) {
        super(msg);
    }
}
