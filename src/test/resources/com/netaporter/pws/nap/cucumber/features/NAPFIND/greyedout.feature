  @todo @Channelized
Feature: Grey out unavailable facets

  Scenario Outline: grey out unavailable facets on level 3 subcategories
     Given I goto <subcategory> in the <category> product listing page
     And I save the current filter facets
     When I randomly select 1 navigation level3 categories
     Then all filter facets are still displayed
    # the only reliable way to check for unavailable facets is through PS API. implement the following step when PS API becomes available and add it to other scenarios from this feature file
    # And the unavailable facets are greyed out

  Examples:
    | category    | subcategory|
    | Clothing    |   Dresses  |
    | Shoes       |   Pumps    |
    | Clothing    |   Pants    |

  Scenario Outline: unselect level 3 categories and all facets are still displayed
    Given I goto <subcategory> in the <category> product listing page
    And I save the current filter facets
    When I randomly select 1 navigation level3 categories
    And I randomly un-select 1 level3 category
    Then all filter facets are still displayed

  Examples:
    | category    | subcategory  |
    | Clothing    |   Jeans      |
    | Shoes       |   Boots      |

  Scenario Outline: grey out unavailable facets of other filter types when filtering by designer or color
    Given I am currently on a <listingType> listing page
    And I save the current filter facets
    When I filter by <filterType>
    Then all other facets except <filterType> facets are still displayed

  Examples:
    | listingType | filterType |
    | custom      |   color    |
    | custom      |   designer |
    | category    |   color    |
    | category    |   designer |
    | search      |   color    |
    | search      |   designer |

  Scenario Outline: grey out unavailable facets of other filter types when filtering by size
    Given I goto <subcategory> in the <category> product listing page
    And I save the current filter facets
    When I filter by size
    Then all other facets except size facets are still displayed

  Examples:
    | category    | subcategory|
    | Clothing    |   Dresses  |
    | Shoes       |   Pumps    |
    | Clothing    |   Pants    |
