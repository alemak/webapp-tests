@napfind @nap
  Feature: Multiselect navigation level 3 filtering


    @nap @Channelized
  Scenario Outline: selecting two level 3 navigation categories works correctly
    Given I goto <navLevel2> in the <navLevel1> product listing page
    When I randomly select 2 navigation level3 categories
    Then the page is partly refreshed
    And the Url only contains required level3 categories
    And only required level3 categories are selected in the left navigation
    And the page header is updated with the number of products

  Examples:
    |navLevel1  | navLevel2   |
    | Clothing  |  Dresses    |
    | Clothing  |  Pants      |
    |  Bags     |  Clutch Bags|
    |  Shoes    |  Boots      |


  @nap @Channelized
  Scenario Outline: selecting two level 3 navigation categories the unselecting one of them works correctly
    Given I goto <navLevel2> in the <navLevel1> product listing page
    When I randomly select 2 navigation level3 categories
    And I randomly un-select 1 level3 category
    Then the page is partly refreshed
    And the Url only contains required level3 categories
    And only required level3 categories are selected in the left navigation
    And the page header is updated with the number of products

  Examples:
    |navLevel1  | navLevel2   |
    | Clothing  |  Dresses    |
    | Clothing  |  Pants      |
    |  Bags     |  Clutch Bags|
    |  Shoes    |  Boots      |


  @nap @Channelized
  Scenario Outline: select level 2 navigation categories after selecting level 3 category should unselect lvl3 cat
    Given I goto <navLevel2> in the <navLevel1> product listing page
    When I randomly select 2 navigation level3 categories
    And I randomly select a different level2 navigation category
    Then the page is fully refreshed
    And the subcategory selection is cleared
    And the page header is updated with the number of products

  Examples:
    |navLevel1  | navLevel2   |
    | Clothing  |  Dresses    |
    | Clothing  |  Pants      |
    |  Bags     |  Clutch Bags|
    |  Shoes    |  Boots      |


  @nap @Channelized
  Scenario Outline: filtering by designer after multiselecting lvl 3 categories should work correctly
    Given I goto <navLevel2> in the <navLevel1> product listing page
    When I randomly select 2 navigation level3 categories
    And I filter by designer
    Then only products from that designer should be displayed
    And only required level3 categories are selected in the left navigation

  Examples:
    |navLevel1  | navLevel2   |
    | Clothing  |  Dresses    |
    | Clothing  |  Pants      |
    |  Bags     |  Clutch Bags|
    |  Shoes    |  Boots      |


  @nap @Channelized
  Scenario Outline: filtering by color after multiselecting lvl 3 categories should work correctly
    Given I goto <navLevel2> in the <navLevel1> product listing page
    When I randomly select 2 navigation level3 categories
    And I filter by color
    Then only required level3 categories are selected in the left navigation
    And I go to a product page from the selected filtered list
    And the color name should be included on that page

  Examples:
    |navLevel1  | navLevel2    |
    | Clothing  |  Dresses     |
    | Clothing  |  Pants       |
    |  Bags     |  Clutch Bags |
    |  Shoes    |  Boots       |

  Scenario Outline: filtering by size after multiselecting lvl 3 categories should work correctly
    Given I goto <navLevel2> in the <navLevel1> product listing page
    And I randomly select 2 navigation level3 categories
    When I filter by size
    Then the page is partly refreshed
    Then only products with the correct size will be displayed

  Examples:
    |navLevel1  | navLevel2    |
    | Clothing  |  Dresses     |
    | Clothing  |  Pants       |
    |  Bags     |  Clutch Bags |
    |  Shoes    |  Boots       |


   @nap @Channelized
  Scenario: selecting all level 3 categories, going to the second page then unselecting 1 should return the user to the first page of results
    Given I am on a multiple-page category listing page
    And I randomly select a multiple-page different level2 navigation category
    When I select all the navigation level 3 categories
    And I click the Next link of page navigation
    And I randomly un-select 1 level3 category
    And I am on the first page of products



