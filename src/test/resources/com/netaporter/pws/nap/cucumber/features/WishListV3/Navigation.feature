@wishlistV3 @desktop
Feature: Wishlist V3 Navigation
  As a WishlistV3 user
  I want a navigation menu
  So that I can navigate across the different wishlist pages easily

  ## Legacy behaviour
  @nap @Channelized @COM-1754 @known-failure
  Scenario: Navigate to legacy wishlist from header (non-migrated user)
    Given I am a quickly registered user on intl
    And I visit the home page
    And the header wishlist link should be visible
    When I click the wishlist link on the header
    Then I am taken to the wish list page

  @COM-1754 @sanity
  Scenario: Navigate to wishlist from header for migrated user
    Given I am a quickly registered user on intl
    And I visit the home page
    And the header wishlist link should be visible
    When I click the wishlist link on the header
    Then I am sent to the all items wish list page

  @COM-1385 @sanity
  Scenario: Wishlist Navigation
    Given I am a quickly registered user on intl
    And I create several wishlists
    When I view one of those lists
    Then I should be able to navigate to one of the lists I created
    And The correct menu item should be ticked in the menu
    And I should be able to navigate to the All Items page
    And The correct menu item should be ticked in the menu
    And I should be able to navigate to the Default Wishlist page
    And The correct menu item should be ticked in the menu

  @COM-1500 @COM-1497
  Scenario: Wishlist Navigation Order
    Given I am a quickly registered user on intl
    And I create several wishlists
    When I view one of those lists
    Then The first menu item should be for the all items page
    And The second menu item should be for alerts
    And The fourth menu item should be for the default wishlist
    And The last menu item should be the create list button
    And The menu items names for the user lists should appear in alphabetical order
    And clicking the default wishlist menu item should go to the default wishlist
    And clicking a user list menu item should go to the appropriate list
    And clicking the all items menu item should go to the all items page


