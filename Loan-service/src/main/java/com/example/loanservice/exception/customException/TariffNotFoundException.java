package com.example.loanservice.exception.customException;

import java.io.Serial;

public class TariffNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public TariffNotFoundException(String msg) {
        super(msg);
    }
}
