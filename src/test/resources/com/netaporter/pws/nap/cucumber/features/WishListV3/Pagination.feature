@wishlistV3 @desktop
Feature: Wishlist Pagination
  As a WishlistV3 user
  I want to be able to be able to paginate through my items
  Because I have a hell of a lot of items and it's far too much to see at once

  ###########
  # All Items
  ###########

  @COM-1461 @slow
  Scenario: All Items: Should see pagination for more than 60 items
    Given I am a quickly registered user on intl
    And I have 61 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I quickly add skus 1 to 61 to my wishlist via the WOAS API
    When I navigate to the All Items page for wishlist
    Then I should see 60 wishlist items
    And I should see the view more button

  @COM-1461 @slow @sanity
  Scenario: All Items: Should be able to load next page for wishlist that fits on one and a bit pages
    Given I am a quickly registered user on intl
    And I have 61 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I quickly add skus 1 to 61 to my wishlist via the WOAS API
    When I navigate to the All Items page for wishlist
    And I click the view more button
    Then I should see 61 wishlist items
    And I should not see the view more button

  @COM-1461 @COM-1941 @slow
  Scenario: All Items: Should be able to load next page for wishlist that fits on exactly two pages
    Given I am a signed in user on intl
    And I have 120 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I quickly add skus 1 to 120 to my wishlist via the WOAS API
    When I navigate to the All Items page for wishlist
    And I click the view more button
    Then I should see 120 wishlist items
    And I should not see the view more button

  @COM-1461 @COM-1941 @slow
  Scenario: All Items: Should be able to load next page for wishlist that fits on two and a bit pages
    Given I am a quickly registered user on intl
    And I have 121 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I quickly add skus 1 to 121 to my wishlist via the WOAS API
    When I navigate to the All Items page for wishlist
    And I click the view more button
    Then I should see 120 wishlist items
    And I should see the view more button

  ##############
  # Default List
  ##############

  @COM-1461 @slow
  Scenario: Default List: Should see pagination for more than 60 items
    Given I am a quickly registered user on intl
    And I browse to the default wishlist page
    And I have 61 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I quickly add skus 1 to 61 to my wishlist via the WOAS API
    When I navigate to the Default Wishlist page
    Then I should see 60 wishlist items
    And I should see the view more button

  @COM-1461 @slow
  Scenario: Default List: Should be able to load next page for wishlist that fits on one and a bit pages
    Given I am a quickly registered user on intl
    And I browse to the default wishlist page
    And I have 61 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I quickly add skus 1 to 61 to my wishlist via the WOAS API
    When I navigate to the Default Wishlist page
    And I click the view more button
    Then I should see 61 wishlist items
    And I should not see the view more button

  @COM-1461 @COM-1941 @slow
  Scenario: Default List: Should be able to load next page for wishlist that fits on exactly two pages
    Given I am a quickly registered user on intl
    And I browse to the default wishlist page
    And I have 120 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I quickly add skus 1 to 120 to my wishlist via the WOAS API
    When I navigate to the Default Wishlist page
    And I click the view more button
    Then I should see 120 wishlist items
    And I should not see the view more button

  @COM-1461 @COM-1941 @slow
  Scenario: Default List: Should be able to load next page for wishlist that fits on two and a bit pages
    Given I am a quickly registered user on intl
    And I browse to the default wishlist page
    And I have 121 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I quickly add skus 1 to 121 to my wishlist via the WOAS API
    When I navigate to the Default Wishlist page
    And I click the view more button
    Then I should see 120 wishlist items
    And I should see the view more button

  ######################
  # Custom Wishlist Page
  ######################

  @COM-1461 @slow
  Scenario: Custom List: Should see pagination for more than 60 items
    Given I am a quickly registered user on intl
    And I have 61 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I quickly add skus 1 to 61 to my wishlist via the WOAS API
    When I view that specific wishlist via its direct url
    Then I should see 60 wishlist items
    And I should see the view more button

  @COM-1461 @slow
  Scenario: Custom List: Should be able to load next page for wishlist that fits on one and a bit pages
    Given I am a quickly registered user on intl
    And I have 61 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I quickly add skus 1 to 61 to my wishlist via the WOAS API
    When I view that specific wishlist via its direct url
    And I click the view more button
    Then I should see 61 wishlist items
    And I should not see the view more button

  @COM-1461 @COM-1941 @slow
  Scenario: Custom List: Should be able to load next page for wishlist that fits on exactly two pages
    Given I am a quickly registered user on intl
    And I have 120 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I quickly add skus 1 to 120 to my wishlist via the WOAS API
    When I view that specific wishlist via its direct url
    And I click the view more button
    Then I should see 120 wishlist items
    And I should not see the view more button

  @COM-1461 @COM-1941 @slow
  Scenario: Custom List: Should be able to load next page for wishlist that fits on two and a bit pages
    Given I am a quickly registered user on intl
    And I have 121 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I quickly add skus 1 to 121 to my wishlist via the WOAS API
    When I view that specific wishlist via its direct url
    And I click the view more button
    Then I should see 120 wishlist items
    And I should see the view more button