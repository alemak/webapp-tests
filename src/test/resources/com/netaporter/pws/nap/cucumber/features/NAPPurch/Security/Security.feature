@purch
Feature: Users cannot access secured pages without logging
  They will be taken either to signing page or error page.

  @nap @Channelized
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
