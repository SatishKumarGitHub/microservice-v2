package com.dragon.microservices.currencyconversionservice.service;

import com.dragon.microservices.currencyconversionservice.dto.CurrencyConversion;
import com.dragon.microservices.currencyconversionservice.inter.communication.CurrencyConversionFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class CurrencyConversionService {

    public static final String CURRENCY_EXCHANGE_SERVICE = "http://localhost:8001/currency-exchange/from/{from}/to/{to}";


    private RestTemplate restTemplate;

    private CurrencyConversionFeignClient currencyConversionFeignClient;

    public CurrencyConversion getCurrencyConversionValue(String from, String to, BigDecimal quantity) {

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CurrencyConversion> conversionResponseEntity = restTemplate.getForEntity(CURRENCY_EXCHANGE_SERVICE, CurrencyConversion.class, uriVariables);

        if (HttpStatus.OK == conversionResponseEntity.getStatusCode() && conversionResponseEntity.getBody() != null) {
            CurrencyConversion currencyConversion = conversionResponseEntity.getBody();

            return CurrencyConversion.builder().id(currencyConversion.getId())
                    .from(from).to(to)
                    .quantity(quantity)
                    .conversionMultiple(currencyConversion.getConversionMultiple())
                    .totalCalculatedAmount(quantity.multiply(currencyConversion.getConversionMultiple()))
                    .build();
        }
        return getCurrencyConversionFallback(from, to, quantity);
    }


    public CurrencyConversion getCurrencyConversionValueFromFeign(String from, String to, BigDecimal quantity) {

        ResponseEntity<CurrencyConversion> conversionResponseEntity = currencyConversionFeignClient.calculateCurrencyConversion( from, to);

        if (HttpStatus.OK == conversionResponseEntity.getStatusCode() && conversionResponseEntity.getBody() != null) {
            CurrencyConversion currencyConversion = conversionResponseEntity.getBody();

            return CurrencyConversion.builder().id(currencyConversion.getId())
                    .from(from).to(to)
                    .quantity(quantity)
                    .conversionMultiple(currencyConversion.getConversionMultiple())
                    .totalCalculatedAmount(quantity.multiply(currencyConversion.getConversionMultiple()))
                    .environment(currencyConversion.getEnvironment())
                    .build();
        }
        return getCurrencyConversionFallback(from, to, quantity);
    }


    private CurrencyConversion getCurrencyConversionFallback(String from, String to, BigDecimal quantity) {
        return CurrencyConversion.builder().id(1009L).from(from).to(to)
                .quantity(quantity)
                .conversionMultiple(BigDecimal.valueOf(72.54))
                .totalCalculatedAmount(quantity.multiply(BigDecimal.valueOf(72.54)))
                .build();
    }

}
