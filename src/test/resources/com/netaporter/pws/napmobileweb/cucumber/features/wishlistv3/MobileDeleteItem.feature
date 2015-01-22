@wishlistV3 @mobileweb
Feature: Wishlist V3 Remove items from Mobile Wishlist
  As a WishlistV3 user
  I want to be able to delete an item in my mobile wishlists
  So that I can curate my collection of items

  @COM-1686 @sanity
  Scenario: Delete an item from a mobile wishlist
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I view that specific mobile wishlist via its direct url
    When I delete item 1 from my mobile wishlist
    Then I should see the correct empty mobile wishlist message

  @COM-1686
  Scenario: Delete some items from a mobile wishlist
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible LINGERIE skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 3 to my wishlist via the WOAS API
    And I view that specific mobile wishlist via its direct url
    When I delete item 3 from my mobile wishlist
    And I delete item 2 from my mobile wishlist
    And I delete item 1 from my mobile wishlist
    Then I should see the correct empty mobile wishlist message

  @COM-1686
  Scenario: Highlight some items for deletion on the mobile wishlist
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible LINGERIE skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 3 to my wishlist via the WOAS API
    And I view that specific mobile wishlist via its direct url
    When I highlight item 2 for deletion from my mobile wishlist
    Then item 2 should be highlighted for deletion