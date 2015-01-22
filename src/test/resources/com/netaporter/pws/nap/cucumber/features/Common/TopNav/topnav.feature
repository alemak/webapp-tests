Feature: Main product categories should be present in the TopNav bar


  @nap @Channelized
  Scenario: Lingerie is present in the TopNav bar and Gifts is removed
    Given I go to Home page
    Then Lingerie is present in the TopNav bar
    And Lingerie is between Accessories and Sport
    When I click the Lingerie link from the TopNav
    Then I reach the Lingerie listing page


  @nap @Channelized
  Scenario: Beauty is present in the TopNav bar
    Given I visit the home page
    And BeautyLink is active in the web dB
    Then Beauty is present in the TopNav bar
    And Beauty is between Sport and Gifts
    When I click the Beauty link from the TopNav
    Then I reach the Beauty listing page


  @nap @Channelized
  Scenario: Sport is present in the TopNav bar
    Given I visit the home page
    Then Sport is present in the TopNav bar
    And Sport is between Lingerie and Beauty
    When I click the Sport link from the TopNav
    Then I reach the Sport listing page


  @nap @Channelized
  Scenario: Sale should be present in the TopNav if it is activated in the DB
      Given I visit the home page
    #TODO refactor this into two steps
      Then if Sale is enabled in the weDB the Sale button is present in the TopNav in the correct place
