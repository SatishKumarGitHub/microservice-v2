package com.dragon.microservices.currencyexchangeservice.controller;

import com.dragon.microservices.currencyexchangeservice.dto.CurrencyExchangeDto;
import com.dragon.microservices.currencyexchangeservice.service.CurrencyExchangeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currency-exchange")
@AllArgsConstructor
public class CurrencyExchangeController {

    private CurrencyExchangeService currencyExchangeService;

    @GetMapping("/from/{from}/to/{to}")
    public ResponseEntity<CurrencyExchangeDto> retrieveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to) {
        CurrencyExchangeDto currencyValue = currencyExchangeService.getCurrencyValue(from, to);
        return ResponseEntity.ok(currencyValue);
    }

}
