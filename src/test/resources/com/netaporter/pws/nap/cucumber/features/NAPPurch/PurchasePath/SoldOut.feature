@purch
Feature: Sold out - product goes sold out while customer interacts with it

  @resetProductStockLevel @nap @Channelized
  Scenario: A Basket item is sold out when the user enters the payment page anonymously
    Given I add 1 in stock products directly to my bag and save the stock
    And I go to Shopping Bag page
    When I go to Shipping page as an anonymous user
    And the item goes sold out
    And I proceed to the Payment page
    Then the product should be marked as sold out


  @resetProductStockLevel @nap @Channelized
  Scenario: Item sold out during payment
    Given I add 1 in stock products directly to my bag and save the stock
    And I go to Shopping Bag page
    And I go to Shipping page as an anonymous user
    And I proceed to the Payment page
    And the item goes sold out
    When I attempt to pay by VISA_CREDIT_CARD
    Then I should see product sold out message in shopping bag page

