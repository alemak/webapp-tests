@wishlistV3 @desktop
Feature: Wishlist V3 All Items Page
  As a WishlistV3 user
  I want to be able to see a list of all items across my wishlists
  So that I can find items easily

  @COM-834 @COM-1461 @sanity
  Scenario: View All Items
    Given I am a quickly registered user on intl
    And I have 5 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 3 to my wishlist via the WOAS API
    And I store the details of products 1 to 2 from the product page
    And I create a new wishlist via the WOAS API called Wishlist2
    And I add skus 4 to 5 to my wishlist via the WOAS API
    And I store the details of products 4 to 5 from the product page
    And I add skus 3 to 3 to my wishlist via the WOAS API
    And I store the details of products 3 to 3 from the product page
    When I navigate to the All Items page for wishlist
    Then I should see the correct wishlist page title
    And I should see the correct wishlist header
    And I should see the wishlist items ordered by date added descending without duplicates
    And I should see the correct size for each item
    And I should not see the view more button

