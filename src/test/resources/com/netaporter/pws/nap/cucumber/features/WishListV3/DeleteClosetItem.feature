@wishlistV3 @desktop
Feature: Wishlist V3 delete closet items from wishlist
  As a WishlistV3 user
  I want to be able to delete an item in my closet
  So I can see only relevant items

  Background:
    Given I am a quickly registered user on intl
    And I sign out
    And I sign in with the correct details

  @COM-2157 @sanity
  Scenario: Delete an item from a closet
    Given I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the https product page
    And I add product 1 to my shopping bag
    And I click 'Proceed to Purchase' on the product details page
    And I go to Shipping page as a signed in user and enter my address
    And I proceed to the Payment page
    And I pay by VISA_CREDIT_CARD
    And Order Confirmation page is displayed
    And I wait 5 seconds
    When I navigate to the Closet page for wishlist
    And I delete item 1 from my wishlist
    Then I should see the correct empty closet message

  @COM-2157
  Scenario: Delete some item from a closet
    Given I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    Given I have 1 IN_STOCK and visible BAGS sku that I force to have similar stock in the db
    Given I have 1 IN_STOCK and visible LINGERIE sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the https product page
    And I add product 1 to my shopping bag
    And I store the details of products 2 to 2 from the https product page
    And I add product 2 to my shopping bag
    And I store the details of products 3 to 3 from the https product page
    And I add product 3 to my shopping bag
    And I click 'Proceed to Purchase' on the product details page
    And I go to Shipping page as a signed in user and enter my address
    And I proceed to the Payment page
    And I pay by VISA_CREDIT_CARD
    And Order Confirmation page is displayed
    And I wait 5 seconds
    When I navigate to the Closet page for wishlist
    And I delete item 3 from my wishlist
    And I delete item 2 from my wishlist
    And I delete item 1 from my wishlist
    Then I should see the correct empty closet message