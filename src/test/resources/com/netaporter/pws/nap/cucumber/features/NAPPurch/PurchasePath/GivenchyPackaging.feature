@purch
Feature: Packaging for Givenchy orders using / not using the Givenchy app


  @ecomm @channelSpecific @promotions=INTL @nap @NonChannelized
  Scenario: Givenchy packaging option is not available from main site for London Premier addresses
    Given I go to Home page
    And I add a NAP product from designer Givenchy to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK London shipping address
    When I click proceed to purchase from the shipping address page
    Then Packaging option NET-A-PORTER Signature is available
    And Packaging option Discreet is available
    And Packaging option GIVENCHY is not available


  @ecomm @channelSpecific @region=INTL @packaging @nap @NonChannelized
  Scenario: Givenchy packaging option is not available from main site for non Premier addreses
    Given I go to Home page
    And I add a NAP product from designer Givenchy to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK non London shipping address
    When I click proceed to purchase from the shipping address page
    Then Packaging option NET-A-PORTER Signature is available
    And Packaging option Basic is available
    And Packaging option GIVENCHY is not available


  @ecomm @channelSpecific @region=AM @packaging @nap @NonChannelized
  Scenario: Givenchy packaging option is not available from main site for NY Premier addreses
    Given I go to Home page
    And I go to Shopping Bag page
    And I add a NAP product from designer Givenchy to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a US NY shipping address
    When I click proceed to purchase from the shipping address page
    Then Packaging option NET-A-PORTER Signature is available
    And Packaging option Discreet is available
    And Packaging option GIVENCHY is not available


  @ecomm @allChannels @packaging @nap @NonChannelized @region=INTL
  Scenario: Givenchy packaging option is available from Givenchy app for UK non Premier addreses
    Given I use a mobile device with the following app Givenchy_Women-1.0
    And I go to Home page
    And I add a NAP product from designer Givenchy to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK non London shipping address
    When I click proceed to purchase from the shipping address page
    Then Packaging option NET-A-PORTER Signature is not available
    And Packaging option Basic is not available
    And Packaging option GIVENCHY is available


  @ecomm @channelSpecific @packaging @region=INTL @nap @NonChannelized
  Scenario: Registered user's default packaging is signature during web express checkout
    Given I have successfully registered on INTL channel website
    And I am on the My Address Book page
    And I add a new shipping address from United Kingdom
    And I add a NAP product from designer Givenchy to my shopping bag
    And I go to Shopping Bag page
    When I proceed to the payment page using express checkout
    Then My selected packaging is NET-A-PORTER Signature Packaging


  @ecomm @channelSpecific @packaging @region=INTL @nap @NonChannelized
  Scenario: Registered user's default packaging is Givenchy during Givenchy app express checkout
    Given I use a mobile device with the following app Givenchy_Women-1.0
    And I have successfully registered on INTL channel website
    And I am on the My Address Book page
    And I add a new shipping address from United Kingdom
    And I add a NAP product from designer Givenchy to my shopping bag
    And I go to Shopping Bag page
    When I proceed to the payment page using express checkout
    Then My selected packaging is GIVENCHY Packaging


  @ecomm @channelSpecific @packaging @region=INTL @nap @NonChannelized
  Scenario: Using the mobile device after the website has set a default packaging
    Given I have successfully registered on INTL channel website
    And I am on the My Address Book page
    And I add a new shipping address from United Kingdom
    And I add a NAP product from designer Givenchy to my shopping bag
    And I go to Shopping Bag page
    And I proceed to the payment page using express checkout
    And My selected packaging is NET-A-PORTER Signature Packaging
    And I pay by VISA_CREDIT_CARD
    And My order is confirmed
    And I use a mobile device with the following app Givenchy_Women-1.0
    And I add a NAP product from designer Givenchy to my shopping bag
    When I proceed to the payment page using express checkout
    Then My selected packaging is GIVENCHY Packaging


  @ecomm @channelSpecific @packaging @region=INTL @nap @NonChannelized
  Scenario: Using the website after the mobile device has set a default packaging
    Given I use a mobile device with the following app Givenchy_Women-1.0
    And I have successfully registered on INTL channel website
    And I am on the My Address Book page
    And I add a new shipping address from United Kingdom
    And I add a NAP product from designer Givenchy to my shopping bag
    And I go to Shopping Bag page
    And I proceed to the payment page using express checkout
    And My selected packaging is GIVENCHY Packaging
    And I pay by VISA_CREDIT_CARD
    And My order is confirmed
    And I clear my cookies
    And I sign in with the correct details
    And I add a NAP product from designer Givenchy to my shopping bag
    And I go to Shopping Bag page
    And I signout
    And I sign in with the correct details
    When I proceed to the payment page using express checkout
    Then My selected packaging is NET-A-PORTER Signature Packaging


  @ecomm @region=INTL @packaging @known-failure @nap @NonChannelized
  Scenario: Anonymous user shopping on Givenchy app, adds a product to their basket and selects Express shipping option
    Given I use a mobile device with the following app Givenchy_Women-1.0
    And I go to Home page
    And I add a NAP product from designer Givenchy to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    When I select to use shipping option Next Business Day
    Then Packaging option GIVENCHY is available
    And The image for the packaging options is a box


  @ecomm @region=INTL @packaging @known-failure @nap @NonChannelized
  Scenario: Anonymous user shopping on Givenchy app, adds a product to their basket and selects Daytime shipping option
    Given I use a mobile device with the following app Givenchy_Women-1.0
    And I go to Home page
    And I add a NAP product from designer Givenchy to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    When I select to use shipping option Daytime
    Then Packaging option GIVENCHY is available
    And The image for the packaging options is a bag


  @ecomm @packaging @region=INTL @nap @NonChannelized
  Scenario: Signed in user shopping on the Givenchy app, adds a product to their basket and selects Nominated Day shipping option
    Given I use a mobile device with the following app Givenchy_Women-1.0
    And I have successfully registered on INTL channel website
    And I add a NAP product from designer Givenchy to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I fill out a UK non London shipping address
    And I click proceed to purchase from the shipping address page
    When I select to use shipping option Nominated
    Then Packaging option GIVENCHY is available
    And The image for the packaging options is a box