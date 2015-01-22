Feature: Tests for the Net-A-Porter Shop page

  Scenario: Title main heading is displayed
    Given I navigate to the Shop Mobile page
    Then the page main heading will display SHOP

  Scenario Outline: Default accordion control and sub-list
    Given I navigate to the Shop Mobile page
    Then the <Category> accordion control will display +
    And the <Category> sub-list will not be displayed

    Examples:
    | Category     |
    | Clothing     |
    | Bags         |
    | Shoes        |
    | Accessories  |
    | Lingerie     |
    | Beauty       |

  Scenario Outline: Open accordion control and sub-list
    Given I navigate to the Shop Mobile page
    When I click the <Category> accordion control
    Then the <Category> accordion control will display -
    And the <Category> sub-list will be displayed

  Examples:
    | Category     |
    | Clothing     |
    | Bags         |
    | Shoes        |
    | Accessories  |
    | Lingerie     |
    | Beauty       |

  Scenario Outline: Close accordion control and sub-list
    Given I navigate to the Shop Mobile page
    When I click the <Category> accordion control
    And I click the <Category> accordion control again
    Then the <Category> accordion control will display +
    And the <Category> sub-list will not be displayed

  Examples:
    | Category     |
    | Clothing     |
    | Bags         |
    | Shoes        |
    | Accessories  |
    | Lingerie     |
    | Beauty       |

  Scenario: Open next and close last accordion control and sub-list
    Given I navigate to the Shop Mobile page
    When I click the Clothing accordion control
    And I click the Bags accordion control
    And I click the Shoes accordion control
    And I click the Accessories accordion control
    And I click the Lingerie accordion control
    And I click the Beauty accordion control
    Then the Clothing accordion control will display +
    And the Clothing sub-list will not be displayed
    And the Bags accordion control will display +
    And the Bags sub-list will not be displayed
    And the Shoes accordion control will display +
    And the Shoes sub-list will not be displayed
    And the Accessories accordion control will display +
    And the Accessories sub-list will not be displayed
    And the Lingerie accordion control will display +
    And the Lingerie sub-list will not be displayed
    And the Beauty accordion control will display -
    And the Beauty sub-list will be displayed

  Scenario Outline: Category sub-list link text
    Given I navigate to the Shop Mobile page
    When I click the <Category> accordion control
    Then the <Category> sub-list links will be displayed

  Examples:
    | Category    |
    | Clothing    |
    | Bags        |
    | Shoes       |
    | Accessories |
    | Lingerie    |
    | Beauty      |

  Scenario Outline: Category sub-list link url
    Given I navigate to the Shop Mobile page
    When I click the <Category> accordion control
    And I click the <Category> <SubList> link
    Then page url will end with <Url>

  Examples:
    | Category    | SubList      | Url                      |
    | Clothing    | Clothing     | Clothing/All             |
    | Clothing    | Activewear   | Clothing/Activewear      |
    | Bags        | Bags         | Bags/All                 |
    | Bags        | Belt_bags    | Bags/Belt_Bags           |
    | Shoes       | Shoes        | Shoes/All                |
    | Shoes       | Pumps        | Shoes/Pumps              |
    | Accessories | Accessories  | Accessories/All          |
    | Accessories | Fine_jewelry | Accessories/Fine_Jewelry |
    | Lingerie    | Lingerie     | Lingerie/All             |
    | Lingerie    | Corsetry     | Lingerie/Corsetry        |
    | Beauty      | Beauty       | Beauty/All               |
    | Beauty      | Makeup       | Beauty/Makeup            |

  Scenario Outline: Category sub-list link page
    Given I navigate to the Shop Mobile page
    When I click the <Category> accordion control
    And I click the <Category> <SubList> link
    Then I will be at the shop <Shop> page

  Examples:
    | Category    | SubList      | Shop         |
    | Clothing    | Clothing     | Clothing     |
    | Clothing    | Activewear   | Activewear   |
    | Bags        | Bags         | Bags         |
    | Bags        | Belt_bags    | Belt_bags    |
    | Shoes       | Shoes        | Shoes        |
    | Shoes       | Pumps        | Pumps        |
    | Accessories | Accessories  | Accessories  |
    | Accessories | Fine_jewelry | Fine_jewelry |
    | Lingerie    | Lingerie     | Lingerie     |
    | Lingerie    | Corsetry     | Corsetry     |
    | Beauty      | Beauty       | Beauty       |
    | Beauty      | Makeup       | Makeup       |

  Scenario Outline: Number of results from heading
    Given I navigate to the <Page> page
    Then the page heading will display at least <Result> results

  Examples:
    | Page                     | Result |
    | Shop Clothing Mobile     | 1      |
    | Shop Bags Mobile         | 1      |
    | Shop Shoes Mobile        | 1      |
    | Shop Accessories Mobile  | 1      |
    | Shop Lingerie Mobile     | 1      |
    | Shop Beauty Mobile       | 1      |

  Scenario Outline: Number of results from sub heading
    Given I navigate to the <Page> page
    Then the page sub-heading will display at least <Result> results

  Examples:
    | Page                     | Result |
    | Shop Activewear Mobile   | 1      |
    | Shop Belt_bags Mobile    | 1      |
    | Shop Pumps Mobile        | 1      |
    | Shop Fine_jewelry Mobile | 1      |
    | Shop Corsetry Mobile     | 1      |
    | Shop Makeup Mobile       | 1      |
    | Shop Box Mobile          | 1      |
    | Shop Tops Mobile         | 1      |

  Scenario Outline: Default sort option for sort by
    Given I navigate to the <Page> page
    Then sort by will display Sort By Price

  Examples:
    | Page                     |
    | Shop Clothing Mobile     |
    | Shop Activewear Mobile   |
    | Shop Bags Mobile         |
    | Shop Belt_bags Mobile    |
    | Shop Shoes Mobile        |
    | Shop Pumps Mobile        |
    | Shop Accessories Mobile  |
    | Shop Fine_jewelry Mobile |
    | Shop Lingerie Mobile     |
    | Shop Corsetry Mobile     |
    | Shop Beauty Mobile       |
    | Shop Makeup Mobile       |

  Scenario Outline: Number of sort options for sort by
    Given I navigate to the <Page> page
    Then sort by will contain 3 sort options

  Examples:
    | Page                     |
    | Shop Clothing Mobile     |
    | Shop Activewear Mobile   |
    | Shop Bags Mobile         |
    | Shop Belt_bags Mobile    |
    | Shop Shoes Mobile        |
    | Shop Pumps Mobile        |
    | Shop Accessories Mobile  |
    | Shop Fine_jewelry Mobile |
    | Shop Lingerie Mobile     |
    | Shop Corsetry Mobile     |
    | Shop Beauty Mobile       |
    | Shop Makeup Mobile       |


  Scenario Outline: Values of sort options for sort by
    Given I navigate to the <Page> page
    Then sort by select will have the following:
      | key | value         |
      | 1   | Sort By Price |
      | 2   | High          |
      | 3   | Low           |

  Examples:
    | Page                     |
    | Shop Clothing Mobile     |
    | Shop Activewear Mobile   |
    | Shop Bags Mobile         |
    | Shop Belt_bags Mobile    |
    | Shop Shoes Mobile        |
    | Shop Pumps Mobile        |
    | Shop Accessories Mobile  |
    | Shop Fine_jewelry Mobile |
    | Shop Lingerie Mobile     |
    | Shop Corsetry Mobile     |
    | Shop Beauty Mobile       |
    | Shop Makeup Mobile       |

  Scenario Outline: Select sort by option
    Given I navigate to the <Page> page
    When I apply sort by <SortBy>
    Then page url will end with <Url>

  Examples:
    | Page                     | SortBy | Url                |
    | Shop Clothing Mobile     | High   | ?sortBy=price-desc |
    | Shop Activewear Mobile   | Low    | ?sortBy=price-asc  |
    | Shop Bags Mobile         | High   | ?sortBy=price-desc |
    | Shop Belt_bags Mobile    | Low    | ?sortBy=price-asc  |
    | Shop Shoes Mobile        | High   | ?sortBy=price-desc |
    | Shop Pumps Mobile        | Low    | ?sortBy=price-asc  |
    | Shop Accessories Mobile  | High   | ?sortBy=price-desc |
    | Shop Fine_jewelry Mobile | Low    | ?sortBy=price-asc  |
    | Shop Lingerie Mobile     | High   | ?sortBy=price-desc |
    | Shop Corsetry Mobile     | Low    | ?sortBy=price-asc  |
    | Shop Beauty Mobile       | High   | ?sortBy=price-desc |
    | Shop Makeup Mobile       | Low    | ?sortBy=price-asc  |

  Scenario Outline: Sort product list prices in order
    Given I navigate to the <Page> page
    When I apply sort by <SortBy>
    Then product list will be sorted in <SortOrder> order

  Examples:
    | Page                     | SortBy | SortOrder  |
    | Shop Clothing Mobile     | Low    | Ascending  |
    | Shop Activewear Mobile   | High   | Descending |
    | Shop Bags Mobile         | Low    | Ascending  |
    | Shop Belt_bags Mobile    | High   | Descending |
    | Shop Shoes Mobile        | Low    | Ascending  |
    | Shop Pumps Mobile        | High   | Descending |
    | Shop Accessories Mobile  | Low    | Ascending  |
    | Shop Fine_jewelry Mobile | High   | Descending |
    | Shop Lingerie Mobile     | Low    | Ascending  |
    | Shop Corsetry Mobile     | High   | Descending |
    | Shop Beauty Mobile       | Low    | Ascending  |
    | Shop Makeup Mobile       | High   | Descending |

  Scenario Outline: Accordion link heading is displayed
    Given I navigate to the <Page> page
    Then the accordion link heading will display FILTER

  Examples:
    | Page                     |
    | Shop Clothing Mobile     |
    | Shop Activewear Mobile   |
    | Shop Bags Mobile         |
    | Shop Belt_bags Mobile    |
    | Shop Shoes Mobile        |
    | Shop Pumps Mobile        |
    | Shop Accessories Mobile  |
    | Shop Fine_jewelry Mobile |
    | Shop Lingerie Mobile     |
    | Shop Corsetry Mobile     |
    | Shop Beauty Mobile       |
    | Shop Makeup Mobile       |

  Scenario Outline: Default accordion control and content
    Given I navigate to the <Page> page
    Then the accordion control will display +
    And the accordion content will not be displayed

  Examples:
    | Page                     |
    | Shop Clothing Mobile     |
    | Shop Activewear Mobile   |
    | Shop Bags Mobile         |
    | Shop Belt_bags Mobile    |
    | Shop Shoes Mobile        |
    | Shop Pumps Mobile        |
    | Shop Accessories Mobile  |
    | Shop Fine_jewelry Mobile |
    | Shop Lingerie Mobile     |
    | Shop Corsetry Mobile     |
    | Shop Beauty Mobile       |
    | Shop Makeup Mobile       |

  Scenario Outline: Open accordion control and content
    Given I navigate to the <Page> page
    When I click the accordion control
    Then the accordion control will display -
    And the accordion content will be displayed

  Examples:
  | Page                     |
  | Shop Clothing Mobile     |
  | Shop Activewear Mobile   |
  | Shop Bags Mobile         |
  | Shop Belt_bags Mobile    |
  | Shop Shoes Mobile        |
  | Shop Pumps Mobile        |
  | Shop Accessories Mobile  |
  | Shop Fine_jewelry Mobile |
  | Shop Lingerie Mobile     |
  | Shop Corsetry Mobile     |
  | Shop Beauty Mobile       |
  | Shop Makeup Mobile       |

  Scenario Outline: Close accordion control and content
    Given I navigate to the <Page> page
    When I click the accordion control
    And I click the accordion control again
    Then the accordion control will display +
    And the accordion content will not be displayed

  Examples:
    | Page                     |
    | Shop Clothing Mobile     |
    | Shop Activewear Mobile   |
    | Shop Bags Mobile         |
    | Shop Belt_bags Mobile    |
    | Shop Shoes Mobile        |
    | Shop Pumps Mobile        |
    | Shop Accessories Mobile  |
    | Shop Fine_jewelry Mobile |
    | Shop Lingerie Mobile     |
    | Shop Corsetry Mobile     |
    | Shop Beauty Mobile       |
    | Shop Makeup Mobile       |

  Scenario Outline: Default option for category filter
    Given I navigate to the <Page> page
    Then category filter will display <Default>

  Examples:
    | Page                     | Default         |
    | Shop Clothing Mobile     | All Clothing    |
    | Shop Activewear Mobile   | All Clothing    |
    | Shop Bags Mobile         | All Bags        |
    | Shop Belt_bags Mobile    | All Bags        |
    | Shop Shoes Mobile        | All Shoes       |
    | Shop Pumps Mobile        | All Shoes       |
    | Shop Accessories Mobile  | All Accessories |
    | Shop Fine_jewelry Mobile | All Accessories |
    | Shop Lingerie Mobile     | All Lingerie    |
    | Shop Corsetry Mobile     | All Lingerie    |
    | Shop Beauty Mobile       | All Beauty      |
    | Shop Makeup Mobile       | All Beauty      |

  Scenario Outline: Category sub-list page
    Given I navigate to the <Page> page
    When I apply sub-list <SubList>
    Then I will be at the shop <Shop> page

  Examples:
    | Page                    | SubList      | Shop         |
    | Shop Clothing Mobile    | Activewear   | Activewear   |
    | Shop Bags Mobile        | Belt Bags    | Belt_bags    |
    | Shop Shoes Mobile       | Pumps        | Pumps        |
    | Shop Accessories Mobile | Fine Jewelry | Fine_jewelry |
    | Shop Lingerie Mobile    | Corsetry     | Corsetry     |
    | Shop Beauty Mobile      | Makeup       | Makeup       |

  Scenario Outline: Category sub-list-child page
    Given I navigate to the <Page> page
    When I apply sub-list-child <SubListChild>
    Then I will be at the shop <Shop> page

  Examples:
    | Page                    | SubListChild | Shop   |
    | Shop Activewear Mobile  | Tops         | Tops   |
    | Shop Clutch_bags Mobile | Box          | Box    |
    | Shop Shorts Mobile      | Tops         | Tops   |
    | Shop Tops Mobile        | Shorts       | Shorts |

  Scenario Outline: Category sub-list-sibling page
    Given I navigate to the <Page> page
    When I apply sub-list-sibling <SubListSibling>
    Then I will be at the shop <Shop> page

  Examples:
    | Page                    | SubListSibling  | Shop        |
    | Shop Belt_bags Mobile   | All Clutch Bags | Clutch_bags |
    | Shop Clutch_bags Mobile | All Belt Bags   | Belt_bags   |
    | Shop Box Mobile         | All Belt Bags   | Belt_bags   |

  Scenario Outline: Category sub-list-parent page
    Given I navigate to the <Page> page
    When I apply sub-list-parent <SubListParent>
    Then I will be at the shop <Shop> page

  Examples:
    | Page                     | SubListParent   | Shop        |
    | Shop Activewear Mobile   | All Clothing    | Clothing    |
    | Shop Belt_bags Mobile    | All Bags        | Bags        |
    | Shop Pumps Mobile        | All Shoes       | Shoes       |
    | Shop Fine_jewelry Mobile | All Accessories | Accessories |
    | Shop Corsetry Mobile     | All Lingerie    | Lingerie    |
    | Shop Makeup Mobile       | All Beauty      | Beauty      |
    | Shop Tops Mobile         | All Activewear  | Activewear  |
    | Shop Tops Mobile         | All Clothing    | Clothing    |
    | Shop Box Mobile          | All Clutch Bags | Clutch_bags |
    | Shop Box Mobile          | All Bags        | Bags        |

  Scenario Outline: Select then reset category sub-list filter
    Given I navigate to the <Page> page
    When I apply sub-list <SubList>
    And I reset the filter
    Then I will be at the shop <Shop> page

  Examples:
    | Page                    | SubList      | Shop        |
    | Shop Clothing Mobile    | Activewear   | Clothing    |
    | Shop Bags Mobile        | Belt Bags    | Bags        |
    | Shop Shoes Mobile       | Pumps        | Shoes       |
    | Shop Accessories Mobile | Fine Jewelry | Accessories |
    | Shop Lingerie Mobile    | Corsetry     | Lingerie    |
    | Shop Beauty Mobile      | Makeup       | Beauty      |

  Scenario Outline: Select then reset category sub-list-child filter
    Given I navigate to the <Page> page
    When I apply sub-list-child <SubListChild>
    And I reset the filter
    Then I will be at the shop <Shop> page

  Examples:
    | Page                    | SubListChild | Shop        |
    | Shop Activewear Mobile  | Tops         | Activewear  |
    | Shop Clutch_bags Mobile | Box          | Clutch_bags |
    | Shop Shorts Mobile      | Tops         | Activewear  |
    | Shop Tops Mobile        | Shorts       | Activewear  |

  Scenario Outline: Select then reset category sub-list-sibling filter
    Given I navigate to the <Page> page
    When I apply sub-list-sibling <SubListSibling>
    And I reset the filter
    Then I will be at the shop <Shop> page

  Examples:
    | Page                    | SubListSibling  | Shop |
    | Shop Belt_bags Mobile   | All Clutch Bags | Bags |
    | Shop Clutch_bags Mobile | All Belt Bags   | Bags |
    | Shop Box Mobile         | All Belt Bags   | Bags |

  Scenario Outline: Select then reset category sub-list-parent filter
    Given I navigate to the <Page> page
    When I apply sub-list-parent <SubListParent>
    And I reset the filter
    Then I will be at the shop <Shop> page

  Examples:
    | Page             | SubListParent   | Shop     |
    | Shop Tops Mobile | All Activewear  | Clothing |
    | Shop Box Mobile  | All Clutch Bags | Bags     |

  Scenario Outline: Default option for designer filter
    Given I navigate to the <Page> page
    Then designer filter will display <Default>

  Examples:
    | Page                   | Default       |
    | Shop Clothing Mobile   | All Designers |
    | Shop Activewear Mobile | All Designers |
    | Shop Tops Mobile       | All Designers |

  Scenario Outline: Number of options for designer filter
    Given I navigate to the <Page> page
    Then designer filter will contain at least <Options> options

  Examples:
    | Page                   | Options |
    | Shop Clothing Mobile   | 2       |
    | Shop Activewear Mobile | 2       |
    | Shop Tops Mobile       | 2       |

  Scenario Outline: Designer filter allows multiple select
    Given I navigate to the <Page> page
    Then designer filter allows multiple select

  Examples:
    | Page                   |
    | Shop Clothing Mobile   |
    | Shop Activewear Mobile |
    | Shop Tops Mobile       |

  Scenario Outline: Select single designer filter
    Given I navigate to the <Page> page
    When I apply designer select option:
      | 1 |
    Then product list will only display selected designer

  Examples:
    | Page                   |
    | Shop Clothing Mobile   |
    | Shop Activewear Mobile |
    | Shop Tops Mobile       |

  Scenario Outline: Select multiple designer filters
    Given I navigate to the <Page> page
    When I apply designer select options:
      | 2 |
      | 3 |
      | 4 |
    Then product list will only display selected designers

  Examples:
    | Page                   |
    | Shop Clothing Mobile   |
    | Shop Activewear Mobile |

  Scenario Outline: Default option for colour filter
    Given I navigate to the <Page> page
    Then colour filter will display <Default>

  Examples:
    | Page                   | Default     |
    | Shop Clothing Mobile   | All Colours |
    | Shop Activewear Mobile | All Colours |
    | Shop Tops Mobile       | All Colours |

  Scenario Outline: Number of options for colour filter
    Given I navigate to the <Page> page
    Then colour filter will contain at least <Options> options

  Examples:
  | Page                   | Options |
  | Shop Clothing Mobile   | 2       |
  | Shop Activewear Mobile | 2       |
  | Shop Tops Mobile       | 2       |

  Scenario Outline: Colour filter allows multiple select
    Given I navigate to the <Page> page
    Then colour filter allows multiple select

  Examples:
  | Page                   |
  | Shop Clothing Mobile   |
  | Shop Activewear Mobile |
  | Shop Tops Mobile       |

  Scenario Outline: Select single colour filter
    Given I navigate to the <Page> page
    When I apply colour select option:
      | 1 |
    Then the colour filter will be included in the url

  Examples:
    | Page                   |
    | Shop Clothing Mobile   |
    | Shop Activewear Mobile |
    | Shop Tops Mobile       |

  Scenario Outline: Select multiple colour filters
    Given I navigate to the <Page> page
    When I apply colour select options:
      | 2 |
      | 3 |
      | 4 |
    Then the colour filters will be included in the url

  Examples:
    | Page                   |
    | Shop Clothing Mobile   |
    | Shop Activewear Mobile |

  Scenario Outline: Default option for size filter
    Given I navigate to the <Page> page
    Then size filter will display <Default>

  Examples:
    | Page                   | Default   |
    | Shop Activewear Mobile | All Sizes |
    | Shop Pumps Mobile      | All Sizes |

  Scenario Outline: Number of options for size filter
    Given I navigate to the <Page> page
    Then size filter will contain at least <Options> options

  Examples:
    | Page                   | Options |
    | Shop Clothing Mobile   | 2       |
    | Shop Activewear Mobile | 2       |
    | Shop Tops Mobile       | 2       |

  Scenario Outline: Size filter allows multiple select
    Given I navigate to the <Page> page
    Then size filter allows multiple select

  Examples:
    | Page                   |
    | Shop Clothing Mobile   |
    | Shop Activewear Mobile |
    | Shop Tops Mobile       |

  Scenario Outline: Select single size filter
    Given I navigate to the <Page> page
    When I apply size select option:
      | 1 |
    Then the size filter will be included in the url

  Examples:
    | Page                   |
    | Shop Clothing Mobile   |
    | Shop Activewear Mobile |
    | Shop Shoes Mobile      |
    | Shop Pumps Mobile      |

  Scenario Outline: Select multiple size filters
    Given I navigate to the <Page> page
    When I apply size select options:
      | 2 |
      | 3 |
      | 4 |
    Then the size filters will be included in the url

  Examples:
    | Page                   |
    | Shop Clothing Mobile   |
    | Shop Activewear Mobile |
    | Shop Shoes Mobile      |
    | Shop Pumps Mobile      |

  Scenario Outline: Pagination number of pages
    Given I navigate to the <Page> page
    When pagination value has been set to 60
    Then I expect the correct number of pages in the pagination

  Examples:
    | Page                     |
    | Shop Clothing Mobile     |
    | Shop Activewear Mobile   |
    | Shop Bags Mobile         |
    | Shop Belt_bags Mobile    |
    | Shop Shoes Mobile        |
    | Shop Pumps Mobile        |
    | Shop Accessories Mobile  |
    | Shop Fine_jewelry Mobile |
    | Shop Lingerie Mobile     |
    | Shop Corsetry Mobile     |
    | Shop Beauty Mobile       |
    | Shop Makeup Mobile       |

  Scenario Outline: Pagination next
    Given I navigate to the <Page> page
    When I click on pagination Next
    Then I will be at page number <Number>
    And page url will end with <Url>

  Examples:
    | Page                     | Number | Url              |
    | Shop Clothing Mobile     | 2      | pn=2&viewall=off |

  Scenario Outline: Pagination next and next
    Given I navigate to the <Page> page
    When I click on pagination Next
    And I click on pagination Next
    Then I will be at page number <Number>
    And page url will end with <Url>

  Examples:
    | Page                     | Number | Url              |
    | Shop Clothing Mobile     | 3      | pn=3&viewall=off |

  Scenario Outline: Pagination next and previous
    Given I navigate to the <Page> page
    When I click on pagination Next
    And I click on pagination Previous
    Then I will be at page number <Number>
    And page url will end with <Url>

  Examples:
    | Page                     | Number | Url              |
    | Shop Clothing Mobile     | 1      | pn=1&viewall=off |

  Scenario Outline: Pagination next twice and previous twice
    Given I navigate to the <Page> page
    When I click on pagination Next
    And I click on pagination Next
    And I click on pagination Previous
    And I click on pagination Previous
    Then I will be at page number <Number>
    And page url will end with <Url>

  Examples:
    | Page                     | Number | Url              |
    | Shop Clothing Mobile     | 1      | pn=1&viewall=off |