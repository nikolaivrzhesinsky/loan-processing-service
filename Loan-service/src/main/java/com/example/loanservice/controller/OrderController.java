package com.example.loanservice.controller;

import com.example.loanservice.dto.request.RequestDelOrder;
import com.example.loanservice.dto.request.RequestNewOrder;
import com.example.loanservice.dto.response.ResponseMessageData;
import com.example.loanservice.service.serviceInteface.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan-service")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
   // private final OrderDAO orderDAO;

    @PostMapping("/order")
    public ResponseEntity<?> addOrder(@RequestBody RequestNewOrder order) {

        return ResponseEntity.ok(
                new ResponseMessageData(orderService.handlingNewOrder(order)));
    }

//    @GetMapping("/rand")
//    public Double checkRandom(){
//        return orderServiceImpl.onCreateGenerateCredRat();
//    }
//
//    @GetMapping("/orderByUserId/{userId}")
//    public ResponseEntity<?> getOrdersById(@PathVariable Long userId) {
//
//        return ResponseEntity.ok(orderDAO.findOrdersByUserId(userId));
//    }

    @GetMapping("/getStatusOrder")
    public ResponseEntity<?> getOrdersStatus(@RequestParam("orderId") String orderId) {

        return ResponseEntity.ok(orderService.showOrderStatus(orderId));
    }

    @PostMapping("/deleteOrder")
    public ResponseEntity<?> deleteOrder(@RequestBody RequestDelOrder requestDelOrder) {

        orderService.delOrderFromDB(requestDelOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
