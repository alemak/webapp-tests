@nap @Channelized @purch
Feature: Validate functionality for admin and admin_addr user roles


  Scenario: Successful admin_addr login
  #  Given I am a registered admin_addr user
    Given I am a seaview registered "admin_addr" user
    When I sign in with the correct details
    Then I should be signed in
    And the customer search form is displayed


  Scenario: Incorrect purchase path login with Admin_addr User
    #  Given I am a registered admin_addr user
    Given I am a seaview registered "admin_addr" user
    And I add a product to my shopping bag
    And I am on the purchase path signin page
    When I sign in with the wrong password in the purchase path
    Then I should see an error message on the sign in page


  Scenario: Successful admin_addr login and validates user
     #  Given I am a registered admin_addr user
    Given I am a seaview registered "admin_addr" user
    And there is another registered regular user
    And I sign in with the correct details
    When I switch to a valid user
    Then I am on the my account page
    And switched user is signed in


  @pci
  Scenario: successful purchase path admin_addr login and purchase as admin
  #  Given I am a registered admin_addr user
    Given I am a seaview registered "admin_addr" user
    And I sign in with the correct details
    And I add a product to my shopping bag
    When I go to Shipping page as a signed in user and enter my address
    And I proceed to the Payment page
    And I pay by VISA_CREDIT_CARD
    And Order Confirmation page is displayed
    And I go to Customer Orders page
    Then the order appears on my orders page


  @pci
  Scenario: Successful purchase as admin_addr impersonating other user
    #  Given I am a registered admin_addr user
    Given I am a seaview registered "admin_addr" user
    And there is another registered regular user
    And I sign in with the correct details
    And I switch to a valid user
    And I add a product to my shopping bag
    When I go to Shipping page as a signed in user and enter my address
    And I proceed to the Payment page
    And I pay by VISA_CREDIT_CARD
    And Order Confirmation page is displayed
    And I sign out
    And I sign in as a valid user
    And I go to Customer Orders page
    Then the order appears on my orders page

  @pci
  Scenario: Checking admin_addr saved credit card details
    #  Given I am a registered admin_addr user
    Given I am a seaview registered "admin_addr" user
    And there is another registered regular user
    And I sign in as a valid user
    And a customer has saved their credit card details
    And I signout
    And I sign in with the correct details
    And I switch to a valid user
    When I go to User Credit Cards page
    Then 1 credit card details should be displayed


  @pci
  Scenario: adding  multiple  credit card details to a regular user's account as an admin_addr user
    #  Given I am a registered admin_addr user
    Given I am a seaview registered "admin_addr" user
    And there is another registered regular user
    And I sign in as a valid user
    And a customer has saved their credit card details
    And I signout
    And I sign in with the correct details
    And I switch to a valid user
    And I go to User Credit Cards page
    When I save 2 new credit card details
    Then 3 credit card details should be displayed

  @pci
  Scenario: Checking change card drop down displays all credit cards added by admin_addr user in purchase path
    #  Given I am a registered admin_addr user
    Given I am a seaview registered "admin_addr" user
    And there is another registered regular user
    And I sign in as a valid user
    And a customer has saved their credit card details
    And I signout
    And I sign in with the correct details
    And I switch to a valid user
    And I save 2 new credit card details
    And I add a product to my shopping bag
    When I proceed to the payment page using express checkout
    Then change card drop down should display all stored cards


  @pci
  Scenario: Checking that changing some card details added by admin_addr works correctly
    #  Given I am a registered admin_addr user
    Given I am a seaview registered "admin_addr" user
    And there is another registered regular user
    And I sign in as a valid user
    And a customer has saved their credit card details
    And I signout
    And I sign in with the correct details
    And I switch to a valid user
    And I save 2 new credit card details
    And I signout
    And I sign in as a valid user
    And I add a product to my shopping bag
    When I proceed to the payment page using express checkout
    And change the selected stored card details
    Then change card drop down should display all stored cards
    And I complete the purchase without entering details
    And Order Confirmation page is displayed


  @pci
  Scenario: Deletion of some saved credit cards as an admin_addr user
    #  Given I am a registered admin_addr user
    Given I am a seaview registered "admin_addr" user
    And there is another registered regular user
    And I sign in as a valid user
    And a customer has saved their credit card details
    And I signout
    And I sign in with the correct details
    And I switch to a valid user
    When I go to User Credit Cards page
    And I save 2 new credit card details
    And delete a saved credit card detail
    Then deleted credit card detail are not displayed


  Scenario: Edit customer email using Admin_addr tool, then login with old email and the customer should see authentication error
     #  Given I am a registered admin_addr user
    Given I am a seaview registered "admin_addr" user
    And there is another registered regular user
    And I sign in with the correct details
    And I switch to a valid user
    And I go to Account Details page
    When I change the user's email address
    And the user details are successfully changed
    And the user logs in with old email address
    Then the user sees authentication error message


  Scenario: Edit customer firstname and lastname using Admin_addr tool
     #  Given I am a registered admin_addr user
    Given I am a seaview registered "admin_addr" user
    And there is another registered regular user
    And I sign in with the correct details
    And I switch to a valid user
    And I go to Account Details page
    When I attempt to change the user's firstname and lastname
    And the user details are successfully changed
    And I signout
    And I sign in as a valid user
    Then   the user's firstname and lastname have changed


  Scenario: Edit customer password using Admin_addr tool and customer can login with the new password
     #  Given I am a registered admin_addr user
    Given I am a seaview registered "admin_addr" user
    And there is another registered regular user
    And I sign in with the correct details
    And I switch to a valid user
    And I go to Account Details page
    When I change the user's password
    And the user details are successfully changed
    And I sign out
    Then the regular user can login using new password


  Scenario: Edit customer password using Admin_addr tool and customer cannot login with the old password
     #  Given I am a registered admin_addr user
    Given I am a seaview registered "admin_addr" user
    And there is another registered regular user
    And I sign in with the correct details
    And I switch to a valid user
    And I go to Account Details page
    When I change the user's password
    And the user details are successfully changed
    And I sign out
    Then the regular user cannot login with the old password

  @pci
  Scenario: A user is partially registered after making a purchase without being registered beforehand
    Given I add a product to my shopping bag
    And I go to Shipping page as an anonymous user
    When I proceed to the Payment page
    And I pay by VISA_CREDIT_CARD
    Then Order Confirmation page is displayed
    And the user is partially registered


  @pci
  Scenario: Using admin_addr tool to fully register a partial user
    Given I add a product to my shopping bag
    And I go to Shipping page as an anonymous user
    And I proceed to the Payment page
    And I pay by VISA_CREDIT_CARD
    #And I am a registered admin_addr user
    And I am a seaview registered "admin_addr" user
    And I sign in with the correct details
    And I switch to a valid partial user
    When I am on Register New Account page
    And  I submit the completed registration details for the partial user
    Then I should see the thank you for registering message
    And I should be signed in
    And I am a fully registered user


  Scenario: Successful signin as EIP customer using admin_addr tool
    Given I am a seaview registered "admin_addr" user
    And there is another EIP registered user
    And I sign in with the correct details
    When I switch to a valid user
    Then I am on the my account page
    And switched user is signed in

  Scenario: Successful signin as EIP customer using admin tool
    Given I am a seaview registered "admin" user
    And there is another EIP registered user
    And I sign in with the correct details
    When I switch to a valid user
    Then I am on the my account page
    And switched user is signed in