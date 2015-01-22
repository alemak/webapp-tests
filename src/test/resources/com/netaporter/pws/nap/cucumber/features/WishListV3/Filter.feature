@wishlistV3 @desktop
Feature: Wishlist V3 Filtering
  As a WishlistV3 user
  I want to be able to filter the items in my wishlist by category
  So that I can view my wishlist items easily


  @COM-1772
  Scenario: Default filter set to All for custom wishlist
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Filter Tests
    When I view that specific wishlist via its direct url
    Then the filter option All is underlined

  @COM-1772
  Scenario: Default filter set to All for All-Items
    Given I am a quickly registered user on intl
    When I navigate to the All Items page for wishlist
    Then the filter option All is underlined

  @COM-1772
  Scenario: Default filter set to All for Default wishlist
    Given I am a quickly registered user on intl
    And I navigate to the All Items page for wishlist
    When I navigate to the Default Wishlist page
    Then the filter option All is underlined

  @COM-1772 @COM-1817
  Scenario: Empty Wish list text for filtered wishlist for custom wishlist
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Filter Tests
    And I view that specific wishlist via its direct url
    When I filter my wishlist items by Clothing
    Then the correct text is displayed for the empty filtered wishlist

  @COM-1772 @COM-1817
  Scenario: Empty Wish list text for filtered wishlist for All Items
    Given I am a quickly registered user on intl
    And I navigate to the All Items page for wishlist
    When I filter my wishlist items by Bags
    Then the correct text is displayed for the empty filtered All Items list

  @COM-1772 @COM-1817
  Scenario: Empty Wish list text for filtered wishlist for Default wish lsit
    Given I am a quickly registered user on intl
    And I navigate to the All Items page for wishlist
    And I navigate to the Default Wishlist page
    When I filter my wishlist items by Bags
    Then the correct text is displayed for the empty filtered wishlist


  @COM-1772
  Scenario: Filter by category only shows items in that category for custom wishlist
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Filter Test
    And I have a list of in stock skus of one sku per product category
    And I quickly add each of my items to my wishlist via the WOAS API
    And I store the details of all my items from the product pages
    When I view that specific wishlist via its direct url
    Then Filtering by each filter category only displays items in that category


  @COM-1772
  Scenario: Filter by category only shows items in that category for All Items list
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Filter Test
    And I have a list of in stock skus of one sku per product category
    And I quickly add each of my items to my wishlist via the WOAS API
    And I store the details of all my items from the product pages
    When I navigate to the All Items page for wishlist
    Then Filtering by each filter category only displays items in that category

  @COM-1772 @sanity
  Scenario: Filter by category only shows items in that category for the Default Wishlist
    Given I am a quickly registered user on intl
    And I have a list of in stock skus of one sku per product category
    And I quickly add each of my items to my default wishlist via the WOAS API
    And I store the details of all my items from the product pages
    And I navigate to the All Items page for wishlist
    When I navigate to the Default Wishlist page
    Then Filtering by each filter category only displays items in that category
#
#  @COM-1772
#  Scenario: Filter by category only shows items in that category for All Items
#    Given I am a quickly registered user on intl
##    And I have 1 IN_STOCK and visible ACCESSORIES skus
#    And I have 1 IN_STOCK and visible BAGS skus
#    And I have 1 IN_STOCK and visible BEAUTY skus
#    And I have 1 IN_STOCK and visible CLOTHING skus
#    And I have 1 IN_STOCK and visible LINGERIE skus
#    And I have 1 IN_STOCK and visible SHOES skus
#    And I store the details of product 1 to 1 from the product page
#    And I create a new wishlist via the WOAS API called Wishlist1
#    And I add skus 1 to 1 to my wishlist via the WOAS API
#    And I view that specific wishlist via its direct url
#    When I filter my wishlist items by Accessories
#    Then I should see those items in the correct chronological order with the correct details


  @COM-1772
  Scenario: Pagination loads more items of the same category when a filter has been applied.
            Items from other categories are still not displayed
    Given I am a quickly registered user on intl
    And I have 65 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible SHOES skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 65 to my wishlist via the WOAS API
    And I store the details of products 1 to 65 from the product page
    And I view that specific wishlist via its direct url
    And I filter my wishlist items by Clothing
    When I click the view more button
    Then I should see 65 wishlist items
    And I should see items 1 to 65 in the correct chronological order with the correct details
    And I should not see items 66 to 66 on the wishlist page


  @COM-1772
  Scenario: All categories are displayed when filtered by All on All-Items wish list
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Filter Test
    And I have a list of in stock skus of one sku per product category
    And I quickly add each of my items to my wishlist via the WOAS API
    And I store the details of all my items from the product pages
    When I navigate to the All Items page for wishlist
    And I filter my wishlist items by All
    Then I should see those items in the correct chronological order with the correct details

  @COM-1772
  Scenario: All categories are displayed when filtered by All on a custom wish list
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Filter Test
    And I have a list of in stock skus of one sku per product category
    And I quickly add each of my items to my wishlist via the WOAS API
    And I store the details of all my items from the product pages
    When I view that specific wishlist via its direct url
    And I filter my wishlist items by All
    Then I should see those items in the correct chronological order with the correct details

  @COM-1772
  Scenario: All categories are displayed when filtered by All on the Default wish list
    Given I am a quickly registered user on intl
    And I have a list of in stock skus of one sku per product category
    And I quickly add each of my items to my default wishlist via the WOAS API
    And I store the details of all my items from the product pages
    And I navigate to the All Items page for wishlist
    When I navigate to the Default Wishlist page
    And I filter my wishlist items by All
    Then I should see those items in the correct chronological order with the correct details


  @COM-1772
  Scenario: Filtering by each category underlines each selected filter option
    Given I am a quickly registered user on intl
    When I navigate to the All Items page for wishlist
    Then each filter option is underlined after clicking it

  @COM-1772
  Scenario: Selecting a new wishlist after applying a filter on previously viewed list resets filter to All on new wish list
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible ACCESSORIES skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Filter Test 1
    And I add skus 1 to 2 to my wishlist via the WOAS API
    And I store the details of products 1 to 2 from the product page
    And I create a new wishlist via the WOAS API called Filter Test 2
    And I add skus 2 to 2 to my wishlist via the WOAS API
    And I navigate to the All Items page for wishlist
    And I click the wishlist navigation menu item called Filter Test 1
    And I filter my wishlist items by Accessories
    And I should see items 1 to 1 in the correct chronological order with the correct details
    When I click the wishlist navigation menu item called Filter Test 2
    Then I should see items 2 to 2 in the correct chronological order with the correct details


  @COM-1772 @COM-1876
  Scenario: Re-choosing the same wishlist after filtering resets the filter to All
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible ACCESSORIES skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Filter Test 1
    And I add skus 1 to 2 to my wishlist via the WOAS API
    And I store the details of products 1 to 2 from the product page
    And I navigate to the All Items page for wishlist
    And I click the wishlist navigation menu item called Filter Test 1
    And I filter my wishlist items by Accessories
    And I should see items 1 to 1 in the correct chronological order with the correct details
    When I click the wishlist navigation menu item called Filter Test 1
    Then I should see items 1 to 2 in the correct chronological order with the correct details






