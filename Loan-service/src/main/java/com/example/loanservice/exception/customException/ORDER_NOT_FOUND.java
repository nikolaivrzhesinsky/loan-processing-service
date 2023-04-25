package com.example.loanservice.exception.customException;

import java.io.Serial;

public class ORDER_NOT_FOUND extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ORDER_NOT_FOUND(String msg) {
        super(msg);
    }
}
