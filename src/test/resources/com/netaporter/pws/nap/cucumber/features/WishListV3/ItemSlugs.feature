@wishlistV3 @desktop
Feature: Wishlist V3 slugging
  As a registered NAP customer
  I want to be able to see the status of the items in my wishlist
  So that I can make an informed decision to purchase them

  @COM-1459 @COM-1870
  Scenario Outline: Slugged items on custom list
    Given I am a quickly registered user on intl
    And I have 1 <state> and visible CLOTHING sku that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called SLUGS
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I view that specific wishlist via its direct url
    Then item 1 should have a '<slug>' slug

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
    And I browse to the default wishlist page
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I view that specific wishlist via its direct url
    Then item 1 should have a '<slug>' slug

  Examples:
    |state     |slug     |
    |ON_SALE   |On Sale  |
    |LOW_STOCK |Low Stock|
    |IN_STOCK  |No Slug  |
#    |SOLD_OUT  |Sold Out |

  @COM-1459 @COM-1870 @sanity
  Scenario Outline: Slugged items on all-items list
    Given I am a quickly registered user on intl
    And I have 1 <state> and visible CLOTHING sku that I force to have similar stock in the db
    And I browse to the default wishlist page
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I navigate to the All Items page for wishlist
    Then item 1 should have a '<slug>' slug

  Examples:
    |state     |slug     |
    |ON_SALE   |On Sale  |
    |LOW_STOCK |Low Stock|
    |IN_STOCK  |No Slug  |
#    |SOLD_OUT  |Sold Out |

  @COM-1459 @COM-1870 @sanity
  Scenario: Sold Out items on all-items list
    Given I am a quickly registered user on intl
    And I have a visible CLOTHING sku SOLD_OUT on INTL and SOLD_OUT on AM and SOLD_OUT on APAC that I force to have similar stock in the db for the current channel
    And I browse to the default wishlist page
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I navigate to the All Items page for wishlist
    Then item 1 should have a 'Sold Out' slug

  @COM-1527
  Scenario: Sold out items that are available in other region shows limited availability slug
    Given I am a quickly registered user on intl
    And I have a visible BAGS sku SOLD_OUT on INTL and IN_STOCK on AM and IN_STOCK on APAC that I force to have similar stock in the db for the current channel
    And I have a visible CLOTHING sku SOLD_OUT on INTL and IN_STOCK on AM and IN_STOCK on APAC that I force to have similar stock in the db for the current channel
    And I create a new wishlist via the WOAS API called LimitedAvailability
    And I add skus 1 to 2 to my wishlist via the WOAS API
    When I view that specific wishlist via its direct url
    Then wishlist item 1 has the slug 'LIMITED AVAILABILITY'
    And wishlist item 2 has the slug 'LIMITED AVAILABILITY'

  @COM-1527
  Scenario: Limited Availability items' slider links to correct form
    Given I am a quickly registered user on intl
    And I have a visible BAGS sku SOLD_OUT on INTL and IN_STOCK on AM and IN_STOCK on APAC that I force to have similar stock in the db for the current channel
    And I create a new wishlist via the WOAS API called LimitedAvailability
    And I add skus 1 to 1 to my wishlist via the WOAS API
    When I view that specific wishlist via its direct url
    And I click the first wishlist product thumbnail
    And the product details slider is displayed
    And the add to bag button on the slider reads 'Check Availability'
    And I click on the add to bag button in the wishlist product details slider
    Then the customer care overlay is displayed
