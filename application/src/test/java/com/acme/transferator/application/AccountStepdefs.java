package com.acme.transferator.application;

import com.acme.transferator.application.domain.Account;
import com.acme.transferator.application.domain.StandardAccount;
import com.acme.transferator.application.domain.User;
import cucumber.api.java8.En;
import org.junit.Assert;

import java.math.BigDecimal;

public class AccountStepdefs implements En {
    private User receiver;
    private User sender;
    private BigDecimal currentBalance;

    public AccountStepdefs() {
        Given("^a new user '(.*)' without money transfer operations$", (String userId) -> {
            Account account = new StandardAccount.Builder().setCurrentBalance(new BigDecimal("0")).build();
            receiver = new User.Builder().setIdentifier(userId).setAccount(account).build();
        });
        When("^balance is requested$", () -> {
            currentBalance = receiver.getAccount().getBalance();
        });
        Then("^current balance is '(.*)'$", (Integer arg0) -> {
            Assert.assertEquals(currentBalance, new BigDecimal("0"));
        });
        Given("^a user '(.*)' with money balance '(.*)'$", (String userId, String currentBalance) -> {
            Account account = new StandardAccount.Builder().setCurrentBalance(new BigDecimal(currentBalance)).build();
            sender = new User.Builder().setIdentifier(userId).setAccount(account).build();
        });
        And("^a '(.*)' with money balance '(.*)'$", (String userId, Integer currentBalance) -> {
            Account account = new StandardAccount.Builder().setCurrentBalance(new BigDecimal(currentBalance)).build();
            receiver = new User.Builder().setIdentifier(userId).setAccount(account).build();
        });
        When("'(.*)' makes a money transfer to '(.*)' of '(.*)'$", (String senderId, String receiverId, String amount) -> {
            sender.getAccount().annotateTransferSent(senderId, receiverId, amount);
            receiver.getAccount().annotateTransferReceived(senderId, receiverId, amount);
        });
        Then("^current balance of sender '(.*)' is '(.*)'$", (String user, String expectedBalance) -> {
            Assert.assertEquals(sender.getAccount().getBalance(), new BigDecimal(expectedBalance));
        });
        And("^current balance of receiver '(.*)' is '(.*)'$", (String user, String expectedBalance) -> {
            Assert.assertEquals(receiver.getAccount().getBalance(), new BigDecimal(expectedBalance));
        });
    }
}
