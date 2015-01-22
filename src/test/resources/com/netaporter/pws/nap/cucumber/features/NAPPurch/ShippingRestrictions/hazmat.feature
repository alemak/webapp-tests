@nap @Channelized @pdp  @todo @productServiceApi

Feature: HAZMAT Restrictions

  Scenario: The product page of a HAZMAT restricted product displays a warning when opened from restricted country
    Given I go to a country that has a HAZMAT restriction
    When I view an instock hazmat restricted product details page
    Then a warning is displayed on the product details page