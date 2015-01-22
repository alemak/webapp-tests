@purch
Feature: Add Address address.feature
  As a registered customer
  I want to add a shipping or billing address
  So that products can be delivered to me

Background:
  Given I am a seaview registered default user
  And I sign in with the correct details
  And I go to Address Book page

  @nap @Channelized @seaview
  Scenario Outline: add a <address type> Address
    When I click on the add <address type> address link
    And I add an address
    Then My <address type> address will appear on the address details page

  Examples:
    |address type|
    |billing     |
    |shipping    |


  @nap @ecomm @Channelized
  Scenario Outline: When filling out their <address type> address, the correct label for state is displayed to the user
  and the postcode label is displayed when required
    When I click on the add <address type> address link
    And I fill out the first name field with Jane
    And I fill out the last name field with Doe
    And I select country <country> on the address form
    And I fill out the first address line field with Test Street
    Then The label displayed for state is <labelForState>
    And The town field <condition> present
    And The postcode field <condition> present
    And The county field <condition2> present

  Examples:
   |address type |country       |labelForState     |condition|condition2 |
   |shipping     |United States |State             |is       | is not    |
   |shipping     |Hong Kong     |Area              |is not   | is not    |
   |shipping     |United Kingdom|Province/Territory|is       | is        |
   |shipping     |Canada        |County/Province   |is       | is not    |
   |billing      |United States |State             |is       | is not    |
   |billing      |Hong Kong     |Area              |is not   | is not    |
   |billing      |United Kingdom|Province/Territory|is       | is        |
   |billing      |Canada        |County/Province   |is       | is not    |


  @nap @ecomm @Channelized
  Scenario Outline: User can select a state from the available list of states displayed for countries
  Hong Kong, United States and Canada on the <address type> address link
    When I click on the add <address type> address link
    And I fill out the first name field with Jane
    And I fill out the last name field with Doe
    When I select country <country> on the address form
    And I fill out the first address line field with Test Street
    Then The state field is present
    And The county field is not present
    And For country <country>, I can select a random state from the available list of states

  Examples:
   |address type |country   |
   |shipping |United States |
   |shipping |Hong Kong     |
   |shipping |Canada        |
   |billing  |United States |
   |billing  |Hong Kong     |
   |billing  |Canada        |


  @nap @ecomm @Channelized
  Scenario Outline: The state field is not present for countries other than Hong Kong, United States and Canada on the <address type> address page
    When I click on the add <address type> address link
    And I select country <country> on the address form
    Then The state field is not present
    And The county field is present

  Examples:
    |address type|country       |
    |shipping    |United Kingdom|
    |shipping    |France        |
    |billing     |United Kingdom|
    |billing     |France        |


  @nap @region=INTL @NonChannelized
  Scenario: A customer can add shipping address successfully after CCDs were deleted from their account (1st edit to remove saved CCDs, 2nd edit to try and save new address)
    And a customer has saved their credit card details
    And I go to Address Book page
    When I edit the shipping address
    And I edit the shipping address again
    Then the second edit of the shipping address is saved correctly