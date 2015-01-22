@wishlistV3 @desktop
Feature: WishlistV3 Closet
  As a net-a-porter buyer
  I want to see the products I have purchased in the past
  So that I can match, re-order and admire my clothes

  @COM-1430
  Scenario: Make a single purchase and view the closet
    Given I am a quickly registered user on intl
    And I sign out
    And I sign in with the correct details
    And I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I store the details of products 1 to 1 from the https product page
    And I add product 1 to my shopping bag
    And I click 'Proceed to Purchase' on the product details page
    And I go to Shipping page as a signed in user and enter my address
    And I proceed to the Payment page
    And I pay by VISA_CREDIT_CARD
    And Order Confirmation page is displayed
    And I navigate to the Closet page for wishlist
    Then I should see those items in the correct chronological order with the correct details

  @COM-2301
  Scenario: Make a single HKD purchase and view the closet
    Given I am a quickly registered user on apac
    And I sign out
    And I sign in with the correct details
    And I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I am on change country page
    And I change my country to Hong Kong
    And I store the details of products 1 to 1 from the https product page
    And I add product 1 to my shopping bag
    And I click 'Proceed to Purchase' on the product details page
    And I go to Shipping page as a signed in user and enter my address
    And I proceed to the Payment page
    And I pay by VISA_CREDIT_CARD
    And Order Confirmation page is displayed
    And I navigate to the Closet page for wishlist
    Then I should see those items in the correct chronological order with the correct details

  @COM-2159 @COM-1921
  Scenario: Examine an empty closet text and make sure you can't share and delete it
    Given I am a quickly registered user on intl
    And I navigate to the Closet page for wishlist
    Then I should see the correct empty closet message
    And I should not see the Manage Wish List Button
    And I should not see the Share Wish List button

  @COM-1922
  Scenario: Filter closet items by classification
    Given I am a quickly registered user on intl
    And I sign out
    And I sign in with the correct details
    And I have 1 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible BAGS sku that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible SHOES sku that I force to have similar stock in the db
    And I go to the product page for item 1
    And I add product 1 to my shopping bag
    And I store the details of products 2 to 2 from the product page
    And I add product 2 to my shopping bag
    And I go to the product page for item 3
    And I add product 3 to my shopping bag
    And I click 'Proceed to Purchase' on the product details page
    And I go to Shipping page as a signed in user and enter my address
    And I proceed to the Payment page
    And I pay by VISA_CREDIT_CARD
    And Order Confirmation page is displayed
    When I navigate to the Closet page for wishlist
    And I filter my wishlist items by Bags
    And the filter option Bags is underlined
    Then I should see those items in the correct chronological order with the correct details
