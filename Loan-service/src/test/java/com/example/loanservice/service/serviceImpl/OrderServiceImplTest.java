package com.example.loanservice.service.serviceImpl;

import com.example.loanservice.dao.OrderDAO;
import com.example.loanservice.dao.TariffDAO;
import com.example.loanservice.dto.request.RequestDelOrder;
import com.example.loanservice.dto.request.RequestNewOrder;
import com.example.loanservice.dto.response.RespoonseUtil.ResponseOrderId;
import com.example.loanservice.entity.Order;
import com.example.loanservice.entity.enums.OrderStatus;
import com.example.loanservice.exception.customException.OrderImpossibleToDeleteException;
import com.example.loanservice.exception.customException.OrderNotFoundException;
import com.example.loanservice.service.utilService.CheckOrdersUtilService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
@RunWith(SpringRunner.class)
class OrderServiceImplTest {

    @Mock
    private OrderDAO orderDAO;
    @Mock
    private TariffDAO tariffDAO;
    @Mock
    private CheckOrdersUtilService checkOrdersUtilService;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;

    @BeforeEach
    public void setup() {
        order = new Order(120356894755L, 4L);
    }

    @Test
    void shouldHandAndCreateNewOrder_AndReturnResponseOrderId() {

        RequestNewOrder requestNewOrder = new RequestNewOrder(120356894755L, 3L);

        when(tariffDAO.existByTariffId(any(Long.class)))
                .thenReturn(true);
        doNothing().when(checkOrdersUtilService)
                .checkUsersOrders(any(RequestNewOrder.class));
        doNothing().when(orderDAO).save(any(Order.class));

        ResponseOrderId responseOrderId = orderService.handlingNewOrder(requestNewOrder);
        log.info(responseOrderId.getOrderId());

        assertNotNull(responseOrderId);
    }

    @Test
    void shouldShowOrderStatusAndReturnResponseOrderStatus() {

        var orderId = "320890f8-bc40-4e00-8a98-5ebf294e2952";
        when(orderDAO.findOrderStatusById(any(String.class)))
                .thenReturn(OrderStatus.IN_PROGRESS.toString());

        assertEquals(OrderStatus.IN_PROGRESS.toString()
                , orderService.showOrderStatus(orderId).getOrderStatus());
    }

    @Test
    void shouldNotShowOrderStatusAndThrowOrderNotFoundException() {

        var orderId = "320890f8-bc40-4e00-8a98-5ebf294e2952";
        when(orderDAO.findOrderStatusById(any(String.class)))
                .thenReturn(null);

        assertThrows(OrderNotFoundException.class
                , () -> orderService.showOrderStatus(orderId));
    }

    @Test
    void shouldDelOrderFromDBAndReturnTrue() {

        var requestDel = new RequestDelOrder(order.getOrder_id(),
                120356894755L);
        order.setStatus(OrderStatus.REFUSED);
        when(orderDAO.findOrderByOrderIdAndUserId(isA(String.class), isA(Long.class)))
                .thenReturn(order);

        assertTrue(orderService.delOrderFromDB(requestDel));
    }

    @Test
    void shouldNotDelOrderFromDBAndThrowOrderNotFoundException() {

        var requestDel = new RequestDelOrder(order.getOrder_id(),
                120356894755L);

        when(orderDAO.findOrderByOrderIdAndUserId(isA(String.class), isA(Long.class)))
                .thenReturn(null);

        assertThrows(OrderNotFoundException.class,
                () -> orderService.delOrderFromDB(requestDel));
    }

    @Test
    void shouldNotDelOrderFromDBAndThrowOrderImpossibleToDeleteException() {

        var requestDel = new RequestDelOrder(order.getOrder_id(),
                120356894755L);
        order.setStatus(OrderStatus.IN_PROGRESS);

        when(orderDAO.findOrderByOrderIdAndUserId(isA(String.class), isA(Long.class)))
                .thenReturn(order);

        assertThrows(OrderImpossibleToDeleteException.class,
                () -> orderService.delOrderFromDB(requestDel));
    }

    @Test
    void shouldResolveForLoanOrdersAndGetSolutionForOne() {

        List<Order> orders = List.of(
                new Order(120356894755L, 3L)
        );
        when(orderDAO.getOrdersByStatus(OrderStatus.IN_PROGRESS.toString()))
                .thenReturn(orders);
        doNothing().when(checkOrdersUtilService)
                .getSolutionForOrder(any(Order.class));

        orderService.resolveForLoanOrders();

        verify(checkOrdersUtilService, times(1))
                .getSolutionForOrder(any());
    }

}