package com.example.loanservice.exception.customException;

import java.io.Serial;

public class TryLaterException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public TryLaterException(String msg) {
        super(msg);
    }
}
