package com.acme.transferator.application.domain;

import java.math.BigDecimal;

public interface Account {
    BigDecimal getBalance();

    void annotateTransferReceived(String senderId, String receiverId, String amount);

    void annotateTransferSent(String senderId, String receiverId, String amount);
}
