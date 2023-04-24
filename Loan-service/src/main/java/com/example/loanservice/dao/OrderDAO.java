package com.example.loanservice.dao;

import com.example.loanservice.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderDAO {

    private final JdbcTemplate jdbcTemplate;

    public void save(Order newOrder) {

        jdbcTemplate.update("INSERT INTO loan_order " +
                        "(order_id, user_id, tariff_id, credit_rating, status, time_insert, time_update)" +
                        "values (?,?,?,?,?,?,?)", newOrder.getOrder_id(), newOrder.getUser_id()
                , newOrder.getTariff_id(), newOrder.getCredit_rating()
                , newOrder.getStatus().toString(), newOrder.getTime_insert(), newOrder.getTime_update());
        log.info("Order was added");
    }

    public List<Order> findOrdersByUserId(Long userId) {

        return jdbcTemplate.query("SELECT * FROM loan_order WHERE user_id= ?"
                , new BeanPropertyRowMapper<>(Order.class), userId);
    }

    public String findOrderStatusById(String orderId) {

        String query = "SELECT status FROM loan_order WHERE order_id= ?";
        try {
            String resStatus = jdbcTemplate.queryForObject(
                    query, new Object[]{orderId}, String.class);
            log.info(resStatus);
            return resStatus;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Order findOrderByOrderIdAndUserId(String orderId, Long userId) {

        return jdbcTemplate.query("SELECT * FROM loan_order WHERE order_id= ? AND user_id= ?"
                        , new BeanPropertyRowMapper<>(Order.class), orderId, userId)
                .stream().findAny().orElse(null);
    }

    public void deleteOrderByOrderId(String orderId) {

        jdbcTemplate.update("DELETE FROM loan_order WHERE order_id= ?", orderId);
    }

    public List<Order> getOrdersByStatus(String status) {

        return jdbcTemplate.query("SELECT * FROM loan_order WHERE status= ?"
                , new BeanPropertyRowMapper<>(Order.class), status);
    }

    public void update(Long id, Order updateOrder) {

        jdbcTemplate.update("UPDATE loan_order SET order_id= ?,user_id= ?" +
                        ",tariff_id= ?,credit_rating= ?,status= ?,time_insert= ?,time_update= ? " +
                        "WHERE id= ?"
                , updateOrder.getOrder_id(), updateOrder.getUser_id()
                , updateOrder.getTariff_id(), updateOrder.getCredit_rating()
                , updateOrder.getStatus().toString(), updateOrder.getTime_insert()
                , updateOrder.getTime_update(), id);
        log.info("Order was updated");
    }

}
