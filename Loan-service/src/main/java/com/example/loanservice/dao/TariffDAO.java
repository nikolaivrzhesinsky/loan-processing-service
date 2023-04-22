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

    public boolean existByTariffId(Long tariffId) {

        String query = "SELECT EXISTS(SELECT * FROM tariff WHERE id= ?)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                query, new Object[]{tariffId}, Boolean.class));
    }


}
