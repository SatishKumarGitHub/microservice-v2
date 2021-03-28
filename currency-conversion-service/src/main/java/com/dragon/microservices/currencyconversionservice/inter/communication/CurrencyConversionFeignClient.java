package com.dragon.microservices.currencyconversionservice.inter.communication;

import com.dragon.microservices.currencyconversionservice.dto.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// @FeignClient(name = "currency-exchange-service", url = "localhost:8001")

@FeignClient(name = "currency-exchange-service")
public interface CurrencyConversionFeignClient {


    @GetMapping("/currency-exchange/from/{from}/to/{to}/")
    ResponseEntity<CurrencyConversion> calculateCurrencyConversion(@PathVariable("from") String from, @PathVariable("to") String to );
}
