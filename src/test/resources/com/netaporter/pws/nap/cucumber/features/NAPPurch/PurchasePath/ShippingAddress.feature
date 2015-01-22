@nap @NonChannelized @purch
Feature: Tests for the Shipping Address page within the purchase path

  @ecomm  @region=INTL
  Scenario Outline: The correct label for state is displayed to the user and the postcode and town fields and labels are displayed when required
    Given I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out the first name field with Jane
    And I fill out the last name field with Doe
    When I select country <country> on the address form
    And I fill out the first address line field with Test Street
    Then The town field <condition> present
    And The label displayed for state is <labelForState>
    And The postcode field <condition> present

  Examples:
    |country       |labelForState     |condition|
    |United States |State             |is       |
    |Hong Kong     |Area              |is not   |
    |United Kingdom|Province/Territory|is       |
    |France        |Province/Territory|is       |
    |Canada        |County/Province   |is       |


  @ecomm @region=INTL
  Scenario Outline: User can select a state from the available list of states displayed for countries
  Hong Kong, United States and Canada
    Given I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out the first name field with Jane
    And I fill out the last name field with Doe
    When I select country <country> on the address form
    And I fill out the first address line field with Test Street
    Then The state field is present
    And The county field is not present
    And For country <country>, I can select a random state from the available list of states

  Examples:
    |country       |
    |United States |
    |Hong Kong     |
    |Canada        |