package com.example.loanservice.service.serviceInteface;

import com.example.loanservice.dto.request.RequestDelOrder;
import com.example.loanservice.dto.request.RequestNewOrder;
import com.example.loanservice.dto.response.RespoonseUtil.ResponseOrderId;
import com.example.loanservice.dto.response.RespoonseUtil.ResponseOrderStatus;

public interface OrderService {

    ResponseOrderId handlingNewOrder(RequestNewOrder requestOrder);

    ResponseOrderStatus showOrderStatus(String orderId);

    void delOrderFromDB(RequestDelOrder requestDelOrder);
}
