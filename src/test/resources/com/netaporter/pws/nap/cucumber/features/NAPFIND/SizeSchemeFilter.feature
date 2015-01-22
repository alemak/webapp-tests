@napfind @nap @Channelized
Feature: Selecting a size scheme for shoes category listing page


  Scenario Outline: Compare US,UK,Italy/Europe,France shoe scheme have same number of sizes
    Given I goto <subcategory> in the Shoes product listing page
    When I select <sizescheme> from size filter
    Then the page is not partly or fully refreshed
    And size scheme should have same number of sizes
    And the URL is updated with correct <sizeschemecode> param
    And size scheme drop down is visible

  Examples:
    | subcategory  |   sizescheme        |   sizeschemecode  |
    |   Boots      |      US             |        US         |
    |   Pumps      |      UK             |        UK         |
    |   Sandals    |      Italy/Europe   |        IT         |
    |   Flat Shoes |      France         |        FR         |
    |   Sneakers   |      UK             |        UK         |


  Scenario Outline: Verify single selection of size is unaltered when selecting different shoe size scheme
    Given I goto <subcategory> in the Shoes product listing page
    And I randomly select 1 navigation level3 categories
    When I filter by size
    And I select <sizescheme> from size filter
    Then the page is partly refreshed
    And the URL will be updated with the size filter
    And the URL is updated with correct <sizeschemecode> param
    And size scheme drop down is visible

  Examples:
    | subcategory  |   sizescheme        |   sizeschemecode  |
    |   Boots      |      US             |        US         |
    |   Pumps      |      UK             |        UK         |
    |   Sandals    |      Italy/Europe   |        IT         |
    |   Flat Shoes |      France         |        FR         |
    |   Sneakers   |      UK             |        UK         |


  Scenario Outline: Verify multiple selection of size is unaltered when selecting different shoe size scheme
    Given I goto <subcategory> in the Shoes product listing page
    When I randomly select 2 navigation level3 categories
    When I filter by size
    And I select <sizescheme> from size filter
    Then the page is partly refreshed
    And the URL will be updated with the size filter
    And the URL is updated with correct <sizeschemecode> param
    And size scheme drop down is visible

  Examples:
  | subcategory  |   sizescheme        |   sizeschemecode  |
  |   Boots      |      US             |        US         |
  |   Pumps      |      UK             |        UK         |
  |   Sandals    |      Italy/Europe   |        IT         |
  |   Flat Shoes |      France         |        FR         |
  |   Sneakers   |      UK             |        UK         |


  Scenario Outline: Verify shoes selection of listing page display shoe scheme filter
    Given I am currently on a <product> listing page
    And I select SHOES from left navigation
    And I select <sizescheme> from size filter
    Then the URL is updated with correct <sizeschemecode> param
    And size scheme drop down is visible

  Examples:
    | product     |    sizescheme        |   sizeschemecode  |
    | custom      |        UK            |        UK         |


  Scenario Outline: Verify size scheme upon selecting left navigation facets on listing page
    Given I am currently on a <product> listing page
    And I select SHOES from left navigation
    When I select <sizescheme> from size filter
    And I filter by <filterType>
    Then the URL is updated with correct <sizeschemecode> param
    And size scheme drop down is visible

  Examples:
    | product    | filterType  |   sizescheme        |   sizeschemecode  |
    | custom     |   designer  |      UK             |        UK         |
    | custom     |   color     |      Italy/Europe   |        IT         |


  Scenario Outline: Verify size scheme upon selecting outfit/product view on listing page
    Given I am currently on a <product> listing page
    And I select SHOES from left navigation
    When I select <sizescheme> from size filter
    And I click the outfit of imageview link
    Then the URL is updated with correct <sizeschemecode> param
    And size scheme drop down is visible


  Examples:
    | product    |    sizescheme       |   sizeschemecode  |
    | custom     |      UK             |        UK         |
    | custom     |      Italy/Europe   |        IT         |


  Scenario Outline: : Verify size scheme dropdown appear on search page
    Given I am currently on a search listing page
    And I search for a leather shoes keyword
    And I select SHOES from left navigation
    When I select <sizescheme> from size filter
    And I click the outfit of imageview link
    And I sort by <sortOrder>
    And I click the <pageLink> link of page navigation
    Then the URL is updated with correct <sizeschemecode> param
    And size scheme drop down is visible

  Examples:
   | sortOrder    |  sizescheme         |   sizeschemecode  |   pageLink    |
   | Price High   |      US             |        US         |    Next       |
   | Price Low    |      UK             |        UK         |    View all   |


  Scenario Outline: Verify size scheme upon selecting sortby filter on listing page
    Given I am currently on a <product> listing page
    And I select SHOES from left navigation
    When I select <sizescheme> from size filter
    And I sort by <sortOrder>
    Then the URL is updated with correct <sizeschemecode> param
    And size scheme drop down is visible


  Examples:
    | product    |  sortOrder      |   sizescheme        |   sizeschemecode  |
    | custom     |   Price Low     |      UK             |        UK         |
    | custom     |   Price High    |      Italy/Europe   |        IT         |


  Scenario Outline: Verify size scheme for shoes category when top filters are selected
      Given I pick a Shoes category in the product listing page
      When I select <sizescheme> from size filter
      And I click the outfit of imageview link
      And I sort by <sortOrder>
      And I click the <pageLink> link of page navigation
      Then the URL is updated with correct <sizeschemecode> param
      And size scheme drop down is visible

    Examples:
      | sortOrder    |   sizescheme        |   sizeschemecode  |   pageLink    |
      | Price High   |      US             |        US         |    Next       |
      | Price Low    |      UK             |        UK         |    View all   |


  Scenario Outline: Verify size scheme for shoes category when left navigation facets are selected
    Given I pick a Shoes category in the product listing page
    When I select <sizescheme> from size filter
    And I filter by <filterType>
    Then the URL is updated with correct <sizeschemecode> param
    And size scheme drop down is visible

  Examples:
    |   filterType    |   sizescheme        |   sizeschemecode  |
    |  designer       |      US             |        US         |
    |  color          |      UK             |        UK         |
