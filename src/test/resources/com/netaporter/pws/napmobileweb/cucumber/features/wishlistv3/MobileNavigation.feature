@wishlistV3 @desktop
Feature: Mobile Wishlist V3 Navigation
  As a WishlistV3 user
  I want a navigation menu
  So that I can navigate across the different wishlist pages easily

  @nap @Channelized @COM-1754
  Scenario: Navigate to legacy mobile wishlist from header (non-migrated user)
    Given I am a quickly registered user on intl
    When I click the wishlist link on the mobile header
    Then I am taken to the legacy mobile wish list page

  @COM-1754 @sanity
  Scenario: Navigate to mobile wishlist from header (migrated user)
    Given I am a quickly registered user on intl
    When I click the wishlist link on the mobile header
    Then I am sent to the mobile all items wish list page

  @COM-1385 @sanity
  Scenario: Mobile Wishlist Navigation
    Given I am a quickly registered user on intl
    And I create several wishlists
    When I view one of those mobile lists
    Then I should be able to navigate to one of the mobile lists I created
    And I should be able to navigate to the mobile All Items page
    And I should be able to navigate to the mobile Default Wishlist page

  @COM-1500 @COM-1497 @COM-1687 @COM-2189
  Scenario: Mobile Wishlist Navigation Order
    Given I am a quickly registered user on intl
    And I create several wishlists
    When I view one of those mobile lists
    Then The first menu item should be for the mobile all items page
    And The second menu item should be for the mobile alerts page
    And The third menu item should be for the mobile closet page
    And The fourth menu item should be for the mobile default wishlist
    And The last menu item should be the mobile create list button
    And The mobile menu items names for the user lists should appear in alphabetical order
    And clicking the default wishlist menu item should go to the mobile default wishlist
    And clicking a user list menu item should go to the appropriate mobile list
    And clicking the all items menu item should go to the mobile all items page
