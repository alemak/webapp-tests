@purch
Feature: UpdateLocation.feature
  As an NAP customer
  I want to be able to switch to my preferred country
  From My Account Page


    @nap @NonChannelized
  Scenario Outline: User update location from my account details page
    Given I am on <channel>
    And I go to Home page
    And there is another registered regular user
    And I sign in as a valid user
    And I go to Account Details page
    When I update location to <country> in my preferences
    Then the country displayed in the top nav is <country>
    And My new location is <country>

  Examples:
    | channel  | country               |
    | apac     | Australia             |
    | apac     | Australia             |
    | intl     | United Kingdom        |
    | intl     | France                |
    | am       | United States         |
    | am       | Canada                |