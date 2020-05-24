@search
Feature: Search in Google

@foo
 Scenario Outline: Search for SeleniumHQ and verify the website
     Given I search for "<Search>"
     When I click on official Selenium HQ website
     Then I verify official website is launched
  Examples:
  |Search|
  |seleniumhq.dev|
  |seleniumhq.de |

  @foo
  Scenario: Search for SeleniumHQ and verify the website
    Given I search for "seleniumhq.dev"
    When I click on official Selenium HQ website
    Then I verify official website is launched