package com.acme.transferator.infra.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @GetMapping(value = "/users/{userId}/balance")
    public ResponseEntity<String> getBalance(@PathVariable("userId") String userId) {
        return ResponseEntity.notFound().build();
    }
}
