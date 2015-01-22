@purch
Feature: PersistAddressChanges.feature
  As a registered customer
  I want changes to my shipping|billing address and email|password
  to be persisted between channels


  @nap @NonChannelized
  Scenario: cannot create account on channel2 with same email address as an existing account on channel1
    Given I have successfully registered on intl channel website
    And I go to Change Country page
    And I change my country to United States
    And I signout
    When I go to Register New Account page
    And I create an account with already existing address
    Then I should see an error message


  @nap @NonChannelized
  Scenario: Create shipping address on channel1, edit on channel2, check on channel1
    Given I have successfully registered on intl channel website
    And I go to Address Book page
    And I add a new shipping address from United Kingdom
    And I sign out
    When I login onto am channel
    And I go to Address Book page
    And I edit the shipping address
    And I sign out
    And I go to Change Country page
    And I change my country to United Kingdom
    And I go to Address Book page
    And I sign in with the correct details
    And I go to Address Book page
    Then My other shipping address will appear on the address details page


  @nap @NonChannelized
  Scenario: Create billing address on channel1, edit on channel2, check on channel1
    Given I have successfully registered on intl channel website
    And I go to Address Book page
    And I add a new billing address from United Kingdom
    And I sign out
    When I login onto am channel
    And I go to Address Book page
    When I edit the billing address
    And I sign out
    And I go to Change Country page
    And I change my country to United Kingdom
    And I go to Address Book page
    And I sign in with the correct details
    And I go to Address Book page
    Then My other billing address will appear on the address details page


  @nap @NonChannelized
  Scenario: Delete shipping address in my account on channel1, check update on channel2
    Given I have successfully registered on intl channel website
    And I go to Address Book page
    And I add new shipping addresses from United Kingdom
    And I go to Change Country page
    And I change my country to United States
    And I go to Address Book page
    When I delete the shipping address in my account
    And I go to Change Country page
    And I change my country to United Kingdom
    And I go to Address Book page
    Then the shipping address in the address book is removed


  @nap @NonChannelized
  Scenario: Delete billing address in my account on channel1, check update on channel2
    Given I have successfully registered on intl channel website
    And I go to Address Book page
    And I add new billing addresses from United Kingdom
    And I go to Change Country page
    And I change my country to United States
    And I go to Address Book page
    When I delete the billing address in my account
    And I go to Change Country page
    And I change my country to United Kingdom
    And I go to Address Book page
    Then the billing address in the address book is removed


  @nap @NonChannelized
  Scenario: Change email address on channel1, try to login on channel2 with old email address, login does not work
    Given I have successfully registered on intl channel website
    And I go to Account Details page
    And I change my email address
    And I signout
    And I go to Change Country page
    And I change my country to United States
    When I try to login with my old email address
    Then an error is displayed and the user is not signed in


  @nap @NonChannelized
  Scenario: Change password on channel1, try to login with old password on channels2, login does not work
    Given I have successfully registered on intl channel website
    And I go to Account Details page
    And I change my password
    And the user details are successfully changed
    And I signout
    And I go to Change Country page
    And I change my country to United States
    When I try to login using my old password
    Then an error is displayed and the user is not signed in


  @nap @NonChannelized
  Scenario: Change first name and last name on channel1, check change exists on channel2
    Given I have successfully registered on intl channel website
    And I go to Account Details page
    When I attempt to change my firstname and lastname
    Then the user details are successfully changed
    And I go to Change Country page
    And I change my country to United States
    When I go to Account Details page
    Then the account details have changed
