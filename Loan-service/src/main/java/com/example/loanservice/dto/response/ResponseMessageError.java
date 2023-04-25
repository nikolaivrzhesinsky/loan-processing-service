package com.example.loanservice.dto.response;

import com.example.loanservice.exception.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessageError {

    private ErrorMessage error;


}
