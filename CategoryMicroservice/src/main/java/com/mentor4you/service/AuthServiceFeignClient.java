package com.mentor4you.service;

import com.mentor4you.domain.TokenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        value = "auth-service",
        url = "http://auth-microservice:8080")
public interface AuthServiceFeignClient {

    @PostMapping("/api/auth/check")
    ResponseEntity<String> checkToken(@RequestBody TokenRequest tokenRequest);
}
