package com.acme.transferator.application.exception;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException(String userId) {
        super(String.format("User %s not found", userId));
    }
}
