Feature: Validated that the various shipping methods are displayed correctly on the mobile website.


  Scenario Outline: Validate that Next Day is displayed correctly in the UK (London premier address)
    Given I navigate to the Shop Bags Mobile page
    When I click on a product from the mobile listing page
    And I add the product to the shopping bag from the mobile product details page
    And I click the shopping bag icon from the mobile product details page
    And I click proceed to purchase from the mobile shopping bag page
    And I sign in as guest in the mobile purchase path
    And I add an <location> address on mobileweb
    And I continue to the mobile shipping options page
    Then I see the <shippingMethods> on the mobile shipping options page
    And the cutoff message is displayed in the mobile shipping options page
    When I select the Next Business Day shipping method on the mobile page
    And I click proceed to purchase on the mobile shipping options page
    Then the Next Business Day shipping method is displayed on the mobile payment summary page
    When I pay on mobileweb by VISA_DEBIT
    Then I see the order confirmation message on the confirmation page
    And the Next Business Day is displayed on the mobile confirmation page

  Examples:
    | location   | shippingMethods                                |
    | UK Premier | Daytime,Evening,Next Business Day, Standard    |