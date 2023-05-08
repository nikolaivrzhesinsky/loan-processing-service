package com.example.loanservice.service.utilService;

import com.example.loanservice.dao.OrderDAO;
import com.example.loanservice.dto.request.RequestNewOrder;
import com.example.loanservice.entity.Order;
import com.example.loanservice.entity.enums.OrderStatus;
import com.example.loanservice.exception.customException.LoanAlreadyApprovedException;
import com.example.loanservice.exception.customException.LoanConsiderationException;
import com.example.loanservice.exception.customException.TryLaterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckOrdersUtilService {

    private final OrderDAO orderDAO;

    public void checkUsersOrders(RequestNewOrder requestOrder) {

        List<Order> ordersByUserId = orderDAO
                .findOrdersByUserId(requestOrder.getUserId());
        ordersByUserId.stream()
                .filter(order -> Objects.equals(order.getTariff_id(), requestOrder.getTariffId()))
                .forEach(this::checkOrderStatus);
    }

    public void checkOrderStatus(Order orderByTariff) {

        if (orderByTariff.getStatus().equals(OrderStatus.IN_PROGRESS)) {
            throw new LoanConsiderationException("Order is already in consideration");
        }
        if (orderByTariff.getStatus().equals(OrderStatus.APPROVED)) {
            throw new LoanAlreadyApprovedException("Order was already approved");
        }
        if (orderByTariff.getStatus().equals(OrderStatus.REFUSED)
                && (orderByTariff.getTime_update().plusMinutes(2)).isAfter(LocalDateTime.now())) {
            throw new TryLaterException("Try later");
        }
    }

    public void getSolutionForOrder(Order order) {

        if (Math.random() < 0.5) {
            order.setStatus(OrderStatus.REFUSED);
        } else {
            order.setStatus(OrderStatus.APPROVED);
        }
        order.setTime_update(LocalDateTime.now());

        orderDAO.update(order.getId(), order);
    }
}
