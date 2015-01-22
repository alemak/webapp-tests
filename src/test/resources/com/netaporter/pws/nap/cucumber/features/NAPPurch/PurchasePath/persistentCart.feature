@nap @NonChannelized @purch
Feature: Persistent cart feature
  # these tests assume that persistent cart is ON in the web config file


  @ecomm @region=INTL @channelSpecific @storeSourceOfShoppingBag @persistentCart
  Scenario: The shopping bag is emptied if a logged in user adds an item to their shopping bag and then logs out when the persistent cart is on
    Given I have selected United Kingdom from the intl Channel
    And I submit valid details on the registration form
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I sign out
    When I view my shopping bag
    Then I should have 0 items in my shopping bag


  @ecomm @region=INTL @channelSpecific @storeSourceOfShoppingBag @persistentCart
  Scenario: The shopping bag is NOT emptied if a logged in user adds an item to their shopping bag and then logs out when the persistent cart is off
    Given I go to Home page
    And I have a cookie set with name persistent_basket_optout and the value is true
    And I have selected United Kingdom from the intl Channel
    And I submit valid details on the registration form
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I sign out
    When I view my shopping bag
    Then I should have 1 items in my shopping bag


  @ecomm @region=INTL @channelSpecific @storeSourceOfShoppingBag @persistentCart
  Scenario: Persistent cart merge occurs when persistent cart is on
    Given I have selected United Kingdom from the intl Channel
    And I submit valid details on the registration form
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I sign out
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I sign in with the correct details
    When I view my shopping bag
    Then I should have 2 items in my shopping bag


  @ecomm @region=INTL @channelSpecific @storeSourceOfShoppingBag @persistentCart
  Scenario: Persistent cart merge does NOT occur when persistent cart is off
    Given I go to Home page
    Given I have a cookie set with name persistent_basket_optout and the value is true
    And I have selected United Kingdom from the intl Channel
    And I submit valid details on the registration form
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I sign out
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I sign in with the correct details
    When I view my shopping bag
    Then I should have 1 items in my shopping bag