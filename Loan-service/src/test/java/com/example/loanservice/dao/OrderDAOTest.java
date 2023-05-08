package com.example.loanservice.dao;

import com.example.loanservice.entity.Order;
import com.example.loanservice.entity.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@JdbcTest
@ActiveProfiles("test")
class OrderDAOTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    void initOrders() {

        OrderDAO orderDAO = new OrderDAO(jdbcTemplate);

        orderDAO.save(new Order(
                1L, "320890f8-bc40-4e00-8a98-5ebf294e2952", 120356894755L, 2L, 0.65
                , OrderStatus.APPROVED, LocalDateTime.now(), LocalDateTime.now().minusDays(1)));
        orderDAO.save(new Order(
                2L, "320890f8-bc40-4e00-8a98-5ebf294e2953", 120356894756L, 3L, 0.65
                , OrderStatus.IN_PROGRESS, LocalDateTime.now(), LocalDateTime.now().minusDays(1)));
        orderDAO.save(new Order(
                3L, "320890f8-bc40-4e00-8a98-5ebf294e2954", 120356894756L, 4L, 0.64
                , OrderStatus.REFUSED, LocalDateTime.now(), LocalDateTime.now().minusDays(2)));
    }

    @Test
    void shouldSave() {
        OrderDAO orderDAO = new OrderDAO(jdbcTemplate);

        orderDAO.save(new Order(
                4L, "320890f8-bc40-4e00-8a98-5ebf294e2952", 120356894755L, 2L, 0.65
                , OrderStatus.APPROVED, LocalDateTime.now(), LocalDateTime.now().minusDays(1)));
        assertEquals("APPROVED", orderDAO.findOrderStatusById("320890f8-bc40-4e00-8a98-5ebf294e2952"));
    }

    @Test
    void shouldFindOrdersByUserId() {
        initOrders();
        OrderDAO orderDAO = new OrderDAO(jdbcTemplate);
        List<Order> res = orderDAO.findOrdersByUserId(120356894756L);
        res.forEach(System.out::println);
        assertEquals(2, res.size());
    }

    @Test
    void shouldNotFindOrdersByUserId() {
        initOrders();
        OrderDAO orderDAO = new OrderDAO(jdbcTemplate);
        List<Order> res = orderDAO.findOrdersByUserId(120356894759L);
        res.forEach(System.out::println);
        assertEquals(0, res.size());
    }

    @Test
    void shouldFindOrderStatusById() {
        initOrders();
        OrderDAO orderDAO = new OrderDAO(jdbcTemplate);
        assertEquals("APPROVED", orderDAO
                .findOrderStatusById("320890f8-bc40-4e00-8a98-5ebf294e2952"));
    }

    @Test
    void shouldNotFindOrderStatusById_AndReturnNull() {
        initOrders();
        OrderDAO orderDAO = new OrderDAO(jdbcTemplate);
        assertNull(orderDAO
                .findOrderStatusById("320890f8-bc40-4e00-8a98-5ebf294e2992"));
    }

    @Test
    void shouldFindOrderByOrderIdAndUserId() {
        initOrders();
        OrderDAO orderDAO = new OrderDAO(jdbcTemplate);
        Order expectedOrder = new Order(
                1L, "320890f8-bc40-4e00-8a98-5ebf294e2952", 120356894755L, 2L, 0.65
                , OrderStatus.APPROVED, LocalDateTime.now(), LocalDateTime.now().minusDays(1));
        assertEquals(expectedOrder.getOrder_id(), orderDAO.findOrderByOrderIdAndUserId(
                "320890f8-bc40-4e00-8a98-5ebf294e2952", 120356894755L).getOrder_id());
    }

    @Test
    void shouldNotFindOrderByOrderIdAndUserId_AndReturnNull() {
        initOrders();
        OrderDAO orderDAO = new OrderDAO(jdbcTemplate);
        assertNull(orderDAO.findOrderByOrderIdAndUserId(
                "320890f8-bc40-4e00-8a98-5ebf294e2957", 120356894775L));
    }

    @Test
    void shouldDeleteOrderByOrderId() {
        initOrders();
        OrderDAO orderDAO = new OrderDAO(jdbcTemplate);
        orderDAO.deleteOrderByOrderId("320890f8-bc40-4e00-8a98-5ebf294e2952");
        assertNull(orderDAO.findOrderByOrderIdAndUserId(
                "320890f8-bc40-4e00-8a98-5ebf294e2952"
                , 120356894755L));
    }

    @Test
    void shouldGetOrdersByStatus() {
        initOrders();
        OrderDAO orderDAO = new OrderDAO(jdbcTemplate);
        assertEquals(1, orderDAO
                .getOrdersByStatus(OrderStatus.IN_PROGRESS.toString()).size());
    }

    @Test
    void shouldUpdate() {
        initOrders();
        OrderDAO orderDAO = new OrderDAO(jdbcTemplate);
        Order updateOrder = (new Order(
                1L, "320890f8-bc40-4e00-8a98-5ebf294e2952", 120356894755L, 2L, 0.65
                , OrderStatus.REFUSED, LocalDateTime.now(), LocalDateTime.now().minusDays(1)));
        orderDAO.update(1L, updateOrder);

        assertEquals(OrderStatus.REFUSED, orderDAO
                .findOrderByOrderIdAndUserId("320890f8-bc40-4e00-8a98-5ebf294e2952", 120356894755L)
                .getStatus());

    }
}