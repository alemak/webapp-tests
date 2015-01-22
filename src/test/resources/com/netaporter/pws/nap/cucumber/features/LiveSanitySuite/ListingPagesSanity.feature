@napLiveSanity @napfind
Feature: ListingPageSanity.feature Main product categories should be present in the TopNav bar; Search ; Change Country Overlay

  #TOP_NAV
  Scenario: Check Clothing is in the TopNav and goes to Clothing listing page
    Given I go to Home page
    Then Clothing is present in the TopNav bar
    And Clothing is between Designers and Bags
    When I click the Clothing link from the TopNav
    Then I reach the Clothing listing page


  Scenario: Check that Whats New TopNav dropdown works correctly
    Given I go to Home page
    And Whats New is present in the TopNav bar
    When I hover over the Whats New link in the TopNav
    Then the Whats New TopNav dropdown is displayed
    When I click the first link in the WhatsNew TopNav dropdown
    Then I am taken to the NAP Whats-New listing page


  Scenario: Check that Whats New TopNav dropdown works correctly
    Given I go to Home page
    When I hover over the Clothing link from the TopNav
    Then the Clothing TopNav dropdown is displayed
    When I click the first link in the Clothing TopNav dropdown
    Then I am taken to the NAP List/All_Exclusives listing page

  #LISTING_PAGES

  #Designers AZ
  Scenario: Click on designer in top nav
   Given I go to Home page
   When I click the designers link from the TopNav
   Then I am taken to the AZ Designers page
   When I select the first designer
   Then I am taken to that designer page


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
    | category  |     Next        |      Previous       |


  Scenario Outline: filtering by designer
    Given I pick a <level1Category> category in the product listing page
    When I filter by designer
    Then only products from that designer should be displayed
    And the page header is updated with the number of products

  Examples:
    | level1Category    |
    | Clothing          |


  Scenario: filtering by size check URL
    Given I goto Dresses in the Clothing product listing page
    When I filter by size
    Then the URL will be updated with the size filter


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
    | category  |  designer   |


  Scenario Outline: Verify multiple selection of size is unaltered when selecting different shoe size scheme
    Given I goto <subcategory> in the Shoes product listing page
    When I randomly select 2 navigation level3 categories
    When I filter by size
    And I select <sizescheme> from size filter
    Then the page is partly refreshed
    And the URL will be updated with the size filter
    And size scheme drop down is visible

  Examples:
    | subcategory  |   sizescheme        |
    |   Boots      |      US             |
    |   Pumps      |      UK             |
    |   Sandals    |      Italy/Europe   |
    |   Flat Shoes |      France         |
    |   Sneakers   |      UK             |


 Scenario: Check that product information is displayed correctly in the product listing pages
   Given I go to Home page
   When I pick a Clothing category in the product listing page
   Then each product should have designer, description and price information
   And the products are displayed in the correct currency


 #SEARCH:
  Scenario Outline: Search for valid keywords like 'Tops', 'gown', 'blouse', 'evening', 'underwear', 'party', 'beach' should return correct results
    Given I go to Home page
    When I search for a <keyword> keyword
    Then I see expected search results

  Examples:
    |  keyword   |
    |   Tops     |


 #Change Country:
  Scenario Outline: Switch country from select country link overlay
    Given I am on <channel>
    And I go to Change Country page
    When I change my country to <country>
    Then the country displayed in the top nav is <country>

  Examples:
    | channel  | country  |
    | intl     | France   |


  #new listing pages
    Scenario: filtering by color
      Given I am currently on a whats_new AWS listing page
      And I select SHOES from left navigation
      When I filter by size
      Then the page header is updated with the number of products


  Scenario Outline: Back to results for different types of listing pages
    Given I goto Dresses in the Clothing product listing page
    And I filter by <filterType>
    And I sort by <sortOrder>
    And I go to a product page from the selected filtered list
    When I select the Back to results link
    And the filter is preserved
    And the price order is not changed

  Examples:
    | filterType    | sortOrder    |
    |  designer     |  Price High  |


  Scenario: multiple filtering
    Given I goto Dresses in the Clothing product listing page
    When I filter by size
    And I filter by color
    And I sort by Price High
    And I go to a product page from the selected filtered list
    When I select the Back to results link
    And the filter is preserved
    And the price order is not changed

