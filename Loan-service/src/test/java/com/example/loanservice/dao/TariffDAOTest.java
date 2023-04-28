package com.example.loanservice.dao;

import com.example.loanservice.entity.Tariff;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@JdbcTest
@ActiveProfiles("test")
class TariffDAOTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void shouldFindAllThenCountIsFour() {

        TariffDAO tariffDAO = new TariffDAO(jdbcTemplate);

        List<Tariff> tariffList = tariffDAO.findAll();
        tariffList.forEach(System.out::println);
        assertEquals(4, tariffList.size());
    }

    @Test
    void shouldExistByTariffIdTrue() {

        TariffDAO tariffDAO = new TariffDAO(jdbcTemplate);

        boolean res = tariffDAO.existByTariffId(1L);
        assertTrue(res);
    }

    @Test
    void shouldNotExistByTariffIdFalse() {

        TariffDAO tariffDAO = new TariffDAO(jdbcTemplate);

        boolean res = tariffDAO.existByTariffId(5L);
        assertFalse(res);
    }
}