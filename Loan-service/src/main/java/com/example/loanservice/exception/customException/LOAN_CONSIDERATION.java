package com.example.loanservice.exception.customException;

import java.io.Serial;

public class LOAN_CONSIDERATION extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public LOAN_CONSIDERATION(String msg) {
        super(msg);
    }
}
