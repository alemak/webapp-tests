@wishlistV3 @desktop
Feature: Wishlist V3 Add To Bag
  As a WishlistV3 user
  I want to be able to see add my wishlist items to my shopping bag
  So that I can proceed to buy my dream products

  @COM-1570 @sanity
  Scenario: Add Single Clothing Item To Shopping bag
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I wait 3 seconds
    And I view that specific wishlist via its direct url
    And I wait 3 seconds
    When I click on add to bag on wishlist item 1
    Then wishlist item 1 should be in the shopping bag 1 time

  @COM-1570
  Scenario: Add Multiple Items To Shopping bag
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible BAGS sku that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible SHOES sku that I force to have similar stock in the db
    And I store the details of products 1 to 3 from the product page
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 3 to my wishlist via the WOAS API
    And I wait 3 seconds
    And I view that specific wishlist via its direct url
    And I wait 3 seconds
    When I click on add to bag on wishlist item 1
    And I click on add to bag on wishlist item 2
    And I click on add to bag on wishlist item 3
    Then wishlist item 1 should be in the shopping bag 1 time
    And wishlist item 2 should be in the shopping bag 1 time
    And wishlist item 3 should be in the shopping bag 1 time
    And the shopping bag icon shows 3 items

  @COM-1570
  Scenario: Add Single Item To Shopping bag and Check the Overlay
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I store the details of products 1 to 1 from the product page
    And I wait 3 seconds
    And I view that specific wishlist via its direct url
    And I wait 3 seconds
    When I click on add to bag for wishlist item 1 and wait for the overlay to show
    Then the recently added overlay should appear with the details of item 1 in it
    And the shopping bag icon shows 1 item

  @wishlistV3
  Scenario: Add Single Pid but 2 sizes To Shopping bag
    Given I am a quickly registered user on intl
    And I have 2 in stock skus from the same pid
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 2 to my wishlist via the WOAS API
    And I store the details of products 1 to 2 from the product page
    And I view that specific wishlist via its direct url
    When I click on add to bag on all items
    Then wishlist item 1 should be in the shopping bag 1 time
    And wishlist item 2 should be in the shopping bag 1 time

  @COM-1570
  Scenario: Add Single Item twice to the shopping bag
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible BAGS sku that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I store the details of products 1 to 1 from the product page
    And I view that specific wishlist via its direct url
    When I click on add to bag on wishlist item 1
    And I click on add to bag on wishlist item 1
    Then wishlist item 1 should be in the shopping bag 2 times
    And the shopping bag icon shows 2 items

  @COM-1570
  Scenario: Add Single Low Stock item 5 times to the shopping bag
    Given I am a quickly registered user on intl
    And I have 1 LOW_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I store the details of products 1 to 1 from the product page
    And I view that specific wishlist via its direct url
    When I click on add to bag on wishlist item 1
    And I click on add to bag on wishlist item 1
    And I click on add to bag on wishlist item 1
    And I click on add to bag on wishlist item 1
    And I click on add to bag on wishlist item 1
    Then wishlist item 1 should be in the shopping bag less than 5 times

  @COM-1570
  Scenario: Sold Out Clothing Item Should Not Have Add To Bag button
    Given I am a quickly registered user on intl
    And I have 3 SOLD_OUT and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 3 to my wishlist via the WOAS API
    And I view that specific wishlist via its direct url
    Then no items should have the add to bag button

  @COM-1570
  Scenario: Add Single Clothing Item to Shopping bag from default wishlist
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    And I click the wishlist link on the header
    And I click the wishlist navigation menu item called Wish List
    And I store the current wishlist details
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I view that specific wishlist via its direct url
    When I click on add to bag on wishlist item 1
    Then wishlist item 1 should be in the shopping bag 1 time

  @COM-1570
  Scenario: Add Single Clothing Item To shopping bag from the All Items page
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible BAGS sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I navigate to the All Items page for wishlist
    When I click on add to bag on wishlist item 1
    Then wishlist item 1 should be in the shopping bag 1 time