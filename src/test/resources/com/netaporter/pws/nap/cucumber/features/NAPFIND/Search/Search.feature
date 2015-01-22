@nap @Channelized @napfind
Feature: Basic search features

   # need to go to home page to get around selenium CurrentPageUnknownException
  Background:
    Given I go to Home page


  Scenario Outline: Search for valid keywords like 'Tops', 'gown', 'blouse', 'evening', 'underwear', 'party', 'beach' should return correct results
    When I search for a <keyword> keyword
    Then I see expected search results

  Examples:
    |  keyword   |
    |   Tops     |
    |   gown     |
    |   beach    |


  Scenario: Search for work goes to workwear
    When I search for a work keyword
    Then I see the workwear shop page


  Scenario Outline: Search for invalid keywords should returns invalid search page
    When I search for a <keyword> keyword
    Then I see the invalid search page

    Examples:
     | keyword    |
     | hfjkdshf   |
     | 5673       |
     | uh113      |
     | hjg76h     |


  Scenario Outline: A search for a product by pid should go to that product page which contains correct stock info
    And I find an <stockInfo> product pid in product listing page
    When I search for the pid
    Then I am taken to the correct <stockInfo> product details page
    And the product page shows <stockInfo>

    Examples:
      |   stockInfo       |
      |   IN_STOCK        |
      |   SOLD_OUT        |

  @responsive
  Scenario: Auto Suggest redirects to search listing page
    Given I navigate to IN_STOCK product page for category CLOTHING using url parameters with country gb language en and device d
    When I type for the Burberry keyword in search box
    When I click on first auto suggest option
    Then search listing page is displayed

  @responsive
  Scenario: Auto Suggest display no result message when search for no matched word
    Given I navigate to IN_STOCK product page for category CLOTHING using url parameters with country gb language en and device d
    When I type for the 123456 keyword in search box
    Then no search keyword match display

