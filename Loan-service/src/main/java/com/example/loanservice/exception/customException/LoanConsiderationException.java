package com.example.loanservice.exception.customException;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;

@Schema
public class LoanConsiderationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public LoanConsiderationException(String msg) {
        super(msg);
    }
}
