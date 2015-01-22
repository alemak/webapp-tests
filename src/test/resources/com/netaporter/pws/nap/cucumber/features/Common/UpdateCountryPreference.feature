@purch
Feature: Update country preference
  As an NAP customer
  I want to be able to switch to my preferred country

  @nap @NonChannelized
  Scenario Outline: User switch country from select country link
    Given I am on <channel>
    And I go to Change Country page
    When I change my country to <country>
    Then the country displayed in the top nav is <country>

  Examples:
    | channel  | country               |
    | apac     | China                 |
    | apac     | Australia             |
    | intl     | United Kingdom        |
    | intl     | France                |
    | am       | United States         |
    | am       | Canada                |