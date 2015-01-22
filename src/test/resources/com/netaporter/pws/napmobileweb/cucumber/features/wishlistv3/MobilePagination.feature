@wishlistV3 @desktop
Feature: Mobile Wishlist Pagination
  As a WishlistV3 user
  I want to be able to be able to paginate through my items
  Because I have a hell of a lot of items and it's far too much to see at once

  ###########
  # All Items
  ###########

  @COM-1690
  Scenario: All Items: No pagination for less than 30 items
    Given I am a quickly registered user on intl
    And I have 3 IN_STOCK and visible SHOES skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I quickly add skus 1 to 3 to my wishlist via the WOAS API
    When I navigate to the mobile All Items page
    Then I should see 3 wishlist items
    And I should not see the view more button

  @COM-1690 @slow
  Scenario: All Items: Should see pagination for more than 30 items
  Given I am a quickly registered user on intl
    And I have 31 IN_STOCK and visible SHOES skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I quickly add skus 1 to 31 to my wishlist via the WOAS API
    When I navigate to the mobile All Items page
    Then I should see 30 wishlist items
    And I should see the view more button

  @COM-1690 @sanity @slow
  Scenario: All Items: Should be able to load next page for wishlist that fits on one and a bit pages
    Given I am a quickly registered user on intl
    And I have 32 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I store the details of products 1 to 32 from the http mobile product page
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 32 to my wishlist via the WOAS API
    When I navigate to the mobile All Items page
    Then I should see the wishlist items 1 to 30 ordered by date added descending without duplicates
    And I should see 30 wishlist items
    And I click the view more button
    And I should see the wishlist items ordered by date added descending without duplicates
    And I should see 32 wishlist items
    And I should not see the view more button

  @COM-1690 @slow
  Scenario: All Items: Should be able to load next page for wishlist that fits on exactly two pages
    Given I am a quickly registered user on intl
    And I have 60 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I quickly add skus 1 to 60 to my wishlist via the WOAS API
    When I navigate to the mobile All Items page
    Then I should see 30 wishlist items
    And I click the view more button
    Then I should see 60 wishlist items
    And I should not see the view more button

  @COM-1690 @slow
  Scenario: All Items: Should be able to load next page for wishlist that fits on two and a bit pages
    Given I am a quickly registered user on intl
    And I have 61 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I quickly add skus 1 to 61 to my wishlist via the WOAS API
    When I navigate to the mobile All Items page
    Then I should see 30 wishlist items
    And I click the view more button
    Then I should see 60 wishlist items
    And I should see the view more button

  @COM-1690 @slow
  Scenario: All Items: Should be able to load next page for wishlists that fit on one and a bit pages
    Given I am a quickly registered user on intl
    And I have 32 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I quickly add skus 1 to 16 to my wishlist via the WOAS API
    And I create a new wishlist via the WOAS API called Wishlist2
    And I quickly add skus 17 to 32 to my wishlist via the WOAS API
    When I navigate to the mobile All Items page
    Then I should see 30 wishlist items
    And I click the view more button
    Then I should see 32 wishlist items
    And I should not see the view more button

  ##############
  # Default List
  ##############

  @COM-1690
  Scenario: Default List: No pagination for less than 30 items
    Given I am a quickly registered user on intl
    And I browse to the default mobile wishlist page
    And I have 3 IN_STOCK and visible SHOES skus that I force to have similar stock in the db
    And I quickly add skus 1 to 3 to my wishlist via the WOAS API
    When I navigate to the Default Wishlist page
    Then I should see 3 wishlist items
    And I should not see the view more button

  @COM-1690 @slow
  Scenario: Default List: Should see pagination for more than 30 items
    Given I am a quickly registered user on intl
    And I browse to the default mobile wishlist page
    And I have 31 IN_STOCK and visible SHOES skus that I force to have similar stock in the db
    And I quickly add skus 1 to 31 to my wishlist via the WOAS API
    When I navigate to the Default Wishlist page
    Then I should see 30 wishlist items
    And I should see the view more button

  @COM-1690 @slow
  Scenario: Default List: Should be able to load next page for wishlist that fits on one and a bit pages
    Given I am a quickly registered user on intl
    And I browse to the default mobile wishlist page
    And I have 32 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I quickly add skus 1 to 32 to my wishlist via the WOAS API
    When I navigate to the Default Wishlist page
    Then I should see 30 wishlist items
    And I click the view more button
    Then I should see 32 wishlist items
    And I should not see the view more button

  @COM-1690 @slow
  Scenario: Default List: Should be able to load next page for wishlist that fits on exactly two pages
    Given I am a quickly registered user on intl
    And I browse to the default mobile wishlist page
    And I have 60 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I quickly add skus 1 to 60 to my wishlist via the WOAS API
    When I navigate to the Default Wishlist page
    Then I should see 30 wishlist items
    And I click the view more button
    Then I should see 60 wishlist items
    And I should not see the view more button

  @COM-1690 @slow
  Scenario: Default List: Should be able to load next page for wishlist that fits on two and a bit pages
    Given I am a quickly registered user on intl
    And I browse to the default mobile wishlist page
    And I have 61 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I quickly add skus 1 to 61 to my wishlist via the WOAS API
    When I navigate to the Default Wishlist page
    Then I should see 30 wishlist items
    And I click the view more button
    Then I should see 60 wishlist items
    And I should see the view more button

  #################
  # Custom Wishlist
  #################

  @COM-1690 @slow
  Scenario: Custom List: No pagination for less than 30 items
    Given I am a quickly registered user on intl
    And I have 3 IN_STOCK and visible SHOES skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I quickly add skus 1 to 3 to my wishlist via the WOAS API
    When I view that specific mobile wishlist via its direct url
    Then I should see 3 wishlist items
    And I should not see the view more button

  @COM-1690 @slow
  Scenario: Custom List: Should see pagination for more than 30 items
    Given I am a quickly registered user on intl
    And I have 31 IN_STOCK and visible SHOES skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I quickly add skus 1 to 31 to my wishlist via the WOAS API
    When I view that specific mobile wishlist via its direct url
    Then I should see 30 wishlist items
    And I should see the view more button

  @COM-1690 @slow
  Scenario: Custom List: Should be able to load next page for wishlist that fits on one and a bit pages
    Given I am a quickly registered user on intl
    And I have 32 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I quickly add skus 1 to 32 to my wishlist via the WOAS API
    When I view that specific mobile wishlist via its direct url
    Then I should see 30 wishlist items
    And I click the view more button
    Then I should see 32 wishlist items
    And I should not see the view more button

  @COM-1690 @slow
  Scenario: Custom List: Should be able to load next page for wishlist that fits on exactly two pages
    Given I am a quickly registered user on intl
    And I have 60 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I quickly add skus 1 to 60 to my wishlist via the WOAS API
    When I view that specific mobile wishlist via its direct url
    Then I should see 30 wishlist items
    And I click the view more button
    Then I should see 60 wishlist items
    And I should not see the view more button

  @COM-1690 @slow
  Scenario: Custom List: Should be able to load next page for wishlist that fits on two and a bit pages
    Given I am a quickly registered user on intl
    And I have 61 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I quickly add skus 1 to 61 to my wishlist via the WOAS API
    When I view that specific mobile wishlist via its direct url
    Then I should see 30 wishlist items
    And I click the view more button
    Then I should see 60 wishlist items
    And I should see the view more button