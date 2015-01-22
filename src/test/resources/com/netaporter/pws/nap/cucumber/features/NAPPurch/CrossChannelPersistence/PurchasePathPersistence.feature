@nap @NonChannelized @purch
Feature: PurchasePathPersistence.feature
  Validate that addresses added/updated in the purchase path are persisted across channels


  Scenario: create shipping address in purchase path on channel1, check address on channel2
    Given I have successfully registered on AM channel website
    And I signout
    And I sign in with the correct details
    And I have successfully purchased an item
    And I signout
    When I login onto INTL channel
    And I go to Address Book page
    Then the shipping address used for the purchase is displayed


  Scenario: create billing address in purchase path on channel1, check address on channel2 in the my accounts page
    Given I have successfully registered on AM channel website
    And I signout
    And I sign in with the correct details
    And I have successfully purchased an item using a different billing address
    And I signout
    When I login onto INTL channel
    And I go to Address Book page
    Then the billing address used for the purchase is displayed


  Scenario: update address in purchase path on channel1, check updated address on channel2
    Given I have successfully registered on INTL channel website
    And I signout
    And I sign in with the correct details
    And I go to Address Book page
    And I add a new shipping address from United Kingdom
    And I signout
    And I login onto AM channel
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I signout
    And I sign in with the correct details
    And I go to Shopping Bag page
    And I proceed to purchase
    And I edit the shipping address in the purchase path
    When I signout
    And I login onto INTL channel
    And I go to Address Book page
    Then the shipping address has been updated


  Scenario: purchase updates my favourite designers with purchased designer, purchase on channel1, check channel2
    Given I have successfully registered on AM channel website
    And I signout
    And I sign in with the correct details
    And I have successfully purchased an item
    And I signout
    When I login onto INTL channel
    And I go to My Favourite Designers page
    Then my favourite designer selection is kept