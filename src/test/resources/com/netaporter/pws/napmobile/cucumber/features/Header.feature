Feature: Tests for the Net-A-Porter Header Component

  # headerLogoLink
  Scenario: Logo link is displayed
    When I navigate to the Shop Mobile page
    Then Logo link is displayed

  # headerLogoLinkImage
  Scenario: Logo link image is displayed
    When I navigate to the Shop Mobile page
    Then Logo link image is displayed

  # headerBasketQuantity
  Scenario: Basket quantity displayed

  # headerBasketLink
  Scenario: Basket link is displayed
    Given I navigate to the Shop Mobile page
    When I click the shopping bag icon from the mobile product details page
    Then I am on shopping basket page

  # headerBasketLinkImage
  Scenario: Basket link image is displayed

  # mobileTopNavLinks
  Scenario: Navigation links are displayed
