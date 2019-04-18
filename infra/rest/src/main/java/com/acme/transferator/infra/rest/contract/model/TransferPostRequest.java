package com.acme.transferator.infra.rest.contract.model;

public class TransferPostRequest {
    private String sender;

    public TransferPostRequest() {
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getAmount() {
        return amount;
    }

    private String receiver;
    private String amount;

    private TransferPostRequest(String sender, String receiver, String amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    public static class Builder {
        private String sender;
        private String receiver;
        private String amount;

        public Builder setSender(String sender) {
            this.sender = sender;
            return this;
        }

        public Builder setReceiver(String receiver) {
            this.receiver = receiver;
            return this;
        }

        public Builder setAmount(String amount) {
            this.amount = amount;
            return this;
        }

        public TransferPostRequest build() {
            return new TransferPostRequest(sender, receiver, amount);
        }
    }
}
