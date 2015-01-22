@nap @slp @napfind
Feature: Sale Listing Page Interaction
  As a customer
  In order to shop the sale
  I want to be able to filter and sort in the sale listing page


    @Channelized
  Scenario Outline: sort by order in a sale product listing page
    Given I visit the home page
    And I navigate to the sale landing page directly
    And I click the <link> link from the sale landing page
    And I have Default,Discount High to Low,Price High,Price Low in sorting drop-down
    When I sort by <sortOrder>
    Then the page is partly refreshed
    And the URL is updated with the correct sorting parameter
    And the products are displayed in correct order

  Examples:
    | link        |   sortOrder               |
    | Clothing    |   Discount High to Low    |
    | Clothing    |   Price High              |
    | Clothing    |   Price Low               |
    | Bags        |   Discount High to Low    |
    | Bags        |   Price High              |
    | Bags        |   Price Low               |
    | Accessories |   Discount High to Low    |
    | Accessories |   Price High              |
    | Accessories |   Price Low               |
    | Shoes       |   Discount High to Low    |
    | Shoes       |   Price High              |
    | Shoes       |   Price Low               |
    | Lingerie    |   Discount High to Low    |
    | Lingerie    |   Price High              |
    | Lingerie    |   Price Low               |
    | Sport       |   Discount High to Low    |
    | Sport       |   Price High              |
    | Sport       |   Price Low               |


   @Channelized
  Scenario Outline: validate that clicking each visible 2nd level category takes the user to a page with products
    Given I visit the home page
    And I am currently on a <category> sale listing page
    When I click on the <subcategory> in the lefthand nav
    Then I am on the <category> sale listing page
    And the url contains the <category> and <subcategory> parameters
     # Note: we may have empty result page?
    And the listing page has at least one product

  Examples:
    | category    | subcategory            |
    | Clothing    | Dresses                |
    | Bags        | Tote_Bags              |
    | Shoes       | Sandals                |
    | Accessories | Jewelry                |
    | Lingerie    | Bras                   |


   @Channelized
  Scenario Outline: check that sorting and filtering by designer after filtering by a level 2 category works correctly
    Given I am on the homepage
    And I am currently on a <category> sale listing page
    And I click on the <subcategory> in the lefthand nav
    When I sort by <sortOrder>
    And I filter by designer
    Then I am on the <category> sale listing page
    And the page is partly refreshed
    And the products are displayed in correct order
    And the url contains the <category> and <subcategory> parameters
    And only products from that designer should be displayed

    Examples:
    | category    | subcategory     | sortOrder               |
    | Clothing    | Dresses         | Price Low               |
    | Bags        | Tote_Bags       | Price High              |
    | Shoes       | Sandals         | Discount High to Low    |
    | Accessories | Technology      | Discount High to Low    |
    | Lingerie    | Bras            | Price Low               |


  @Channelized
  Scenario Outline: filtering by designer in a sale listing page
    Given I am on the homepage
    And I am currently on a <category> sale listing page
    When I filter by designer
    Then only products from that designer should be displayed
    And the page header is updated with the number of products

  Examples:
    | category    |
    | Shoes       |
    | Clothing    |
    | Bags        |
    | Accessories |
    | Lingerie    |



  @Channelized
  Scenario Outline: filtering by designer in a sale listing page
    Given I am on the homepage
    And I am currently on a <category> sale listing page
    When I filter by color
    And I go to a product page from the selected filtered list
    Then the color name should be included on that page

  Examples:
    | category    |
    | Shoes       |
    | Clothing    |
    | Bags        |
    | Accessories |
    | Lingerie    |



  @Channelized
  Scenario Outline: filtering by designer in a sale listing page
    Given I am on the homepage
    And I am currently on a <category> sale listing page
    When I filter by size
    Then the URL will be updated with the size filter
    And the page header is updated with the number of products

  Examples:
    | category       |
    | Clothing       |
    | Shoes          |
    | Accessories    |
    | Lingerie       |



  @Channelized
  Scenario Outline: pagination on a sale listing page
    Given I am on the homepage
    And I am currently on a multiple page <category> sale listing page
    When I click the Next link of page navigation
    Then the page is partly refreshed
    And I see the page change accordingly
    When I click the Previous link of page navigation
    Then the page is partly refreshed
    And I see the page change accordingly

  Examples:
    | category    |
    | Clothing    |
    | Shoes       |
    | Bags        |
    | Lingerie    |
    | Accessories |


   @Channelized
  Scenario Outline: new sale navigation functionality: switch to each level 1 category from a level 1 listing page
    Given I am on the homepage
    And I am currently on a <category> sale listing page
    When I click <target_category> from the sale navigation
    Then I am on the <target_category> sale listing page
    And the URL contains the correct <target_category> name

  Examples:
    | category    | target_category |
    | Clothing    | Shoes           |
    | Shoes       | Bags            |
    | Bags        | Lingerie        |
    | Lingerie    | Accessories     |
    | Accessories | Clothing        |


   @Channelized
  Scenario Outline: new sale navigation functionality: switch to each level 1 category from a level 2 listing page
    Given I am on the homepage
    And I am currently on a <category> sale listing page
    And I click on the <subcategory> in the lefthand nav
    When I click <target_category> from the sale navigation
    Then I am on the <target_category> sale listing page
    And the URL contains the correct <target_category> name

  Examples:
    | category    | subcategory   | target_category |
    | Clothing    | Dresses       | Shoes           |
    | Shoes       | Sandals       | Bags            |
    | Bags        | Clutch_Bags   | Lingerie        |
    | Lingerie    | Bras          | Accessories     |
    | Accessories | Hats          | Clothing        |


  @Channelized
  Scenario Outline: new sale navigation functionality: choosing sale home from the sale navigation will take the user to the sale landing page
    Given I am on the homepage
    And I am currently on a <category> sale listing page
    When I click Sale_Home from the sale navigation
    Then I am taken to the sale landing page

  Examples:
    | category    |
    | Clothing    |
    | Shoes       |
    | Bags        |
    | Lingerie    |
    | Accessories |


  @Channelized
  Scenario Outline: product rollovers work correctly in the product catalogue app
    Given I am on the homepage
    And I am currently on a <category> sale listing page
    When I mouseover the first product image
    Then the correct rollover image <parameter> is loaded for the first product

  Examples:
    | category | parameter |
    | Shoes    |  _ou_     |
    | Bags     |  _ou_     |


  @NonChannelized
  Scenario Outline: there are no product rollovers for lingerie for the following countries: SA,KW,AE,QA,EG,JO,LB,MY,SY
    Given I navigate to the sale landing page using a url param with country <country> language en and deviceType d
    And I click the Lingerie link from the sale landing page
    When I mouseover the first product image
    Then the correct rollover image _in_ is loaded for the first product

  Examples:
    | country |
    | sa      |
    | kw      |