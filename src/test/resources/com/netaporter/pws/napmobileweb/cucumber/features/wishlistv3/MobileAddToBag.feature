@wishlistV3 @mobileweb
Feature: Mobile Wishlist V3 Add To Bag
  As a mobile WishlistV3 user
  I want to be able to add my wishlist items to my shopping bag
  So that I can proceed to buy my dream products

  @COM-1716
  Scenario: Add Single Clothing Item To Mobile Shopping bag
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I store the details of products 1 to 1 from the http mobile product page
    And I view that specific mobile wishlist via its direct url
    When I click on the mobile add to bag on wishlist item 1
    Then wishlist item 1 should be in the mobile shopping bag 1 time

  @COM-1716
  Scenario: Add Single Clothing Item To Mobile Shopping bag multiple times
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I store the details of products 1 to 1 from the http mobile product page
    And I view that specific mobile wishlist via its direct url
    When I click on the mobile add to bag on wishlist item 1
    And I click on the mobile add to bag on wishlist item 1
    And I click on the mobile add to bag on wishlist item 1
    Then wishlist item 1 should be in the mobile shopping bag 3 times

  @COM-1716 @sanity
  Scenario: Add Multiple Items To Mobile Shopping bag
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible BAGS sku that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible LINGERIE sku that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 3 to my wishlist via the WOAS API
    And I store the details of products 1 to 1 from the http mobile product page
    And I view that specific mobile wishlist via its direct url
    When I click on the mobile add to bag on wishlist item 1
    And I click on the mobile add to bag on wishlist item 2
    And I click on the mobile add to bag on wishlist item 3
    Then wishlist item 1 should be in the mobile shopping bag 1 time
    Then wishlist item 2 should be in the mobile shopping bag 1 time
    Then wishlist item 3 should be in the mobile shopping bag 1 time

  @COM-1716
  Scenario: Add Single Pid but 2 sizes To mobile Shopping bag
    Given I am a quickly registered user on intl
    And I have 2 in stock skus from the same pid for mobile
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 2 to my wishlist via the WOAS API
    And I store the details of products 1 to 2 from the http mobile product page
    And I view that specific mobile wishlist via its direct url
    When I click on add to bag on all items on mobile
    Then wishlist item 1 should be in the mobile shopping bag 1 time
    And wishlist item 2 should be in the mobile shopping bag 1 time

  @COM-1716 @sanity
  Scenario: Add Single Clothing Item To Mobile Shopping bag from all-items page
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I store the details of products 1 to 1 from the http mobile product page
    When I navigate to the mobile All Items page
    And I click on the mobile add to bag on wishlist item 1
    Then wishlist item 1 should be in the mobile shopping bag 1 time

  @COM-1716
  Scenario: Sold Out Clothing Item Should Not Have Add To Bag button
    Given I am a quickly registered user on intl
    And I have 3 SOLD_OUT and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 3 to my wishlist via the WOAS API
    And I store the details of products 1 to 1 from the http mobile product page
    And I view that specific mobile wishlist via its direct url
    Then all 3 items should have the add to bag button disabled