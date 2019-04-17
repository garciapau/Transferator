package com.acme.transferator.application.domain;

import java.math.BigDecimal;

public class User {
    private String identifier;

    public Account getAccount() {
        return account;
    }

    private Account account;

    private User(String identifier) {
        this.identifier = identifier;
        this.account = new StandardAccount.Builder().setCurrentBalance(new BigDecimal("0")).build();
    }

    public static class Builder {
        private String identifier;

        public Builder setIdentifier(String identifier) {
            this.identifier = identifier;
            return this;
        }

        public User build() {
            return new User(identifier);
        }
    }
}
