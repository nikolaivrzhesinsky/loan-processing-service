package com.example.loanservice.service.serviceInteface;

import com.example.loanservice.dto.request.RequestOrder;
import com.example.loanservice.dto.response.RespoonseUtil.ResponseOrderId;

public interface OrderService {

    ResponseOrderId handlingOrder(RequestOrder requestOrder);
}
