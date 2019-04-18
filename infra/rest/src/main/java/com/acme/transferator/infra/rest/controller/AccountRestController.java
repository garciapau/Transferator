package com.acme.transferator.infra.rest.controller;

import com.acme.transferator.application.exception.NotEnoughFundsException;
import com.acme.transferator.application.exception.UserNotFoundException;
import com.acme.transferator.application.usecase.GetBalanceByUser;
import com.acme.transferator.application.usecase.TransferMoney;
import com.acme.transferator.infra.rest.contract.AccountController;
import com.acme.transferator.infra.rest.contract.model.TransferPostRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class AccountRestController implements AccountController {
    private GetBalanceByUser getBalanceByUser;
    private TransferMoney transferMoney;

    public AccountRestController(GetBalanceByUser getBalanceByUser, TransferMoney transferMoney) {
        this.getBalanceByUser = getBalanceByUser;
        this.transferMoney = transferMoney;
    }

    @Override
    @GetMapping(value = "/users/{userId}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable("userId") String userId) throws UserNotFoundException {
        return ResponseEntity.ok(getBalanceByUser.execute(userId));
    }

    @Override
    @PostMapping(value = "/transfers")
    public ResponseEntity transferMoney(@RequestBody TransferPostRequest transferPostRequest) throws UserNotFoundException, NotEnoughFundsException {
        transferMoney.execute(transferPostRequest.getSender(), transferPostRequest.getReceiver(), transferPostRequest.getAmount());
        return ResponseEntity.ok().build();
    }
}
