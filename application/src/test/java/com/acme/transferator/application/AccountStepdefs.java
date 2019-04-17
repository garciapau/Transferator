package com.acme.transferator.application;

import com.acme.transferator.application.domain.User;
import cucumber.api.java8.En;
import org.junit.Assert;

import java.math.BigDecimal;

public class AccountStepdefs implements En {
    User user;
    BigDecimal currentBalance;

    public AccountStepdefs() {
        Given("^a new user \"([^\"]*)\" without money transfer operations$", (String userId) -> {
            user = new User.Builder().setIdentifier(userId).build();
        });
        When("^balance is requested$", () -> {
            currentBalance = user.getAccount().getBalance();
        });
        Then("^current balance is (\\d+)$", (Integer arg0) -> {
            Assert.assertEquals(currentBalance, new BigDecimal("0"));
        });
    }
}
