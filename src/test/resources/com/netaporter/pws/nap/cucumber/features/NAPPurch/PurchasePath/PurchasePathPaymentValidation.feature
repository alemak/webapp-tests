@purch
Feature: Purchase Path Payment validation

  @ecomm @allChannels @purchasePath @paymentValidation @nap @Channelized
  Scenario: Payment details Javascript validations
    Given I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page

  # all fields have not been filled out
    When I click purchase now from the payment page
    Then Card number has error message Please enter your card number
    And Card name has error message Please enter the name on your card
    And Card security number has error message Please enter a valid security number
    And Card expiry date has error message Please enter a valid expiry date

  # blank card holder name is invalid
    When I click purchase now from the payment page
    Then The payment page field name has a mandatory field error
    And Card name has error message Please enter the name on your card

  # card number invalid characters test
    When I fill in the card number as abcd efg
    And I change focus on the payment page
    Then The payment page field card has a mandatory field error
    And Card number has error message Your card number contains invalid characters

  # too few numbers test with Visa card type selected
    When I click on payment option VISA_CREDIT_CARD
    And I fill in the card number as 1234 5678 9012
    And I change focus on the payment page
    Then The payment page field card has a mandatory field error
    And Card number has error message Your card number must be 16 characters

  # too many numbers test with Visa card type selected
    When I click on payment option VISA_CREDIT_CARD
    And I fill in the card number as 1234 5678 9012 3456 7892
    And I change focus on the payment page
    Then The payment page field card has a mandatory field error
    And Card number has error message Your card number must be 16 characters

  # too few numbers test with AMEX card type selected
    When I click on payment option AMERICAN_EXPRESS
    And I fill in the card number as 1234 5678 9012
    And I change focus on the payment page
    Then The payment page field card has a mandatory field error
    And Card number has error message Your card number must be 15 characters

  # too many numbers test with AMEX card type selected
    When I click on payment option AMERICAN_EXPRESS
    And I fill in the card number as 1234 5678 9012 3456 7892
    And I change focus on the payment page
    Then The payment page field card has a mandatory field error
    And Card number has error message Your card number must be 15 characters

  # too few numbers test with Mastercard card type selected
    When I click on payment option MASTER_CARD
    And I fill in the card number as 1234 5678 9012
    And I change focus on the payment page
    Then The payment page field card has a mandatory field error
    And Card number has error message Your card number must be 16 characters

  # too many numbers test with Mastercard card type selected
    When I click on payment option MASTER_CARD
    And I fill in the card number as 1234 5678 9012 3456 7892
    And I change focus on the payment page
    Then The payment page field card has a mandatory field error
    And Card number has error message Your card number must be 16 characters

  # invalid card number for card type Visa
    When I click on payment option VISA_CREDIT_CARD
    And I fill in the card number as 3434563145632132
    And I change focus on the payment page
    Then The payment page field card has a mandatory field error
    And Card number has error message Your card number is invalid

  # invalid card number for card type AMEX
    When I click on payment option AMERICAN_EXPRESS
    And I fill in the card number as 004000563145632
    And I change focus on the payment page
    Then The payment page field card has a mandatory field error
    And Card number has error message Your card number is invalid

  # invalid card number (Luhn check)
    When I click on payment option MASTER_CARD
    And I fill in the card number as 0000 9601 2200 1999
    And I change focus on the payment page
    Then The payment page field card has a mandatory field error
    And Card number has error message Your card number is invalid

  # security number invalid characters test
    When I fill in the security number as abc
    And I change focus on the payment page
    Then The payment page field cvs has a mandatory field error
    And Card security number has error message Your security number contains invalid characters

  # invalid security number for American Express (4 digits)
    When I click on payment option AMERICAN_EXPRESS
    And I fill in the security number as 123
    And I change focus on the payment page
    Then The payment page field cvs has a mandatory field error
    And Card security number has error message Your security number must be 4 digits long

  # invalid security number for Visa (3 digits)
    When I click on payment option VISA_CREDIT_CARD
    And I fill in the security number as 1234
    And I change focus on the payment page
    Then The payment page field cvs has a mandatory field error
    And Card security number has error message Your security number must be 3 digits long

  # invalid security number for Mastercard (3 digits)
    When I click on payment option MASTER_CARD
    And I fill in the security number as 1234
    And I change focus on the payment page
    Then The payment page field cvs has a mandatory field error
    And Card security number has error message Your security number must be 3 digits long

#  expiry dates in the past are invalid - can not be run in January as the expiry won't exist on the web page
    When I fill in the expiry month 01 and expiry year 14
    And I change focus on the payment page
    Then Card expiry date has error message Please enter a valid expiry date


  @ecomm @allChannels @purchasePath @paymentValidation @debug @nap @NonChannelized
  Scenario Outline: Payment details Javascript validations for Maestro card. Maestro option only displays if browsing country is set to a
    Given I have selected <country> from the <channel> Channel
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page

  # too few numbers test with Maestro card type selected
    When I click on payment option MAESTRO
    And I fill in the card number as 1234 5678 901
    And I change focus on the payment page
    Then The payment page field card has a mandatory field error
    And Card number has error message Your card number must be between 12 and 19 characters

  # too many numbers test with Maestro card type selected
    When I click on payment option MAESTRO
    And I fill in the card number as 1234 5678 9012 3456 7892
    And I change focus on the payment page
    Then The payment page field card has a mandatory field error
    And Card number has error message Your card number must be between 12 and 19 characters

  # invalid security number for Maestro (3 digits)
    When I click on payment option MAESTRO
    And I fill in the security number as 1234
    And I change focus on the payment page
    Then The payment page field cvs has a mandatory field error
    And Card security number has error message Your security number must be 3 digits long

  Examples:
    |country       |channel|
    |Australia     |apac   |
    |United Kingdom|intl   |


  @ecomm @region=INTL @channelSpecific @purchasePath @paymentValidation @nap @Channelized
  Scenario: Payment details Javascript validations for JCB
    Given I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page

  # too few numbers test with JCB card type selected
    When I click on payment option JCB
    And I fill in the card number as 1234 5678 9012
    And I change focus on the payment page
    Then The payment page field card has a mandatory field error
    And Card number has error message Your card number must be 16 characters

  # too many numbers test with JCB card type selected
    When I click on payment option JCB
    And I fill in the card number as 1234 5678 9012 3456 7892
    And I change focus on the payment page
    Then The payment page field card has a mandatory field error
    And Card number has error message Your card number must be 16 characters

  # invalid security number for JCB (3 digits)
    When I click on payment option JCB
    And I fill in the security number as 1234
    And I change focus on the payment page
    Then The payment page field cvs has a mandatory field error
    And Card security number has error message Your security number must be 3 digits long