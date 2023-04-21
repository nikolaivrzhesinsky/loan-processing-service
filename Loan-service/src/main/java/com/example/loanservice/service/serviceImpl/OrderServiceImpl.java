package com.example.loanservice.service.serviceImpl;

import com.example.loanservice.dto.request.RequestOrder;
import com.example.loanservice.dto.response.RespoonseUtil.ResponseOrderId;
import com.example.loanservice.entity.Order;
import com.example.loanservice.entity.enums.OrderStatus;
import com.example.loanservice.service.serviceInteface.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    @Override
    public ResponseOrderId handlingOrder(RequestOrder requestOrder) {

        return new ResponseOrderId(createOrder(requestOrder).getOrder_id());
    }


    private Order createOrder(RequestOrder requestOrder) {

        return Order.builder()
                .order_id(onCreateOrderId())
                .user_id(requestOrder.getUserId())
                .tariff_id(requestOrder.getTariffId())
                .credit_rating(onCreateGenerateCredRat())
                .status(OrderStatus.IN_PROGRESS)
                .time_insert(onCreateUpdateTime())
                .time_update(onCreateUpdateTime())
                .build();
    }


    private LocalDateTime onCreateUpdateTime() {
        return LocalDateTime.now();
    }

    private String onCreateOrderId() {
        return UUID.randomUUID().toString();
    }

    private Double onCreateGenerateCredRat() {

        double lowerBound = 0.10;
        double upperBound = 0.90;
        int decimalPlaces = 2;

        final double dbl =
                new Random().nextDouble() * (upperBound - lowerBound) + lowerBound;
        //String dblString=String.format("%." + decimalPlaces + "f", dbl);
        BigDecimal bd = BigDecimal.valueOf(dbl)
                .setScale(decimalPlaces, RoundingMode.HALF_UP);
        return bd.doubleValue();

    }

}
