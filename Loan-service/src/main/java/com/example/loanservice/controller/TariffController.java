package com.example.loanservice.controller;

import com.example.loanservice.dao.TariffDAO;
import com.example.loanservice.dto.response.ResponseMessageData;
import com.example.loanservice.service.serviceInteface.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan-service")
@RequiredArgsConstructor
public class TariffController {

    private final TariffService tariffService;
    private final TariffDAO tariffDAO;

    @GetMapping("/getTariffs")
    public ResponseEntity<?> showTariffs() {

        return ResponseEntity.ok(
                new ResponseMessageData(tariffService.getTariffs())
        );
    }

    @GetMapping("/exists/{tariffId}")
    public boolean exists(@PathVariable Long tariffId) {
        return tariffDAO.existByTariffId(tariffId);
    }
}
