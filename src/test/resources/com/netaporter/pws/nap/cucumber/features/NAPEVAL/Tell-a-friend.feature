@Channelized @napeval
Feature: Tell-a-friend
  Users can share product by email in product details page.
  Note: This test does not validate that the email is sent, it only validates the steps and forms required for sharing the product


  Scenario: Non-signed in users share product by email
    Given I navigate to IN_STOCK product detail page for category CLOTHING
    When I click the Tell-a-friend button
    Then the Tell-a-friend page is open
    When I fill the whole form and submit the completed tell a friend page
    Then the submission is successful


  Scenario: Signed in users share product by email
    Given I am a seaview registered default user
    And I sign in with the correct details
    And I navigate to IN_STOCK product detail page for category BAGS
    When I click the Tell-a-friend button
    Then the Tell-a-friend page is open with the users details already filled
    When I fill the remaining info and submit the completed tell a friend page
    Then the submission is successful