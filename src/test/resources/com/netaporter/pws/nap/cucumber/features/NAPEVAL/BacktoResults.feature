@Channelized @napeval
Feature: Get back to the previous screen with Back to results link on the product page


   Scenario Outline: Back to results is displayed
    Given I goto <subcategory> in the <category> product listing page
     When I go to a product page from the selected filtered list
     Then the Back to results link is displayed

    Examples:
    | category      | subcategory |
    | Clothing      |  Dresses    |
    | Bags          |  Clutch Bags|
    | Accessories   |  Collars    |


  Scenario Outline: Back to results is displayed when filtering
    Given I goto <subcategory> in the <category> product listing page
    When I filter by color
    And I go to a product page from the selected filtered list
    Then the Back to results link is displayed

  Examples:
    | category      | subcategory      |
    | Clothing      |  Dresses         |
    | Bags          |  Clutch Bags     |
    | Accessories   |  Fine_Jewelry    |

  Scenario Outline: Filtering is preserved when clicking on the Back to results link
    Given I goto <subcategory> in the <category> product listing page
    And I randomly select 2 navigation level3 categories
    When I filter by <filterType>
    And I sort by <sortOrder>
    And I go to a product page from the selected filtered list
    When I select the Back to results link
    Then I reach the <category> listing page
    And only required level3 categories are selected in the left navigation
    And the filter is preserved
    And the price order is not changed

  Examples:
    | category    | subcategory   | filterType    | sortOrder    |
#    | Clothing    |  Dresses      |  designer     |  Price High  |   designer filter is not working
    | Bags        |  Clutch Bags  |  color        |  Price Low   |
    | Accessories |  Fine_Jewelry |  color        |  Price High  |


  Scenario Outline: Back to results for different types of listing pages
    Given I am currently on a <category> listing page
    And I filter by <filterType>
    And I sort by <sortOrder>
    And I go to a product page from the selected filtered list
    When I select the Back to results link
    And the filter is preserved
    And the price order is not changed

    Examples:
      | category   | filterType    | sortOrder    |
      | designer   |  color        |  Price Low   |
      | boutiques  |  color        |  Price High  |


  Scenario: Shopping bag Back to Shopping link displayed
    Given I visit the home page
    And I add a product to my shopping bag
    And I select the product from the shopping bag
    When I select the Back to results link
    Then I should be on the Shopping Bag page

#  Failing: Wishlist stack is missing in stream dave environment

#  Scenario Outline: Wishlist Back to Wishlist link is displayed
#    Given I am a seaview registered default user
#    When I sign in with the correct details
#    When I goto <subcategory> in the <category> product listing page
#    And I go to a product page from the selected filtered list
#    And I click the new Add to Wish List button
#    When I click on view wishlist
#    When I click on the more information link in the wishlist
#    And I select Go to Product page
#    When I select the Back to results link
#    Then I am taken to the wish list page
#
#  Examples:
#  | category     | subcategory   |
#  | Bags         | Clutch Bags   |
#  | Accessories  | Wallets       |