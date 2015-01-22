@seaview @purch
Feature: Customer information such as address, favourite designers and saved credit cards are persisted between channels

    @nap @NonChannelized
  Scenario: Customer shipping address is persisted between AM and INTL channels
      Given I have successfully registered on am channel website
      And I go to Address Book page
      And I click on the add shipping address link
      And I add an am address
      And I signout
      When I login onto apac channel
      And I go to Address Book page
      Then My am shipping address will appear on the address details page


  @nap @NonChannelized
  Scenario: Customer billing address is persisted between APAC and AM channels
    Given I have successfully registered on APAC channel website
    And I go to Address Book page
    And I click on the add billing address link
    And I add a HongKong address
    And I signout
    When I login onto AM channel
    And I go to Address Book page
    Then My HongKong billing address will appear on the address details page


    @nap @NonChannelized
  Scenario: Customer favourite designers are persisted between channels
    Given I have successfully registered on INTL channel website
    And I go to My Favourite Designers page
    And I select one favourite designer
    And I signout
    When I login onto APAC channel
    And I go to My Favourite Designers page
    Then my favourite designer selection is kept


    @nap @NonChannelized
  Scenario: Customer saved credit card, after making a purchase, is saved between channels
    Given I have successfully registered on AM channel website
    And a customer has saved their credit card details
    And I signout
    When I login onto INTL channel
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to the payment page
    Then the credit card details should have been remembered