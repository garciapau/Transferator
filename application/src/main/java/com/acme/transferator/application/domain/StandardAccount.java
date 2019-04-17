package com.acme.transferator.application.domain;

import java.math.BigDecimal;

public class StandardAccount implements Account {
    private BigDecimal currentBalance;
    private StandardAccount(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    @Override
    public BigDecimal getBalance() {
        return currentBalance;
    }

    @Override
    public void annotateTransferReceived(String senderId, String receiverId, String amount) {
        this.currentBalance = this.currentBalance.add(new BigDecimal(amount));
    }

    @Override
    public void annotateTransferSent(String senderId, String receiverId, String amount) {
        this.currentBalance = this.currentBalance.subtract(new BigDecimal(amount));
    }

    public static class Builder {
        private BigDecimal currentBalance;

        public Builder setCurrentBalance(BigDecimal currentBalance) {
            this.currentBalance = currentBalance;
            return this;
        }

        public StandardAccount build() {
            return new StandardAccount(currentBalance);
        }
    }
}
