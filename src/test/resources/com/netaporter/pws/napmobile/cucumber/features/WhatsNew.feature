Feature: Tests for the Net-A-Porter What's New page

  Scenario: Title main heading is displayed
    Given I navigate to the What's New Mobile page
    Then the page main heading will display WHAT'S NEW

  Scenario: Number of results from header
    Given I navigate to the What's New Mobile page
    Then the page heading will display at least 1 result

  Scenario: Default sort option for sort by
    Given I navigate to the What's New Mobile page
    Then sort by will display Sort By Price

  Scenario: Number of sort options for sort by
    Given I navigate to the What's New Mobile page
    Then sort by will contain 3 sort options

  Scenario: Values of sort options for sort by
    Given I navigate to the What's New Mobile page
    Then sort by select will have the following:
      | key | value         |
      | 1   | Sort By Price |
      | 2   | High          |
      | 3   | Low           |

  Scenario Outline: Select sort by option
    Given I navigate to the What's New Mobile page
    When I apply sort by <SortBy>
    Then page url will end with <Url>

    Examples:
    | SortBy | Url                |
    | High   | ?sortBy=price-desc |
    | Low    | ?sortBy=price-asc  |

  Scenario Outline: Sort product list prices in order
    Given I navigate to the What's New Mobile page
    When I apply sort by <SortBy>
    Then product list will be sorted in <SortOrder> order

  Examples:
    | SortBy | SortOrder  |
    | High   | Descending |
    | Low    | Ascending  |

  Scenario: Accordion link heading is displayed
    Given I navigate to the What's New Mobile page
    Then the accordion link heading will display FILTER

  Scenario: Default accordion control and content
    Given I navigate to the What's New Mobile page
    Then the accordion control will display +
    And the accordion content will not be displayed

  Scenario: Open accordion control and content
    Given I navigate to the What's New Mobile page
    When I click the accordion control
    Then the accordion control will display -
    And the accordion content will be displayed

  Scenario: Close accordion control and content
    Given I navigate to the What's New Mobile page
    When I click the accordion control
    And I click the accordion control again
    Then the accordion control will display +
    And the accordion content will not be displayed

  Scenario: Default option for category filter
    Given I navigate to the What's New Mobile page
    Then category filter will display All

  Scenario: Number of options for category filter
    Given I navigate to the What's New Mobile page
    Then category filter will contain at least 2 options

  Scenario: Values of options for category filter
    Given I navigate to the What's New Mobile page
    Then category filter will contain valid category values

  Scenario Outline: Select <Category> category filter
    Given I navigate to the What's New Mobile page
    When I apply category <Category>
    Then page url will end with <Category>
    And category filter will display <Category>

    Examples:
    | Category |
    | Clothing |

  Scenario Outline: Select then reset <Category> category filter
    Given I navigate to the What's New Mobile page
    When I apply category <Category>
    And I reset the filter
    Then category filter will display All

  Examples:
    | Category |
    | Clothing |

  Scenario Outline: Select category <Category> and sort by <SortBy>
    Given I navigate to the What's New Mobile page
    When I apply category <Category>
    And I apply sort by <SortBy>
    Then page url will end with <Url>
    And category filter will display <Category>
    And product list will be sorted in <SortOrder> order

  Examples:
    | Category | SortBy | Url                        | SortOrder  |
    | Clothing | High   | Clothing?sortBy=price-desc | Descending |
    | Clothing | Low    | Clothing?sortBy=price-asc  | Ascending  |

  Scenario: Default option for designer filter
    Given I navigate to the What's New Mobile page
    Then designer filter will display All Designers

  Scenario: Number of options for designer filter
    Given I navigate to the What's New Mobile page
    Then designer filter will contain at least 2 options

  Scenario: Designer filter allows multiple select
    Given I navigate to the What's New Mobile page
    Then designer filter allows multiple select

  Scenario: Select single designer filter
    Given I navigate to the What's New Mobile page
    When I apply designer select option:
      | 1 |
    Then product list will only display selected designer

  Scenario: Select multiple designer filters
    Given I navigate to the What's New Mobile page
    When I apply designer select options:
      | 2 |
      | 3 |
      | 4 |
    Then product list will only display selected designers

  Scenario: Default option for colour filter
    Given I navigate to the What's New Mobile page
    Then colour filter will display All Colours

  Scenario: Number of options for colour filter
    Given I navigate to the What's New Mobile page
    Then colour filter will contain at least 2 options

  Scenario: Colour filter allows multiple select
    Given I navigate to the What's New Mobile page
    Then colour filter allows multiple select

  Scenario: Select single colour filter
    Given I navigate to the What's New Mobile page
    When I apply colour select option:
      | 1 |
    Then the colour filter will be included in the url

  Scenario: Select multiple colour filters
    Given I navigate to the What's New Mobile page
    When I apply colour select options:
      | 2 |
      | 3 |
      | 4 |
    Then the colour filters will be included in the url
