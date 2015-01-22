@purch
Feature: Linkshare
  As an online shopper
  I want to be able to to go Net-A-Porter via an affiliate site
  So that I can buy luxury branded clothes online

  @nap @Channelized
  Scenario: An anonymous Customer can buy items via an affiliate link
    Given I visit Home page with an affiliate Id
    And a linkshare cookie is stored in my browser
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I go to Shipping page as an anonymous user
    And I proceed to the Payment page
    And I pay by VISA_CREDIT_CARD
    Then Order Confirmation page is displayed
    And the Order Confirmation page should display the product(s)
    And an affiliate order line should be created

  @nap @Channelized
  Scenario: An registered customer can buy items via an affiliate link
    Given I visit Home page with an affiliate Id
    And a linkshare cookie is stored in my browser
    And I am a seaview registered default user
    And I am logged in
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I go to Shipping page as a signed in user and enter my address
    And I proceed to the Payment page
    And I pay by VISA_CREDIT_CARD
    Then Order Confirmation page is displayed
    And the Order Confirmation page should display the product(s)
    And an affiliate order line should be created