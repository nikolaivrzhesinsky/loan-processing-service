package com.example.loanservice.controller;

import com.example.loanservice.dto.response.RespoonseUtil.ResponseTariffs;
import com.example.loanservice.entity.Tariff;
import com.example.loanservice.entity.enums.TariffType;
import com.example.loanservice.service.serviceInteface.TariffService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TariffController.class)
class TariffControllerTest {

    @MockBean
    private TariffService tariffService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnTariffs() throws Exception {

        List<Tariff> responseTariffs = List.of(
                new Tariff(1L, TariffType.CONSUMER, "4-15%"),
                new Tariff(2L, TariffType.MORTGAGE, "10-16%")
        );

        when(tariffService.getTariffs()).thenReturn(
                new ResponseTariffs(responseTariffs));

        mockMvc.perform(get("/getTariffs"))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tariffs").value(responseTariffs));
    }
}






















