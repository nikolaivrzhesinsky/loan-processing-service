package com.example.loanservice.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema
public class ErrorMessage {

    private String code;
    private String message;
}
