Feature: Manage account balance

  Scenario: Get current balance of new user
    Given a new user 'UserA' without money transfer operations
    When balance is requested
    Then current balance is '0'

  Scenario: Get current balance of existing user
    Given a user 'UserA' with money balance '100'
    And a 'UserB' with money balance '200'
    When 'UserA' makes a money transfer to 'UserB' of '50'
    Then current balance of sender 'UserA' is '50'
    And current balance of receiver 'UserB' is '250'
