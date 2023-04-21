package com.example.loanservice.dao;

import com.example.loanservice.entity.Tariff;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TariffDAO {

    private final JdbcTemplate jdbcTemplate;

    public List<Tariff> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM tariff", new BeanPropertyRowMapper<>(Tariff.class)
        );
    }


}
