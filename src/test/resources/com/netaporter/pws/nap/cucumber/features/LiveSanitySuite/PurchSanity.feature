@napLiveSanity
Feature: PurchSanity.feature: sanity tests for the purchase path, NO PURCHASES ARE MADE!!!


  Scenario: Go to an empty shopping bag and check message, add a product from the carousel to bag, check number of items in the bag
    Given I go to Home page
    When I click the NAP shopping bag icon
    Then I should be on the Shopping Bag page
    And I should have 0 items in my shopping bag
    When I select add to bag in carousel product list without checking stock
    Then I should see added carousel product in the shopping bag


  Scenario: Add two products to bag and check the number in the shopping bag
    Given I go to Home page
    And I pick a Clothing category in the product listing page
    When I add any NAP product from the current listing page to the shopping bag
    Then the NAP shopping bag overlay is displayed correctly
    And I should see that product in the shopping bag
    And I should have 1 items in my shopping bag
    And I pick a Shoes category in the product listing page
    When I add any NAP product from the current listing page to the shopping bag
    Then the NAP shopping bag overlay is displayed correctly
    And I should see that product in the shopping bag
    And I should have 2 items in my shopping bag


  Scenario: Guest checkout journey up to the payment summary page, check product is displayed in the payment summary page
    Given I go to Home page
    And I pick a Bags category in the product listing page
    When I add any NAP product from the current listing page to the shopping bag
    And I go to Shopping Bag page
    And I go to Shipping page as an anonymous user
    And I proceed to the Payment page
    Then I verify that I am on the payment order summary page
    And the product is displayed correctly in the Payment page


  Scenario: Registered user checkout journey up to the payment summary page, check product is displayed in the payment summary page
    Given I am a registered user
    And I sign in with the correct details
    And I pick a Bags category in the product listing page
    When I add any NAP product from the current listing page to the shopping bag
    And I go to Shopping Bag page
    And I go to Shipping page as a signed in user and enter my address
    And I proceed to the Payment page
    Then I verify that I am on the payment order summary page
    And the product is displayed correctly in the Payment page


  Scenario: check that mini shopping bag overlay is displayed correctly when adding a product to bag
    Given I go to Home page
    And I pick a Clothing category in the product listing page
    When I add any NAP product from the current listing page to the shopping bag
    Then the NAP shopping bag overlay is displayed correctly
    And the NAP shopping bag overlay contains the correct product