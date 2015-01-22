@wishlistV3 @desktop
Feature: Wishlist V3 Alerts Page
  As a WishlistV3 user
  I want to be able to see a list of all items across my wishlists filtered by my active alerts
  So that I can find items easily

  @COM-2152 @sanity
  Scenario: View Alerted items
    Given I am a quickly registered user on intl
    And I have 1 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I have 1 ON_SALE and visible SHOES skus that I force to have similar stock in the db
    And I have 1 LOW_STOCK and visible LINGERIE skus that I force to have similar stock in the db
    And I have 1 SOLD_OUT and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 2 to my wishlist via the WOAS API
    And I store the details of products 1 to 2 from the product page
    And I create a new wishlist via the WOAS API called Wishlist2
    And I add skus 3 to 4 to my wishlist via the WOAS API
    And I store the details of products 3 to 4 from the product page
    And I insert an alert in to the database for item 2 with alert type SALE
    And I insert an alert in to the database for item 3 with alert type LOW
    When I navigate to the Alerts page for wishlist
    Then I should see the correct wishlist page title
    And I should see the correct wishlist header
    And I should see 2 wishlist items
    And I should see items 2 to 3 in the correct chronological order with the correct details
    And I should not see items 1 to 1 on the wishlist page
    And I should not see items 4 to 4 on the wishlist page
    And I should not see the view more button

  @COM-2218
  Scenario: Check Alert slugs in custom list
    Given I am a quickly registered user on intl
    And I have 1 LOW_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I have 1 ON_SALE and visible BAGS skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 3 to my wishlist via the WOAS API
    And I insert an alert in to the database for item 1 with alert type LOW
    And I insert an alert in to the database for item 2 with alert type BACK
    And I insert an alert in to the database for item 3 with alert type SALE
    When I view that specific wishlist via its direct url
    And I should see 3 wishlist items
    And wishlist item 1 has the slug 'LOW STOCK'
    And wishlist item 2 has the slug 'BACK IN STOCK'
    And wishlist item 3 has the slug 'ON SALE'

  @COM-2218 @COM-2225
  Scenario: Check Alerts and their slugs on the Alerts page
    Given I am a quickly registered user on intl
    And I have 1 LOW_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I have 2 ON_SALE and visible BAGS skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Wishlist1
    And I add skus 1 to 4 to my wishlist via the WOAS API
    And I insert an alert in to the database for item 1 with alert type LOW
    And I insert an alert in to the database for item 2 with alert type SALE
    And I insert an alert in to the database for item 3 with alert type SALE_BACK
    And I insert an alert in to the database for item 4 with alert type BACK
    When I navigate to the Alerts page for wishlist
    And I should see 4 wishlist items
    And the wishlist item in position 1 has the slug 'LOW STOCK'
    And the wishlist item in position 2 has the slug 'BACK IN STOCK'
    And the wishlist item in position 3 has the slug 'BACK IN STOCK'
    And the wishlist item in position 4 has the slug 'ON SALE'

  @COM-2218
  Scenario: Check Alert slugs in default wish list
    Given I am a quickly registered user on intl
    And I browse to the default wishlist page
    And I have 1 LOW_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I have 1 ON_SALE and visible BAGS skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I add skus 1 to 3 to my wishlist via the WOAS API
    And I insert an alert in to the database for item 1 with alert type LOW
    And I insert an alert in to the database for item 2 with alert type SALE
    And I insert an alert in to the database for item 3 with alert type BACK
    And I browse to the default wishlist page
    And I should see 3 wishlist items
    And wishlist item 1 has the slug 'LOW STOCK'
    And wishlist item 2 has the slug 'ON SALE'
    And wishlist item 3 has the slug 'BACK IN STOCK'

  @COM-2218
  Scenario: Check Alert slugs in all-items page list
    Given I am a quickly registered user on intl
    And I browse to the default wishlist page
    And I have 1 LOW_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I have 1 ON_SALE and visible BAGS skus that I force to have similar stock in the db
    And I have 1 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
    And I add skus 1 to 3 to my wishlist via the WOAS API
    And I insert an alert in to the database for item 1 with alert type LOW
    And I insert an alert in to the database for item 2 with alert type SALE
    And I insert an alert in to the database for item 3 with alert type BACK
    And I navigate to the All Items page for wishlist
    And I should see 3 wishlist items
    And wishlist item 1 has the slug 'LOW STOCK'
    And wishlist item 2 has the slug 'ON SALE'
    And wishlist item 3 has the slug 'BACK IN STOCK'
