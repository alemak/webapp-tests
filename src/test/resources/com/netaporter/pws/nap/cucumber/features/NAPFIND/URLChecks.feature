@napfind
Feature: Check various changes to URLs for filters, sorting, pagination

  @nap @Channelized
  Scenario Outline: changing from Product to Outfit is reflected in URL
    Given I am currently on a <product> listing page
    When I click the <view1> of imageview link
    Then the URL is updated with the correct <view1> parameter
    When I click the <view2> of imageview link
    Then the URL is updated with the correct <view2> parameter

  Examples:
    | product    |    view1  |  view2   |
    | designer   |   outfit  | product  |
    | category   |   outfit  | product  |
    | custom     |   outfit  | product  |


  @nap @Channelized
  Scenario Outline: changing to "View all" is reflected in the URL
    Given I am on a multiple-page <product> listing page
    When I click the View all link of page navigation
    Then the URL is updated with the correct View all parameter

  Examples:
    | product   |
    | category  |
    | designer  |
    | custom    |


  @nap @Channelized
  Scenario Outline: : Visiting a bookmarked URL directly (containing sorting, filtering) works correctly
    Given I go to a bookmarked <product> listing URL
    Then the bookmarked page is opened correctly

  Examples:
    | product   |
    | designer  |
    | category  |
    | custom    |


