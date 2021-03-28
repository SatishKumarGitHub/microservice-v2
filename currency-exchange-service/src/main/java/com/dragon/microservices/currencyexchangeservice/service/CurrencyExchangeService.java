package com.dragon.microservices.currencyexchangeservice.service;

import com.dragon.microservices.currencyexchangeservice.model.CurrencyExchange;
import com.dragon.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;
import com.dragon.microservices.currencyexchangeservice.dto.CurrencyExchangeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CurrencyExchangeService {

    private CurrencyExchangeRepository currencyExchangeRepository;

    private Environment environment;

    public CurrencyExchangeDto getCurrencyValue(String from, String to) {
       Optional <CurrencyExchange> optional = currencyExchangeRepository.findByFromAndTo(from, to);

        if (optional.isPresent()) {
            CurrencyExchange currencyExchange = optional.get();
        return   CurrencyExchangeDto.builder().id(currencyExchange.getId()).
                from(currencyExchange.getFrom()).to(currencyExchange.getTo())
                .conversionMultiple(currencyExchange.getConversionMultiple())
                .environment(environment.getProperty("local.server.port")).build();
        }
        else {
            throw new RuntimeException("Currency not found ..!! ");
        }

    }


    public static<T, R> List<T> map ( ObjectMapper mapper,List<R> source, Class<T> type){

        return null;

    }

}
