package com.example.loanservice.exception;

import com.example.loanservice.dto.response.ResponseMessageError;
import com.example.loanservice.exception.customException.*;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TariffNotFoundException.class)
    public ResponseEntity<ResponseMessageError> handleTARIFF_NOT_FOUNDException(Exception ex) {

        ErrorMessage message = new ErrorMessage("TARIFF_NOT_FOUND"
                , "Тариф не найден");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseMessageError(message)
                );
    }

    @ExceptionHandler(LoanConsiderationException.class)
    public ResponseEntity<ResponseMessageError> handleLOAN_CONSIDERATIONException(Exception ex) {

        ErrorMessage message = new ErrorMessage("LOAN_CONSIDERATION"
                , "Order already in consideration");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseMessageError(message));
    }

    @ExceptionHandler(LoanAlreadyApprovedException.class)
    public ResponseEntity<ResponseMessageError> handleLOAN_ALREADY_APPROVEDException(Exception ex) {

        ErrorMessage message = new ErrorMessage("LOAN_ALREADY_APPROVED"
                , "Order was already approved");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseMessageError(message));
    }

    @ExceptionHandler(TryLaterException.class)
    public ResponseEntity<ResponseMessageError> handleTRY_LATERException(Exception ex) {

        ErrorMessage message = new ErrorMessage("TRY_LATER"
                , "Try to make order later");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseMessageError(message));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ResponseMessageError> handleORDER_NOT_FOUNDException(Exception ex) {

        ErrorMessage message = new ErrorMessage("ORDER_NOT_FOUND"
                , "Заявка не найдена");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseMessageError(message));
    }

    @ExceptionHandler(OrderImpossibleToDeleteException.class)
    public ResponseEntity<ResponseMessageError> handleORDER_IMPOSSIBLE_TO_DELETEException(Exception ex) {

        ErrorMessage message = new ErrorMessage("ORDER_IMPOSSIBLE_TO_DELETE"
                , "Невозможно удалить заявку");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseMessageError(message));
    }

    @ExceptionHandler(CallNotPermittedException.class)
    public ResponseEntity<?> handleTimeoutException() {

        ErrorMessage message = new ErrorMessage(HttpStatus.REQUEST_TIMEOUT.name(),
                "TimeoutException");
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                .body(new ResponseMessageError(message));
    }


}
