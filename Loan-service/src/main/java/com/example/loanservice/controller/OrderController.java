package com.example.loanservice.controller;

import com.example.loanservice.dto.request.RequestOrder;
import com.example.loanservice.dto.response.ResponseMessageData;
import com.example.loanservice.service.serviceInteface.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan-service")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<?> addOrder(@RequestBody RequestOrder order) {

        return ResponseEntity.ok(
                new ResponseMessageData(orderService.handlingOrder(order)));
    }

//    @GetMapping("/rand")
//    public Double checkRandom(){
//        return orderServiceImpl.onCreateGenerateCredRat();
//    }


}
