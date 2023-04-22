package com.example.loanservice.dao;

import com.example.loanservice.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


}
