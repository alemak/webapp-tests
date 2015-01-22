@seaview @purch
Feature: User can sign in to Net-A-Porter
  As a user
  So that I can shop on Net-A-Porter.com
  I want to be able to sign in to my account

  @nap @Channelized
  Scenario: Non-registered user cannot sign in
    Given I sign in with unregistered details
    Then I should see an error message on the sign in page

  @nap @Channelized
  Scenario: Registered user cannot sign in with wrong password
    Given I am a seaview registered default user
    When I sign in with the wrong password
    Then I should see an error message on the sign in page

  @nap @Channelized
  Scenario: Registered user can sign in with correct details
    Given I am a seaview registered default user
    When I sign in with the correct details
    Then I should be signed in
