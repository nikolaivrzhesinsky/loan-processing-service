package com.example.loanservice.exception.customException;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;

@Schema
public class TryLaterException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public TryLaterException(String msg) {
        super(msg);
    }
}
