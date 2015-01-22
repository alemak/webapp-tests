@wishlistV3 @desktop
Feature: Wishlist V3 Add to Wishlist
  As a NAP user
  I would like to add items in my size to my wishlist from the product page
  So I can spend all my money on pay day

  @COM-1695 @COM-1755
  Scenario: Add multi-sized item to default Wish List from http product page
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible SHOES sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    When I select sku 1's size and click the new Add to Wish List button
    Then the new Wish List popup should appear
    And the new Wish List popup should contain the correct product details of item 1
    And I click Add to Wish List on the popup
    And the new Wish List popup should disappear
    And I click the 'VIEW WISH LIST' link
    And I should see the wishlist page title called Wish List
    And wishlist item 1 should be on the page

  @COM-1695 @COM-1739 @sanity
  Scenario: Add multi-sized item to default Wish List from https product page
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible SHOES sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the https product page
    When I select sku 1's size and click the new Add to Wish List button
    Then the new Wish List popup should appear
    And the new Wish List popup should contain the correct product details of item 1
    And I click Add to Wish List on the popup
    And the new Wish List popup should disappear
    And I click the 'VIEW WISH LIST' link
    And I should see the wishlist page title called Wish List
    And wishlist item 1 should be on the page

  @COM-1695 @sanity
  Scenario: Add single-sized item to default Wish List from product page
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible BAGS sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    When I click the new Add to Wish List button
    Then the new Wish List popup should appear
    And the new wishlist popup does not have a size selector
    And the new Wish List popup should contain the correct product details of item 1
    And I click Add to Wish List on the popup
    And the new Wish List popup should disappear
    And I browse to the default wishlist page
    And wishlist item 1 should be on the page

  @COM-1695
  Scenario: Pre select the product size and see it in Wishlist popup
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    When I select sku 1's size and click the new Add to Wish List button
    Then the new Wish List popup should appear
    And the new Wish List popup should contain the correct product details of item 1
    And sku number 1's size has been pre chosen in the Wish List popup

  @COM-1695 @COM-1755 @sanity
  Scenario: Add item to custom wishlist from product page
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible LINGERIE sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    And I create a new wishlist via the WOAS API called CustomList
    When I select sku 1's size and click the new Add to Wish List button
    Then the new Wish List popup should appear
    And the new Wish List popup should contain the correct product details of item 1
    And sku number 1's size has been pre chosen in the Wish List popup
    And I select 'CustomList' from the Add to Wishlist popup wishlist menu
    And I click Add to Wish List on the popup
    And the new Wish List popup should disappear
    And I click the 'VIEW WISH LIST' link
    And I should see the custom wishlist page title called CustomList
    And wishlist item 1 should be on the page

  @COM-1695
  Scenario: Click Add to Wishlist without choosing size
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible LINGERIE sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    And I click the new Add to Wish List button
    And the new Wish List popup should appear
    And the new Wish List popup should contain the correct product details of item 1
    When I click Add to Wish List on the popup
    Then I should see a 'Please select a size' error message in the popup

  @COM-1695 @COM-1793 @COM-1804
  Scenario: Create a wishlist using the 'Create New' button and add an item
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible LINGERIE sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    And I click the new Add to Wish List button
    And the new Wish List popup should appear
    And the new Wish List popup should contain the correct product details of item 1
    And I select the size for sku 1 in the new Add to Wish List popup
    When I select 'Create New' from the Add to Wishlist popup wishlist menu
    And I enter a new wishlist name of NewName in the create box and click create
    And the wishlist NewName should be automatically selected
    And I click Add to Wish List on the popup
    And the new Wish List popup should disappear
    And I click the 'VIEW WISH LIST' link
    And I should see the custom wishlist page title called NewName
    Then wishlist item 1 should be on the page

  @COM-1695
  Scenario: Click away from Add To Wishlist popup
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible LINGERIE sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    And I click the new Add to Wish List button
    And the new Wish List popup should appear
    And the new Wish List popup should contain the correct product details of item 1
    When I click some whitespace on the page
    Then the new Wish List popup should disappear

  @COM-1695
  Scenario: Click X button on Add To Wishlist popup
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible LINGERIE sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    And I click the new Add to Wish List button
    And the new Wish List popup should appear
    And the new Wish List popup should contain the correct product details of item 1
    When I click the close button on the wishlist popup
    Then the new Wish List popup should disappear

  @COM-1695 @COM-1793
  Scenario: Create a wishlist using 25 character name truncates to 24
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible LINGERIE sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    And I click the new Add to Wish List button
    And the new Wish List popup should appear
    And I select 'Create New' from the Add to Wishlist popup wishlist menu
    When I enter a new wishlist name of 1234567890123456789012345 in the create box and click create
    Then I select '123456789012345678901234' from the Add to Wishlist popup wishlist menu

  @COM-1695
  Scenario: Wishlist popup menu items are in alphabetical order
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Raspberry
    And I create a new wishlist via the WOAS API called Apple
    And I create a new wishlist via the WOAS API called banana
    And I store the details of products 1 to 1 from the product page
    When I select sku 1's size and click the new Add to Wish List button
    And the new Wish List popup should appear
    And I select 'Create New' from the Add to Wishlist popup wishlist menu
    And I enter a new wishlist name of carrot in the create box and click create
    And I select 'Create New' from the Add to Wishlist popup wishlist menu
    And I enter a new wishlist name of Açaí in the create box and click create
    And I click the close button on the wishlist popup
    And I select sku 1's size and click the new Add to Wish List button
    Then The wishlist popup menu items custom lists should appear in alphabetical order

  @COM-1546 @COM-1958 @COM-1977
  Scenario: Add to wishlist when not logged in - opted in
    Given I am a quickly registered user on intl
    And I sign out
    And I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    When I select sku 1's size and click the new Add to Wish List button
    And I am taken to the NAP SIGN IN page
    And I sign in with the correct details on the Sign In page
    Then the new Wish List popup should appear
    And the new Wish List popup should contain the correct product details of item 1
    And sku number 1's size has been pre chosen in the Wish List popup
    And I click Add to Wish List on the popup
    And the new Wish List popup should disappear
    And I click the 'VIEW WISH LIST' link
    And I should see the wishlist page title called Wish List
    And wishlist item 1 should be on the page

  @COM-1547 @known-failure
  Scenario: Add to wishlist when not registered or logged in
    Given I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    When I select sku 1's size and click the new Add to Wish List button
    And I am taken to the NAP SIGN IN page
    And I click 'Register Now' on the sign in page
    And I fill in the registration form with valid details and click sign up
    Then I select sku 1's size and click the new Add to Wish List button
    And the new Wish List popup should appear
    And the new Wish List popup should contain the correct product details of item 1
    And sku number 1's size has been pre chosen in the Wish List popup
    And I click Add to Wish List on the popup
    And the new Wish List popup should disappear
    And I click the 'VIEW WISH LIST' link
    And I should see the wishlist page title called Wish List
    And wishlist item 1 should be on the page

  @COM-2228
  Scenario: add to wishlist on new design sold out product details page
    Given I am a quickly registered user on intl
    And I have 1 SOLD_OUT and visible BAGS sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    When I select the add to wishlist button on the sold out page
    And I click Add to Wish List on the popup
    And the new Wish List popup should disappear
    And I click the wishlist link on the header
    Then wishlist item 1 should be on the page

