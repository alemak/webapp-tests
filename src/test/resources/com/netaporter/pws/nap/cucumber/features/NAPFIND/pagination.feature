@nap @napfind
Feature: Product listing pages pagination


  @nap @Channelized
  Scenario: five pages show all page numbers
    Given I am on a multiple page product listing page of 5 pages
    Then I should see all page numbers
    And all the page numbers have links except the current page number


  @nap @Channelized
  Scenario: six or more pages show a summary
    Given I am on a multiple-page category listing page of 6 pages or more
  #  $T is the total number of of pages
    Then I should see a summary of 1 2 3 … $T
    And all the page numbers have links except the current page number


  @nap @Channelized
  Scenario Outline: first/last page have previous/next links enabled/disabled
    Given I am on a multiple-page category listing page
    When I navigate to the <FirstOrLast> page
    Then the <PreviousOrNext1> link is unselected
    And the <PreviousOrNext2> link is selected

  Examples:
    |  FirstOrLast |  PreviousOrNext1 | PreviousOrNext2 |
    |     First    |     Previous     |      Next       |
    |     Last     |     Next         |      Previous   |


  @nap @Channelized
  Scenario: three middle page numbers
    Given I am on a multiple-page category listing page of 8 pages or more
    And I navigate to the Third page
    When I click the Next link of page navigation
    Then I should see a summary of 1 … 3 4 5 … $T
    And all the page numbers have links except the current page number
    When I click the Next link of page navigation
    Then I should see a summary of 1 … 4 5 6 … $T
    And all the page numbers have links except the current page number


  @nap @Channelized
  Scenario: On the third last page
    Given I am on a multiple-page category listing page of 8 pages or more
    When I navigate to the Third Last page
    Then I should see the first and the last three page numbers
    And all the page numbers have links except the current page number


  @nap @Channelized
  Scenario: Pagination and view all are not visible in a single page listing
    Given I am currently on a category listing page
    When I select designer filter to goto a single page listing
    Then pagination is invisible
    And the View all link is unselected


  @nap @Channelized
  Scenario: Pagination and view all are visible in a multiple page listing: 60-120 products
    Given I am on a multiple page product listing page of 2 pages
    Then pagination is visible
    And drop down is displayed