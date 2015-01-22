@wishlistV3 @desktop
Feature: Share wishlist
  As a Wishlist user
  I want to be able to share my curated wishlists
  So that I can show my collections to my friends

  @COM-2000 @sanity
  Scenario: Set a custom wishlist to shared and view it as a different user
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Shared
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I store the details of products 1 to 1 from the https product page
    And I view that specific wishlist via its direct url
    And the privacy padlock is showing in the wishlist header
    And the wishlist nav menu should contain a privacy symbol on the wishlist with name Shared
    When I click the Manage Wish List button
    And I select the Shared radio button on the Manage Wishlist popup and Save Changes for Wishlist Shared
    And the privacy padlock is NOT showing in the wishlist header
    And the wishlist nav menu should NOT contain a privacy symbol on the wishlist with name Shared
    And I sign out
    And I view that specific wishlist via its direct url
    Then I should see the wishlist header called Shared
    And I should not see the Manage Wish List Button
    And I should see items 1 to 1 in the correct chronological order with the correct details

  @COM-2000
  Scenario: Set the default wishlist to shared and view it as a different user
    Given I am a quickly registered user on intl
    And I browse to the default wishlist page
    And the privacy padlock is showing in the wishlist header
    And the wishlist nav menu should contain a privacy symbol on the wishlist with name Wish List
    When I click the Manage Wish List button
    And I select the Shared radio button on the Manage Wishlist popup and Save Changes for Wishlist Wish List
    And the privacy padlock is NOT showing in the wishlist header
    And the wishlist nav menu should NOT contain a privacy symbol on the wishlist with name Wish List
    And I sign out
    And I view that specific wishlist via its direct url
    Then I should see the wishlist header called Wish List
    And I should not see the Manage Wish List Button

  @COM-2000
  Scenario: All-items page is not shareable
    Given I am a quickly registered user on intl
    And I visit the home page
    When I click the wishlist link on the header
    Then I should not see the Manage Wish List Button
    And I should not see the Share Wish List button

  @COM-2000
  Scenario: Attempt to view a wishlist that is private
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called NotShared
    And I view that specific wishlist via its direct url
    And I sign out
    And I directly hit the signin URL with query params redirect = 1 and httpsRedirect = true
    And I am a quickly registered user on intl
    When I view that specific wishlist via its direct url
    Then I should be on This Page Cannot Be Found page
    And I sign out
    When I view that specific wishlist via its direct url
    Then I am on the sign in page

  @COM-2000
  Scenario: Check the padlocks on the product details wishlist popup
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Shared
    And I view that specific wishlist via its direct url
    And I click the Manage Wish List button
    And I select the Shared radio button on the Manage Wishlist popup and Save Changes for Wishlist Shared
    And I create a new wishlist via the WOAS API called NotShared
    And I store the details of products 1 to 1 from the https product page
    When I select sku 1's size and click the new Add to Wish List button
    Then the wishlist popup menu item called Shared shows as Shared
    And the wishlist popup menu item called NotShared shows as Private

  @COM-2000
  Scenario: Check the Get Link in the Share menu
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Shared
    And I view that specific wishlist via its direct url
    And I click the Share wish list button
    And I wait 3 seconds
    When I select the Get Link menu option
    And the Share Wish List popup should appear
    And I click the Share Wish List confirmation button in the Share Wishlist popup
    Then the link in the overlay matches the address of the wishlist
    And I click the close button in the get wishlist link popup
    And the privacy padlock is NOT showing in the wishlist header