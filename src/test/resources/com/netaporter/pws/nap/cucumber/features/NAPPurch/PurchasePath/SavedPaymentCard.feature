@purch
Feature: Prevent the immediate deletion of saved payment cards
     In order to re-use a saved payment card
     As a customer of net-a-porter
     When I make a payment using a different payment method (PayPal) then my saved card should not be deleted


@ecomm @allChannels @purchasePath @paypal @nap @NonChannelized
Scenario Outline: Ensure a saved payment card is not deleted after paying with PayPal as a standard user

#  save the card
#  ==============
  Given I am on <channel>
  And I am a seaview registered default user
  And I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  And I proceed to purchase
  When I sign in using the default customer within the purchase path
  And I fill out a UK London shipping address
  And I click proceed to purchase from the shipping address page
  And I click proceed to purchase from the shipping options page
  And The payment page shows my shipping and billing addresses are the same
  And I pay with payment card <cardType> and verify the payment was successful
  And My order is confirmed
  And the Order Confirmation page should display the product(s)

#  pay with PayPal
#  ===============
  And I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  And I proceed to purchase
  And I use a different payment method
  And I click on payment option PAYPAL
  When I click purchase now from the payment page
  Then I am on the Paypal login page
  And My registered email address is pre-populated on the Paypal login page
  And I login to Paypal with user email nappaypalqa@nap.com and password drowssap
  And I am logged in to PayPal
  And I click on the Pay Now button to confirm the Paypal payment
  And My order is confirmed
  And the Order Confirmation page should display the product(s)

#  make sure the saved card is available
#  =====================================
  When I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  And I proceed to the payment page using express checkout
  Then I verify the last 4 digits of the payment card number is displayed

Examples:
  | channel | cardType         |
  | apac    | MASTER_CARD      |
  | intl    | VISA_CREDIT_CARD |
  | am      | AMERICAN_EXPRESS |


@ecomm @allChannels @purchasePath @nap @NonChannelized
Scenario Outline: Ensure an existing payment card is replaced when a new payment card is used

  #  save the card
  #  ==============
    Given I am on <channel>
    And I am a seaview registered default user
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    When I sign in using the default customer within the purchase path
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And The payment page shows my shipping and billing addresses are the same
    And I pay with payment card <cardType1> and verify the payment was successful
    And My order is confirmed
    And the Order Confirmation page should display the product(s)

  #  save a different payment card
  #  ============================================
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I use a different payment method
    And I pay with payment card <cardType2> and verify the payment was successful
    And My order is confirmed
    And the Order Confirmation page should display the product(s)


  #  make sure the new saved card is available
  #  =========================================
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to the payment page using express checkout
    Then I verify the last 4 digits of the payment card number is displayed

  Examples:
    | channel | cardType1        | cardType2         |
    | apac    | MASTER_CARD      | VISA_CREDIT_CARD  |
    | intl    | MASTER_CARD      | VISA_CREDIT_CARD  |
    | am      | MASTER_CARD      | VISA_CREDIT_CARD  |


  @ecomm @allChannels @purchasePath @nap @NonChannelized
  Scenario Outline: Ensure an existing payment card is not deleted and when a new invalid payment card

  #  save the card
  #  ==============
    Given I am on <channel>
    And I am a seaview registered default user
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    When I sign in using the default customer within the purchase path
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And The payment page shows my shipping and billing addresses are the same
    And I pay with payment card <cardType1> and verify the payment was successful
    And I save the card number
    And My order is confirmed
    And the Order Confirmation page should display the product(s)

  #  save a different payment card
  #  ============================================
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I use a different payment method
    And I populate the card details using payment card <cardType2>
    And I fill in the card number as <invalidCardNumber>
    And I click purchase now from the payment page
    Then I verify the payment is not authorized

  #  make sure existing saved card is available
  #  ==========================================
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    Then I verify the last 4 digits of the payment card number is displayed

  Examples:
    | channel | cardType1        | cardType2         | invalidCardNumber   |
    | apac    | MASTER_CARD      | VISA_CREDIT_CARD  | 5478050000000007    |
    | intl    | MASTER_CARD      | VISA_CREDIT_CARD  | 5478050000000007    |
    | am      | VISA_CREDIT_CARD | VISA_CREDIT_CARD  | 4444333322221194    |

    #todo: add scenario for saved card being deleted when a new card is used and checkbox is unchecked