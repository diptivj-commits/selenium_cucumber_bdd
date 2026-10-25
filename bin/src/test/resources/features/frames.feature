@frames @regression
Feature: Work with nested browser frames
  Selenium must switch into the correct frame before locating its contents.

  Scenario: Read text from a nested middle frame
    Given I am on the nested frames page
    When I switch to the top frame and then the middle frame
    Then the current frame body should contain "MIDDLE"

  Scenario: Return to the main document before entering a sibling frame
    Given I am on the nested frames page
    When I switch to the top frame and then the middle frame
    And I return to the main document
    And I switch to the bottom frame
    Then the current frame body should contain "BOTTOM"
