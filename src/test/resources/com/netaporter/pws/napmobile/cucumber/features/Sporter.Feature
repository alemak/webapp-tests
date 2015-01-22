Feature: Tests for the Net-A-Sporter page


  Scenario Outline: Sporter mobile landing page
    Given I navigate to the sport mobile landing page directly
    When I click the <link> from the sport mobile landing page
    Then the URL contains the <link> name
    And the page heading will display at least <Result> results
    And the accordion link heading will display FILTER
    And the accordion control will display +
    And the accordion content will not be displayed
    When I apply designer select option:
      | 1 |
      | 2 |
      | 3 |
      | 4 |
    Then product list will only display selected designer

    Examples:
      | link                  | Result |
      | All_Sportswear        |  1     |
      | Tennis                |  1     |
      | Equestrian            |  1     |
      | Golf                  |  1     |
      | Accessories           |  1     |
      | Outdoor               |  1     |


   Scenario Outline: Colour filters
     Given I navigate to the sport mobile landing page directly
     When I click the <link> from the sport mobile landing page
     And I click the accordion control again
     Then colour filter will display <Default>

     Examples:
       | link   |   Default    |
       | Tennis | All Colors  |


  Scenario Outline: Colour filters
    Given I navigate to the sport mobile landing page directly
    When I click the <link> from the sport mobile landing page
    When I apply colour select option:
       | 2 |
       | 3 |
    Then the colour filter will be included in the url

  Examples:
    | link   |
    | Tennis |


  Scenario Outline: Category sub-list link url
    Given I navigate to the Shop Mobile page
    When I click the <Category> accordion control
    And I click the <Category> <SubList> link
    Then page url will end with <Url>
    And the page heading will display at least <Results> results

  Examples:
    | Category    | SubList      | Url          | Results  |
    | Sport       | Golf         | Sport/Golf   |  1       |


  Scenario Outline: Size filter
    Given I navigate to the Shop Mobile page
    When I click the <Category> accordion control
    And I click the <Category> <SubList> link
    When I apply sub-list-child <SubListChild>
    Then size filter will display <Default>

  Examples:
    | Category    | SubList      | SubListChild |   Default   |
    | Sport       | Golf         |  Tops        |  All sizes  |


  Scenario Outline: Select single designer filter
    Given I navigate to the Shop Mobile page
    When I click the <Category> accordion control
    And I click the <Category> <SubList> link
    When I apply designer select option:
      | 2 |
      | 3 |
    Then product list will only display selected designers

  Examples:
   | Category    | SubList      |
   | Sport       | Golf         |