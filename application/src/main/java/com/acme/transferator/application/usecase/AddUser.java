package com.acme.transferator.application.usecase;

import com.acme.transferator.application.repository.UserRepository;
import com.acme.transferator.application.domain.StandardAccount;
import com.acme.transferator.application.domain.User;

import java.math.BigDecimal;

public class AddUser {
    private UserRepository userRepository;

    public AddUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(String userId) {
        userRepository.save(new User.Builder()
                .setIdentifier(userId)
                .setAccount(new StandardAccount.Builder()
                        .setCurrentBalance(new BigDecimal("0"))
                        .build())
                .build());
    }
}
