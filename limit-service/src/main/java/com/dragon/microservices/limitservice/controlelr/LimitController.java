package com.dragon.microservices.limitservice.controlelr;

import com.dragon.microservices.limitservice.config.LimitConfig;
import com.dragon.microservices.limitservice.vo.Limit;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/limit/api")
@AllArgsConstructor
public class LimitController {

    private LimitConfig limitConfig;

    @GetMapping("/limits")
    public ResponseEntity<Limit> retrieveLimit() {

      //  Limit limit = new Limit(1, 100);
        Limit limit = new Limit(limitConfig.getMin(), limitConfig.getMax());
        return ResponseEntity.ok(limit);

    }
}
