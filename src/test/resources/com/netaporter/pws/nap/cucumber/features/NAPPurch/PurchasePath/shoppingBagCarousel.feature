@Channelized @nap @purch
Feature: Customer can add products to the shopping bag from carousel of products in the shopping bag
  As the NAP PO
  I want a carousel of beauty products added to the basket page
  So that customer can purchase products from the product carousel list


  Scenario: Sign in user can see carousel in shopping bag is visible with maximum 6 products and can add carousel product to bag
    When I am a signed in user on NAP website
    And I go to Shopping Bag page
    Then I should see products in the upsell custom list
#    add to bag
    When I select add to bag in carousel product list
    Then I should see added carousel product in the shopping bag


  Scenario: Not sign in user can see carousel in shopping bag is visible with maximum 6 products
    When I go to Shopping Bag page
    Then I should see products in the upsell custom list
#    add to bag
    When I select add to bag in carousel product list
    Then I should see added carousel product in the shopping bag


  Scenario: Sign in User can add in stock products to shopping bag from carousel after adding product from listing page
    Given I am a signed in user on NAP website
    And I add 1 in stock products directly to my bag
    And I should see that product in the shopping bag
    When I select add to bag in carousel product list
    Then I should see added carousel product in the shopping bag


  Scenario: Not sign in user can add in stock products to shopping bag from carousel after adding product from listing page
    Given I add 1 in stock products directly to my bag
    And I should see that product in the shopping bag
    When I select add to bag in carousel product list
    Then I should see added carousel product in the shopping bag


  Scenario: User is redirected to product page with promo parameter in URL upon clicking products from carousel
    Given I add 1 in stock products directly to my bag
    And I should see that product in the shopping bag
    When I click on any product inside carousel list
    Then I should see the promo parameter in the URL