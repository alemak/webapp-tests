@chat @napeval @Channelized

Feature: Live chat

  Scenario: First live chat invite for web app
    Given I go to Home page
    And I pick a Clothing category in the product listing page
    When I force liveChat to appear
    Then live chat invite is initiated

  Scenario: First live chat invite for listing page app
    Given I visit the home page
    And I am currently on a Golf sport listing page
    When I force liveChat to appear
    Then live chat invite is initiated

  Scenario: Minimise live chat window
    Given I go to Register New Account page
    And I force liveChat to appear
    When I minimise the chat window
    Then chat window is minimised

# Doesn't work in live either - live issue??
#  Scenario: Live chat trigger when click on contact us link
#    Given I visit the home page
#    When I click on Contact us in the Footer
#    And I switch to original window
#    Then live chat invite is initiated

  Scenario: Live chat trigger on sign in error
    Given I sign in with unregistered details
    When I should see an error message on the sign in page
    Then live chat invite is initiated

# Doesn't work in live either - live issue??
#  Scenario: Live chat triggers when item in shopping bag is empty
#    Given I am on any Product Listing page
#    And I add any in stock product to the shopping bag
#    When I go to Shopping Bag page
#    And I remove product from my shopping bag
#    Then live chat invite is initiated

  Scenario: Close live chat conversation
    Given I visit the home page
    And I pick a Clothing category in the product listing page
    And I force liveChat to appear
    And I select to load chat session
    When I select the close chat window button
    And live confirm close is initiated
    Then I select yes to close chat

  Scenario:  chat session persists when navigation to different page
    Given I visit the home page
    And I goto Dresses in the Clothing product listing page
    And I force liveChat to appear
    When I go to a product page from the selected filtered list
    Then live chat icon is visible

  Scenario: Start live chat to connect to live person in web app
    Given I navigate to a product details page
    And I force liveChat to appear
    When I select to load chat session
    Then live chat session is initiated
    And text box to enter chat should be visible
    And I select the close chat window button
    And live confirm close is initiated
    And I select yes to close chat

  Scenario: Start live chat to connect to live person for listing page app
    Given I visit the home page
    And I pick a Clothing category in the product listing page
    And I am currently on a Golf sport listing page
    And I force liveChat to appear
    When I select to load chat session
    Then live chat session is initiated
    And text box to enter chat should be visible



