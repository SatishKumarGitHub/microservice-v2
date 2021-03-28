package com.dragon.microservices.currencyexchangeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyExchangeDto {

    private long id;

    private String from;

    private String to;

    private BigDecimal conversionMultiple;

    private String environment;


}