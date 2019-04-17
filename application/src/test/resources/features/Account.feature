Feature: Manage account balance

  Scenario: Get current balance of user
    Given a user without money transfer operations
    When balance is requested
    Then current balance is 0
