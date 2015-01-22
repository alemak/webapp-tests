@nap @purch
Feature: Purchase Path Paypal Payments Tests


  ######################################## Start Anonymous Users Tests #########################################
  @ecomm @allChannels @purchasePath @paypal @Channelized
   Scenario: Anonymous user buys an item and pays by Paypal, ending up on the confirmation page.
   Given I add 1 in stock products directly to my bag
   And I go to Shopping Bag page
   And I proceed to purchase
   And I attempt to purchase anonymously and reach the payment page
   And I review my order summary details on the payment page
   And I click on payment option PAYPAL
   When I click purchase now from the payment page
   Then I am on the Paypal login page
   And My registered email address is pre-populated on the Paypal login page
   And I login to Paypal with user email nappaypalqa@nap.com and password drowssap
   And I am logged in to PayPal
   And The list of items in my Paypal account are the same as in the order summary on the payment page
   And The total amount in my Paypal account is the same as in the order summary on the payment page
   And The postage and packaging amount in my Paypal account is the same as in the order summary on the payment page
   And The postal address in my Paypal account is the same as in the order summary on the payment page
   And I click on the Pay Now button to confirm the Paypal payment
   And My order is confirmed
   And the Order Confirmation page should display the product(s)


  @ecomm @allChannels @purchasePath @paypal @Channelized
  Scenario: Anonymous user buys a printed gift card and another pid, pays by Paypal and ends up on the confirmation page.
  Given I add a printed gift card of value 500 to my basket
  And I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  And I proceed to purchase
  And I attempt to purchase anonymously and reach the payment page
  And I review the items in the order summary
  And I review the total amount to pay
  And I review the total postage and packaging costs to pay
  And I review the chosen shipping address
  And I click on payment option PAYPAL
  When I click purchase now from the payment page
  Then I am on the Paypal login page
  And My registered email address is pre-populated on the Paypal login page
  And I login to Paypal with user email nappaypalqa@nap.com and password drowssap
  And I am logged in to PayPal
  And The list of items in my Paypal account are the same as in the order summary on the payment page
  And The total amount in my Paypal account is the same as in the order summary on the payment page
  And The postage and packaging amount in my Paypal account is the same as in the order summary on the payment page
  And I click on the Pay Now button to confirm the Paypal payment
  And My order is confirmed
  And the Order Confirmation page should display the product(s)


  @ecomm @allChannels @purchasePath @paypal @Channelized
  Scenario:Anonymous user buys a virtual gift card, pays by Paypal and ends up on the confirmation page.
  Given I add a virtual gift card of value 500 to my basket
  And I go to Shopping Bag page
  And I proceed to purchase
  And I sign in anonymously within the purchase path
  And I fill out a UK London billing address
  And I click proceed to purchase from the billing address page
  And I review the items in the order summary
  And I review the total amount to pay
  And I review the total postage and packaging costs to pay
  And I click on payment option PAYPAL
  When I click purchase now from the payment page
  Then I am on the Paypal login page
  And My registered email address is pre-populated on the Paypal login page
  And I login to Paypal with user email nappaypalqa@nap.com and password drowssap
  And I am logged in to PayPal
  And The list of items in my Paypal account are the same as in the order summary on the payment page
  And The total amount in my Paypal account is the same as in the order summary on the payment page
  And The postage and packaging amount in my Paypal account is the same as in the order summary on the payment page
  And I click on the Pay Now button to confirm the Paypal payment
  And My order is confirmed
  And the Order Confirmation page should display the product(s)


  @ecomm @allChannels @purchasePath @paypal @debug @Channelized
 Scenario:Anonymous user buys two gift cards, pays by Paypal, and ends up on the confirmation page.
  Given I add a virtual gift card of value 500 to my basket
  And I add a printed gift card of value 500 to my basket
  And I go to Shopping Bag page
  And I proceed to purchase
  And I attempt to purchase anonymously and reach the payment page
  And I review the items in the order summary
  And I review the total amount to pay
  And I review the total postage and packaging costs to pay
  And I review the chosen shipping address
  And I click on payment option PAYPAL
  When I click purchase now from the payment page
  Then I am on the Paypal login page
  And My registered email address is pre-populated on the Paypal login page
  And I login to Paypal with user email nappaypalqa@nap.com and password drowssap
  And I am logged in to PayPal
  And The list of items in my Paypal account are the same as in the order summary on the payment page
  And The total amount in my Paypal account is the same as in the order summary on the payment page
  And The postage and packaging amount in my Paypal account is the same as in the order summary on the payment page
  And I click on the Pay Now button to confirm the Paypal payment
  And My order is confirmed
  And the Order Confirmation page should display the product(s)


  @ecomm @allChannels @purchasePath @paypal @Channelized
  Scenario:Anonymous user buys several items, selects Paypal as payment method, and cancels the payment on the Paypal login page.
  Given I add a virtual gift card of value 500 to my basket
  And I add a printed gift card of value 500 to my basket
  And I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  And I proceed to purchase
  And I attempt to purchase anonymously and reach the payment page
  And I review the items in the order summary
  And I review the total amount to pay
  And I review the total postage and packaging costs to pay
  And I review the chosen shipping address
  And I click on payment option PAYPAL
  And I click purchase now from the payment page
  And I am on the Paypal login page
  And My registered email address is pre-populated on the Paypal login page
  When I cancel my PayPal payment
  Then I verify that I am on the payment order summary page


  @ecomm @allChannels @purchasePath @paypal @NonChannelized @region=APAC
  Scenario: Address validation for an anonymous user without a postcode paying for an item using Paypal
    Given I am on apac
    And I update my country preference to use country Australia
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I attempt to purchase anonymously with a missing postcode in my shipping address and reach the payment page
    And I review my order summary details on the payment page
    And I click on payment option PAYPAL
    When I click purchase now from the payment page
    Then I am on the Paypal login page
    And My registered email address is pre-populated on the Paypal login page
    And I login to Paypal with user email nappaypalqa@nap.com and password drowssap
    And I am logged in to PayPal
    And The list of items in my Paypal account are the same as in the order summary on the payment page
    And The total amount in my Paypal account is the same as in the order summary on the payment page
    And The postage and packaging amount in my Paypal account is the same as in the order summary on the payment page
    And The postal address in my Paypal account contains the following character -
    And I click on the Pay Now button to confirm the Paypal payment
    And My order is confirmed
    And the Order Confirmation page should display the product(s)

  ######################################## End Anonymous Users Tests ############################################

  ######################################## Start Registered Users Tests #########################################

  @ecomm @allChannels @purchasePath @paypal @Channelized
  Scenario: Registered user buys an item, and pays by Paypal, ending up on the confirmation page.
  Given I am a seaview registered default user
  And I am logged in
  And I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  And I proceed to purchase
  And I fill out a UK London shipping address
  And I click proceed to purchase from the shipping address page
  And I click proceed to purchase from the shipping options page
  And I review the items in the order summary
  And I review the total amount to pay
  And I review the total postage and packaging costs to pay
  And I review the chosen shipping address
  And I click on payment option PAYPAL
  When I click purchase now from the payment page
  Then I am on the Paypal login page
  And My registered email address is pre-populated on the Paypal login page
  And I login to Paypal with user email nappaypalqa@nap.com and password drowssap
  And I am logged in to PayPal
  And The list of items in my Paypal account are the same as in the order summary on the payment page
  And The total amount in my Paypal account is the same as in the order summary on the payment page
  And The postage and packaging amount in my Paypal account is the same as in the order summary on the payment page
  And The postal address in my Paypal account is the same as in the order summary on the payment page
  And I click on the Pay Now button to confirm the Paypal payment
  And My order is confirmed
  And the Order Confirmation page should display the product(s)


  @ecomm @allChannels @purchasePath @paypal @Channelized
  Scenario:Registered user buys a printed gift card, and pays by Paypal, ending up on the confirmation page.
  Given I am a seaview registered default user
  And I am logged in
  And I add a printed gift card of value 500 to my basket
  And I go to Shopping Bag page
  And I proceed to purchase
  And I fill out a UK London shipping address
  And I click proceed to purchase from the shipping address page
  And I click proceed to purchase from the shipping options page
  And I review the items in the order summary
  And I review the total amount to pay
  And I review the total postage and packaging costs to pay
  And I review the chosen shipping address
  And I click on payment option PAYPAL
  When I click purchase now from the payment page
  And I am on the Paypal login page
  And My registered email address is pre-populated on the Paypal login page
  And I login to Paypal with user email nappaypalqa@nap.com and password drowssap
  And I am logged in to PayPal
  And The list of items in my Paypal account are the same as in the order summary on the payment page
  And The total amount in my Paypal account is the same as in the order summary on the payment page
  And The postage and packaging amount in my Paypal account is the same as in the order summary on the payment page
  And I click on the Pay Now button to confirm the Paypal payment
  And My order is confirmed
  And the Order Confirmation page should display the product(s)


  @ecomm @allChannels @purchasePath @paypal @Channelized
  Scenario:Registered user buys a virtual gift card, and pays by Paypal, ending up on the confirmation page.
  Given I am a seaview registered default user
  And I am logged in
  And I add a virtual gift card of value 500 to my basket
  And I go to Shopping Bag page
  And I proceed to purchase
  And I fill out a UK London billing address
  And I click proceed to purchase from the billing address page
  And I review the items in the order summary
  And I review the total amount to pay
  And I review the total postage and packaging costs to pay
  And I click on payment option PAYPAL
  When I click purchase now from the payment page
  Then I am on the Paypal login page
  And My registered email address is pre-populated on the Paypal login page
  And I login to Paypal with user email nappaypalqa@nap.com and password drowssap
  And I am logged in to PayPal
  And The list of items in my Paypal account are the same as in the order summary on the payment page
  And The total amount in my Paypal account is the same as in the order summary on the payment page
  And The postage and packaging amount in my Paypal account is the same as in the order summary on the payment page
  And I click on the Pay Now button to confirm the Paypal payment
  And My order is confirmed
  And the Order Confirmation page should display the product(s)


  @ecomm @allChannels @purchasePath @paypal @Channelized
  Scenario:Registered user buys a mix of normal items and gift cards, pays by Paypal, and ends up on the confirmation page.
  Given I am a seaview registered default user
  And I am logged in
  And I add a printed gift card of value 500 to my basket
  And I add a virtual gift card of value 500 to my basket
  And I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  And I proceed to purchase
  And I fill out a UK London shipping address
  And I click proceed to purchase from the shipping address page
  And I click proceed to purchase from the shipping options page
  And I review the items in the order summary
  And I review the total amount to pay
  And I review the total postage and packaging costs to pay
  And I review the chosen shipping address
  And I click on payment option PAYPAL
  When I click purchase now from the payment page
  Then I am on the Paypal login page
  And My registered email address is pre-populated on the Paypal login page
  And I login to Paypal with user email nappaypalqa@nap.com and password drowssap
  And I am logged in to PayPal
  And The list of items in my Paypal account are the same as in the order summary on the payment page
  And The total amount in my Paypal account is the same as in the order summary on the payment page
  And The postage and packaging amount in my Paypal account is the same as in the order summary on the payment page
  And I click on the Pay Now button to confirm the Paypal payment
  And My order is confirmed
  And the Order Confirmation page should display the product(s)


  @ecomm @allChannels @purchasePath @paypal  @Channelized
  Scenario:Registered user buys an item, selects Paypal as payment method, and cancels the payment on the Paypal payment confirmation page.
  Given I am a seaview registered default user
  And I am logged in
  And I add a virtual gift card of value 500 to my basket
  And I add a printed gift card of value 500 to my basket
  And I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  And I proceed to purchase
  And I fill out a UK London shipping address
  And I click proceed to purchase from the shipping address page
  And I click proceed to purchase from the shipping options page
  And I review the items in the order summary
  And I review the total amount to pay
  And I review the total postage and packaging costs to pay
  And I review the chosen shipping address
  And I click on payment option PAYPAL
  And I click purchase now from the payment page
  And I am on the Paypal login page
  And My registered email address is pre-populated on the Paypal login page
  And I login to Paypal with user email nappaypalqa@nap.com and password drowssap
  And I am logged in to PayPal
  When I cancel my PayPal payment
  Then I verify that I am on the payment order summary page


  @ecomm @allChannels @purchasePath @paypal @NonChannelized
  Scenario Outline:Registered user buys an item and makes a payment partial payment by store credit and Paypal, and completes the purchase.
  Given I am on <channel>
  And I update my country preference to use country <country>
  And I am a seaview registered default user
  And I am logged in
  And I have store credit to the amount of <amount> <currency>
  And I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  And I proceed to purchase
  And I fill out a UK London shipping address
  And I click proceed to purchase from the shipping address page
  And I click proceed to purchase from the shipping options page
  And I review the items in the order summary
  And I review the store credit amount
  And I review the total amount to pay
  And I review the total postage and packaging costs to pay
  And I review the chosen shipping address
  And I click on payment option PAYPAL
  When I click purchase now from the payment page
  Then I am on the Paypal login page
  And My registered email address is pre-populated on the Paypal login page
  And I login to Paypal with user email nappaypalqa@nap.com and password drowssap
  And I am logged in to PayPal
  And The list of items in my Paypal account are the same as in the order summary on the payment page
  And The store credit amount applied in my Paypal account is the same as in the order summary on the payment page
  And The total amount in my Paypal account is the same as in the order summary on the payment page
  And The postage and packaging amount in my Paypal account is the same as in the order summary on the payment page
  And I click on the Pay Now button to confirm the Paypal payment
  And My order is confirmed
  And the Order Confirmation page should display the product(s)


  Examples:
  | channel | country        | amount | currency |
  | intl    | United Kingdom | 5      | GBP      |
  | am      | United States  | 5      | USD      |
  | apac    | Hong Kong      | 5      | HKD      |
  | intl    | France         | 5      | EUR      |