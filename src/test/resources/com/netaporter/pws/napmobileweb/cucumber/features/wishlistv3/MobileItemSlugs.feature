@wishlistV3 @desktop
Feature: Wishlist V3 slugging
  As a registered NAP customer
  I want to be able to see the status of the items in my wishlist
  So that I can make an informed decision to purchase them

  @COM-1459 @COM-1870 @sanity
  Scenario Outline: Slugged items on custom list
    Given I am a quickly registered user on intl
    And I have 1 <state> and visible CLOTHING sku that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called SLUGS
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I view that specific mobile wishlist via its direct url
    Then mobile item 1 should have a '<slug>' slug

  Examples:
    |state     |slug     |
    |ON_SALE   |On Sale  |
    |LOW_STOCK |Low Stock|
    |IN_STOCK  |No Slug  |
#    |SOLD_OUT  |Sold Out |

  @COM-1459 @COM-1870
  Scenario Outline: Slugged items on default list
    Given I am a quickly registered user on intl
    And I have 1 <state> and visible CLOTHING sku that I force to have similar stock in the db
    And I browse to the default mobile wishlist page
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I view that specific mobile wishlist via its direct url
    Then mobile item 1 should have a '<slug>' slug

  Examples:
    |state     |slug     |
    |ON_SALE   |On Sale  |
    |LOW_STOCK |Low Stock|
    |IN_STOCK  |No Slug  |
#    |SOLD_OUT  |Sold Out |

  @COM-1459 @COM-1870
  Scenario Outline: Slugged items on all-items list
    Given I am a quickly registered user on intl
    And I have 1 <state> and visible CLOTHING sku that I force to have similar stock in the db
    And I browse to the default mobile wishlist page
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I navigate to the mobile All Items page
    Then mobile item 1 should have a '<slug>' slug

  Examples:
    |state     |slug     |
    |ON_SALE   |On Sale  |
    |LOW_STOCK |Low Stock|
    |IN_STOCK  |No Slug  |
#    |SOLD_OUT  |Sold Out |
