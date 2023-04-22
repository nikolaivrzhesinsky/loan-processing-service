package com.example.loanservice.exception.customException;

import java.io.Serial;

public class TARIFF_NOT_FOUND extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public TARIFF_NOT_FOUND(String msg) {
        super(msg);
    }
}
