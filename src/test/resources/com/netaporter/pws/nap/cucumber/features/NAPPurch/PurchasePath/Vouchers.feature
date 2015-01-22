@purch
Feature: Purchasing with gift vouchers

  @nap @NonChannelized @region=INTL
  Scenario: A customer makes a purchase using a gift voucher with a value greater than that of the shopping bag
    Given I am a seaview registered default user
    And I am logged in
    And I have store credit to the amount of 10000 GBP
    And I add a NAP product priced under the basket threshold 10000 to my shopping bag
    When I go to Shopping Bag page
    And I proceed to purchase
    Then I complete a purchase logged in as default customer without entering CCDs and my order is confirmed


  @ecomm @region=INTL @channelSpecific @storeSourceOfShoppingBag @nap @NonChannelized @purch
  Scenario: Record device identifier against all basket gift card items
    Given I am on change country page
    And I change my country to United Kingdom
    When I add a virtual gift card of value 50 to my basket
    And I add a printed gift card of value 50 to my basket
    Then  My device identifier and user agent are recorded against my basket items


  @ecomm @region=AM @channelSpecific @storeSourceOfShoppingBag @nap @NonChannelized @purch
  Scenario: Record device identifier against all basket gift card items
    Given I am on change country page
    And I change my country to United States
    When I add a virtual gift card of value 50 to my basket
    And I add a printed gift card of value 50 to my basket
    Then  My device identifier and user agent are recorded against my basket items


  @ecomm @region=APAC @channelSpecific @storeSourceOfShoppingBag @nap @NonChannelized @purch
  Scenario: Record device identifier against all basket gift card items
    Given I am on change country page
    And I change my country to Hong Kong
    When I add a virtual gift card of value 500 to my basket
    And I add a printed gift card of value 500 to my basket
    Then  My device identifier and user agent are recorded against my basket items


  @nap @COM-1570
  Scenario: Shopping bag overlay and add to bag still works - multi sized product
    Given I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    And I add multi sized item 1 to the shopping bag
    Then the recently added overlay should appear with the details of item 1 in it
    And the shopping bag icon shows 1 item
    And item 1 should be in the shopping bag 1 time


  @nap @COM-1570
  Scenario: Shopping bag overlay and add to bag still works - single sized product
    Given I have 1 IN_STOCK and visible ACCESSORIES sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    And I add single sized item 1 to the shopping bag
    Then the recently added overlay should appear with the details of item 1 in it
    And the shopping bag icon shows 1 item
    And item 1 should be in the shopping bag 1 time