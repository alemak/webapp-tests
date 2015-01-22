@wishlistV3 @desktop
Feature: Mobile Wishlist V3 page verification
  As a registered NAP mobile customer
  I want to be able to add items to a wishlist
  So that I can start building up saved items from the site

  @COM-1271 @COM-1743
  Scenario: Customer views an empty mobile wishlist
    Given I am a quickly registered user on intl
    And I create a new empty wishlist via the WOAS API
    When I view that specific mobile wishlist via its direct url
    Then I should see the correct mobile wishlist page title
    And  I should see the correct mobile wishlist header
    And  I should see the correct empty mobile wishlist message

  @COM-1397 @COM-1433 @COM-1434 @COM-1458 @COM-1571 @COM-1456 @COM-1743  @COM-1744  @COM-1654 @sanity @slow
  Scenario: Customer views a specific mobile wishlist with items
    Given I am a quickly registered user on intl
    And I have 5 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I have 5 IN_STOCK and visible SHOES skus that I force to have similar stock in the db
    And I have 5 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I have 5 IN_STOCK and visible LINGERIE skus that I force to have similar stock in the db
    And I have 5 IN_STOCK and visible BEAUTY skus that I force to have similar stock in the db
    And I have 5 IN_STOCK and visible ACCESSORIES skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called AllCategories
    And I add skus 1 to 30 to my wishlist via the WOAS API
    And I store the details of products 1 to 30 from the mobile product page
    When I view that specific mobile wishlist via its direct url
    Then I should see the correct thumbnails for each item on the mobile page
    And I should see the correct mobile wishlist page title
    And I should see the correct mobile wishlist header
    And I should see those items in the correct chronological order with the correct details in the mobile wishlist
    And I should see the correct size for each item in the mobile wishlist
    And I should see the delete item button for each item on the mobile page
    And I should see the Add to bag button for each item on the mobile page

  @COM-1571
  Scenario: Customer views an single sized item
    Given I am a quickly registered user on intl
    And I have 5 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Bags
    And I add skus 1 to 5 to my wishlist via the WOAS API
    And I store the details of products 1 to 5 from the mobile product page
    When I view that specific mobile wishlist via its direct url
    And I should see the correct size for each item in the mobile wishlist
    And I should see those items in the correct chronological order with the correct details in the mobile wishlist

  @COM-1622
  Scenario: Customer views a non existant wishlist
    Given I am a quickly registered user on intl
    When I browse to a non-existant mobile wishlist page
    Then I should see the mobile wishlist error page

  @COM-1707
  Scenario: Non-migrated customer accessing legacy mobile wishlist
    Given I am a quickly registered user on intl
    When I browse to the legacy mobile wish list page on INTL
    Then I am taken to the legacy mobile wish list page

  @COM-1707
  Scenario: Migrated customer accessing legacy mobile list sent to new all-items page
    Given I am a quickly registered user on intl
    When I browse to the legacy mobile wish list page on INTL
    Then I should be on the mobile wishlist page called All Items

  @COM-1689
  Scenario: Customer views the all-items mobile wishlist with items
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible SHOES skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible LINGERIE skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible BEAUTY skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible ACCESSORIES skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I create a new wishlist via the WOAS API called Wishlist2
    And I add skus 2 to 2 to my wishlist via the WOAS API
    And I create a new wishlist via the WOAS API called Wishlist3
    And I add skus 3 to 3 to my wishlist via the WOAS API
    And I create a new wishlist via the WOAS API called Wishlist4
    And I add skus 4 to 4 to my wishlist via the WOAS API
    And I create a new wishlist via the WOAS API called Wishlist5
    And I add skus 5 to 5 to my wishlist via the WOAS API
    And I store the details of products 1 to 5 from the mobile product page
    When I navigate to the mobile All Items page
    Then I should see those items in the correct chronological order with the correct details in the mobile wishlist