package com.example.loanservice.exception;

import com.example.loanservice.dto.response.ResponseMessageError;
import com.example.loanservice.exception.customException.TARIFF_NOT_FOUND;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TARIFF_NOT_FOUND.class)
    public ResponseEntity<ResponseMessageError> handleTARIFF_NOT_FOUNDException(Exception ex) {

        ErrorMessage message = new ErrorMessage("TARIFF_NOT_FOUND"
                , "Тариф не найден");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseMessageError(message)
        );
    }
}
