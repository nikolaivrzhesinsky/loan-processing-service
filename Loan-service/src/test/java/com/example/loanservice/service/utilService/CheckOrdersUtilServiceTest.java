package com.example.loanservice.service.utilService;

import com.example.loanservice.dao.OrderDAO;
import com.example.loanservice.dto.request.RequestNewOrder;
import com.example.loanservice.entity.Order;
import com.example.loanservice.entity.enums.OrderStatus;
import com.example.loanservice.exception.customException.LoanAlreadyApprovedException;
import com.example.loanservice.exception.customException.LoanConsiderationException;
import com.example.loanservice.exception.customException.TryLaterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckOrdersUtilServiceTest {

    @Mock
    private OrderDAO orderDAO;
    @InjectMocks
    private CheckOrdersUtilService checkOrdersUtilService;

    private Order order;

    @BeforeEach
    public void setup() {
        order = new Order(120356894755L, 4L);
    }

    @Test
    void shouldCheckUsersOrdersAndThrowLoanConsiderationException() {

        CheckOrdersUtilService checkMock = mock(CheckOrdersUtilService.class);

        List<Order> ordersByUserId = List.of(
                new Order(120356894755L, 3L),
                new Order(120356894755L, 2L)
        );

        when(orderDAO.findOrdersByUserId(any(Long.class)))
                .thenReturn(ordersByUserId);
        RequestNewOrder requestNewOrder = new RequestNewOrder(120356894755L, 3L);

        assertThrows(LoanConsiderationException.class, () -> checkOrdersUtilService
                .checkUsersOrders(requestNewOrder));

    }

    @Test
    void shouldCheckUsersOrdersAndThrowLoanAlreadyApprovedException() {

        CheckOrdersUtilService checkMock = mock(CheckOrdersUtilService.class);

        List<Order> ordersByUserId = List.of(
                new Order(120356894755L, 3L),
                new Order(120356894755L, 2L)
        );
        ordersByUserId.get(0).setStatus(OrderStatus.APPROVED);

        when(orderDAO.findOrdersByUserId(any(Long.class)))
                .thenReturn(ordersByUserId);
        RequestNewOrder requestNewOrder = new RequestNewOrder(120356894755L, 3L);

        assertThrows(LoanAlreadyApprovedException.class, () -> checkOrdersUtilService
                .checkUsersOrders(requestNewOrder));

    }

    @Test
    void shouldCheckUsersOrdersAndThrowTryLaterException() {

        CheckOrdersUtilService checkMock = mock(CheckOrdersUtilService.class);

        List<Order> ordersByUserId = List.of(
                new Order(120356894755L, 3L),
                new Order(120356894755L, 2L)
        );
        ordersByUserId.get(0).setStatus(OrderStatus.REFUSED);

        when(orderDAO.findOrdersByUserId(any(Long.class)))
                .thenReturn(ordersByUserId);
        RequestNewOrder requestNewOrder = new RequestNewOrder(120356894755L, 3L);

        assertThrows(TryLaterException.class, () -> checkOrdersUtilService
                .checkUsersOrders(requestNewOrder));

    }

    @Test
    void shouldCheckUsersOrdersAndReturnNothing() {

        CheckOrdersUtilService checkMock = mock(CheckOrdersUtilService.class);

        List<Order> ordersByUserId = List.of(
                new Order(120356894755L, 3L),
                new Order(120356894755L, 2L)
        );

        when(orderDAO.findOrdersByUserId(any(Long.class)))
                .thenReturn(ordersByUserId);
        RequestNewOrder requestNewOrder = new RequestNewOrder(120356894755L, 4L);

        checkOrdersUtilService.checkUsersOrders(requestNewOrder);

    }

    @Test
    void shouldCheckOrderStatusAndThrowLoanConsiderationException() {

        assertThrows(LoanConsiderationException.class
                , () -> checkOrdersUtilService.checkOrderStatus(order));
    }

    @Test
    void shouldCheckOrderStatusAndThrowLoanAlreadyApprovedException() {
        order.setStatus(OrderStatus.APPROVED);
        assertThrows(LoanAlreadyApprovedException.class
                , () -> checkOrdersUtilService.checkOrderStatus(order));
    }

    @Test
    void shouldCheckOrderStatusAndThrowTryLaterException() {
        order.setStatus(OrderStatus.REFUSED);
        assertThrows(TryLaterException.class
                , () -> checkOrdersUtilService.checkOrderStatus(order));
    }

    @Test
    void shouldGetSolutionForOrder() {

        long randId = new Random().nextLong();

        doNothing().when(orderDAO).update(any(), any(Order.class));
        checkOrdersUtilService.getSolutionForOrder(order);

        assertNotEquals(OrderStatus.IN_PROGRESS, order.getStatus());
    }
}