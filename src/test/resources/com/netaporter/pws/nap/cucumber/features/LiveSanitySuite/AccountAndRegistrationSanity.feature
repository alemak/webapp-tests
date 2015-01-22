@napLiveSanity
Feature: AccountAndRegistrationSanity.feature - check new account creation, login, logout, add address, try to access secure pages while logged out


  Scenario: a user creates an account with valid details
    Given I submit valid details on the registration form
    Then I should see the thank you for registering message
    And I should be signed in
    When I go to Address Book page
    And I click on the add shipping address link
    And I add an address
    Then My shipping address will appear on the address details page
    And I signout
    When I sign in with the correct details
    Then I should be signed in


  Scenario: registration failed when using incorrect email address
    Given I go to Register New Account page
    When I submit an invalid email address on the registration form
    Then I should see an error message


  Scenario Outline: Attempt to access secured pages when not signed in
    Given I go to Home page
    When I try to access a secured page: <relativePath>
    Then I am taken to the <pageName> page

  Examples:
    |   relativePath                          |     pageName             |
    |  myaccount.nap                          |     signin               |
    |  customeraddresses.nap                  |     signin               |
    |  myaccount.nap?forwardTo=emailSettings  |     signin               |
    |  purchasepath.nap                       |     signinPurchasePath   |
    |  signinpurchasepath.nap                 |     signinPurchasePath   |
    |  editaddress.nap                        |     signin               |
    |  CustomerOrders.nap                     |     signin               |
    |  CustomerOrders.nap?order=1234567       |     signin               |
    |  catwishlist.nap						  |     signin	             |
    |  gridwishlist.nap					      |     signin	             |
    |  accountdetails.nap					  |     error		         |
    |  customerpreferences.nap				  |     signin	             |
    |  wishlist.nap							  |     signin	             |
    |  return.nap?execution=e1s1			  |     signin	             |
    |  return.nap?execution=e1s2			  |     signin	             |
    |  customervouchers.nap					  |     signin	             |
    |  customerreservations.nap				  |     signin	             |
    |  ConfirmReturn.nap?order=123455         |     signin		         |