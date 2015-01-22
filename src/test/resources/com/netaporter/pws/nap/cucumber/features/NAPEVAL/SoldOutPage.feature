@Channelized @napeval
Feature: New sold out product pages

  Scenario: Verify product information are hidden and minimum required details are displayed
    Given I go to Home page
    And I have 1 SOLD_OUT and visible BAGS sku that I force to have similar stock in the db
    When I navigate to product page
    Then collapse mode product information are shown

  Scenario: View full product view can be displayed for sold out items
    Given I go to Home page
    And I have 1 SOLD_OUT and visible BAGS sku that I force to have similar stock in the db
    When I navigate to product page
    And I click on view product detail link
    Then product information should be shown

  Scenario: View full product view can be minimize for sold out items
    Given I go to Home page
    And I have 1 SOLD_OUT and visible BAGS sku that I force to have similar stock in the db
    When I navigate to product page
    And I click on view product detail link
    And I click on hide product detail link
    Then product information should be hidden

  Scenario: Verify sold out YMAL is visible with product information
    Given I go to Home page
    And I have 1 SOLD_OUT and visible BAGS sku that I force to have similar stock in the db
    When I navigate to product page
    Then YMAL product information are displayed

  Scenario: Verify redirection to product detail page when clicked on YMAL product
    Given I go to Home page
    And I have 1 SOLD_OUT and visible BAGS sku that I force to have similar stock in the db
    When I navigate to product page
    And I click on random YMAL product
    Then I am taken to the correct IN_STOCK product details page

  Scenario: Trying to add a sold out item into bag
    Given I navigate to an instock PDP
    And the item goes sold out
    When I try to add the item into bag
    Then I should see product sold out message

# Sold out Web Analytic

  Scenario: Verify analytics for instock single size item
    Given I go to Home page
    And I have 1 IN_STOCK and visible BAGS sku that I force to have similar stock in the db
    When I navigate to product page
    Then Default analytics are fired
    Then Single size instock analytics are fired

  Scenario: Verify analytics for completely sold out single size item
    Given I go to Home page
    And I have 1 SOLD_OUT and visible BEAUTY sku that I force to have similar stock in the db
    When I navigate to product page
    Then Default analytics are fired
    Then Single size sold out analytics are fired

  Scenario: Verify analytics for low stock single size item
    Given I go to Home page
    And I have 1 LOW_STOCK and visible BEAUTY sku that I force to have similar stock in the db
    When I navigate to product page
    Then Default analytics are fired
    Then Single size low stock analytics are fired

  Scenario: Verify analytics for atleast 1 low stock sku in multiple size item
    Given I go to Home page
    And I have 1 LOW_STOCK and visible Clothing sku that I force to have similar stock in the db
    When I navigate to product page
    Then Default analytics are fired
    Then Multiple size low stock analytics are fired

  Scenario: Verify analytics for atleast 1 sold out sku in multiple size item
    Given I go to Home page
    And I have 1 SOLD_OUT and visible CLOTHING sku that I force to have similar stock in the db
    When I navigate to product page
    Then Default analytics are fired
    Then Multiple size sold out analytics are fired

  Scenario: Verify analytics when user selects a size from size drop down
    When I navigate to IN_STOCK product detail page for category SHOES
    Then Default analytics are fired
    And I select random skus from size drop down
    Then Selected size analytics are fired

  #view all option is not working
#  Scenario: Verify analytics for completely sold out multiple sizes product
#    Given I goto Dresses in the Clothing product listing page
#    And I click the View all link of page navigation
#    When I navigate to sold out page with all sizes sold out
#    Then Default analytics are fired
#    Then Analytic for completely sold out multi-size item are fired











