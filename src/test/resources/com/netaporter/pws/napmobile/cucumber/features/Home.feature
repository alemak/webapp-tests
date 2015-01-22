Feature: Tests for the Net-A-Porter Homepage

  Scenario: The Homepage is displayed
    Given I navigate to the Shop Mobile page
    Then The page title should contain NET-A-PORTER.COM


  Scenario Outline: Search
    Given I navigate to the Shop Mobile page
    When I search for a <keyword>
    Then the URL contains the <keyword> name

    Examples:
     | keyword   |
     |   tops    |