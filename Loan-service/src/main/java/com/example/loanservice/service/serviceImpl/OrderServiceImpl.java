package com.example.loanservice.service.serviceImpl;

import com.example.loanservice.dao.OrderDAO;
import com.example.loanservice.dao.TariffDAO;
import com.example.loanservice.dto.request.RequestDelOrder;
import com.example.loanservice.dto.request.RequestNewOrder;
import com.example.loanservice.dto.response.RespoonseUtil.ResponseOrderId;
import com.example.loanservice.dto.response.RespoonseUtil.ResponseOrderStatus;
import com.example.loanservice.entity.Order;
import com.example.loanservice.entity.enums.OrderStatus;
import com.example.loanservice.exception.customException.*;
import com.example.loanservice.service.serviceInteface.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final TariffDAO tariffDAO;
    private final OrderDAO orderDAO;

    @Override
    public ResponseOrderId handlingNewOrder(RequestNewOrder requestOrder) {

        if (!tariffDAO.existByTariffId(requestOrder.getTariffId())) {
            throw new TARIFF_NOT_FOUND("Тариф не найден");
        }
        checkUsersOrders(requestOrder);
        Order newOrder = createOrder(requestOrder);
        orderDAO.save(newOrder);

        return new ResponseOrderId(newOrder.getOrder_id());
    }

    @Override
    public ResponseOrderStatus showOrderStatus(String orderId) {

        String orderStatus = orderDAO.findOrderStatusById(orderId);
        log.info(orderStatus);
        if (orderStatus != null) {
            return new ResponseOrderStatus(orderStatus);
        }
        throw new ORDER_NOT_FOUND("Заявка не найдена");
    }

    @Override
    public void delOrderFromDB(RequestDelOrder requestDelOrder) {

        Order orderFromDB = orderDAO.findOrderByOrderIdAndUserId(
                requestDelOrder.getOrderId(), requestDelOrder.getUserId());

        if (orderFromDB == null) {
            throw new ORDER_NOT_FOUND("Заявка не найдена");
        }
        if (orderFromDB.getStatus().equals(OrderStatus.IN_PROGRESS)) {
            throw new ORDER_IMPOSSIBLE_TO_DELETE("Невозможно удалить заявку");
        } else {
            orderDAO.deleteOrderByOrderId(orderFromDB.getOrder_id());
            log.info("Order was deleted");
        }
    }

    @Override
    @Scheduled(fixedRateString = "${schedule.fixedRate}")
    @SchedulerLock(name = "orderService_resolveForLoanOrders")
    public void resolveForLoanOrders() {

        List<Order> orderList = orderDAO
                .getOrdersByStatus(OrderStatus.IN_PROGRESS.toString());
        orderList.forEach(this::getSolutionForOrder);
    }

    @Transactional
    public void getSolutionForOrder(Order order) {

        if (Math.random() < 0.5) {
            order.setStatus(OrderStatus.REFUSED);
        } else {
            order.setStatus(OrderStatus.APPROVED);
        }
        order.setTime_update(onCreateUpdateTime());
        log.info(order.getStatus().toString());

        orderDAO.update(order.getId(), order);
    }

    private void checkUsersOrders(RequestNewOrder requestOrder) {

        List<Order> ordersByTariff = orderDAO
                .findOrdersByUserId(requestOrder.getUserId());
        ordersByTariff.stream()
                .filter(order -> Objects.equals(order.getTariff_id(), requestOrder.getTariffId()))
                .forEach(this::checkOrderStatus);
    }

    private void checkOrderStatus(Order orderByTariff) {

        if (orderByTariff.getStatus().equals(OrderStatus.IN_PROGRESS)) {
            throw new LOAN_CONSIDERATION("Order is already in consideration");
        }
        if (orderByTariff.getStatus().equals(OrderStatus.APPROVED)) {
            throw new LOAN_ALREADY_APPROVED("Order was already approved");
        }
        if (orderByTariff.getStatus().equals(OrderStatus.REFUSED)
                && (orderByTariff.getTime_update().plusMinutes(2)).isAfter(LocalDateTime.now())) {
            throw new TRY_LATER("Try later");
        }
    }


    private Order createOrder(RequestNewOrder requestOrder) {

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
