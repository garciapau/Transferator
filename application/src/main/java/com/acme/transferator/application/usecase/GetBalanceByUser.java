package com.acme.transferator.application.usecase;

import com.acme.transferator.application.repository.UserRepository;
import com.acme.transferator.application.exception.UserNotFoundException;

import java.math.BigDecimal;

public class GetBalanceByUser {
    private UserRepository userRepository;

    public GetBalanceByUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public BigDecimal execute(String userId) throws UserNotFoundException {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException())
                .getAccount()
                .getBalance();
    }
}
