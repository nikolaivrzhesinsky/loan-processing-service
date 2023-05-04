package com.example.loanservice.exception.customException;

import java.io.Serial;

public class LoanConsiderationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public LoanConsiderationException(String msg) {
        super(msg);
    }
}
