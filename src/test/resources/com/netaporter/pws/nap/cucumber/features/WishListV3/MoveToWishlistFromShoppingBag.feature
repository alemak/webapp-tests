@wishlistV3 @Channelized @desktop
Feature: Customer can add products to the shopping bag and move to wishlist
  Add to shopping bag then move to wishlist from the shopping bag page

# Note this also covers when an item is sold out and in the basket and we show a different styled link
# as the CSS selector to click the link is the same.
  @nap @COM-2290
  Scenario: Signed in User can add in stock products to shopping bag from listing page and move to wishlist
    Given I am a quickly registered user on intl
    And I have 1 ON_SALE and visible CLOTHING sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    And I add multi sized item 1 to the shopping bag
    And item 1 should be in the shopping bag 1 time
    When I click move item 1 to wishlist
    Then the new Wish List popup should appear
    And the new Wish List popup should contain the correct product details of item 1
    And I click Move to Wish List on the popup
    And item 1 should not be in the shopping bag
    And I navigate to the All Items page for wishlist
    And wishlist item 1 should be on the page

  @COM-2290
  Scenario: Signed out User can add in stock products to shopping bag from listing page and move to wishlist
    Given I am a quickly registered user on intl
    And I sign out
    And I clear my cookies
    And I have 1 IN_STOCK and visible LINGERIE sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    And I add multi sized item 1 to the shopping bag
    And item 1 should be in the shopping bag 1 time
    When I click move item 1 to wishlist
    And I sign in with the correct details on the Sign In page
    Then the new Wish List popup should appear
    And I click Move to Wish List on the popup
    And item 1 should not be in the shopping bag
    And I navigate to the All Items page for wishlist
    And wishlist item 1 should be on the page

  #TODO - when registration flow is supported, add test for this.

