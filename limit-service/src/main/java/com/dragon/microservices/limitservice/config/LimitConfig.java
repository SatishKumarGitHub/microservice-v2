package com.dragon.microservices.limitservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties( prefix = "limit-service")
@Data
public class LimitConfig {

    private  long min;
    private long max;
}
