@login
Feature: Login Page

  Background: Navigate to the home page
    Given The user is on the Home Page

  Scenario Outline: Verify valid users can sign in
    And The user provides the username as "<username>" and password as "<password>"
    And The user clicks the 'Login' button
    Then The user should login successfully and is brought to the inventory page
    Examples:
      |username       |password     |
      |standard_user  |secret_sauce |
      |problem_user   |secret_sauce |


  Scenario Outline: Verify invalid users cannot sign in
    And The user provides the username as "<username>" and password as "<password>"
    And The user clicks the 'Login' button
    Then The user should be shown an invalid username/password message
    Examples:
      |username       |password     |
      |fake_user      |bogus        |



