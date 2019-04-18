package com.acme.transferator.application.exception;

import com.acme.transferator.application.domain.User;

public class NotEnoughFundsException extends Throwable {
    public NotEnoughFundsException(User sender) {
        super(String.format("User %s does not have enough funds for the transfer. Current balance is %s.",
                sender.getIdentifier(), sender.getAccount().getBalance()));
    }
}
