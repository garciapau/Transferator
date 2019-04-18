package com.acme.transferator.application.domain;

public class User {
    public String getIdentifier() {
        return identifier;
    }

    private String identifier;
    private Account account;

    public Account getAccount() {
        return account;
    }

    private User(String identifier, Account account) {
        this.identifier = identifier;
        this.account = account;
    }

    public static class Builder {
        private String identifier;
        private Account account;

        public Builder setIdentifier(String identifier) {
            this.identifier = identifier;
            return this;
        }

        public Builder setAccount(Account account) {
            this.account = account;
            return this;
        }

        public User build() {
            return new User(identifier, account);
        }
    }
}
