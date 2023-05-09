
@SeuBarrigaTests @positive
Feature: Positive tests on the "SeuBarriga" API

  Background:
    * i retrieve the authentication token from the /signin endpoint

  @TC01
  Scenario: Create an account and insert balance into it
    Given that i want to create a new account
    When i post the request to create the new account
    Then i can get the account's informations through its id
    And i post the request to insert balance into the account
    Then i can check if the balance has been updated

  @TC02
  Scenario: Edit a previously created account's name
    Given that i want to edit a new account
    When i post the request to update the account's name
    Then i can verify that the name's been changed successfully

  @TC03
  Scenario: Insert and remove banking movement from account
    Given that i want to insert banking movement into an account
    When i post the request to insert banking movement
    Then i can verify that the transaction's been successfully inserted through its id
    When i delete the banking movement that i've just created
    Then i can check if the delete operation has been successful

