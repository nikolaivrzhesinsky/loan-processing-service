package com.example.loanservice.service.serviceImpl;

import com.example.loanservice.dao.TariffDAO;
import com.example.loanservice.dto.response.RespoonseUtil.ResponseTariffs;
import com.example.loanservice.entity.Tariff;
import com.example.loanservice.entity.enums.TariffType;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@Slf4j
@RunWith(SpringRunner.class)
class TariffServiceImplTest {

    @Mock
    private TariffDAO tariffDAO;
    @InjectMocks
    private TariffServiceImpl tariffService;

    List<Tariff> tariffList;

    @BeforeEach
    public void setup() {
        tariffList = List.of(
                new Tariff(1L, TariffType.MORTGAGE, "0.65"),
                new Tariff(2L, TariffType.CONSUMER, "0.51")
        );
    }

    @Test
    void shouldGetTariffs() {

        when(tariffDAO.findAll()).thenReturn(tariffList);

        assertEquals(new ResponseTariffs(tariffList).getTariffs()
                , tariffService.getTariffs().getTariffs());

    }
}