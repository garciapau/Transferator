package com.acme.transferator.application.usecase;

import com.acme.transferator.application.domain.User;
import com.acme.transferator.application.exception.NotEnoughFundsException;
import com.acme.transferator.application.exception.UserNotFoundException;
import com.acme.transferator.application.repository.UserRepository;

import java.math.BigDecimal;

public class TransferMoney {
    private UserRepository userRepository;

    public TransferMoney(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean execute(String senderId, String receiverId, String amount) throws UserNotFoundException, NotEnoughFundsException {
        User sender = retrieveUser(senderId);
        User receiver = retrieveUser(receiverId);
        if (sender.getAccount().getBalance().compareTo(new BigDecimal(amount)) < 0) {
            throw new NotEnoughFundsException(sender);
        }
        sender.getAccount().annotateTransferSent(senderId, receiverId, amount);
        receiver.getAccount().annotateTransferReceived(senderId, receiverId, amount);
        return true;
    }

    private User retrieveUser(String senderId) throws UserNotFoundException {
        return userRepository
                    .findById(senderId)
                    .orElseThrow(() -> new UserNotFoundException(senderId));
    }
}
