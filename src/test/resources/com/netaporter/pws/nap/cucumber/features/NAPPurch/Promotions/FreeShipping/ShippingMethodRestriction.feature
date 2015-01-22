@nap @NonChannelized @purch
Feature: ShippingMethodRestriction.feature Free shipping promotion with shipping method restriction


  @ecomm @channelSpecific @promotions=INTL
  Scenario: A free shipping promotion is configured with a single (Premier) shipping restriction. Shipping Cost is displayed as FREE against the Premier shipping option
    Given I am on intl
    Given I configure a present global free shipping promotion
    And I create my configured promotion
    And I restrict the promotion to shipping method Premier
    And I enable the promotion
    And I go to Shopping Bag page
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    When I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    Then Free shipping is applied to shipping option Daytime, Evening


  @ecomm @channelSpecific @promotions=AM
  Scenario: A free shipping promotion is configured with a single (Premier) shipping restriction. Shipping Cost is displayed as FREE against the Premier shipping option
  Given I am on am
  Given I configure a present global free shipping promotion
  And I create my configured promotion
  And I restrict the promotion to shipping method Premier
  And I enable the promotion
  And I go to Shopping Bag page
  And I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  When I proceed to purchase
  And I sign in anonymously within the purchase path
  And I fill out a US NY shipping address
  And I click proceed to purchase from the shipping address page
  Then Free shipping is applied to shipping option Premier Daytime, Premier Evening


  @ecomm @channelSpecific @promotions=APAC
  Scenario: A global free shipping promotion is configured with single (Premier) shipping restriction. Shipping Cost is displayed as FREE on the shipping options against the Premier shipping options only.
    Given I am on apac
    Given I configure a present global free shipping promotion
    And I create my configured promotion
    And I restrict the promotion to shipping method Premier
    And I enable the promotion
    And I go to Shopping Bag page
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    When I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out the first name field with Jane
    And I fill out the last name field with Doe
    And I select country Hong Kong on the address form
    And I fill out the first address line field with Test Street
    And I fill out the state field with Happy Valley
    And I fill out the telephone field on the address form with 123456789
    And I click proceed to purchase from the shipping address page
    Then Free shipping is applied to shipping option Daytime, Evening


  @ecomm @channelSpecific @promotions=INTL
  Scenario: A free shipping promotion is configured with multiple (Standard and Express) shipping restrictions. Shipping Cost is displayed as FREE on the Standard and Express shipping options.
  Given I am on intl
  Given I configure a present global free shipping promotion
  And I create my configured promotion
  And I restrict the promotion to shipping method Express, Standard
  And I enable the promotion
  And I go to Shopping Bag page
  And I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  When I proceed to purchase
  And I sign in anonymously within the purchase path
  And I fill out a shipping address for country France
  And I click proceed to purchase from the shipping address page
  Then Free shipping is applied to shipping option Standard, Express


  @ecomm @channelSpecific @promotions=INTL
  Scenario: A free shipping promotion is configured with single (Global) shipping restrictions. Shipping Cost is displayed as FREE on the Express(Global as shipping to Australia) shipping option.
  Given I configure a present global free shipping promotion
  And I create my configured promotion
  And I restrict the promotion to shipping method Global
  And I enable the promotion
  And I go to Shopping Bag page
  And I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  When I proceed to purchase
  And I sign in anonymously within the purchase path
  And I enter an Australia Next Day
  And I click proceed to purchase from the shipping address page
  Then Free shipping is applied to shipping option Express


  @ecomm @channelSpecific @promotions=AM
  Scenario: A free shipping promotion is configured with multiple (Premier, Express, Ground) shipping restrictions, which are all available for the country that the customer is shipping to. IE. New York, US. Shipping Cost is displayed as FREE for all shipping options.
  Given I am on am
  Given I configure a present global free shipping promotion
  And I create my configured promotion
  And I restrict the promotion to shipping method Premier, Next Day, Standard
  And I enable the promotion
  And I go to Shopping Bag page
  And I add 1 in stock products directly to my bag
  And I go to Shopping Bag page
  When I proceed to purchase
  And I sign in anonymously within the purchase path
  And I fill out a US NY shipping address
  And I click proceed to purchase from the shipping address page
  Then Free shipping is applied to shipping option Premier Daytime, Premier Evening, Next Business Day, Standard


  @ecomm @channelSpecific @promotions=INTL
  Scenario: Free shipping promotion configured for Next Day and Standard delivery options to France is not displayed on the shipping options page when shipping to Austria
  A free shipping promotion is configured with multiple (Standard and Express) shipping restrictions, and include
  shipping country restriction to France. The customer is shipping to Austria and shipping Cost is NOT displayed as FREE on
  the Standard and Express shipping options.
    Given I am on intl
    Given I configure a present global free shipping promotion
    And I set a INCLUDE_SHIPPING_COUNTRY rule with a value of France
    And I create my configured promotion
    And I restrict the promotion to shipping method Standard, Express
    And I restrict the promotion to shipping country France
    And I enable the promotion
    And I go to Shopping Bag page
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    When I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a shipping address for country Austria
    And I click proceed to purchase from the shipping address page
    Then Free shipping is not applied to any shipping options


  @ecomm @channelSpecific @promotions=INTL
  Scenario: Free shipping promotion configured for Next Day and Standard delivery options to France is displayed on the shipping options page when shipping to France
    Given I am on intl
    Given I configure a present global free shipping promotion
    And I set a INCLUDE_SHIPPING_COUNTRY rule with a value of France
    And I create my configured promotion
    And I restrict the promotion to shipping method Standard
    And I restrict the promotion to shipping country France
    And I enable the promotion
    And I go to Shopping Bag page
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    When I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a shipping address for country France
    And I click proceed to purchase from the shipping address page
    Then Free shipping is applied to shipping option Standard