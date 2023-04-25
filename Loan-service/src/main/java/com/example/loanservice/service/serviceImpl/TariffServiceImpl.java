package com.example.loanservice.service.serviceImpl;

import com.example.loanservice.dao.TariffDAO;
import com.example.loanservice.dto.response.RespoonseUtil.ResponseTariffs;
import com.example.loanservice.service.serviceInteface.TariffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TariffServiceImpl implements TariffService {

    private final TariffDAO tariffDAO;

    @Override
    public ResponseTariffs getTariffs() {

        return new ResponseTariffs(tariffDAO.findAll());
    }


}
