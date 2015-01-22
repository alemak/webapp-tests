 @nap @NonChannelized @purch
Feature: Shipping options restricted to only ground methods when basket contains HAZMAT items.


  @ecomm @channelSpecific @purchasePath @shippingRestrictions @region=AM
  Scenario Outline: An anonymous user shipping Hazmat items to US does not have any shipping restrictions
     Given I have selected United States from the am Channel
#    need to go to Shopping Bag page to get cookies before adding something to bag using the basked API
     And I go to Shopping Bag page
     And I have added a Hazmat product restricted for country code BR to my shopping bag
     When I go to Shopping Bag page
     And I proceed to purchase
     And I continue to the Shipping page as an anonymous user
     And I enter an <address>
     Then I should see <shippingMethodName> shipping methods

   Examples:
    | address    | shippingMethodName                                                           |
    | US Premier | Premier Daytime 10am-5pm,Premier Evening 6pm-9pm,Next Business Day,Standard  |
    | US Midwest | Standard,Next Business Day                                                   |
    | US Alaska  | Express, Standard                                                            |



#  @ecomm @channelSpecific @purchasePath @shippingRestrictions @region=INTL
#  Scenario: An anonymous user shipping Hazmat items to INTL is restricted to Ground shipping
#    Given I have selected United Kingdom from the intl Channel
#    And I go to Shopping Bag page
#    And I have added a Hazmat product restricted for country code GB to my shopping bag
#    When I go to Shopping Bag page
#    And I proceed to purchase
#    And I continue to the Shipping page as an anonymous user
#    And I enter an address from United Kingdom
#    Then I should only be able to select Ground shipping methods


  @ecomm @channelSpecific @purchasePath @shippingRestrictions @region=AM
  Scenario Outline: A signed in user shipping Hazmat items to US does not have any shipping restrictions
     Given I have selected United States from the am Channel
     And I am a seaview registered default user
     And I sign in with the correct details
     And I have added a Hazmat product restricted for country code BR to my shopping bag
     And I go to Shopping Bag page
     And I proceed to purchase
     And I sign in using the default customer within the purchase path
     And I continue to the Shipping page
     And I enter an <address>
     Then I should see <shippingMethodName> shipping methods

  Examples:
  | address    | shippingMethodName                                                           |
  | US Premier | Premier Daytime 10am-5pm,Premier Evening 6pm-9pm,Next Business Day,Standard  |
  | US Midwest | Standard,Next Business Day                                                   |
  | US Alaska  | Express, Standard                                                            |


#  @ecomm @channelSpecific @purchasePath @shippingRestrictions @region=INTL
#  Scenario: An anonymous user shipping Hazmat items to INTL is restricted to Ground shipping
#    Given I have selected United Kingdom from the intl Channel
#    Given I am a seaview registered default user
#    And I sign in with the correct details
#    And I have added a Hazmat product restricted for country code GB to my shopping bag
#    When I go to Shopping Bag page
#    And I proceed to purchase
#    And I sign in using the default customer within the purchase path
#    And I am taken to the Shipping page
#    And I enter an address from United Kingdom
#    Then I should only be able to select Ground shipping methods


  @ecomm @channelSpecific @purchasePath @shippingRestrictions @region=AM
  Scenario: A signed in user with a remembered non-ground AM shipping method can ship Hazmat items to US without any restrictions
     Given I have selected United States from the am Channel
     And I am a seaview registered default user
     And I sign in with the correct details
     And I add 1 in stock products directly to my bag
     And I go to Shopping Bag page
     And I proceed to purchase
     And I sign in using the default customer within the purchase path
     And I enter an US Premier
     And I select the Next Business Day NAP shipping method
     And I proceed to the Payment page
     And I pay by VISA_CREDIT_CARD
     When I have added a Hazmat product restricted for country code BR to my shopping bag
     And I go to Shopping Bag page
     And I proceed to purchase
     And I am taken to the Payment page
     Then the shipping method from the NAP payment summary page is Next Business Day


#  @ecomm @channelSpecific @purchasePath @shippingRestrictions @region=INTL
#  Scenario: A signed in user with a remembered non-ground INTL shipping method will be offered an INTL ground shipping method by default for Hazmat items
#    Given I have selected United Kingdom from the intl Channel
#    And I am a seaview registered default user
#    And I sign in with the correct details
#    And I add 1 in stock products directly to my bag
#    And I go to Shopping Bag page
#    And I proceed to purchase
#    And I sign in using the default customer within the purchase path
#    And I enter an United Kingdom Premier
#    And I select the Next Business Day NAP shipping method
#    And I proceed to the Payment page
#    And I pay by VISA_CREDIT_CARD
#    When I have added a Hazmat product restricted for country code UK to my shopping bag
#    And I go to Shopping Bag page
#    And I proceed to purchase
#    And I am taken to the Payment page
#    Then the default shipping method should be a Ground shipping method
  
  
  @ecomm @channelSpecific @purchasePath @shippingRestrictions @region=AM
  Scenario: An anonymous user who enters a non-US address and then changes it to a US address has no shipping restrictions when purchasing Hazmat items
     Given I have selected United States from the am Channel
     And I am not signed in
     And I go to Shopping Bag page
     And I have added a Hazmat product restricted for country code BR to my shopping bag
     When I go to Shopping Bag page
     And I proceed to purchase
     And I continue to the Shipping page as an anonymous user
     And I enter an address from Brazil
     When I click on Edit shipping address from Shipping options page
     And I enter an US Premier
     Then I should see Premier Daytime 10am-5pm,Premier Evening 6pm-9pm,Next Business Day,Standard shipping methods


#  @ecomm @channelSpecific @purchasePath @shippingRestrictions @region=INTL
#  Scenario: An anonymous user who enters a non-INTL address and then changes it to an INTL address is restricted to Ground shipping methods when purchasing Hazmat items
#    Given I have selected United Kingdom from the intl Channel
#    And I am not signed in
#    And I go to Shopping Bag page
#    And I have added a Hazmat product restricted for country code UK to my shopping bag
#    When I go to Shopping Bag page
#    And I proceed to purchase
#    And I continue to the Shipping page as an anonymous user
#    And I enter an address from China
#    When I click on Edit shipping address from Shipping options page
#    And I enter an United Kingdom Premier
#    Then I should only be able to select Ground shipping methods including Premier


  @ecomm @channelSpecific @purchasePath @shippingRestrictions @region=AM
  Scenario: A signed in user who enters a non-US address and then changes it to a US address has no shipping restrictions when purchasing Hazmat items
     Given I have selected United States from the am Channel
     And I am a seaview registered default user
     And I sign in with the correct details
     And I have added a Hazmat product restricted for country code BR to my shopping bag
     When I go to Shopping Bag page
     And I signout
     And I sign in with the correct details
     And I go to Shopping Bag page
     And I proceed to purchase
     And I continue to the Shipping page
     And I enter an address from Brazil
     When I click on Edit shipping address from Shipping options page
     And I enter an US Premier
     Then I should see Premier Daytime 10am-5pm,Premier Evening 6pm-9pm,Next Business Day,Standard shipping methods


#  @ecomm @channelSpecific @purchasePath @shippingRestrictions @region=INTL
#  Scenario: A signed in user who enters a non-INTL address and then changes it to a INTL address can only ship Hazmat restricted items via Ground
#    Given I have selected United Kingdom from the intl Channel
#    And I am a seaview registered default user
#    And I sign in with the correct details
#    And I have added a Hazmat product restricted for country code UK to my shopping bag
#    When I go to Shopping Bag page
#    And I signout
#    And I sign in with the correct details
#    And I go to Shopping Bag page
#    And I proceed to purchase
#    And I continue to the Shipping page
#    And I enter an address from France
#    When I click on Edit shipping address from Shipping options page
#    And I enter an address from United States
#    Then I should only be able to select Ground shipping methods


#  @ecomm @channelSpecific @purchasePath @shippingRestrictions @region=AM
#  Scenario: An anonymous user enters a non-US address with a Hazmat product
#    Given I have selected United Kingdom from the intl Channel
#    And I go to Shopping Bag page
#    And I have added a Hazmat product restricted for country code BR to my shopping bag
#    When I go to Shopping Bag page
#    And I proceed to purchase
#    And I continue to the Shipping page as an anonymous user
#    And I enter an address from Brazil
#    Then I should be able to select at least 1 shipping method
#      # As no valid shipping option, should not reduce to nothing and blow up, and Shipping restriction should kick in
#
#
#  @ecomm @channelSpecific @purchasePath @shippingRestrictions @region=INTL
#  Scenario: An signed in user enters a non-US address with a Hazmat product
#    Given I have selected United Kingdom from the intl Channel
#    And I am a seaview registered default user
#    And I sign in with the correct details
#    And I have added a Hazmat product restricted for country code RU to my shopping bag
#    When I go to Shopping Bag page
#    And I signout
#    And I sign in with the correct details
#    And I go to Shopping Bag page
#    And I proceed to purchase
#    And I continue to the Shipping page
#    And I enter an address from Russia
#    Then I should be able to select at least 1 shipping method
#      # As no valid shipping option, should not reduce to nothing and blow up, and Shipping restriction should kick in

