@wishlistV3 @desktop
Feature: Wishlist V3 Create Wishlist
  As a WishlistV3 user
  I want to be able to create a wishlist
  So that I can curate a collection of products

  @COM-1497
  Scenario: Clicking The Create New Menu Item
    Given I am a quickly registered user on intl
    And I navigate to the All Items page for wishlist
    When I click the wish list menu and count the menu items
    Then The create wishlist form should be visible

  @COM-1497 @sanity
  Scenario: Creating a new wishlist with click
    Given I am a quickly registered user on intl
    And I navigate to the All Items page for wishlist
    And I click the wish list menu and count the menu items
    When I click to submit the create wishlist form with name Hamsters
    Then the nav menu should now contain that wishlist

  @COM-1497 @sanity
  Scenario: Creating a new wishlist with enter
    Given I am a quickly registered user on intl
    And I navigate to the All Items page for wishlist
    And I click the wish list menu and count the menu items
    When I press enter to submit the create wishlist form with name Hamsters
    Then the nav menu should now contain that wishlist

  @COM-1497
  Scenario: Clicking away closes the mega menu
    Given I am a quickly registered user on intl
    And I visit the home page
    And I click the wishlist link on the header
    And I wait 3 seconds
    And I click the wish list menu and count the menu items
    When I click some whitespace on the page
    Then The create wishlist form should not be visible

  @COM-1497
  Scenario: Check character limit
    Given I am a quickly registered user on intl
    And I navigate to the All Items page for wishlist
    And I click the wish list menu and count the menu items
    When I press enter to submit the create wishlist form with name twentyfive-character-long
    And I click the wishlist navigation menu item called twentyfive-character-lon
    Then I should see the correct custom wishlist page title

  @COM-1497
  Scenario: Submiting with empty name textbox with click should have no effect
    Given I am a quickly registered user on intl
    And I navigate to the All Items page for wishlist
    And I click the wish list menu and count the menu items
    When I click to submit the create wishlist form with no name
    Then the number of wishlist menu items does not increase
    And The create wishlist form should be visible

  @COM-1497
  Scenario: Submiting with empty name textbox with enter should have no effect
    Given I am a quickly registered user on intl
    And I navigate to the All Items page for wishlist
    And I click the wish list menu and count the menu items
    When I press enter to submit the create wishlist form with no name
    Then the number of wishlist menu items does not increase
    And The create wishlist form should be visible

  @COM-1497
  Scenario: Creating two wishlists with the same name
    Given I am a quickly registered user on intl
    And I navigate to the All Items page for wishlist
    And I click the wish list menu and count the menu items
    And I click to submit the create wishlist form with name Hamsters
    When I click to submit the create wishlist form with name Hamsters
    Then the nav menu should now contain 2 wishlists with the name Hamsters

  @COM-1497
  Scenario: Alphabetical order of wishlist items after creating a new item
    Given I am a quickly registered user on intl
    And I create several wishlists
    And I view one of those lists
    And I click the wish list menu and count the menu items
    When I click to submit the create wishlist form with name Vacation Time
    Then The menu items names for the user lists should appear in alphabetical order

  @COM-1720
  Scenario: Whitespace wishlist name should be ignored
    Given I am a quickly registered user on intl
    And I navigate to the All Items page for wishlist
    And I click the wish list menu and count the menu items
    When I press enter to submit the create wishlist form with whitespace for the name
    Then the number of wishlist menu items does not increase
    And The create wishlist form should be visible

  @COM-1718
  Scenario: Empty wishlist name should be ignored
    Given I am a quickly registered user on intl
    And I navigate to the All Items page for wishlist
    And I click the wish list menu and count the menu items
    When I submit the create wishlist form with no name
    Then the number of wishlist menu items does not increase
    And The create wishlist form should be visible

#  @COM-1899 This is no longer the case
#  Scenario: Creating a wishlist from the suggestions menu
#    #Given I am a quickly registered user on intl
#    Given I am a quickly registered user on intl
##    And I navigate to the All Items page for wishlist
#    And I click the wish list menu and count the menu items
#    When I click the suggested wishlist name 'Vacation'
#    Then the nav menu should now contain 1 wishlist with the name Vacation
#    And the suggested wishlist name 'Vacation' is no longer listed
#
#  @COM-1899 This is no longer the case
#  Scenario: Suggested wishlist names are not listed if already created
#    #Given I am a quickly registered user on intl
#    Given I am a quickly registered user on intl
##    And I navigate to the All Items page for wishlist
#    And I click the wish list menu and count the menu items
#    When I click to submit the create wishlist form with name Vacation
#    Then the suggested wishlist name 'Vacation' is no longer listed
#
#  @COM-1899 This is no longer the case
#  Scenario: Suggested wishlists list disappears after choosing 4 lists
#    Given I am a quickly registered user on intl
##    And I navigate to the All Items page for wishlist
#    And I click the wish list menu and count the menu items
#    When I click the suggested wishlist name 'Weekend Trip'
#    And I click the suggested wishlist name 'My Birthday'
#    And I click the suggested wishlist name 'Vacation'
#    And I click the suggested wishlist name 'Wedding List'
#    And I click the suggested wishlist name 'Favorites'
#    And I click the suggested wishlist name 'Off the Runway'
#    Then the suggested wishlists list disappears
#    And The menu items names for the user lists should appear in alphabetical order

#  @COM-1899 This is no longer the case
#  Scenario: Suggested wishlists list disappears after creating 4 lists
#    #Given I am a quickly registered user on intl
#    Given I am a quickly registered user on intl
##    And I navigate to the All Items page for wishlist
#    And I click the wish list menu and count the menu items
#    When I click to submit the create wishlist form with name List1
#    And I click to submit the create wishlist form with name List2
#    And I click to submit the create wishlist form with name List3
#    And I click to submit the create wishlist form with name List4
#    Then the suggested wishlists list disappears

  @COM-2242
  Scenario: Creating 30 new wishlists with enter
    Given I am a quickly registered user on intl
    And I navigate to the All Items page for wishlist
    And I click the wish list menu and count the menu items
    When I press enter to submit the create wishlist form 30 times with different names
    Then the nav menu should now contain the wishlist Wishlist 1
    And the nav menu should now contain the wishlist Wishlist 30