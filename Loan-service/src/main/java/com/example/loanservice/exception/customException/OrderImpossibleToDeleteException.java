package com.example.loanservice.exception.customException;

import java.io.Serial;

public class OrderImpossibleToDeleteException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public OrderImpossibleToDeleteException(String msg) {
        super(msg);
    }
}
