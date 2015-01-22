@seaview @purch
Feature: Forgotten Password
  As a Net-a-Porter customer
  I want to be able to reset my password
  So that I am never locked out of my account

  @nap  @Channelized
  Scenario: Customer requests to reset their password
    Given I am a seaview registered default user
    When I navigate to the sign in page
    And I click Change Password
    And I submit my registered email address
    Then I should see an email sent confirmation message

  @nap @Channelized
  Scenario: Customer requests to reset their password while purchasing an item
    Given I am a seaview registered default user
    And I am on any Product Listing page
    And I add a product to my shopping bag
    When I proceed to purchase
    And I click Forgotten password link
    And I submit my registered email address
    Then I should see an email sent confirmation message