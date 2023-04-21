package com.example.loanservice.dto.response.RespoonseUtil;

import com.example.loanservice.entity.Tariff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTariffs {

    private List<Tariff> tariffs;
}
