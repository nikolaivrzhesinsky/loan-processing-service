package com.example.loanservice.exception.customException;

import java.io.Serial;

public class LOAN_ALREADY_APPROVED extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public LOAN_ALREADY_APPROVED(String msg) {
        super(msg);
    }
}
