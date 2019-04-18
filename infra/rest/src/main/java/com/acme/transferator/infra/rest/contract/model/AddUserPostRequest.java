package com.acme.transferator.infra.rest.contract.model;

public class AddUserPostRequest {
    private String identifier;
    private String initialBalance;

    public String getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(String initialBalance) {
        this.initialBalance = initialBalance;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public AddUserPostRequest() {
    }

    private AddUserPostRequest(String identifier, String initialBalance) {
        this.identifier = identifier;
        this.initialBalance = initialBalance;
    }

    public static class Builder {
        private String identifier;
        private String initialBalance;

        public Builder setInitialBalance(String initialBalance) {
            this.initialBalance = initialBalance;
            return this;
        }

        public Builder setIdentifier(String identifier) {
            this.identifier = identifier;
            return this;
        }

        public AddUserPostRequest build() {
            return new AddUserPostRequest(identifier, initialBalance);
        }
    }
}
