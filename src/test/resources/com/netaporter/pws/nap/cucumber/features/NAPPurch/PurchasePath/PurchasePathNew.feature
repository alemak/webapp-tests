@seaview @nap @NonChannelized @purch
Feature: PurchasePathNew.feature: Purchase Path Core tests for user - anonymous or registered


  @ecomm @allChannels @purchasePath @storeSourceOfOrder
  Scenario Outline: Record device identifier against all order items from an anonymous user's purchase
  Given I am on <channel>
  And I update my country preference to use country <country>
  And I add 2 in stock products directly to my bag
  And I go to Shopping Bag page
  And I proceed to purchase
  When I sign in anonymously within the purchase path
  And I fill out a UK London shipping address
  And I click proceed to purchase from the shipping address page
  And I click proceed to purchase from the shipping options page
  And The payment page shows my shipping and billing addresses are the same
  And I pay with payment card <cardType> and verify the payment was successful
  And My order is confirmed
  Then the Order Confirmation page should display the product(s)
  And My device identifier and user agent are recorded against my order items

  Examples:
    | channel | country         | cardType         |
    | apac    | Hong Kong       | MASTER_CARD      |
    | intl    | United Kingdom  | VISA_CREDIT_CARD |
    | am      | United States   | AMERICAN_EXPRESS |


  @ecomm @allChannels @purchasePath @storeSourceOfOrder
  Scenario Outline: Record device identifier against all order items from a fully registered user's purchase
  Given I am on <channel>
  And I update my country preference to use country <country>
  And I submit valid details on the registration form
  And I add 2 in stock products directly to my bag
  And I go to Shopping Bag page
  And I proceed to purchase
  When I fill out a UK London shipping address
  And I click proceed to purchase from the shipping address page
  And I click proceed to purchase from the shipping options page
  And The payment page shows my shipping and billing addresses are the same
  And I pay with payment card <cardType> and verify the payment was successful
  And My order is confirmed
  And the Order Confirmation page should display the product(s)
  And My device identifier and user agent are recorded against my order items

  Examples:
    | channel | country         | cardType         |
    | apac    | Hong Kong       | MASTER_CARD      |
    | intl    | United Kingdom  | VISA_CREDIT_CARD |
    | am      | United States   | AMERICAN_EXPRESS |


  @ecomm @allChannels @purchasePath @storeSourceOfOrder
  Scenario Outline: Record device identifier against all order items from an anonymous user's purchase. Order contains a product
  and a boxed gift card.
  Given I am on <channel>
  And I update my country preference to use country <country>
  And I add a printed gift card of value 500 to my basket
  And I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  And I proceed to purchase
  When I sign in anonymously within the purchase path
  And I fill out a UK London shipping address
  And I click proceed to purchase from the shipping address page
  And I click proceed to purchase from the shipping options page
  And The payment page shows my shipping and billing addresses are the same
  And I pay with payment card <cardType> and verify the payment was successful
  And My order is confirmed
  Then the Order Confirmation page should display the product(s)
  And My device identifier and user agent are recorded against my order items

  Examples:
  | channel | cardType         |country       |
  | apac    | MASTER_CARD      |Hong Kong     |
  | intl    | VISA_CREDIT_CARD |United Kingdom|
  | am      | AMERICAN_EXPRESS |United States |


  @ecomm @allChannels @purchasePath @storeSourceOfOrder
  Scenario Outline: Record device identifier against all order items from an anonymous user's purchase. Order contains a product
  and a virtual gift card.
  Given I am on <channel>
  And I update my country preference to use country <country>
  And I add a virtual gift card of value 500 to my basket
  And I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  And I proceed to purchase
  When I sign in anonymously within the purchase path
  And I fill out a UK London shipping address
  And I click proceed to purchase from the shipping address page
  And I click proceed to purchase from the shipping options page
  And The payment page shows my shipping and billing addresses are the same
  And I pay with payment card <cardType> and verify the payment was successful
  And My order is confirmed
  Then the Order Confirmation page should display the product(s)
  And My device identifier and user agent are recorded against my order items

  Examples:
  | channel | cardType         | country       |
  | apac    | MASTER_CARD      | Hong Kong     |
  | intl    | VISA_CREDIT_CARD | United Kingdom|
  | am      | AMERICAN_EXPRESS | United States |


  @ecomm @allChannels @purchasePath @storeSourceOfOrder
  Scenario Outline: Record device identifier against all order items from an anonymous user's purchase. Order contains a product
  and a boxed and a virtual gift card.
  Given I am on <channel>
  And I update my country preference to use country <country>
  And I add a printed gift card of value 500 to my basket
  And I add a virtual gift card of value 500 to my basket
  And I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  And I proceed to purchase
  When I sign in anonymously within the purchase path
  And I fill out a UK London shipping address
  And I click proceed to purchase from the shipping address page
  And I click proceed to purchase from the shipping options page
  And The payment page shows my shipping and billing addresses are the same
  And I pay with payment card <cardType> and verify the payment was successful
  And My order is confirmed
  Then the Order Confirmation page should display the product(s)
  And My device identifier and user agent are recorded against my order items

  Examples:
  | channel | cardType         | country       |
  | apac    | MASTER_CARD      | Hong Kong     |
  | intl    | VISA_CREDIT_CARD | United Kingdom|
  | am      | AMERICAN_EXPRESS | United States |


  @purchasePath @pci
  Scenario Outline: Ask for CV2 number when a user with saved CCDs tries to purchase a virtual credit card
  Given I am on <channel>
  And I update my country preference to use country <country>
  And there is another registered regular user
  And I sign in as a valid user
  And a customer has saved their credit card details
  And I add a virtual gift card of value 500 to my basket
  When I proceed to the payment page using express checkout
  Then the security number textbox is displayed
  When I fill in the correct security number
  And I click purchase now from the payment page
  Then My order is confirmed
  And the Order Confirmation page should display the product(s)


  Examples:
    | channel | country        |
    | intl    | United Kingdom |
    | am      | United States  |
    | apac    | Hong Kong      |

#  this scenario need the payment interceptor to reject incorrect CV2
  @purchasePath @pci @nonJenkins
  Scenario Outline: Input a wrong CV2 number when trying to buy a virtual gift voucher with a saved credit card
    Given I am on <channel>
    And I update my country preference to use country <country>
    And there is another registered regular user
    And I sign in as a valid user
    And a customer has saved their credit card details
    And I add a virtual gift card of value 500 to my basket
    And I proceed to the payment page using express checkout
    When I fill in an incorrect security number
    And I click purchase now from the payment page
    Then The Order Confirmation page is NOT displayed
    And I verify a payment error is displayed on the payment page

  Examples:
    | channel | country        |
    | intl    | United Kingdom |
    | am      | United States  |
    | apac    | Hong Kong      |


  @ecomm @allChannels @purchasePath
  Scenario Outline: An anonymous user completes a purchase
  Given I am on <channel>
  And I update my country preference to use country <country>
  And I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  And I proceed to purchase
  When I sign in anonymously within the purchase path
  And I fill out a UK London shipping address
  And I click proceed to purchase from the shipping address page
  And I click proceed to purchase from the shipping options page
  And The payment page shows my shipping and billing addresses are the same
  And I pay with payment card <cardType> and verify the payment was successful
  And My order is confirmed
  And the Order Confirmation page should display the product(s)


  Examples:
    | channel | cardType         | country       |
    | apac    | MASTER_CARD      | Hong Kong     |
    | intl    | VISA_CREDIT_CARD | United Kingdom|
    | am      | AMERICAN_EXPRESS | United States |


  @ecomm @allChannels @purchasePath
  Scenario Outline: An anonymous user completes a purchase using a different billing address
  Given I am on <channel>
  And I update my country preference to use country <country>
  And I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  And I proceed to purchase
  When I sign in anonymously within the purchase path
  And I fill out a UK London shipping address
  And I choose to use a separate billing address
  And I click proceed to purchase from the shipping address page
  And I click proceed to purchase from the shipping options page
  And I fill out a UK non London billing address
  And I click proceed to purchase from the billing address page
  And The payment page shows my shipping and billing addresses are different
  And I pay with payment card <cardType> and verify the payment was successful
  And My order is confirmed
  And the Order Confirmation page should display the product(s)


  Examples:
    | channel | cardType         | country       |
    | apac    | MASTER_CARD      | Hong Kong     |
    | intl    | VISA_CREDIT_CARD | United Kingdom|
    | am      | AMERICAN_EXPRESS | United States |


  @ecomm @allChannels @purchasePath
  Scenario Outline: An existing user logged in anonymously completes a purchase signing in at the purchase path.
  Given I am on <channel>
  And I update my country preference to use country <country>
  And I am a seaview registered default user
  And I go to Home page
  And I am not logged in
  And I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  And I proceed to purchase
  When I sign in using the default customer within the purchase path
  And I fill out a UK London shipping address
  And I click proceed to purchase from the shipping address page
  And I click proceed to purchase from the shipping options page
  And The payment page shows my shipping and billing addresses are the same
  And I pay with payment card <cardType> and verify the payment was successful
  Then My order is confirmed
  And the Order Confirmation page should display the product(s)


  Examples:
    | channel | cardType         | country       |
    | apac    | MASTER_CARD      | Hong Kong     |
    | intl    | VISA_CREDIT_CARD | United Kingdom|
    | am      | AMERICAN_EXPRESS | United States |


  @ecomm @allChannels @purchasePath
  Scenario Outline: An existing user logged in anonymously completes a purchase signing in at the purchase path using a different billing address.
  Given I am on <channel>
  And I update my country preference to use country <country>
  And I am a seaview registered default user
  And I go to Home page
  And I am not logged in
  And I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  And I proceed to purchase
  When I sign in using the default customer within the purchase path
  And I fill out a UK London shipping address
  And I choose to use a separate billing address
  And I click proceed to purchase from the shipping address page
  And I click proceed to purchase from the shipping options page
  And I fill out a UK non London billing address
  And I click proceed to purchase from the billing address page
  And The payment page shows my shipping and billing addresses are different
  And I pay with payment card <cardType> and verify the payment was successful
  Then My order is confirmed
  And the Order Confirmation page should display the product(s)


  Examples:
    | channel | cardType         | country       |
    | apac    | MASTER_CARD      | Hong Kong     |
    | intl    | VISA_CREDIT_CARD | United Kingdom|
    | am      | AMERICAN_EXPRESS | United States |


  @ecomm @allChannels @purchasePath
  Scenario Outline: Item becomes sold out during purchase path
    Given I am on <channel>
    And I update my country preference to use country <country>
    And I add 1 in stock products directly to my bag and save the stock
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And I want all items in my shopping bag to go out of stock
    And I make payment using payment card <cardType>
    Then The product shows out of stock in the Shopping Bag page
    And I want all items in my shopping bag to go in stock

  Examples:
    | channel | cardType         | country       |
    | apac    | MASTER_CARD      | Hong Kong     |
    | intl    | VISA_CREDIT_CARD | United Kingdom|
    | am      | AMERICAN_EXPRESS | United States |


  @ecomm @allChannels @purchasePath
  Scenario Outline: Verify an error message is displayed on the payment page when an invalid card number is used
    Given I am on <channel>
    And I update my country preference to use country <country>
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And I populate the card details using payment card <cardType>
    And I fill in the card number as <invalidCardNumber>
    And I click purchase now from the payment page
    Then I verify a payment error is displayed on the payment page

  Examples:
    | channel | country        | cardType         |invalidCardNumber|
    | intl    | United Kingdom | MASTER_CARD      |5478050000000007 |
    | am      | United States  | VISA_CREDIT_CARD |4242425000000009 |
    | apac    | Hong Kong      | MASTER_CARD      |5478050000000007 |


  @ecomm @allChannels @purchasePath @pci @nonJenkins
  Scenario Outline: Verify an error message is displayed on the payment page when an invalid CV2 number is used
    Given I am on <channel>
    And I update my country preference to use country <country>
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And I populate the card details using payment card <cardType>
    And I fill in the security number as 999
    And I click purchase now from the payment page
    Then I am on the payment summary page
    Then I verify a payment error is displayed on the payment page

  Examples:
    | channel | cardType         | country       |
    | apac    | MASTER_CARD      | Hong Kong     |
    | intl    | VISA_CREDIT_CARD | United Kingdom|
    | am      | AMERICAN_EXPRESS | United States |