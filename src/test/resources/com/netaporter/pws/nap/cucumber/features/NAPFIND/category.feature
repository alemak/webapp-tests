@nap @napfind
Feature: Category Filtering, Sorting, Back and Forward navigation: functional, url and sorting checks


   @Channelized
  Scenario Outline: filtering by color
    Given I pick a <level1Category> category in the product listing page
    When I filter by color
    Then the page header is updated with the number of products
    And I go to a product page from the selected filtered list
    And the color name should be included on that page

  Examples:
    | level1Category |
    | Shoes          |
    | Clothing       |
    | Bags           |
    | Accessories    |
    | Boutiques      |
    | Search         |


  @Channelized
  Scenario Outline: filter by color on boutiques and search
    Given I am currently on a <categoryType> listing page
    When I filter by color
    Then the page header is updated with the number of products
    And I go to a product page from the selected filtered list
    And the color name should be included on that page

  Examples:
    | categoryType   |
    | boutiques      |
    | search         |


   @Channelized
  Scenario Outline: filtering by designer
    Given I pick a <level1Category> category in the product listing page
    When I filter by designer
    Then only products from that designer should be displayed
    And the page header is updated with the number of products

  Examples:
    | level1Category    |
    | Shoes             |
    | Clothing          |
    | Bags              |
    | Accessories       |


   @Channelized
  Scenario Outline: Selecting a category non-AJAX check
    Given I am currently on a <product> listing page
    When I randomly select a level2Category
    Then the page is fully refreshed

  Examples:
    | product   |
    | designer  |
    | custom    |
    | category  |


   @Channelized
  Scenario Outline: filtering by designer check AJAX
    Given I am currently on a <product> listing page
    When I filter by <filterType>
    Then the page is partly refreshed

  Examples:
    | product   | filterType |
    | category  | designer   |
    | custom    | designer   |
    | category  | color      |
    | custom    | color      |


    @Channelized
  Scenario Outline: filtering by designer check URL
    Given I am currently on a <product> listing page
    When I filter by designer
    Then the URL will be updated with the designer filter

  Examples:
    | product   |
    | category  |
    | custom    |


  @Channelized
  Scenario: filtering by size check AJAX
  Given I goto Dresses in the Clothing product listing page
  When I filter by size
  Then the page is partly refreshed


  @Channelized
  Scenario: filtering by size check URL
  Given I goto Dresses in the Clothing product listing page
  When I filter by size
  Then the URL will be updated with the size filter


  @Channelized
  Scenario Outline: filtering by color check URL
  Given I am currently on a <product> listing page
  When I filter by color
  Then the URL will be updated with the color filter

  Examples:
    | product   |
    | designer  |
    | category  |
    | custom    |


  @Channelized
  Scenario Outline: filter by color or designer, navigate back, check URL
    Given I am currently on a <product> listing page
    And I filter by <filterType>
    And the URL will be updated with the <filterType> filter
    When I select the back button on the browser
    Then the page is partly refreshed
    And the <filterType> code should be removed from the url

  Examples:
    | product   |  filterType |
    | category  |  color      |
    | custom    |  color      |
    | designer  |  color      |
    | category  |  designer   |
    | custom    |  designer   |


  @Channelized
  Scenario: filter by size, navigate back, check URL
    Given I goto Dresses in the Clothing product listing page
    And I filter by size
    Then the URL will be updated with the size filter
    When I select the back button on the browser
    Then the page is partly refreshed
    And the size code should be removed from the url


  @Channelized
  Scenario Outline: select the "View all" link on any product listing page except for "What's new"
    Given I am on a multiple-page <product> listing page
    When I click the View all link of page navigation
    Then the page is partly refreshed
    And pagination is invisible

  Examples:
    | product   |
    | category  |
    | designer  |
    |  custom   |


  @Channelized
  Scenario Outline: view Product/Outfit pages check AJAX and functionality
    Given I am currently on a <product> listing page
    When I click the <view1> of imageview link
    Then the page is not partly or fully refreshed
    And the correct <view1> image is displayed
    When I click the <view2> of imageview link
    Then the page is not partly or fully refreshed
    And the correct <view2> image is displayed

  Examples:
    |   product  |    view1  |  view2   |
    |  designer  |   outfit  | product  |
    |  custom    |   outfit  | product  |
    |  category  |   outfit  | product  |
    |  boutiques |   outfit  | product  |
    |  search    |   outfit  | product  |


   @Channelized
  Scenario Outline: view Product/Outfit pages check URL
    Given I am on a multiple-page <product> listing page
    When I click the <view1> of imageview link
    Then the URL is updated with the correct <view1> parameter
    When I click the <view2> of imageview link
    Then the URL is updated with the correct <view2> parameter

  Examples:
    |   product  |    view1  |  view2   |
#    |  designer  |   outfit  | product  |
#    |  custom    |   outfit  | product  |
#    |  category  |   outfit  | product  |
    |  boutiques |   outfit  | product  |
    |  search    |   outfit  | product  |

#     these tests often fail due to data issues, rollovers are incorrect in test environments for subcategories of accessories page
   @Channelized
  Scenario Outline: correct rollover image is displayed depending on the product subcategory page
    Given I goto <subcategory> in the <category> product listing page
    When I click the Outfit of imageview link
    Then the correct rollover image <parameter> is loaded

  Examples:
    | subcategory       | category    | parameter |
#    |  beauty cases     | accessories |   _e1_    |
#    |  belts            | accessories |   _fr_    |
#    |  books            | accessories |   _fr_    |
#    |  collars          | accessories |   _fr_    |
#    |  gloves           | accessories |   _fr_    |
#    |  hats             | accessories |   _ou_    |
#    |  homeware         | accessories |   _fr_    |
#    |  key fobs         | accessories |   _fr_    |
#    |  scarves          | accessories |   _fr_    |
#    |  stationery       | accessories |   _fr_    |
#    |  technology       | accessories |   _e1_    |
    #travel should have no rollover?
#    |  travel           | accessories |   _fr_    |
#    |  umbrellas        | accessories |   _bk_    |
#    |  wallets          | accessories |   _e1_    |
#    |  watches          | accessories |   _fr_    |
    |  jeans           | clothing    |   _fr_    |
    |                   | lingerie    |   _ou_    |
    |  all              | shoes       |   _fr_    |
#    |  beachwear        | clothing    |   _fr_    |
#
#
#  @Channelized
#  Scenario Outline: correct rollover image is displayed on level3 categories depending on the product subcategory page
#    Given I am on a <subcategory> subcategory of a <category> page
#    When I go to <level3Category> level3 category
#    Then the correct rollover image <parameter> is loaded
#
#  Examples:
#    | level3Category  | subcategory      | category    | parameter |
#    |  headbands      | Hair Accessories | accessories |  _ou_     |
#    |  headpieces     | Hair Accessories | accessories |  _fr_     |


    @Channelized
  Scenario Outline: sorted by order check URL and AJAX
    Given I am currently on a <product> listing page
    When I sort by <sortOrder>
    Then the page header is updated with the number of products
    And the page is partly refreshed
    And the products are displayed in correct order
    And the URL is updated with the correct sorting parameter

  Examples:
    | product   |  sortOrder        |
    | designer  |    Price High     |
    | designer  |    Price Low      |
    | category  |    Price High     |
    | category  |    Price Low      |
    | custom    |    Price High     |
    | custom    |    Price Low      |

   @Channelized
  Scenario Outline: sorted, navigate back, check URL and AJAX
    Given I am currently on a <product> listing page
    When I sort by <sortOrder>
    And I select the back button on the browser
    Then the page is partly refreshed
    And the sort code should be removed from the url
    And the sorting is lost

  Examples:
    | product   |  sortOrder        |
    | designer  |    Price High     |
    | designer  |    Price Low      |
    | category  |    Price High     |
    | category  |    Price Low      |
    | custom    |    Price High     |
    | custom    |    Price Low      |


   @Channelized
  Scenario Outline: can go to previous/next page AJAX
    Given I am on a multiple-page <product> listing page
    When I click the <pageLinkOne> link of page navigation
    Then the page is partly refreshed
    And I see the page change accordingly
    When I click the <pageLinkTwo> link of page navigation
    Then the page is partly refreshed
    And I see the page change accordingly

  Examples:
    | product   |   pageLinkOne   |   pageLinkTwo       |
    | designer  |     Next        |      Previous       |
    | designer  |     Next        |      View all       |
    | category  |     Next        |      Previous       |
    | category  |     Next        |      View all       |
    | custom    |     Next        |      Previous       |
    | custom    |     Next        |      View all       |
#    | whats_new |     Next        |      Previous       |
#    | whats_new |     Next        |      View all       |


   @Channelized
  Scenario Outline: go to next and previous product pages, check URL
    Given I am on a multiple-page <product> listing page
    When I click the <pageLinkOne> link of page navigation
    Then the URL will be updated with the page number
    When I click the <pageLinkTwo> link of page navigation
    Then the URL will be updated with the page number

  Examples:
    | product   |  pageLinkOne    |  pageLinkTwo    |
    | designer  |   Next          |   Previous      |
    | category  |   Next          |   Previous      |
    | custom    |   Next          |   Previous      |
#    | whats_new |   Next          |   Previous      |

   @Channelized
  Scenario Outline: Previous and Next is hidden in the first page and last page, respectively
    Given I am on a multiple-page <product> listing page
    When I navigate to the <numOfPage> page
    Then the <pageLink> link is unselected

  Examples:
    | product   |   numOfPage   |   pageLink      |
    | category  |     First     |   Previous      |
    | category  |     Last      |   Next          |
    | designer  |     Last      |   Next          |
    | designer  |     First     |   Previous      |
    | custom    |     Last      |   Next          |
    | custom    |     First     |   Previous      |
#    | whats_new |     Last      |   Next          |
#    | whats_new |     First     |   Previous      |


   @Channelized
  Scenario Outline: price sort order remembered when paginating
    Given I am on a multiple-page <product> listing page
    When I sort by <sortOrder>
    And I click the <pageLink> link of page navigation
    Then the price order is not changed
 #commented out some view all tests to reduce the duration of the suite
  Examples:
    | product    | sortOrder          |   pageLink    |
    | category   |   Price High       |   Next        |
#    | category   |   Price High       |   View all    |
    | category   |   Price Low        |   View all    |
    | category   |   Price Low        |   Next        |
    | designer   |   Price High       |   Next        |
#    | designer   |   Price High       |   View all    |
    | designer   |   Price Low        |   View all    |
    | designer   |   Price Low        |   Next        |
    | custom     |   Price High       |   Next        |
#    | custom     |   Price High       |   View all    |
    | custom     |   Price Low        |   View all    |
    | custom     |   Price Low        |   Next        |


   @Channelized
  Scenario Outline: price sort order remembered when filtering
    Given I am currently on a <product> listing page
    When I sort by <sortOrder>
    And I filter by <filterType>
    Then the price order is not changed

  Examples:
    | product    | sortOrder        | filterType |
    | category   |   Price High     | color      |
    | category   |   Price Low      | color      |
    | designer   |   Price High     | color      |
    | designer   |   Price Low      | color      |
    | custom     |   Price High     | color      |
    | custom     |   Price Low      | color      |
    | category   |   Price High     | designer   |
    | category   |   Price Low      | designer   |
    | custom     |   Price High     | designer   |
    | custom     |   Price Low      | designer   |


  @Channelized
  Scenario Outline: filtering preserved when sorting by price
    Given I am currently on a <product> listing page
    When I filter by <filterType>
    And I sort by <sortOrder>
    Then the filter is preserved

  Examples:
    | product    | sortOrder        | filterType |
    | category   |   Price High     | color      |
    | category   |   Price Low      | color      |
    | designer   |    Price High    | color      |
    | designer   |   Price Low      | color      |
    | custom     |   Price High     | color      |
    | custom     |   Price Low      | color      |
    | category   |   Price High     | designer   |
    | category   |   Price Low      | designer   |
    | custom     |   Price High     | designer   |
    | custom     |   Price Low      | designer   |



  @Channelized
  Scenario Outline: filtering preserved when paginating
    Given I am on a multiple-page <product> listing page
    When I select all filters of <filterType> by creating a new Url
    And I click the <pageLink> link of page navigation
    Then the filter is preserved

  Examples:
    | product    | pageLink   | filterType |
    | category   |   Next     | color      |
    | category   |   View all | color      |
    | designer   |   Next     | color      |
    | designer   |   View all | color      |
    | custom     |   Next     | color      |
    | custom     |   View all | color      |
    | category   |   Next     | designer   |
    | category   |   View all | designer   |
    | custom     |   Next     | designer   |
    | custom     |   View all | designer   |


  Scenario Outline: Open Color filter
    Given I pick a <level1Category> category in the product listing page
    When I randomly select a level2Category
    Then color filter is open

  Examples:
     | level1Category|
     | Clothing      |
     | Bags          |
     | Shoes         |
     | Accessories   |


  Scenario Outline: Open Size filter
    Given I goto <subcategory> in the <category> product listing page
    Then size filter is open

  Examples:
    | category    | subcategory   |
    | Clothing    |   Dresses     |
    | Shoes       |   Pumps       |