package com.acme.transferator.infra.rest.controller;

import com.acme.transferator.application.exception.UserNotFoundException;
import com.acme.transferator.application.usecase.GetBalanceByUser;
import com.acme.transferator.infra.rest.contract.AccountController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class AccountRestController implements AccountController {
    private GetBalanceByUser getBalanceByUser;

    public AccountRestController(GetBalanceByUser getBalanceByUser) {
        this.getBalanceByUser = getBalanceByUser;
    }

    @Override
    @GetMapping(value = "/users/{userId}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable("userId") String userId) throws UserNotFoundException {
        return ResponseEntity.ok(getBalanceByUser.execute(userId));
    }
}
