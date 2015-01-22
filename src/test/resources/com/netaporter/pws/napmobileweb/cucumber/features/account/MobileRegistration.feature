Feature: User registration for account on Net-A-Porter
  As a user
  So that I can shop on Net-A-Porter.com
  I want to be able to create a customer account

  @mobileweb @nap @Channelized
  Scenario: User creates a customer account with valid details
    Given I submit valid details on the mobile registration form
    Then I should see the mobile thank you for registering message
    And I should be signed in on the mobile site

