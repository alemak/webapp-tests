@nap @Channelized @purch
Feature: MultipleSavedCardDetails.feature: A Customer with multiple saved CCDs makes purchases

   @pci
  Scenario: A customer with multiple saved CCDs tries to make a purchase using an existing saved card
    Given I am a seaview registered "admin_addr" user
    And there is another registered regular user
    And I sign in as a valid user
    And a customer has saved their credit card details
    And I signout
    And I sign in with the correct details
    And I switch to a valid user
    And I go to User Credit Cards page
    And I save 2 new credit card details
    And I signout
    And I sign in as a valid user
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    Then change card drop down should display all stored cards
    When I complete the purchase without entering details
    Then Order Confirmation page is displayed


   @pci
  Scenario: A customer with multiple saved CCDs tries to make a purchase using a new card and save it
    Given I am a seaview registered "admin_addr" user
    And there is another registered regular user
    And I sign in as a valid user
    And a customer has saved their credit card details
    And I signout
    And I sign in with the correct details
    And I switch to a valid user
    And I go to User Credit Cards page
    And I save 2 new credit card details
    And I signout
    And I sign in as a valid user
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    Then change card drop down should display all stored cards
    When I choose to use a different payment type
    And I pay by MASTER_CARD
    Then Order Confirmation page is displayed
    #add check for new saved card


   @pci
  Scenario: A customer with multiple saved CCDs tries to make a purchase using a new card and not save it
     Given I am a seaview registered "admin_addr" user
     And there is another registered regular user
     And I sign in as a valid user
     And a customer has saved their credit card details
     And I signout
     And I sign in with the correct details
     And I switch to a valid user
     And I go to User Credit Cards page
     And I save 2 new credit card details
     And I signout
     And I sign in as a valid user
     And I add 1 in stock products directly to my bag
     And I go to Shopping Bag page
     And I proceed to purchase
     Then change card drop down should display all stored cards
     When I choose to use a different payment type
     And I deselect the save credit cards box
     And I pay by MASTER_CARD
     Then Order Confirmation page is displayed
    #add check that new card is not saved


   @pci
  Scenario: A customer with multiple saved CCDs is asked for the CV2 number when purchasing a virtual gift card
    Given I am a seaview registered "admin_addr" user
    And there is another registered regular user
    And I sign in as a valid user
    And a customer has saved their credit card details
    And I signout
    And I sign in with the correct details
    And I switch to a valid user
    And I go to User Credit Cards page
    And I save 2 new credit card details
    And I signout
    And I sign in as a valid user
    And I add a virtual gift card of value 500 to my basket
    And I view my active shopping bag
    And I proceed to purchase
    Then change card drop down should display all stored cards
    When I fill in the correct security number
    And I complete the purchase without entering details
    Then My order is confirmed