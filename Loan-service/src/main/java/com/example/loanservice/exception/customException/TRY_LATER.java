package com.example.loanservice.exception.customException;

import java.io.Serial;

public class TRY_LATER extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public TRY_LATER(String msg) {
        super(msg);
    }
}
