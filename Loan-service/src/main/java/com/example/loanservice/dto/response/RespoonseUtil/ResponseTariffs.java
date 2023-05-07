package com.example.loanservice.dto.response.RespoonseUtil;

import com.example.loanservice.entity.Tariff;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ResponseTariffs {

    private List<Tariff> tariffs;
}
