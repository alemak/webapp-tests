@nap @napfind @Channelized
Feature: Sport Landing Page

  Scenario Outline: sorting in a sport listing page
    Given I navigate to the sport landing page directly
    When I click the <link> from the sport landing page
    And I have Default, New In, Price High, Price Low in sorting drop-down
    When I sort by <sortOrder>
    And the URL is updated with the correct sorting parameter
    And the products are displayed in correct order

   Examples:
    | link           |  sortOrder  |
    | All_Sportswear |  Price High |
    | Gym/Crossfit   |  Price Low  |
    | Run            |  Price High |
    | Yoga/Dance     |  Price Low  |
    | Tennis         |  Price High |
    | Swim/Surf      |  Price Low  |
    | Equestrian     |  Price High |
    | Après          |  Price Low  |
    | Golf           |  Price High |
    | Accessories    |  Price Low  |
    | Outdoor        |  Price High |



  Scenario Outline: check if each 2nd level category takes the user to a page with products
    Given I am currently on a <category> sport listing page
    Then the listing page has at least one product

  Examples:
    | category         |
    | Golf             |
    | Run              |
    | Yoga_and_Dance   |
    | Tennis           |
    | Swim_and_Surf    |
    | Equestrian       |
    | Golf             |
    | Outdoor          |

  Scenario: filtering by size in a level3 sport listing page
    Given I am currently on a All_Sportswear sport listing page
    And I select Tops sport navigation level3 categories
    When I filter by size
    Then the URL will be updated with the size filter
    And the page header is updated with the number of products