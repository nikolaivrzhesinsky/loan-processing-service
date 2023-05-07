package com.example.loanservice.controller;

import com.example.loanservice.dto.response.ResponseMessageData;
import com.example.loanservice.service.serviceInteface.TariffService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @GetMapping("/getTariffs")
    @Operation(
            description = "show all tariffs from DB",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success shown",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "data": {
                                                                    "tariffs": [
                                                                        {
                                                                            "id": 1,
                                                                            "type": "CONSUMER",
                                                                            "interest_rate": "8-15%"
                                                                        },
                                                                        {
                                                                            "id": 2,
                                                                            "type": "MORTGAGE",
                                                                            "interest_rate": "9-17%"
                                                                        },
                                                                        {
                                                                            "id": 3,
                                                                            "type": "POS",
                                                                            "interest_rate": "4-9%"
                                                                        },
                                                                        {
                                                                            "id": 4,
                                                                            "type": "CREDIT_CARD",
                                                                            "interest_rate": "10-20%"
                                                                        }
                                                                    ]
                                                                }
                                                            }"""
                                            )
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<?> showTariffs() {

        return ResponseEntity.ok(
                new ResponseMessageData(tariffService.getTariffs())
        );
    }

    @Hidden
    @GetMapping("/api/circuit-breaker")
    @CircuitBreaker(name = "loan-order")
    public String timeLimiterApi() {
        return restTemplate.getForObject("http://localhost:8081/loan-service/api/test", String.class);
    }

    @Hidden
    @GetMapping("/api/test")
    public String testResponse() throws InterruptedException {
        Thread.sleep(3000);
        return "response";
    }

}
