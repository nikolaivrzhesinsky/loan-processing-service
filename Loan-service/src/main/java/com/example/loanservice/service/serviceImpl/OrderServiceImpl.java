package com.example.loanservice.service.serviceImpl;

import com.example.loanservice.dao.OrderDAO;
import com.example.loanservice.dao.TariffDAO;
import com.example.loanservice.dto.request.RequestOrder;
import com.example.loanservice.dto.response.RespoonseUtil.ResponseOrderId;
import com.example.loanservice.entity.Order;
import com.example.loanservice.entity.enums.OrderStatus;
import com.example.loanservice.exception.customException.TARIFF_NOT_FOUND;
import com.example.loanservice.service.serviceInteface.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final TariffDAO tariffDAO;
    private final OrderDAO orderDAO;

    @Override
    public ResponseOrderId handlingOrder(RequestOrder requestOrder) {

        if (!tariffDAO.existByTariffId(requestOrder.getTariffId())) {
            throw new TARIFF_NOT_FOUND("Тариф не найден");
        }

        checkUsersOrders(requestOrder);


        Order newOrder = createOrder(requestOrder);
        orderDAO.save(newOrder);

        return new ResponseOrderId(newOrder.getOrder_id());
    }

    private void checkUsersOrders(RequestOrder requestOrder) {

        Optional<Order> orderByTariff = orderDAO
                .findOrdersByUserId(requestOrder.getUserId())
                .stream().filter(order -> Objects.equals(order.getTariff_id(), requestOrder.getTariffId()))
                .findFirst();
        log.info(orderByTariff.toString());
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
        BigDecimal bd = BigDecimal.valueOf(dbl)
                .setScale(decimalPlaces, RoundingMode.HALF_UP);
        return bd.doubleValue();

    }

}
