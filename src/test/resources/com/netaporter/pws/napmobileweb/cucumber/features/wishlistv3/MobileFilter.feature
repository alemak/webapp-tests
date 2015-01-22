@wishlistV3 @desktop
Feature: Mobile Wishlist V3 Filtering
  As a Mobile WishlistV3 user
  I want to be able to filter the items in my mobile wishlist by category
  So that I can view my mobile wishlist items easily


  @COM-1779
  Scenario: Default filter set to All for custom wishlist
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Filter Tests
    When I view that specific mobile wishlist via its direct url
    Then the mobile filter option ALL is selected

  @COM-1779
  Scenario: Default filter set to All for All-Items
    Given I am a quickly registered user on intl
    When I navigate to the mobile All Items page
    Then the mobile filter option ALL is selected

  @COM-1779
  Scenario: Default filter set to All for Default wishlist
    Given I am a quickly registered user on intl
    And I browse to the default mobile wishlist page
    When I browse to the default mobile wishlist page
    Then the mobile filter option ALL is selected

  @COM-1779 @COM-1817
  Scenario: Empty Wish list text for filtered wishlist for custom wishlist
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Filter Tests
    And I view that specific mobile wishlist via its direct url
    When I filter my mobile wishlist items by CLOTHING
    Then the correct text is displayed for the empty filtered mobile wishlist

  @COM-1779 @COM-1817
  Scenario: Empty Wish list text for filtered wishlist for All Items
    Given I am a quickly registered user on intl
    And I navigate to the mobile All Items page
    When I filter my mobile wishlist items by BAGS
    Then the correct text is displayed for the empty filtered All Items mobile list

  @COM-1779 @COM-1817
  Scenario: Empty Wish list text for filtered wishlist for Default wish list
    Given I am a quickly registered user on intl
    And I browse to the default mobile wishlist page
    When I filter my mobile wishlist items by BAGS
    Then the correct text is displayed for the empty filtered mobile wishlist


  @COM-1779 @COM-1875
  Scenario: Filter by category only shows items in that category for custom wishlist
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Filter Test
    And I have a list of in stock skus of one sku per product category
    And I quickly add each of my items to my wishlist via the WOAS API
    And I store the details of all my items from the mobile product pages
    When I view that specific mobile wishlist via its direct url
    Then Filtering by each mobile filter category only displays items in that category


  @COM-1779 @COM-1875
  Scenario: Filter by category only shows items in that category for All Items list
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Filter Test
    And I have a list of in stock skus of one sku per product category
    And I quickly add each of my items to my wishlist via the WOAS API
    And I store the details of all my items from the mobile product pages
    When I navigate to the mobile All Items page
    Then Filtering by each mobile filter category only displays items in that category

  @COM-1779 @COM-1875 @sanity
  Scenario: Filter by category only shows items in that category for the Default Wishlist
    Given I am a quickly registered user on intl
    And I have a list of in stock skus of one sku per product category
    And I quickly add each of my items to my default wishlist via the WOAS API
    And I store the details of all my items from the mobile product pages
    And I browse to the default mobile wishlist page
    When I navigate to the Default Wishlist page
    Then Filtering by each mobile filter category only displays items in that category

  @COM-1779
  Scenario: Pagination loads more items of the same category when a filter has been applied. Items from other categories are still not displayed
    Given I am a quickly registered user on intl
    And I have 31 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible SHOES skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 32 to my wishlist via the WOAS API
    And I store the details of products 1 to 32 from the mobile product page
    And I view that specific mobile wishlist via its direct url
    And I filter my mobile wishlist items by CLOTHING
    When I click the view more button
    Then I should see 31 wishlist items
    And I should see mobile items 1 to 31 in the correct chronological order with the correct details
    And I should not see items 32 to 32 on the mobile wishlist page


  @COM-1779
  Scenario: All categories are displayed when filtered by All on All-Items wish list
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Filter Test
    And I have a list of in stock skus of one sku per product category
    And I quickly add each of my items to my wishlist via the WOAS API
    And I store the details of all my items from the mobile product pages
    When I navigate to the mobile All Items page
    And I filter my mobile wishlist items by ALL
    Then I should see those items in the correct chronological order with the correct details in the mobile wishlist

  @COM-1779
  Scenario: All categories are displayed when filtered by All on a custom wish list
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Filter Test
    And I have a list of in stock skus of one sku per product category
    And I quickly add each of my items to my wishlist via the WOAS API
    And I store the details of all my items from the mobile product pages
    When I view that specific mobile wishlist via its direct url
    And I filter my mobile wishlist items by ALL
    Then I should see those items in the correct chronological order with the correct details in the mobile wishlist

  @COM-1779
  Scenario: All categories are displayed when filtered by All on the Default wish list
    Given I am a quickly registered user on intl
    And I have a list of in stock skus of one sku per product category
    And I quickly add each of my items to my default wishlist via the WOAS API
    And I store the details of all my items from the mobile product pages
    When I browse to the default mobile wishlist page
    And I filter my mobile wishlist items by ALL
    Then I should see those items in the correct chronological order with the correct details in the mobile wishlist

  @COM-1779
  Scenario: Selecting a new wishlist after applying a filter on previously viewed list resets filter to All on new wish list
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible ACCESSORIES skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Filter Test 1
    And I add skus 1 to 2 to my wishlist via the WOAS API
    And I store the details of products 1 to 2 from the mobile product page
    And I create a new wishlist via the WOAS API called Filter Test 2
    And I add skus 2 to 2 to my wishlist via the WOAS API
    And I navigate to the mobile All Items page
    And I select the mobile wishlist called Filter Test 1 from the wishlist navigation menu
    And I filter my mobile wishlist items by ACCESSORIES
    And I should see mobile items 1 to 1 in the correct chronological order with the correct details
    And I select the mobile wishlist called Filter Test 2 from the wishlist navigation menu
    Then I should see mobile items 2 to 2 in the correct chronological order with the correct details


  @COM-1779 @COM-1876
  Scenario: Re-choosing the same wishlist after filtering resets the filter to All
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible ACCESSORIES skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Filter Test 1
    And I add skus 1 to 2 to my wishlist via the WOAS API
    And I store the details of products 1 to 2 from the mobile product page
    And I navigate to the mobile All Items page
    And I select the mobile wishlist called Filter Test 1 from the wishlist navigation menu
    And I filter my mobile wishlist items by ACCESSORIES
    And I should see mobile items 1 to 1 in the correct chronological order with the correct details
    And I select the mobile wishlist called Filter Test 1 from the wishlist navigation menu
    Then I should see mobile items 1 to 2 in the correct chronological order with the correct details






