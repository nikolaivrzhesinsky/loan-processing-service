package com.example.loanservice.controller;

import com.example.loanservice.dto.request.RequestDelOrder;
import com.example.loanservice.dto.request.RequestNewOrder;
import com.example.loanservice.dto.response.RespoonseUtil.ResponseOrderId;
import com.example.loanservice.dto.response.RespoonseUtil.ResponseOrderStatus;
import com.example.loanservice.entity.enums.OrderStatus;
import com.example.loanservice.exception.customException.*;
import com.example.loanservice.service.serviceInteface.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAddOrderAndReturnOrderId() throws Exception {

        var requestNewOrder = new RequestNewOrder(120356894755L, 2L);
        var responseOrderId = new ResponseOrderId("320890f8-bc40-4e00-8a98-5ebf294e2952");
        when(orderService.handlingNewOrder(any(RequestNewOrder.class)))
                .thenReturn(responseOrderId);
        mockMvc.perform(post("/loan-service/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestNewOrder)))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    void shouldNotAddOrderAndThrowTARIFF_NOT_FOUND() throws Exception {

        var requestNewOrder = new RequestNewOrder(120356894755L, 5L);
        when(orderService.handlingNewOrder(any(RequestNewOrder.class)))
                .thenThrow(TARIFF_NOT_FOUND.class);
        mockMvc.perform(post("/loan-service/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestNewOrder)))
                .andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    void shouldNotAddOrderAndThrowLOAN_CONSIDERATION() throws Exception {

        var requestNewOrder = new RequestNewOrder(120356894755L, 5L);
        when(orderService.handlingNewOrder(any(RequestNewOrder.class)))
                .thenThrow(LOAN_CONSIDERATION.class);
        mockMvc.perform(post("/loan-service/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestNewOrder)))
                .andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    void shouldNotAddOrderAndThrowLOAN_ALREADY_APPROVED() throws Exception {

        var requestNewOrder = new RequestNewOrder(120356894755L, 5L);
        when(orderService.handlingNewOrder(any(RequestNewOrder.class)))
                .thenThrow(LOAN_ALREADY_APPROVED.class);
        mockMvc.perform(post("/loan-service/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestNewOrder)))
                .andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    void shouldNotAddOrderAndThrowTRY_LATER() throws Exception {

        var requestNewOrder = new RequestNewOrder(120356894755L, 5L);
        when(orderService.handlingNewOrder(any(RequestNewOrder.class)))
                .thenThrow(TRY_LATER.class);
        mockMvc.perform(post("/loan-service/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestNewOrder)))
                .andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    void shouldGetOrdersStatus() throws Exception {

        var resStatus = new ResponseOrderStatus(OrderStatus.APPROVED.toString());
        var directorIdRand = UUID.randomUUID();
        String requestUrl = "/loan-service/getStatusOrder?orderId=" + directorIdRand;

        when(orderService.showOrderStatus(any(String.class)))
                .thenReturn(resStatus);
        mockMvc.perform(get(requestUrl))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldDeleteOrder() throws Exception {

        var reqDel = new RequestDelOrder("320890f8-bc40-4e00-8a98-5ebf294e2952"
                , 120356894755L);
        mockMvc.perform(post("/loan-service/deleteOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqDel)))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    void shouldNotDeleteOrder_AndThrowORDER_NOT_FOUND() throws Exception {

        var reqDel = new RequestDelOrder("320890f8-bc40-4e00-8a98-5ebf294e2952"
                , 120356894755L);
        when(orderService.delOrderFromDB(any(RequestDelOrder.class)))
                .thenThrow(ORDER_NOT_FOUND.class);

        mockMvc.perform(post("/loan-service/deleteOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqDel)))
                .andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    void shouldNotDeleteOrder_AndThrowORDER_IMPOSSIBLE_TO_DELETE() throws Exception {

        var reqDel = new RequestDelOrder("320890f8-bc40-4e00-8a98-5ebf294e2952"
                , 120356894755L);
        when(orderService.delOrderFromDB(any(RequestDelOrder.class)))
                .thenThrow(ORDER_IMPOSSIBLE_TO_DELETE.class);

        mockMvc.perform(post("/loan-service/deleteOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqDel)))
                .andExpect(status().isBadRequest()).andDo(print());
    }


}