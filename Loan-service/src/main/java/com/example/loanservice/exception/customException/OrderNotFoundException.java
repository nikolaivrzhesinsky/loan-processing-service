package com.example.loanservice.exception.customException;

import java.io.Serial;

public class OrderNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public OrderNotFoundException(String msg) {
        super(msg);
    }
}
