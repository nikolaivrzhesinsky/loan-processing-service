package com.example.loanservice.controller;

import com.example.loanservice.dto.request.RequestDelOrder;
import com.example.loanservice.dto.request.RequestNewOrder;
import com.example.loanservice.dto.response.ResponseMessageData;
import com.example.loanservice.dto.response.RespoonseUtil.ResponseOrderId;
import com.example.loanservice.exception.ErrorMessage;
import com.example.loanservice.service.serviceInteface.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan-service")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    @Operation(
            description = "adding new order for loan",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success Created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseOrderId.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "data": {
                                                                    "orderId": "74aab8f8-7309-4276-bf3e-e06e92a1bc79"
                                                                }
                                                            }"""
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Order already in consideration",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "LOAN_CONSIDERATION",
                                                    value = """
                                                            {
                                                                "error": {
                                                                    "code": "LOAN_CONSIDERATION",
                                                                    "message": "Order already in consideration"
                                                                }
                                                            }"""
                                            ),
                                            @ExampleObject(
                                                    name = "LOAN_ALREADY_APPROVED",
                                                    value = """
                                                            {
                                                                "error": {
                                                                    "code": "LOAN_ALREADY_APPROVED",
                                                                    "message": "Order was already approved"
                                                                }
                                                            }"""
                                            ),
                                            @ExampleObject(
                                                    name = "TRY_LATER",
                                                    value = """
                                                            {
                                                                "error": {
                                                                    "code": "TRY_LATER",
                                                                    "message": "Try to make order later"
                                                                }
                                                            }"""
                                            )
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<?> addOrder(@RequestBody RequestNewOrder order) {

        return ResponseEntity.ok(
                new ResponseMessageData(orderService.handlingNewOrder(order)));
    }


    @GetMapping("/getStatusOrder")
    @Operation(
            description = "get order status by req param",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "success getting order status",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseOrderId.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "orderStatus": "APPROVED"
                                                            }"""
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Order already in consideration",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "ORDER_NOT_FOUND",
                                                    value = """
                                                            {
                                                                "error": {
                                                                    "code": "ORDER_NOT_FOUND",
                                                                    "message": "Заявка не найдена"
                                                                }
                                                            }"""
                                            )
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<?> getOrdersStatus(@RequestParam("orderId") String orderId) {

        return ResponseEntity.ok(orderService.showOrderStatus(orderId));
    }

    @PostMapping("/deleteOrder")
    @Operation(
            description = "del order from service",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "success deleting"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Order already in consideration",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "ORDER_NOT_FOUND",
                                                    value = """
                                                            {
                                                                "error": {
                                                                    "code": "ORDER_NOT_FOUND",
                                                                    "message": "Заявка не найдена"
                                                                }
                                                            }"""
                                            ),
                                            @ExampleObject(
                                                    name = "ORDER_IMPOSSIBLE_TO_DELETE",
                                                    value = """
                                                            {
                                                                "error": {
                                                                    "code": "ORDER_IMPOSSIBLE_TO_DELETE",
                                                                    "message": "Невозможно удалить заявку"
                                                                }
                                                            }"""
                                            )
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<?> deleteOrder(@RequestBody RequestDelOrder requestDelOrder) {

        orderService.delOrderFromDB(requestDelOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
