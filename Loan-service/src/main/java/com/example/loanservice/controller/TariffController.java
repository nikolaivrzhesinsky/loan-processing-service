package com.example.loanservice.controller;

import com.example.loanservice.dto.response.ResponseMessageData;
import com.example.loanservice.service.serviceInteface.TariffService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/loan-service")
@RequiredArgsConstructor
public class TariffController {

    private final TariffService tariffService;
    private final RestTemplate restTemplate = new RestTemplate();
    //private final TariffDAO tariffDAO;

    @GetMapping("/getTariffs")
    public ResponseEntity<?> showTariffs() {

        return ResponseEntity.ok(
                new ResponseMessageData(tariffService.getTariffs())
        );
    }

//    @GetMapping("/exists/{tariffId}")
//    public boolean exists(@PathVariable Long tariffId) {
//        return tariffDAO.existByTariffId(tariffId);
//    }

    @GetMapping("/api/circuit-breaker")
    @CircuitBreaker(name = "loan-order")
    public String timeLimiterApi() {
        return restTemplate.getForObject("http://localhost:8081/loan-service/api/test", String.class);
    }

    @GetMapping("/api/test")
    public String testResponse() throws InterruptedException {
        Thread.sleep(3000);
        return "response";
    }

}
