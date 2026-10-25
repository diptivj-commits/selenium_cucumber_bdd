@login
Feature: Login authentication
  As a user of the practice application
  I want to sign in with my credentials
  So that valid users can access the secure area

  Background:
    Given I am on the login page

  @smoke
  Scenario: Valid credentials allow access
    When I log in with username "tomsmith" and password "SuperSecretPassword!"
    Then I should see the login message "You logged into a secure area!"
    And the logout button should be visible
    And I should be on the "secure" page

  @regression
  Scenario Outline: Invalid credentials are rejected
    When I log in with username "<username>" and password "<password>"
    Then I should see the login message "<message>"
    And I should be on the "login" page

    Examples:
      | username  | password             | message                   |
      | wrongUser | SuperSecretPassword! | Your username is invalid! |
      | tomsmith  | wrongPassword        | Your password is invalid! |
      |           |                      | Your username is invalid! |
