@wishlistV3 @desktop
Feature: Wishlist V3 Remove items from Wishlist
  As a WishlistV3 user
  I want to be able to delete an item in my wishlists
  So that I can curate my collection of items

  @COM-1466 @sanity
  Scenario: Delete an item from a wishlist
  Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I view that specific wishlist via its direct url
    When I delete item 1 from my wishlist
    Then I should see the correct empty wishlist message

  @COM-1466
  Scenario: Delete some items from a wishlist
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible LINGERIE skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 3 to my wishlist via the WOAS API
    And I view that specific wishlist via its direct url
    When I delete item 3 from my wishlist
    And I delete item 2 from my wishlist
    And I delete item 1 from my wishlist
    Then I should see the correct empty wishlist message

  @COM-1466 @sanity
  Scenario: Delete some items from all items page
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible LINGERIE skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 3 to my wishlist via the WOAS API
    And I view that specific wishlist via its direct url
    And I navigate to the All Items page for wishlist
    When I delete item 3 from my wishlist
    And I delete item 2 from my wishlist
    And I delete item 1 from my wishlist
    Then I should see the correct empty wishlist message
    And I click the wishlist navigation menu item called Wishlist1
    And I should see the correct empty wishlist message

  @COM-1466
  Scenario: Delete some items from default wishlist
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible LINGERIE skus that I force to have similar stock in the db
    And I browse to the default wishlist page
    And I add skus 1 to 3 to my wishlist via the WOAS API
    And I view that specific wishlist via its direct url
    When I delete item 3 from my wishlist
    And I delete item 2 from my wishlist
    And I delete item 1 from my wishlist
    Then I should see the correct empty wishlist message

  @COM-1466
  Scenario: Delete single item check order
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible LINGERIE skus that I force to have similar stock in the db
    And I store the details of product 1 to 1 from the product page
    And I store the details of product 3 to 3 from the product page
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 3 to my wishlist via the WOAS API
    And I click the wishlist link on the header
    And I view that specific wishlist via its direct url
    When I delete item 2 from my wishlist
    Then I should see those items in the correct chronological order with the correct details

  @COM-1466 @COM-1884 @COM-1795 @known-failure @COM-1775
  Scenario: Delete single item from all-items page that exists in 2 lists
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I store the details of product 1 to 1 from the product page
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I create a new wishlist via the WOAS API called Wishlist2
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I navigate to the All Items page for wishlist
    When I delete item 1 from my wishlist
    Then I should see those items in the correct chronological order with the correct details
    And I click the wishlist navigation menu item called Wishlist2
    And I should see those items in the correct chronological order with the correct details
    And I click the wishlist navigation menu item called Wishlist1
    And I should see the correct empty wishlist message

  @COM-1769 @COM-1808 @slow
  Scenario: Removing an item from list will back fill with 1 item
    Given I am a quickly registered user on intl
    And I have 62 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I store the details of product 1 to 2 from the product page
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 2 to my wishlist via the WOAS API
    And I quickly add skus 3 to 62 to my wishlist via the WOAS API
    And I view that specific wishlist via its direct url
    And wishlist item 1 is not on the page
    And wishlist item 2 is not on the page
    When I delete item 62 from my wishlist
    Then wishlist item 2 should be on the page
    And wishlist item 1 is not on the page
    And I delete item 61 from my wishlist
    And wishlist item 1 should be on the page


#TODO: Need to add tests for pagination, code not written yet.