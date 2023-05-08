package com.example.loanservice.service.serviceImpl;

import com.example.loanservice.dao.OrderDAO;
import com.example.loanservice.dao.TariffDAO;
import com.example.loanservice.dto.request.RequestDelOrder;
import com.example.loanservice.dto.request.RequestNewOrder;
import com.example.loanservice.dto.response.RespoonseUtil.ResponseOrderId;
import com.example.loanservice.dto.response.RespoonseUtil.ResponseOrderStatus;
import com.example.loanservice.entity.Order;
import com.example.loanservice.entity.enums.OrderStatus;
import com.example.loanservice.exception.customException.OrderImpossibleToDeleteException;
import com.example.loanservice.exception.customException.OrderNotFoundException;
import com.example.loanservice.exception.customException.TariffNotFoundException;
import com.example.loanservice.service.serviceInteface.OrderService;
import com.example.loanservice.service.utilService.CheckOrdersUtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final TariffDAO tariffDAO;
    private final OrderDAO orderDAO;
    private final CheckOrdersUtilService checkOrdersUtilService;

    @Override
    public ResponseOrderId handlingNewOrder(RequestNewOrder requestOrder) {

        if (!tariffDAO.existByTariffId(requestOrder.getTariffId())) {
            throw new TariffNotFoundException("Тариф не найден");
        }
        checkOrdersUtilService.checkUsersOrders(requestOrder);
        Order newOrder = new Order(requestOrder.getUserId(), requestOrder.getTariffId());
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
        throw new OrderNotFoundException("Заявка не найдена");
    }

    @Override
    public boolean delOrderFromDB(RequestDelOrder requestDelOrder) {

        Order orderFromDB = orderDAO.findOrderByOrderIdAndUserId(
                requestDelOrder.getOrderId(), requestDelOrder.getUserId());

        if (orderFromDB == null) {
            throw new OrderNotFoundException("Заявка не найдена");
        }
        if (orderFromDB.getStatus().equals(OrderStatus.IN_PROGRESS)) {
            throw new OrderImpossibleToDeleteException("Невозможно удалить заявку");
        } else {
            orderDAO.deleteOrderByOrderId(orderFromDB.getOrder_id());
        }
        return true;
    }

    @Override
    @Scheduled(fixedRateString = "${schedule.fixedRate}")
    @SchedulerLock(name = "orderService_resolveForLoanOrders")
    public void resolveForLoanOrders() {

        List<Order> orderList = orderDAO
                .getOrdersByStatus(OrderStatus.IN_PROGRESS.toString());
        orderList.forEach(order -> checkOrdersUtilService.getSolutionForOrder(order));
    }

}
