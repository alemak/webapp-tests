@nap @napfind
Feature: Sorting Drop-down in product listing page


  @Channelized @nap
  Scenario Outline: sorted by order in a non-sale product listing page
    Given I am currently on a <product> listing page
    When I have Default,New In,Price High,Price Low in sorting drop-down
    And I sort by a randomly selected order
    Then the page is partly refreshed
    And the URL is updated with the correct sorting parameter
    And the correct sorting option is selected in the drop-down

  Examples:
    | product   |
    | category  |
    | designer  |
    | custom    |


  @Channelized @nap
  Scenario Outline: selecting any sort selection the pagination should return to page 1
    Given I am on a multiple-page <product> listing page
    When I navigate to any non-First page
    And I sort by a randomly selected order
    Then pagination should return to page 1

  Examples:
    | product   |
    | category  |
    | designer  |
    | custom    |