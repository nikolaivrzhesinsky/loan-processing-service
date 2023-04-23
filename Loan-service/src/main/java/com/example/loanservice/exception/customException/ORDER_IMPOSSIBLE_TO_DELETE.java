package com.example.loanservice.exception.customException;

import java.io.Serial;

public class ORDER_IMPOSSIBLE_TO_DELETE extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ORDER_IMPOSSIBLE_TO_DELETE(String msg) {
        super(msg);
    }
}
