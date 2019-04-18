package com.acme.transferator.infra.rest.controller;

import com.acme.transferator.infra.rest.contract.AccountController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountRestController implements AccountController {

    @Override
    @GetMapping(value = "/users/{userId}/balance")
    public ResponseEntity<String> getBalance(@PathVariable("userId") String userId) {
        return ResponseEntity.notFound().build();
    }
}
