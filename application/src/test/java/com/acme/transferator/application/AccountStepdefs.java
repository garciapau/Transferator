package com.acme.transferator.application;

import cucumber.api.java8.En;

public class AccountStepdefs implements En {
    public AccountStepdefs() {
        Given("^a user without money transfer operations$", () -> {
        });
        When("^balance is requested$", () -> {
        });
        Then("^current balance is (\\d+)$", (Integer arg0) -> {
        });
    }
}
