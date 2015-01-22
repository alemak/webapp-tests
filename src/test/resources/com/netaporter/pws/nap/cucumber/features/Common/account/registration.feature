@seaview
Feature: User registration for account on Net-A-Porter
  As a user
  So that I can shop on Net-A-Porter.com
  I want to be able to create a customer account

  @nap @Channelized
  Scenario: Failing registration blank form
    Given I submit invalid details on the registration form
    Then I should see an error message

  @nap @Channelized
  Scenario: User creates a customer account with valid details
    Given I submit valid details on the registration form
    Then I should see the thank you for registering message
    And I should be signed in

  @nap @Channelized
  Scenario: User registers for email updates
    Given I register for email updates
    Then I should see the thank you for registering message
    And I should be signed in

  @nap @Channelized
  Scenario: Partially registered user completes their registration
    Given I add a product to my shopping bag
    And I go to Shipping page as an anonymous user
    And I proceed to the Payment page
    And I pay by VISA_CREDIT_CARD
    When Order Confirmation page is displayed
    And I register a new account with my previously entered email address
    Then I should see an email confirmation sent to my email address

  @nap @Channelized
  Scenario: Failing registration invalid email
    Given I go to Register New Account page
    When I submit an invalid email address on the registration form
    Then I should see an error message

  @nap @Channelized
  Scenario: Failing registration existing email
    Given there is another registered regular user
    When I go to Register New Account page
    And I submit an existing customer email
    Then I should see an error message

  @nap @Channelized
  Scenario: Failing registration blank first name
    Given I go to Register New Account page
    When I submit a blank first name on the registration form
    Then I should see an error message

  @nap @Channelized
  Scenario: Failing registration invalid password
    Given I go to Register New Account page
    And I submit an invalid password
    Then I should see an error message

  @nap @Channelized
  Scenario: Failing registration non-matching passwords
    Given I go to Register New Account page
    When I submit non matching passwords
    Then I should see an error message