@wishlistV3 @desktop
Feature: Wishlist V3 page verification
  As a registered NAP customer
  I want to be able to add items to a wishlist
  So that I can start building up saved items from the site

  @COM-1271
  Scenario: Customer views an empty wishlist
    Given I am a quickly registered user on intl
    And I create a new empty wishlist via the WOAS API
    When I view that specific wishlist via its direct url
    Then I should see the correct custom wishlist page title
    And  I should see the correct wishlist header
    And  I should see the correct empty wishlist message

  @COM-1397 @COM-1433 @COM-1434 @COM-1458 @COM-1571 @COM-1456 @sanity @slow
  Scenario: Customer views a specific wishlist with items
    Given I am a quickly registered user on intl
    And I have 5 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I have 5 IN_STOCK and visible SHOES skus that I force to have similar stock in the db
    And I have 5 IN_STOCK and visible LINGERIE skus that I force to have similar stock in the db
    And I have 5 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I have 5 IN_STOCK and visible BEAUTY skus that I force to have similar stock in the db
    And I have 5 IN_STOCK and visible ACCESSORIES skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called AllCategories
    And I add skus 1 to 30 to my wishlist via the WOAS API
    And I store the details of products 1 to 30 from the product page
    When I view that specific wishlist via its direct url
    Then I should see the correct thumbnails for each item
    And I should see the correct custom wishlist page title
    And I should see the correct wishlist header
    And I should see those items in the correct chronological order with the correct details
    And I should see the correct size for each item

  @COM-2237 @COM-2268
  Scenario: Customer views a french wishlist with items
    Given I am a quickly registered user on intl
    And I am on change country page
    And I change my language to French
    And I have 5 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called FrenchList
    And I add skus 1 to 5 to my wishlist via the WOAS API
    And I store the details of products 1 to 5 from the product page
    When I view that specific wishlist via its direct url
    Then I should see the correct thumbnails for each item
    And I should see the correct custom wishlist page title
    And I should see the correct wishlist header
    And I should see those items in the correct chronological order with the correct details
    And I should see the correct size for each item

  @COM-1571
  Scenario: Customer views a single sized item
    Given I am a quickly registered user on intl
    And I have 5 ON_SALE and visible BAGS skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Bags
    And I add skus 1 to 5 to my wishlist via the WOAS API
    And I store the details of products 1 to 5 from the product page
    When I view that specific wishlist via its direct url
    And I should see the correct size for each item
    And I should see those items in the correct chronological order with the correct details


  @COM-1622
  Scenario: Customer views a non existant wishlist
    Given I am a quickly registered user on intl
    When I browse to a non-existant wishlist page
    Then I should see the wishlist error page

#  @COM-1707
#  Scenario: Migrated customer accessing legacy grid list sent to new all-items page
#    Given I am a quickly registered user on intl
#    When I browse to the legacy grid wish list page on INTL
#    Then I should be on the wishlist page called All Items
#
#  @COM-1707
#  Scenario: Migrated customer accessing legacy category list sent to new all-items page
#    Given I am a quickly registered user on intl
#    When I browse to the legacy category wish list page on INTL
#    Then I should be on the wishlist page called All Items

  @COM-1805
  Scenario: Using the browser back button to return to the wishlist page
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called BACK
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I store the details of products 1 to 1 from the product page
    And I view that specific wishlist via its direct url
    And I click the first wishlist product thumbnail
    And I click on the View More Details button in the wishlist product details slider
 #   And I am on the product details page of the PID I just clicked
    When I select the back button on the browser
    Then I should see those items in the correct chronological order with the correct details

  @COM-2113
  Scenario: Wishlist All-Items sorted by alerts
    Given I am a quickly registered user on intl
    And I have 2 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I have 2 LOW_STOCK and visible SHOES skus that I force to have similar stock in the db
    And I have 2 ON_SALE and visible LINGERIE skus that I force to have similar stock in the db
    And I have 2 SOLD_OUT and visible LINGERIE skus that I force to have similar stock in the db
    When I create a new wishlist via the WOAS API called Sort List 1
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I add skus 5 to 5 to my wishlist via the WOAS API
    And I add skus 3 to 3 to my wishlist via the WOAS API
    And I add skus 7 to 7 to my wishlist via the WOAS API
    And I create a new wishlist via the WOAS API called Sort List 2
    And I add skus 2 to 2 to my wishlist via the WOAS API
    And I add skus 6 to 6 to my wishlist via the WOAS API
    And I add skus 4 to 4 to my wishlist via the WOAS API
    And I add skus 8 to 8 to my wishlist via the WOAS API
    And I store the details of products 7 to 8 from the product page
    And I store the details of products 1 to 2 from the product page
    And I store the details of products 5 to 6 from the product page
    And I store the details of products 3 to 4 from the product page
    And I navigate to the All Items page for wishlist
    Then I should see those items in the correct chronological order with the correct details