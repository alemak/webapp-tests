@seaview @purch
Feature: Purchase Path

  @nap @Channelized @purch
  Scenario: User can add in stock products to shopping bag
    Given I add 1 in stock products directly to my bag
    Then I should see that product in the shopping bag


  @HappyPathAnonymousUser @nap @Channelized @pci
  Scenario Outline: An anonymous user completes the purchase using different payment types
    Given I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I go to Shipping page as an anonymous user
    And I proceed to the Payment page
    And I pay by <card type>
    Then Order Confirmation page is displayed
    And the Order Confirmation page should display the product(s)

  Examples:
    | card type             |
    | VISA_CREDIT_CARD      |


  @HappyPathSignedInUser @nap @Channelized @pci
  Scenario Outline: A signed in user completes the purchase using different payment types
    Given I am a seaview registered default user
    And I sign in with the correct details
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I go to Shipping page as a signed in user and enter my address
    And I proceed to the Payment page
    When I pay by <card type>
    Then Order Confirmation page is displayed
    And the Order Confirmation page should display the product(s)

  Examples:
    | card type             |
    | VISA_CREDIT_CARD      |


	@3DSecure @nap @NonChannelized
	Scenario Outline: Failed authorisation for 3d Secure payment
      Given I have selected <country> from the <channel> Channel
      And I go to Shopping Bag page
      And I add 1 in stock products directly to my bag
      And I go to Shopping Bag page
      And I go to Shipping page as an anonymous user
      And I proceed to the Payment page
      And I pay by <card type>
      And 3d secure payment page is displayed
      When 3d secure payment is not authorised
      Then I should see an error message on the payment page

    Examples:
      | country 			| card type         | channel 	|
      | China				|MAESTRO_3D_SECURE 	| apac   	|
      | United Kingdom  	|MAESTRO_3D_SECURE 	| intl 		|
    # there is no magic maestro card for am
    #  | United States       |MAESTRO_3D_SECURE 	| am 		|


  @3DSecure @nap @NonChannelized
  Scenario Outline: Successful authorisation for 3d Secure payment
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I go to Shipping page as an anonymous user
    And I proceed to the Payment page
    And I pay by <card type>
    And 3d secure payment page is displayed
    When 3d secure payment is authorised
    Then Order Confirmation page is displayed
    And the Order Confirmation page should display the product(s)

  Examples:
    | country 			| card type         | channel 	|
    | China				|MAESTRO_3D_SECURE 	| apac   	|
    | United Kingdom	|MAESTRO_3D_SECURE 	| intl 		|
# card isn't supported in AM
#    | United States     |MAESTRO_3D_SECURE 	| am 		|


  @3DSecure @nap @NonChannelized
  Scenario Outline: An anonymous user is shown the 3D secure form the purchase paying by 3D Secure Maestro card
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I go to Shipping page as an anonymous user
    And I proceed to the Payment page
    When I pay by MAESTRO_3D_SECURE
    Then 3d secure payment page is displayed

  Examples:
    | channel | country        |
    | intl    | United Kingdom |
#    card isn't supported in AM
#    | am      | United States  |
    | apac    | China          |


  @PurchaseDecline @nap @Channelized
  Scenario: Card payment declined
    Given I go to Shopping Bag page
    And I add 1 in stock products directly to my bag and save the stock
    And I go to Shopping Bag page
    And I go to Shipping page as an anonymous user
    And I proceed to the Payment page
    When I use an invalid card to pay
    Then Payment authorization error message is displayed
    And Product stock is not reduced


  # nonChannelized because of the way the shipping address works
  @ChangeShipping @nap @NonChannelized
  Scenario Outline: Edit the shipping address in the purchase path
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I go to Shipping page as an anonymous user
    When I click on Edit shipping address from Shipping options page
    And I enter an address from <country>
    And I proceed to the Payment page
    And I pay by VISA_CREDIT_CARD
    Then Order Confirmation page is displayed
    And the Order Confirmation page should display the product(s)

  Examples:
    | channel | country        |
    | apac    | Hong Kong      |
    | intl    | United Kingdom |
    | am      | United States  |


  @DifferentBilling @nap @Channelized
  Scenario: Anonymous user, completes purchase path with different modified billing address
    Given I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I go to Shipping page and use separate billing address
    And I proceed to the Payment page
    When I change billing address
    And I pay by VISA_CREDIT_CARD
    Then Order Confirmation page is displayed
    And the Order Confirmation page should display the product(s)


  @FailedPPSignIn @nap @Channelized
  Scenario: Registered user, Failed signin with wrong password on the purchase path
    Given I am a seaview registered default user
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
  	And I am not logged in
  	And I am on the purchase path signin page
  	When I sign in with the wrong password in the purchase path
  	Then I should see an error message on the sign in page


  @FailedPPSignIn @nap @Channelized
  Scenario: Registered user, Failed signin with empty email on the purchase path
    Given I am a seaview registered default user
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I am not logged in
    And I am on the purchase path signin page
    When I do not provide an email
    Then I should see an error message on the sign in page


  @FailedPPSignIn @nap @Channelized
  Scenario: Registered user, Failed signin with empty password on the purchase path
    Given I am a seaview registered default user
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I am not logged in
    And I am on the purchase path signin page
    When I do not provide a password
    Then I should see an error message on the sign in page


  @DifferentBilling @nap @Channelized
  Scenario: Anonymous user, Failed signin with empty email on the purchase path
    Given I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I am not logged in
    And I am on the purchase path signin page
    And I do not provide an email for anonymous checkout
    Then I should see an error message on the sign in page


  @DifferentBilling @nap @NonChannelized
  Scenario Outline: Anonymous user, successful signin with unknown email host on the purchase path
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I am not logged in
    And I am on the purchase path signin page
    And I provide an unknown email server
    And I check the email address confirmation check box
    And I checkout as an anonymous user
    And I proceed to the Payment page
    And I pay by <card type>
    And 3d secure payment page is displayed
    When 3d secure payment is authorised
    Then Order Confirmation page is displayed
    And the Order Confirmation page should display the product(s)

  Examples:
  | country 		| card type         | channel 	|
  | China			| MAESTRO_3D_SECURE | apac   	|
  | United Kingdom	| MAESTRO_3D_SECURE | intl 		|
# card isn't supported in AM
#    | United States     |MAESTRO_3D_SECURE 	| am 		|



  @SavedCreditCardDetails @nap @Channelized
  Scenario: Ensure when save credit card check box is selected card details are saved
	Given I am a seaview registered default user
	And I sign in with the correct details
	And a customer has saved their credit card details
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
	And I proceed to the payment page using express checkout
	Then the credit card details should have been remembered


  @SavedCreditCardDetails @nap @Channelized @pci
  Scenario: Ensure saved credit card details can be used for future purchase
	Given I am a seaview registered default user
	And I sign in with the correct details
	And a customer has saved their credit card details
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
	And I proceed to the payment page using express checkout
	Then the credit card details should have been remembered
	And I complete the purchase without entering details
	Then Order Confirmation page is displayed
    And the Order Confirmation page should display the product(s)


  @SavedCreditCardDetails @nap @Channelized @pci
  Scenario: When purchasing items without selecting the save credit card details checkbox, details are not saved
	Given I am a seaview registered default user
	And I sign in with the correct details
	And a customer who has not saved their credit card details
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
	And I proceed to the payment page using express checkout
	Then the credit card details should not have been remembered


  @SavedCreditCardDetails @nap @Channelized
  Scenario: Pay using a different payment method does not remove previously saved ccds
	Given I am a seaview registered default user
	And I sign in with the correct details
	And a customer has saved their credit card details
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
	And I proceed to the payment page using express checkout
	And I select the use different payment method link
    And I deselect the save credit cards box
    And I pay by MASTER_CARD
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    When I proceed to the payment page using express checkout
	Then the credit card details should have been remembered


  @SavedCreditCardDetails @nap @Channelized
  Scenario: Clicking use a different purchase method link prevents the user to proceed to purchase
	Given I am a seaview registered default user
	And I sign in with the correct details
	And a customer has saved their credit card details
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
	And I proceed to the payment page using express checkout
	When I select the use different payment method link
	And I attempt to purchase without entering details
	Then I should see an error message on the payment page


  @SavedCreditCardDetails @nap @Channelized
  Scenario: Clicking forget card details link does not clear card details even when not completing the purchase
	Given I am a seaview registered default user
	And I sign in with the correct details
	And a customer has saved their credit card details
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
	And I proceed to the payment page using express checkout
	When I select the use different payment method link
	And I go to Shopping Bag page
	And I proceed to the payment page using express checkout
	Then the credit card details should have been remembered


  @SavedCreditCardDetails @nap @Channelized
  Scenario: Remembered credit card details are not cleared after using a different payment type and unchecking the remember option
	Given I am a seaview registered default user
	And I sign in with the correct details
	And a customer has saved their credit card details
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
	And I proceed to the payment page using express checkout
    When I choose to use a different payment type
	And I deselect the save credit cards box
	And I pay by MASTER_CARD
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
	And I proceed to the payment page using express checkout
	Then the credit card details should have been remembered


  @3DSecure
  @SavedCreditCardDetails @nap @NonChannelized
  Scenario Outline: Ensure when remember credit details box is selected 3d secure card details are saved
	Given I have selected <country> from the <channel> Channel
    And I am a seaview registered default user
	And I sign in with the correct details
	And a customer has saved their 3d secure credit card details
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
	And I proceed to the payment page using express checkout
	Then the credit card details should have been remembered

  Examples:
    | channel | country        |
    | apac    | Australia      |
    | intl    | United Kingdom |
#     card does not work in AM
#    | am      | United States  |

  @3DSecure
  @SavedCreditCardDetails @nap @NonChannelized
  Scenario Outline: Ensure saved 3d secure credit card details can be used for future purchase
	Given I have selected <country> from the <channel> Channel
	And I am a seaview registered default user
	And I sign in with the correct details
	And a customer has saved their 3d secure credit card details
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
	And I proceed to the payment page using express checkout
	And I complete the purchase without entering details
	And 3d secure payment is authorised
	Then Order Confirmation page is displayed
    And the Order Confirmation page should display the product(s)

  Examples:
    | channel | country        |
    | apac    | Australia      |
    | intl    | United Kingdom |
#     card does not work in AM
#    | am      | United States  |

  @SavedCreditCardDetails
  @3DSecure @nap @NonChannelized
  Scenario Outline: Unchecked remember card details box still remembers details when proceeding to purchase with 3d secure card
	Given I have selected <country> from the <channel> Channel
	And I am a seaview registered default user
	And I sign in with the correct details
	And a customer has saved their 3d secure credit card details
	And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
	And I proceed to the payment page using express checkout
	When I choose to use a different payment type
	And I proceed to the payment page using express checkout
	Then the credit card details should have been remembered

  Examples:
    | channel | country        |
    | apac    | Australia      |
    | intl    | United Kingdom |
#    card does not work in AM
#    | am      | United States  |


  @SavedCreditCardDetails @nap @Channelized
  Scenario: Check saved credit card details are deleted when Shipping address is edited
	Given I am a seaview registered default user
	And I sign in with the correct details
	And a customer has saved their credit card details
	When I go to Address Book page
	And I edit the shipping address
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
	And I proceed to the payment page using express checkout
	Then the credit card details should not have been remembered


  @SavedCreditCardDetails @nap @Channelized
  Scenario: Check saved credit card details are deleted when Billing address is edited
	Given I am a seaview registered default user
	And I sign in with the correct details
	And a customer has saved their credit card details
	When I go to Address Book page
	And I edit the billing address
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
	And I proceed to the payment page using express checkout
	Then the credit card details should not have been remembered


    #enable this when discover card payment method goes live
   @NonChannelized @region=AM
  Scenario: An anonymous user completes a purchase on AM channel using Discover credit card
    Given I add a product to my shopping bag
    And I go to Shipping page as an anonymous user
    And I proceed to the Payment page
    And I pay by DISCOVER
    Then Order Confirmation page is displayed