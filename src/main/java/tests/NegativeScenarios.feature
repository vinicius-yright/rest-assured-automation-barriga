
@SeuBarrigaTests @negative
Feature: Negative tests on the "SeuBarriga" API

  @TC04
  Scenario: Trying to create an account without authenticating
    Given that i want to create a new account
    When i post the request to create the new account without the authentication token
    Then i receive an error message and am unable to complete the operation

  @TC05
  Scenario: Trying to create a duplicate account
    * i retrieve the authentication token from the /signin endpoint
    Given that i want to create a new account
    When i post the request to create the new account with an already registered name
    Then i receive an error message and am unable to complete the operation

  @TC06
  Scenario: Trying to insert a banking movement without the appropriate payload
    * i retrieve the authentication token from the /signin endpoint
    Given that i want to insert banking movement into an account
    When i post the request to insert the banking movement without the correct fields
    Then i receive an error message and am unable to complete the operation

  @TC07
  Scenario: Trying to insert a banking movement with incorrect end date
    * i retrieve the authentication token from the /signin endpoint
    Given that i want to insert banking movement into an account
    When i post the request to insert the banking movement with innapropriate end date
    Then i receive an error message and am unable to complete the operation

  @TC08
  Scenario: Trying to delete an account with banking transactions
    * i retrieve the authentication token from the /signin endpoint
    Given that i want to insert banking movement into an account
    When i post the request to try to delete an account that has banking records
    Then i receive an error message and am unable to complete the operation





