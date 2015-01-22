@napfind @Channelized

Feature: Category Filtering, Sorting, Back and Forward navigation: functional, url and sorting checks


  Scenario: Selecting a category non-AJAX check
    Given I am currently on a whats_new AWS listing page
    When I randomly select a level2Category
    And I filter by designer
    #Then the page is fully refreshed
    Then the URL will be updated with the designer filter


  Scenario Outline: filtering by designer check AJAX
    Given I am currently on a whats_new AWS listing page
    And I select SHOES from left navigation
    When I filter by <filterType>
    Then the page is partly refreshed

  Examples:
     | filterType |
     | size       |
     | color      |
     | designer   |



  Scenario: filtering by designer check URL
    Given I am currently on a whats_new AWS listing page
    And I select SHOES from left navigation
    When I filter by size
    Then the URL will be updated with the size filter


  Scenario: filtering by color check URL
    Given I am currently on a whats_new AWS listing page
    When I filter by color
    Then the URL will be updated with the color filter


  Scenario Outline: filter by color or designer, navigate back, check URL
    Given I am currently on a whats_new AWS listing page
    And I filter by <filterType>
    And the URL will be updated with the <filterType> filter
    When I select the back button on the browser
    Then the page is partly refreshed
    And the <filterType> code should be removed from the url

  Examples:
    |  filterType |
    |  designer      |



  Scenario: pagination on a "What's new" page
    Given I am currently on a whats_new AWS listing page
    When I click the Next link of page navigation
    Then the page is partly refreshed
    And I see the page change accordingly
    When I click the Previous link of page navigation
    Then the page is partly refreshed
    And I see the page change accordingly



  Scenario Outline: sorted by order check URL and AJAX
    Given I am currently on a whats_new AWS listing page
    When I sort by <sortOrder>
    Then the page header is updated with the number of products
    And the page is partly refreshed
    And the products are displayed in correct order
    And the URL is updated with the correct sorting parameter

  Examples:
     |  sortOrder        |
     |    Price High     |
     |    Price Low      |


  Scenario Outline: sorted, navigate back, check URL and AJAX
    Given I am currently on a whats_new AWS listing page
    When I sort by <sortOrder>
    And I select the back button on the browser
    Then the page is partly refreshed
    And the sort code should be removed from the url
    And the sorting is lost

  Examples:
     |  sortOrder        |
     |    Price High     |
     |    Price Low      |



  Scenario Outline: Previous and Next is hidden in the first page and last page, respectively
    Given I am currently on a whats_new AWS listing page
    When I navigate to the <numOfPage> page
    Then the <pageLink> link is unselected

  Examples:
     |   numOfPage   |   pageLink      |
     |     Last      |   Next          |
     |     First     |   Previous      |



  Scenario Outline: price sort order remembered when paginating
    Given I am currently on a whats_new AWS listing page
    When I sort by <sortOrder>
    And I click the <pageLink> link of page navigation
    Then the price order is not changed

  Examples:
     | sortOrder          |   pageLink    |
     |   Price High       |   Next        |
     |   Price Low        |   Next        |



  Scenario Outline: price sort order remembered when filtering
    Given I am currently on a whats_new AWS listing page
    When I sort by <sortOrder>
    And I filter by <filterType>
    Then the price order is not changed

  Examples:
    | sortOrder        | filterType |
    |   Price High     | color      |
    |   Price Low      | color      |
    |   Price High     | designer   |
    |   Price Low      | designer   |


  Scenario Outline: filtering preserved when sorting by price
    Given I am currently on a whats_new AWS listing page
    When I filter by <filterType>
    And I sort by <sortOrder>
    Then the filter is preserved

  Examples:
     | sortOrder        | filterType |
     |   Price High     | color      |
     |   Price Low      | color      |
     |   Price High     | designer   |
     |   Price Low      | designer   |



  Scenario Outline: Back to results for different types of listing pages
    Given I am currently on a whats_new AWS listing page
    And I filter by <filterType>
    And I sort by <sortOrder>
    And I go to a product page from the selected filtered list
    When I select the Back to results link
    And the filter is preserved
    And the price order is not changed

  Examples:
     | filterType    | sortOrder    |
     |  designer     |  Price High  |



  Scenario Outline: Verify size scheme upon selecting outfit/product view on listing page
    Given I am currently on a whats_new AWS listing page
    And I select SHOES from left navigation
    When I select <sizescheme> from size filter
    And I click the outfit of imageview link
    Then the URL is updated with correct <sizeschemecode> param
    And size scheme drop down is visible


  Examples:
     |    sizescheme       |   sizeschemecode  |
     |      US             |        US         |
     |      France         |        FR         |



   Scenario: pagination on a "What's new" page
     Given I am on a multiple-page whats_new AWS listing page
     When the number of products is 210 pagination is visible
     When I click the Next link of page navigation
     Then the page is partly refreshed
     #And I see the page change accordingly
     When I click the Previous link of page navigation
     Then the page is partly refreshed
     And I see the page change accordingly
