@wishlistV3 @desktop
Feature: Wishlist Product Details Slider
  As a WishlistV3 user
  I want to be able to be able to quickly view some details about the wishlist item
  To inform my purchasing or re-wishlisting decisions

  @COM-2181 @sanity
  Scenario: Clicking the wishlist product thumbnail opens the product slider, the close icon works and clicking on the desinger name re-opens it
    Given I am a quickly registered user on intl
    And I have 5 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Clothing
    And I add skus 1 to 5 to my wishlist via the WOAS API
    When I view that specific wishlist via its direct url
    And I click the first wishlist product thumbnail
    Then the product details slider is displayed
    And I click the close icon in the product details slider
    Then the product details slider is not displayed
    And I click the first wishlist product designer name
    Then the product details slider is displayed

  @COM-2181
  Scenario: Information in slider match those from product details page
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page including the product name
    And I create a new wishlist via the WOAS API called MyWishlist
    And I add skus 1 to 1 to my wishlist via the WOAS API
    When I view that specific wishlist via its direct url
    And I click the first wishlist product thumbnail
    Then the product details slider is displayed
    And the product details in the slider match those from the product details page

  @COM-2181
  Scenario: Information in slider match those from product details page for a sale item
    Given I am a quickly registered user on intl
    And I have 1 ON_SALE and visible CLOTHING skus that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page including the product name
    And I create a new wishlist via the WOAS API called MySaleItemWishlist
    And I add skus 1 to 1 to my wishlist via the WOAS API
    When I view that specific wishlist via its direct url
    And I click the first wishlist product thumbnail
    Then the product details slider is displayed
    And the product details in the slider match those from the product details page

  @COM-2181
  Scenario: Information in slider match those from product details page for a sold out item and I can navigate to the product details page
    Given I am a quickly registered user on intl
    And I have 1 SOLD_OUT and visible CLOTHING skus that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page including the product name
    And I create a new wishlist via the WOAS API called MyWishlist
    And I add skus 1 to 1 to my wishlist via the WOAS API
    When I view that specific wishlist via its direct url
    And I click the first wishlist product thumbnail
    Then the product details slider is displayed
    And the product details in the slider match those from the product details page
    And I click on the View More Details button in the wishlist product details slider
    Then I am on the product details page of the first item in the wishlist

  @COM-2181
  Scenario: Add to bag from within slider
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called MyWishlist
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I view that specific wishlist via its direct url
    And I click the first wishlist product thumbnail
    And the product details slider is displayed
    And I click on the add to bag button in the wishlist product details slider
    And the shopping bag container appears and disappears
    Then wishlist item 1 should be in the shopping bag 1 time
    And the shopping bag icon shows 1 items


  # This fails sometimes as some SOLD-OUT items show as LIMITED AVAILABILITY
  @COM-2181 @known-failure
  Scenario: Add to bag from within slider sold out product
    Given I am a quickly registered user on intl
    And I have 1 SOLD_OUT and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called MyWishlist
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I view that specific wishlist via its direct url
    And I click the first wishlist product thumbnail
    And the product details slider is displayed
    When I click on the add to bag button in the wishlist product details slider
    Then the error 'Sorry, item sold out' appears
    And the shopping bag icon shows 0 items

  @COM-2181
  Scenario: Add to wishlist within slider: same size
    Given I am a quickly registered user on intl
    And I have 2 IN_STOCK and visible SHOES sku that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called MyWishlist
    And I add skus 1 to 2 to my wishlist via the WOAS API
    And I store the details of products 1 to 2 from the product page
    And I view that specific wishlist via its direct url
    And I click the first wishlist product thumbnail
    And the product details slider is displayed
    When I click on the Add to Wishlist button in the wishlist product details slider
    Then the new Wish List popup should appear
    And the new Wish List popup should contain the correct product details of item 2
    And I click Add to Wish List on the popup
    And the new Wish List popup should disappear
    And wishlist item 2 should be on the page
    ## Refreshing the page will re-order and move the re-added item to the top
    And I view that specific wishlist via its direct url
    And wishlist item 2 should be on the page


  @COM-2181
  Scenario: Add to wishlist within slider : different size
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible SHOES sku that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called MyWishlist
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I store the details of products 1 to 1 from the product page
    And I view that specific wishlist via its direct url
    And I click the first wishlist product thumbnail
    And the product details slider is displayed
    And I select another size in the product details slider
    When I click on the Add to Wishlist button in the wishlist product details slider
    Then the new Wish List popup should appear
    And I select 'MyWishlist' from the Add to Wishlist popup wishlist menu
    And I click Add to Wish List on the popup
    And the new Wish List popup should disappear
    And I wait until the wishlist has updated to have 2 items
    Then an item of the size I just selected is added to the front of the current wishlist

  @COM-2181
  Scenario: Add item to custom wishlist from product slider
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called ListB
    And I create a new wishlist via the WOAS API called ListA
    And I have 1 IN_STOCK and visible LINGERIE sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page
    # Adds to ListA
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I view that specific wishlist via its direct url
    And I wait 3 seconds
    And I click the first wishlist product thumbnail
    And the product details slider is displayed
    When I click on the Add to Wishlist button in the wishlist product details slider
    Then the new Wish List popup should appear
    And I select 'ListB' from the Add to Wishlist popup wishlist menu
    And I click Add to Wish List on the popup
    And the new Wish List popup should disappear
    And I click the wishlist navigation menu item called ListB
    And I should see the custom wishlist page title called ListB
    And wishlist item 1 should be on the page

  @COM-2181 @COM-2222
  Scenario: Add to wishlist within slider: different size sold out
    Given I am a quickly registered user on intl
    And I have 1 SOLD_OUT and visible SHOES sku that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called MyWishlist
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I store the details of products 1 to 1 from the product page
    And I view that specific wishlist via its direct url
    And I click the first wishlist product thumbnail
    And the product details slider is displayed
    And I select another size in the product details slider
    When I click on the Add to Wishlist button in the wishlist product details slider
    Then the new Wish List popup should appear
    And I select 'MyWishlist' from the Add to Wishlist popup wishlist menu
    And I click Add to Wish List on the popup
    And the new Wish List popup should disappear
    And I wait until the wishlist has updated to have 2 items
    Then an item of the size I just selected is added to the front of the current wishlist

  @COM-2181 @COM-2208
  Scenario: Information in shared wishlist slider match those from product details page
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the product page including the product name
    And I create a new wishlist via the WOAS API called MyWishlist
    And I view that specific wishlist via its direct url
    And I click the Manage Wish List button
    And I select the Shared radio button on the Manage Wishlist popup and Save Changes for Wishlist MyWishlist
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I sign out
    When I view that specific wishlist via its direct url
    And I click the first wishlist product thumbnail
    Then the product details slider is displayed
    And the product details in the slider match those from the product details page
