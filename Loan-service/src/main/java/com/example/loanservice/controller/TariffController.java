package com.example.loanservice.controller;

import com.example.loanservice.dto.response.ResponseMessageData;
import com.example.loanservice.service.serviceInteface.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan-service")
@RequiredArgsConstructor
public class TariffController {

    private final TariffService tariffService;

    @GetMapping("/getTariffs")
    public ResponseEntity<?> showTariffs() {

        return ResponseEntity.ok(
                new ResponseMessageData(tariffService.getTariffs())
        );
    }
}
