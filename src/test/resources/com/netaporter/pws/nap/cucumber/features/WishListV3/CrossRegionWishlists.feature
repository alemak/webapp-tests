@wishlistV3 @desktop
Feature: Wishlist V3 Cross region wishlists
  As a globe trotting independent woman
  I want to be able to access the same wishlist no mater which country I am in
  So that I can can have a consistent user experience all over the world


  @COM-1735 @COM-1626
  Scenario Outline: Add to one region and view on another
    Given I am a quickly registered user on <startRegion>
    And I have an IN_STOCK and visible CLOTHING sku on all channels that I force to have similar stock in the db for the current channel
    And I create a new wishlist via the WOAS API called <startRegion> - <destinationCountry>
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I am on change country page
    And I change my country to <destinationCountry>
    And I store the details of products 1 to 1 from the product page
    And I view that specific wishlist via its direct url
    Then I should see items 1 to 1 in the correct chronological order with the correct details

  @always
  Examples:
    |startRegion  |destinationCountry |
    |intl         |Australia          |
    |intl         |China              |
    |intl         |United States      |
    |intl         |France             |
    |am           |United Kingdom     |
    |am           |Hong Kong          |
    |apac         |United Kingdom     |
    |apac         |United States      |

  @sanity
  Examples:
    |startRegion  |destinationCountry |
    |intl         |Hong Kong          |

  @COM-2235 @COM-1626
  Scenario Outline: Add a product that has not been uploaded and sold out on another channels and view it on those channels
    Given I am a quickly registered user on <startRegion>
    When I have a visible CLOTHING sku IN_STOCK on INTL and SOLD_OUT on AM and NOT_UPLOADED on APAC that I force to have similar stock in the db for the current channel
    And I create a new wishlist via the WOAS API called <startRegion> - <destinationCountry>
    And I add skus 1 to 1 to my wishlist via the WOAS API
    And I store the details of products 1 to 1 from the product page
    And I am on change country page
    And I change my country to <destinationCountry>
    And I view that specific wishlist via its direct url
    Then wishlist item 1 should be on the page

  Examples:
    |startRegion  |destinationCountry |
    |intl         |United States      |
    |intl         |Hong Kong          |
    |am           |United Kingdom     |
    |am           |Hong Kong          |


    @COM-2250
    Scenario Outline:prevent accessing product details page if not available or sold out on current channel
      Given I am a quickly registered user on <startRegion>
        When I have a visible BAGS sku IN_STOCK on INTL and IN_STOCK on AM and NOT_UPLOADED on APAC that I force to have similar stock in the db for the current channel
      And I create a new wishlist via the WOAS API called <startRegion> - <destinationCountry>
      And I add skus 1 to 1 to my wishlist via the WOAS API
      And I am on change country page
      And I change my country to <destinationCountry>
      And I view that specific wishlist via its direct url
      Then wishlist item 1 should be on the page
      And I click the first wishlist product thumbnail
      And the product details slider is displayed
      Then the View More Details link in the wishlist product details slider should not be displayed

    Examples:
      |startRegion  |destinationCountry |
      |intl         |Hong Kong          |
      |am           |Hong Kong          |


