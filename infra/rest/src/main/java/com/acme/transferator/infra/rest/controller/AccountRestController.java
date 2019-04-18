package com.acme.transferator.infra.rest.controller;

import com.acme.transferator.application.exception.NotEnoughFundsException;
import com.acme.transferator.application.exception.UserNotFoundException;
import com.acme.transferator.application.usecase.AddUser;
import com.acme.transferator.application.usecase.GetBalanceByUser;
import com.acme.transferator.application.usecase.TransferMoney;
import com.acme.transferator.infra.rest.contract.AccountController;
import com.acme.transferator.infra.rest.contract.model.AddUserPostRequest;
import com.acme.transferator.infra.rest.contract.model.TransferPostRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class AccountRestController implements AccountController {
    private GetBalanceByUser getBalanceByUser;
    private TransferMoney transferMoney;
    private AddUser addUser;

    public AccountRestController(GetBalanceByUser getBalanceByUser, TransferMoney transferMoney, AddUser addUser) {
        this.getBalanceByUser = getBalanceByUser;
        this.transferMoney = transferMoney;
        this.addUser = addUser;
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

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/users")
    public ResponseEntity addUser(@RequestBody AddUserPostRequest addUserPostRequest) {
        addUser.execute(addUserPostRequest.getIdentifier(), addUserPostRequest.getInitialBalance());
        return ResponseEntity.ok().build();
    }
}
