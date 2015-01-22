@nap @napfind
Feature: Sport Landing Page - check topnav and links from the sport landing page


  #Landing page
  Scenario Outline: check topnav and subcategory links from the sport landing page
    Given I visit the home page
    Then Sport is present in the TopNav bar
    When I navigate to the sport landing page using the TopNav
    Then I am taken to the sport landing page
    When I click the <link> from the sport landing page
    Then the URL contains the correct <link> name


  Examples:
    | link                  |
    | Run                   |
    | Tennis                |
    | Equestrian            |
    | Golf                  |
    | Accessories           |
    | Outdoor               |
    | All_Sportswear        |