@purch
Feature: Customers can purchase multiple items of different size of the same product, and the amount they need to pay is correct

  @nap @Channelized
  Scenario: When a user purchases multiple items of the same type with different sizes all calculations are correct
    Given I add a product to the shopping bag multiple times with different sizes
    When I go to Shopping Bag page
    Then the Item total calculation is correct in the shopping bag
    When I go to Shipping page as an anonymous user
    And I proceed to the Payment page
    Then the Item total calculation is correct
    And I pay by VISA_CREDIT_CARD
    Then Order Confirmation page is displayed
    Then the Item total calculation is correct
    And the Order Confirmation page should display the product(s)