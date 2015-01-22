@nap @Channelized @purch
Feature: Tests for the Billing Address page within the purchase path


  @ecomm
  Scenario Outline: The correct label for state is displayed to the user and the postcode label is displayed when required
    Given I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK London shipping address
    And I choose to use a separate billing address
    And I click proceed to purchase from the billing address page
    And I click proceed to purchase from the shipping options page
    And I fill out the first name field with Jane
    And I fill out the last name field with Doe
    When I select country <country> on the address form
    And I fill out the first address line field with Test Street
    Then The town field <condition1> present
    And The label displayed for state is <labelForState>
    And The postcode field <condition1> present
    And The county field <condition2> present

  Examples:
    |country       |labelForState     |condition1 | condition2 |
    |United States |State             |is         | is not     |
    |Hong Kong     |Area              |is not     | is not     |
    |United Kingdom|Province/Territory|is         | is         |
    |France        |Province/Territory|is         | is         |
    |Canada        |County/Province   |is         | is not     |


  @ecomm
  Scenario Outline: User can select a state from the available list of states displayed for country Hong Kong and United States
    Given I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK London shipping address
    And I choose to use a separate billing address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
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
