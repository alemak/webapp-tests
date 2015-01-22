@purch
Feature: FailedPaymentErrorMessages.feature Purchase Path Failed Payment Error Messages Tests

  ################################### START GENERIC ERROR MESSAGE TESTS   ##############################################################

  @ecomm @allChannels @purchasePath @nap @NonChannelized
  Scenario Outline: The generic payment failure error message is shown to the customer when their payment fails
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill in the shipping address with country <shippingCountry> and state <state> and postcode <postcode> in the address form
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    When I click on payment option <cardType>
    And I fill in the card number as <cardNumber>
    And I fill in the card holder name as Mr Card Holder
    And I fill in the security number as <securityNumber>
    And I fill in the expiry month 03 and expiry year 16
    And I click purchase now from the payment page
    Then An error message is displayed containing the text We are sorry, your payment has not been authorized. Please try again or use a different payment method.
    And An error message is displayed containing the text If you are unable to proceed with your order, please contact us on <phoneNumber> for assistance.

  Examples:
    |channel |country       |shippingCountry|state   |postcode|cardType        |cardNumber      |securityNumber| phoneNumber           |
    |am      |United States |United States  |New York|10001   |VISA_CREDIT_CARD|1000070000000001|123           | 1 877 6789 NAP (627)  |
    |intl    |United Kingdom|United Kingdom |London  |SW185RH |AMERICAN_EXPRESS|340000000000025 |3434          | 0800 044 5700         |
    |am      |United States |Argentina      |Test    |123456  |VISA_CREDIT_CARD|1000070000000001|123           | 1 877 6789 NAP (627)  |
    |intl    |United Kingdom|France         |Test    |123456  |AMERICAN_EXPRESS|340000000000025 |3434          | +44 (0)203 471 4510   |
    |apac    |Hong Kong     |Australia      |Brisbane|4001    |AMERICAN_EXPRESS|340000000000025 |3434          | +44 (0)330 022 5700   |


  @ecomm @region=APAC @channelSpecific @purchasePath @nap @NonChannelized
  Scenario Outline: The generic payment failure error message is shown to the customer when their payment fails for Hong Kong
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a HK non Premier shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    When I click on payment option <cardType>
    And I fill in the card number as <cardNumber>
    And I fill in the card holder name as Mr Card Holder
    And I fill in the security number as <securityNumber>
    And I fill in the expiry month 03 and expiry year 16
    And I click purchase now from the payment page
    Then An error message is displayed containing the text We are sorry, your payment has not been authorized. Please try again or use a different payment method.
    And An error message is displayed containing the text If you are unable to proceed with your order, please contact us on <phoneNumber> for assistance.

  Examples:
    |channel |country       |cardType        |cardNumber      |securityNumber|phoneNumber        |
    |apac    |Hong Kong     |AMERICAN_EXPRESS|340000000000025 |3434          |+44 (0)330 022 5700|


  @ecomm @allChannels @purchasePath @French @nap @NonChannelized
  Scenario Outline: Customer is shopping in French. The generic payment failure error message is shown to the customer when their payment fails.
    Given I am on <channel>
    And I am on change country page
    And I change my language to <language>
    And I go to Shopping Bag page
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill in the shipping address with country <shippingCountry> and state <state> and postcode <postcode> in the address form
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    When I click on payment option <cardType>
    And I fill in the card number as <cardNumber>
    And I fill in the card holder name as Mr Card Holder
    And I fill in the security number as <securityNumber>
    And I fill in the expiry month 03 and expiry year 16
    And I click purchase now from the payment page
    Then An error message is displayed containing the text Nous sommes désolés, votre paiement n’a pas été autorisé. Veuillez réessayer ou choisir un autre moyen de paiement.
    And An error message is displayed containing the text Si vous n’arrivez pas à procéder à l’achat, veuillez nous contacter au <phoneNumber>.

  Examples:
    | channel | language | shippingCountry | state    | postcode | cardType         | cardNumber      | securityNumber| phoneNumber          |
    | intl    | French   | Royaume-Uni     | London   | SW185RH  | AMERICAN_EXPRESS | 340000000000025 | 3434          | 0800 044 5700        |
    | am      | French   | Colombie        | Test     | 10001    | VISA_CREDIT_CARD | 1000070000000001| 123           | 1 877 6789 NAP (627) |
    | apac    | French   | Australie       | QLD      | 4215     | VISA_CREDIT_CARD | 4508751000000262| 123           | +44 (0)330 022 5700  |


  @ecomm @allChannels @purchasePath @German @nap @NonChannelized
  Scenario Outline: Customer is shopping in German. The generic payment failure error message is shown to the customer when their payment fails.
    Given I am on <channel>
    And I am on change country page
    And I change my language to <language>
    And I go to Shopping Bag page
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill in the shipping address with country <shippingCountry> and state <state> and postcode <postcode> in the address form
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    When I click on payment option <cardType>
    And I fill in the card number as <cardNumber>
    And I fill in the card holder name as Mr Card Holder
    And I fill in the security number as <securityNumber>
    And I fill in the expiry month 03 and expiry year 16
    And I click purchase now from the payment page
    Then An error message is displayed containing the text Es tut uns leid – Ihre Zahlung wurde nicht zugelassen. Bitte versuchen Sie es mit einer anderen Zahlungsmethode erneut.
    Then An error message is displayed containing the text Wenn es Ihnen nicht möglich sein sollte, Ihre Bestellung abzuschließen, kontaktieren Sie uns bitte unter der folgenden Nummer: <phoneNumber>

  Examples:
    | channel |language | shippingCountry        | state    | postcode | cardType         | cardNumber       | securityNumber| phoneNumber         |
    | intl    |German   | Vereinigtes Königreich | London   | SW185RH  | AMERICAN_EXPRESS | 340000000000025  | 3434          | 0800 044 5700       |
    | am      |German   | Argentinien            | Test     | 10001    | VISA_CREDIT_CARD | 1000070000000001 | 123           | 1 877 6789 NAP (627)|
    | apac    |German   | Australien             | QLD      | 4215     | VISA_CREDIT_CARD | 4508751000000262 | 123           | +44 (0)330 022 5700 |


  @ecomm @allChannels @purchasePath @nap @NonChannelized
  Scenario Outline: Failed 3D Secure authorization and customer is shown the generic payment error message
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And I click on payment option <cardType>
    And I fill in the card number as <cardNumber>
    And I fill in the card holder name as Mr Card Holder
    And I fill in the security number as <securityNumber>
    And I fill in the expiry month 01 and expiry year 16
    And I fill in the card issue number <cardIssueNumber>
    And I click purchase now from the payment page
    And The 3D secure payment page is displayed
    When The 3D secure payment is not authorised
    Then An error message is displayed containing the text We are sorry, your payment has not been authorized. Please try again or use a different payment method.
    And An error message is displayed containing the text If you are unable to proceed with your order, please contact us on <phoneNumber> for assistance.

  Examples:
    | country 			| channel| cardType         | cardNumber 	      | securityNumber| cardIssueNumber| phoneNumber        |
    | United Kingdom  	| intl   | MAESTRO_3D_SECURE| 6799998900000000014 | 679 		  | 1              | 0800 044 5700      |
    | China             | apac   | MAESTRO_3D_SECURE| 6799998900000000014 | 679           | 1              | +44 (0)330 022 5700|


  ################################### END GENERIC ERROR MESSAGE TESTS   ##############################################################


  ################################### START CUSTOMER CARE PHONE NUMBERS ##############################################################

  @ecomm @allChannels @purchasePath @nap @NonChannelized
  Scenario Outline: Customer shopping on the Givenchy APP is shown the Givenchy customer care phone number on the payment page when payment fails on submission.
  If a customer is shipping locally (that is shipping to the channel's default country), the local Givenchy phone number is displayed. If a customer is shipping internationally,
  the international Givenchy customer care phone number is displayed.
    Given I use a mobile device with the following app Givenchy_Women-1.0
    And I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I add a NAP product from designer Givenchy to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill in the shipping address with country <shippingCountry> and state <state> and postcode <postcode> in the address form
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    When I click on payment option <cardType>
    And I fill in the card number as <cardNumber>
    And I fill in the card holder name as Mr Card Holder
    And I fill in the security number as <securityNumber>
    And I fill in the expiry month 03 and expiry year 16
    And I click purchase now from the payment page
    Then An error message is displayed containing the text We are sorry,
    And The <phoneNumberType> customer care phone number displayed in the error message is correct

  Examples:
    |channel |country       |shippingCountry|state   |postcode|cardType        |cardNumber      |securityNumber|phoneNumberType       |
    |am      |United States |United States  |New York|10001   |VISA_CREDIT_CARD|1000070000000001|123           |Givenchy Local        |
    |intl    |United Kingdom|United Kingdom |London  |SW185RH |AMERICAN_EXPRESS|340000000000025 |3434          |Givenchy Local        |
    |am      |United States |Argentina      |Test    |123456  |VISA_CREDIT_CARD|1000070000000001|123           |Givenchy International|
    |intl    |United Kingdom|France         |Test    |123456  |AMERICAN_EXPRESS|340000000000025 |3434          |Givenchy International|
    |apac    |Hong Kong     |Australia      |Brisbane|4001    |AMERICAN_EXPRESS|340000000000025 |3434          |Givenchy International|


  @ecomm @region=APAC @channelSpecific @purchasePath @nap @NonChannelized
  Scenario Outline: Customer shopping on the Givenchy APP is shown the Givenchy customer care phone number on the payment page when payment fails on submission.
  If a customer is shipping locally (that is shipping to the channel's default country), the local Givenchy phone number is displayed. If a customer is shipping internationally,
  the international Givenchy customer care phone number is displayed.
    Given I use a mobile device with the following app Givenchy_Women-1.0
    And I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I add a NAP product from designer Givenchy to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a HK Premier shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    When I click on payment option <cardType>
    And I fill in the card number as <cardNumber>
    And I fill in the card holder name as Mr Card Holder
    And I fill in the security number as <securityNumber>
    And I fill in the expiry month 03 and expiry year 16
    And I click purchase now from the payment page
    Then An error message is displayed containing the text We are sorry,
    And The <phoneNumberType> customer care phone number displayed in the error message is correct

  Examples:
    |channel |country       |cardType         |cardNumber      |securityNumber|phoneNumberType       |
    |apac    |Hong Kong     |AMERICAN_EXPRESS |340000000000025 |3434          |Givenchy Local        |


  @ecomm @allChannels @purchasePath @nap @NonChannelized
  Scenario Outline: Customer shopping on the website is shown the standard customer care phone number on the payment page when payment fails on submission.
  If a customer is shipping locally (that is shipping to the channel's default country), the local phone number is displayed. If a customer is shipping internationally,
  the international customer care phone number is displayed.
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill in the shipping address with country <shippingCountry> and state <state> and postcode <postcode> in the address form
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    When I click on payment option <cardType>
    And I fill in the card number as <cardNumber>
    And I fill in the card holder name as Mr Card Holder
    And I fill in the security number as <securityNumber>
    And I fill in the expiry month 03 and expiry year 16
    And I click purchase now from the payment page
    Then An error message is displayed containing the text We are sorry,
    And The <phoneNumberType> customer care phone number displayed in the error message is correct

  Examples:
    |channel |country       |shippingCountry|state   |postcode|cardType        |cardNumber      |securityNumber|phoneNumberType   |
    |am      |United States |United States  |New York|10001   |VISA_CREDIT_CARD|1000070000000001|123           |Local             |
    |intl    |United Kingdom|United Kingdom |London  |SW185RH |AMERICAN_EXPRESS|340000000000025 |3434          |Local             |
    |am      |United States |Argentina      |Test    |123456  |VISA_CREDIT_CARD|1000070000000001|123           |AM International  |
    |intl    |United Kingdom|France         |Test    |123456  |AMERICAN_EXPRESS|340000000000025 |3434          |INTL International|
    |apac    |Hong Kong     |Australia      |Brisbane|4001    |AMERICAN_EXPRESS|340000000000025 |3434          |APAC International|



  @ecomm @region=APAC @channelSpecific @purchasePath @nap @NonChannelized
  Scenario Outline: Customer shopping on the website is shown the standard customer care phone number on the payment page when payment fails on submission.
  If a customer is shipping locally (that is shipping to the channel's default country), the local phone number is displayed. If a customer is shipping internationally,
  the international customer care phone number is displayed.
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a HK non Premier shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    When I click on payment option <cardType>
    And I fill in the card number as <cardNumber>
    And I fill in the card holder name as Mr Card Holder
    And I fill in the security number as <securityNumber>
    And I fill in the expiry month 03 and expiry year 16
    And I click purchase now from the payment page
    Then An error message is displayed containing the text We are sorry,
    And The <phoneNumberType> customer care phone number displayed in the error message is correct

  Examples:
    |channel |country       |cardType        |cardNumber      |securityNumber|phoneNumberType   |
    |apac    |Hong Kong     |AMERICAN_EXPRESS|340000000000025 |3434          |Local             |


  ####################################### END CUSTOMER PHONE NUMBERS #########################################################################