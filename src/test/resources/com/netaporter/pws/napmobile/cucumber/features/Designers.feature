Feature: Tests for the Net-A-Porter Designers page

  Scenario: Title main heading is displayed
    Given I navigate to the Designers Mobile page
    Then the page main heading will display DESIGNERS

  Scenario: Default sort option for sort by
    Given I navigate to the Designers Mobile page
    Then designers by category will display Designers by Category

  Scenario Outline: Select category navigation option
    Given I navigate to the Designers Mobile page
    When I apply designers by category <DesignersByCategory>
    Then page url will end with <Url>

  Examples:
    | DesignersByCategory     | Url                     |
    | Beachwear Designers     | ?category=57            |
    | Accessory Designers     | ?category=4             |
    | Bag Designers           | ?category=3             |
    | Clothing Designers      | ?category=7             |
    | Lingerie Designers      | ?category=5             |
    | Shoe Designers          | ?category=2             |
    | Beauty Brands           | ?category=61            |
    | Jewelry Designers       | ?category=9             |
    | New This Season         | ?stdCat=New-This-Season |
    | NET-A-PORTER Exclusives | Content/exclusives      |
