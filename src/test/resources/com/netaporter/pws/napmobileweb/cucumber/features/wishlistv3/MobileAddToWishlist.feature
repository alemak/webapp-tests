@mobileweb
Feature: Mobile Wishlist V3 Add To Wishlist
  As a signed in mobile NAP user
  I want to add NAP items to my Wish List
  So that I can buy them later

  @COM-1728
  Scenario: Clicking the ADD TO WISHLIST button changes the button text to SELECT A LIST and displays the existing wishlists
    Given I submit valid details on the mobile registration form
    And I have 1 IN_STOCK and visible SHOES sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the mobile product page
    And I create several wishlists
    When I select sku 1's size and click the new mobile Add to Wish List button
    Then the mobile ADD TO WISHLIST button's text changes to 'SELECT A LIST'
    And a list of my mobile wishlists are displayed with the default wishlist first
    And the 'Create New' wishlist option is displayed
    And clicking the 'SELECT A LIST' button reverts the button text back to 'ADD TO WISHLIST'

  @COM-1728
  Scenario: Check Add To Wishlist Popup details are correct and can close the popup
    Given I submit valid details on the mobile registration form
    And I create a new wishlist via the WOAS API called Mob Custom List
    And I have 1 IN_STOCK and visible SHOES sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the mobile product page
    When I choose to add item 1 to the mobile wishlist called Mob Custom List
    Then the Added To Wishlist Pop Up appears with the correct details for item 1
    And and I can close the Added To Wishlist popup successfully

  @COM-1728 @sanity
  Scenario: The 'Added To Wishlist' link on the Added To Wishlist popup goes to the correct wishlist
    Given I submit valid details on the mobile registration form
    And I have 1 IN_STOCK and visible SHOES sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the mobile product page
    And I choose to add item 1 to the mobile default wishlist
    When I click the 'Added To Wishlist' link on the mobile Added To Wishlist popup
    Then I should be on the mobile wishlist page called Wish List
    And wishlist item 1 should be on the mobile page

  @COM-1728
  Scenario: The 'Show Wishlist' link on the Added To Wishlist popup goes to the correct wishlist
    Given I submit valid details on the mobile registration form
    And I have 1 IN_STOCK and visible SHOES sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the mobile product page
    And I choose to add item 1 to the mobile default wishlist
    When I click the 'Show Wishlist' link on the mobile Added To Wishlist popup
    Then I should be on the mobile wishlist page called Wish List
    And wishlist item 1 should be on the mobile page

  @COM-1728
  Scenario: Add a multi-sized item to the default Wish List from https product page
    Given I submit valid details on the mobile registration form
    And I have 1 IN_STOCK and visible SHOES sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the mobile product page
    When I choose to add item 1 to the mobile default wishlist
    And I browse to the default mobile wishlist page
    Then wishlist item 1 should be on the mobile page

  @COM-1728
  Scenario: Add a single-sized item to the default Wish List from https product page
    Given I submit valid details on the mobile registration form
    And I have 1 IN_STOCK and visible BAGS sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the mobile product page
    When I choose to add item 1 to the mobile default wishlist
    And I browse to the default mobile wishlist page
    Then wishlist item 1 should be on the mobile page

  @COM-1728
  Scenario: Add a Sold Out item to the default Wish List from https product page
    Given I submit valid details on the mobile registration form
    And I have 1 SOLD_OUT and visible BAGS sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the mobile product page
    When I choose to add item 1 to the mobile default wishlist
    And I browse to the default mobile wishlist page
    Then wishlist item 1 should be on the mobile page

  @COM-1728
  Scenario: Add a Low Stock item to the default Wish List from https product page
    Given I submit valid details on the mobile registration form
    And I have 1 LOW_STOCK and visible SHOES sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the mobile product page
    When I choose to add item 1 to the mobile default wishlist
    And I browse to the default mobile wishlist page
    Then wishlist item 1 should be on the mobile page

  @COM-1728
  Scenario: Add a multi-sized item to the default Wish List from http product page
    Given I submit valid details on the mobile registration form
    And I have 1 IN_STOCK and visible SHOES sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the http mobile product page
    When I choose to add item 1 to the mobile default wishlist
    And I browse to the default mobile wishlist page
    Then wishlist item 1 should be on the mobile page

  @COM-1728 @sanity
  Scenario: Add a multi-sized item to a custom Wish List from https product page
    Given I submit valid details on the mobile registration form
    And I create a new wishlist via the WOAS API called Mob Custom List
    And I have 1 IN_STOCK and visible SHOES sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the mobile product page
    When I choose to add item 1 to the mobile wishlist called Mob Custom List
    And I click the 'Show Wishlist' link on the mobile Added To Wishlist popup
    Then I should be on the mobile wishlist page called Mob Custom List
    And wishlist item 1 should be on the mobile page

  @COM-1728
  Scenario: Click Add to Wishlist without choosing size
    Given I submit valid details on the mobile registration form
    And I have 1 IN_STOCK and visible SHOES sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the mobile product page
    When I click the new mobile Add to Wish List button
    Then I should see a 'Please select a size' error message on the mobile product details page

  @COM-1728 @COM-1916 @COM-1958
  Scenario: Add to wishlist when not logged in - opted in
    Given I submit valid details on the mobile registration form
    And I sign out of the mobile site
    And I have 1 IN_STOCK and visible SHOES sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the mobile product page
    When I choose to add item 1 to the mobile default wishlist when not signed in
    And I am taken to the mobile NAP SIGN IN page
    And I sign in with the correct details on the mobile Sign In page
    Then the mobile product details page is displayed for item 1
    And I choose to add item 1 to the mobile default wishlist
    And I click the 'Added To Wishlist' link on the mobile Added To Wishlist popup
    And I should be on the mobile wishlist page called Wish List
    Then wishlist item 1 should be on the mobile page

  @COM-1728
  Scenario: Can create a new wishlist 24 characters long when adding to wishlist
    Given I submit valid details on the mobile registration form
    And I have 1 IN_STOCK and visible SHOES sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the mobile product page
    And I select sku 1's size and click the new mobile Add to Wish List button
    When I create a new wishlist called 24CharactersLongWishlist and hit enter
    And the list of mobile wishlists are in alphabetical order
    And the wishlist called 24CharactersLongWishlist is in the list of mobile wishlists
    #And I select the wishlist called 24CharactersLongWishlist to add item 1 to
    And I click the 'Show Wishlist' link on the mobile Added To Wishlist popup
    And I should be on the mobile wishlist page called 24CharactersLongWishlist
    And wishlist item 1 should be on the mobile page

  @COM-1728
  Scenario: Can create a new wishlist with non standard characters when Adding To Wishlist
    Given I submit valid details on the mobile registration form
    And I have 1 IN_STOCK and visible SHOES sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the mobile product page
    And I select sku 1's size and click the new mobile Add to Wish List button
    When I create a new wishlist called ?<:{!@£$%^&*( and hit enter
    And the list of mobile wishlists are in alphabetical order
    And the wishlist called ?<:{!@£$%^&*( is in the list of mobile wishlists
    #And I select the wishlist called ?<:{!@£$%^&*( to add item 1 to
    And I click the 'Show Wishlist' link on the mobile Added To Wishlist popup
    And I should be on the mobile wishlist page called ?<:{!@£$%^&*(
    And wishlist item 1 should be on the mobile page

  @COM-1728
  Scenario: Can create two wishlists with the same name
    Given I submit valid details on the mobile registration form
    And I create a new wishlist via the WOAS API called Test List 1
    And I have 1 IN_STOCK and visible SHOES sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the mobile product page
    And I select sku 1's size and click the new mobile Add to Wish List button
    When I create a new wishlist called Test List 1 and hit enter
    And the list of mobile wishlists are in alphabetical order
    And the wishlist called Test List 1 is in the list of mobile wishlists twice

