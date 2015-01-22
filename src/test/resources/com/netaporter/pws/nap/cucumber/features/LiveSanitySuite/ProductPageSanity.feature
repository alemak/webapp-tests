@napLiveSanity
Feature: ProductPageSanity.feature Product Detail Page live sanity: add to wishlist, size and fit accordion, outfit and ymal modules, gift cards

  Scenario: Verify product has been added to wish list page
    Given I go to Home page
    And I sign in with the correct live test user
    And I pick a Bags category in the product listing page
    When I go to a product page from the selected filtered list
    When I click the new Add to Wish List button
    Then the new Wish List popup should appear
    And I click Add to Wish List on the popup
    And the new Wish List popup should disappear
    And I click the 'VIEW WISH LIST' link
    And I should see the wishlist page title called Wish List
    And added wishlist item should be on the page


  Scenario: Size & Fit accordion opens upon selecting a size in the drop down
    Given I go to Home page
    And I goto multiple size product details page
    When I select size of the product
    Then the Size & Fit accordion is open


  Scenario: Verify outfit module is visible along with product detail
    Given I pick a Bags category in the product listing page
    When I go to a product page from the selected filtered list
    Then I should see outfit module
    And I should see designer, title and price on each outfit pid


  Scenario: Verify YMAL module is visible along with product detail
    Given I pick a Accessories category in the product listing page
    When I go to a product page from the selected filtered list
    Then I should see ymal module
    And I should see designer, title and price on each ymal pid


  Scenario: Verify printed and virtual gift voucher are added to shopping bag
    Given I go to Home page
    And I try to access a secured page: Content/giftvoucherlogin
    And I click on Printed voucher
    And I select random voucher price and fill out required detail
    When I add selected voucher in shopping bag
    And I click on Proceed to Purchase in the Voucher PDP
    Then I should see selected voucher in shopping bag


  Scenario: Product App - Verify Product app can be accessed through Google glass
    Given I visit the home page
    And I navigate to 495274 product detail page
