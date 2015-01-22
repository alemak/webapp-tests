@Channelized @napeval
Feature: Shipping Restrictions

  Scenario: The product page of a CITES restricted product displays a warning when opened from a CITES restricted country
    Given I have selected Kuwait from the intl Channel
    And I am currently on a search listing page
    And I search for a Python keyword
    When I click on a random product from the listing page
    Then a warning is displayed on the product details page

  Scenario: The product page of a CITES restricted product does not display a warning when opened from a non-CITES restricted country
    Given I have selected United Kingdom from the intl Channel
    And I am currently on a search listing page
    And I search for a Python keyword
    When I click on a random product from the listing page
    Then a warning is not displayed on my shopping bag page

  Scenario: Adding a CITES restricted product to the shopping bag from a CITES restricted country will display a warning on the shopping bag
    Given I have selected Kuwait from the intl Channel
    And I am currently on a search listing page
    And I search for a Python keyword
    When I click on a random product from the listing page
    And I add a product into my shopping bag from the product details page
    When I go to Shopping Bag page
    Then a warning is displayed on my shopping bag page

  Scenario: Adding a CITES restricted product to the shopping bag from a non-CITES restricted country will display a warning
    Given I have selected United Kingdom from the intl Channel
    And I am currently on a search listing page
    And I search for a Python keyword
    When I click on a random product from the listing page
    And I add a product into my shopping bag from the product details page
    When I go to Shopping Bag page
    Then a warning is not displayed on my shopping bag page

  Scenario: The product page of a HAZMAT restricted product displays a warning when opened from restricted country
    Given I have selected Kuwait from the intl Channel
    When I arrive at Hazmat restricted product detail page for country code KW
    Then a warning is displayed on the product details page