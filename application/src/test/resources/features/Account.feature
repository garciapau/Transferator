Feature: Manage account balance

  Scenario: Get current balance of new user
    Given a new user "UserA" without money transfer operations
    When balance is requested
    Then current balance is 0
