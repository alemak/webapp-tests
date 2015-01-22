@napeval @Channelized

Feature: Check locale links and canonical links

  Scenario: Checking the locale links are displayed in the page source
    Given I navigate to an instock PDP
    Then the locale links are displayed

  Scenario: Canonical links
    Given I goto a canonical product detail page
    Then the canonical link is displayed

