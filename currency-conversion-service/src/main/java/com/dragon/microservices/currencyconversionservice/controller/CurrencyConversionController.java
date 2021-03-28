package com.dragon.microservices.currencyconversionservice.controller;

import com.dragon.microservices.currencyconversionservice.dto.CurrencyConversion;
import com.dragon.microservices.currencyconversionservice.service.CurrencyConversionService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/currency-conversion")
@AllArgsConstructor
@Slf4j
public class CurrencyConversionController {


    private static int count = 1;
    private CurrencyConversionService currencyConversionService;

    /**
     * this API fetch the currency exchange value using Rest template
     *
     * @param from
     * @param to
     * @param quantity
     * @return conversionValue
     */
    @GetMapping("from/{from}/to/{to}/quantity/{quantity}")
    public ResponseEntity<CurrencyConversion> calculateCurrencyConversion(@PathVariable("from") String from,
                                                                          @PathVariable("to") String to,
                                                                          @PathVariable("quantity") BigDecimal quantity) {
        CurrencyConversion conversionValue = currencyConversionService.getCurrencyConversionValue(from, to, quantity);

        return ResponseEntity.ok(conversionValue);

    }


    /**
     * this API fetch the currency exchange value using Feign Client
     *
     * @param from
     * @param to
     * @param quantity
     * @return conversionValue
     */

    @GetMapping("feign/from/{from}/to/{to}/quantity/{quantity}")
    public ResponseEntity<CurrencyConversion> calculateCurrencyConversionFeign(@PathVariable("from") String from,
                                                                               @PathVariable("to") String to,
                                                                               @PathVariable("quantity") BigDecimal quantity) {
        CurrencyConversion conversionValue = currencyConversionService.getCurrencyConversionValueFromFeign(from, to, quantity);

        return ResponseEntity.ok(conversionValue);

    }


    @GetMapping("/hello/{no}")
    @Retry(name = "hello-api", fallbackMethod = "calculateCurrencyConversionFb")
    public ResponseEntity<String> calculateCurrencyConversion(@PathVariable("no") int no) {
        log.info(" hello world API Count -----> {}", count++);
        throw new RuntimeException(" manual exception in API ");
        // return ResponseEntity.ok(" " + no);

    }

    @GetMapping("/hello-api-circuit/{no}")
    @CircuitBreaker(name = "hello-api-circuit", fallbackMethod = "calculateCurrencyConversionFb")
    public ResponseEntity<String> calculateCurrencyCircuit(@PathVariable("no") int no) {
        log.info(" hello world API Count -----> {}", count++);
        throw new RuntimeException(" manual exception in API ");
        // return ResponseEntity.ok(" " + no);

    }

    @GetMapping("/hello-api-rate-limit/{no}")
    @RateLimiter(name = "hello-api-rate-limit", fallbackMethod = "calculateCurrencyConversionFb")
    public ResponseEntity<String> calculateCurrencyRateLimit(@PathVariable("no") int no) {
        log.info(" hello world API Count -----> {}", count++);
        return ResponseEntity.ok(" rate limit");

    }

    @GetMapping("/hello-api-bulk-head/{no}")
    @Bulkhead(name = "hello-api-bulk-Head", fallbackMethod = "calculateCurrencyConversionFb")
    public ResponseEntity<String> calculateCurrencyBulkHead(@PathVariable("no") int no) {
        log.info(" hello world API Count -----> {}", count++);
        return ResponseEntity.ok(" Bulk head");

    }


    public ResponseEntity<String> calculateCurrencyConversionFb(Exception e) {
        log.info(" hello world {}", e.getMessage());
        return ResponseEntity.ok(" default no " + 10);

    }

}
