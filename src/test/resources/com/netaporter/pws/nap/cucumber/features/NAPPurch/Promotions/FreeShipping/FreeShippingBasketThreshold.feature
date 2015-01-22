@purch
Feature: FreeShippingBasketThreshold.feature. Free Shipping Promotion with Basket Threshold Tests


  @ecomm @allChannels @criticalPath @promotions=INTL @nap @NonChannelized
  Scenario: Customer receives free shipping from an enabled global basket threshold promotion
    Given I am on intl
    Given I configure a present global free shipping promotion
    And I set a BASKET_THRESHOLD rule with a value of USD:200, GBP:200, HKD:200
    And I create my configured promotion
    And I enable the promotion
    And I go to Home page
    And I add a NAP product priced over the basket threshold 300 to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK non London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And Free shipping is applied
    When I pay by AMERICAN_EXPRESS
    Then My order is confirmed
    And The promotion is applied to my confirmed order


  @ecomm @allChannels @criticalPath @promotions=INTL @nap @NonChannelized
  Scenario: Customer does not receive free shipping from an enabled global basket threshold promotion as basket threshold has not been reached.
    Given I am on intl
    Given I configure a present global free shipping promotion
    And I set a BASKET_THRESHOLD rule with a value of USD:200, GBP:200, HKD:200
    And I create my configured promotion
    And I enable the promotion
    And I go to Home page
    And I add a NAP product priced under the basket threshold 150 to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK non London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And Free shipping is not applied
    When I pay by AMERICAN_EXPRESS
    Then My order is confirmed
    And The promotion is not applied to my confirmed order


  @ecomm @allChannels @criticalPath @promotions=INTL @nap @NonChannelized
  Scenario: Free shipping is not applied for currencies other than threshold restriction currency
  An enabled global free shipping promotion with basket threshold restriction of EUR:200 has been configured.
  Free shipping will not be applied to a customer not shopping in Euros.
    Given I am on intl
    Given I configure a present global free shipping promotion
    And I set a BASKET_THRESHOLD rule with a value of EUR:200
    And I create my configured promotion
    And I enable the promotion
    And I go to Home page
    And I add a NAP product priced over the basket threshold 300 to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK non London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And Free shipping is not applied
    When I pay by AMERICAN_EXPRESS
    Then My order is confirmed
    And The promotion is not applied to my confirmed order


  @ecomm @allChannels @promotions=INTL @nap @NonChannelized
  Scenario: Free shipping promotion is applied to event customer obeying basket threshold
  An enabled free shipping promotion with basket threshold restriction and event customer
  has been configured. Customer using the site is in the event customer list and has added a product over the basket
  threshold to their basket. The customer receives free shipping.
    Given I am on intl
    Given I configure a present global free shipping promotion
    And I set a BASKET_THRESHOLD rule with a value of USD:200, GBP:200, HKD:200
    And I create my configured promotion
    And I enable the promotion
    And I go to Home page
    And I am a seaview registered default user
    And I sign in with the correct details
    And I add the logged in customer to the promotion
    And I add a NAP product priced over the basket threshold 300 to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And Free shipping is applied
    When I pay by AMERICAN_EXPRESS
    Then My order is confirmed
    And The promotion is applied to my confirmed order


  @ecomm @allChannels @promotions=INTL @nap @NonChannelized
  Scenario: Free shipping is not applied for customers other than event customer even when obeying the threshold
  An enabled free shipping promotion with basket threshold restriction and event customer
  has been configured. Customer using the site is NOT in the event customer list and has added a product over the basket
  threshold to their basket. The customer does not receive free shipping.
    Given I am on intl
    Given I configure a present global free shipping promotion
    And I set a BASKET_THRESHOLD rule with a value of USD:200, GBP:200, HKD:200
    And I create my configured promotion
    And I enable the promotion
    And I am on the homepage
    And I am a seaview registered default user
    And I sign in with the correct details
    And I add the logged in customer to the promotion
    And I sign out
    And I add a NAP product priced over the basket threshold 300 to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK non London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And Free shipping is not applied
    When I pay by AMERICAN_EXPRESS
    Then My order is confirmed
    And The promotion is not applied to my confirmed order

  ############### START: Shipping Cost displays as FREE on Shipping Options page ##################################

  @ecomm @allChannels @promotions=INTL @nap @NonChannelized
  Scenario: A present global basket threshold promotion is configured. Free shipping is NOT displayed as FREE on the shipping options page as customer has not reached the basket threshold.
    Given I am on intl
    Given I configure a present global free shipping promotion
    And I set a BASKET_THRESHOLD rule with a value of GBP:500, USD:500, HKD:500
    And I create my configured promotion
    And I enable the promotion
    And I am on the homepage
    And I add a NAP product priced under the basket threshold 200 to my shopping bag
    And I go to Shopping Bag page
    When I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK non London shipping address
    And I click proceed to purchase from the shipping address page
    Then Free shipping is not applied to any shipping options


  @ecomm @channelSpecific @promotions=INTL @nap @NonChannelized
  Scenario: A present global basket threshold promotion has been configured. Free shipping is displayed as FREE on the shipping options page when the customer reaches the basket threshold.
    Given I am on intl
    Given I configure a present global free shipping promotion
    And I set a BASKET_THRESHOLD rule with a value of GBP:500
    And I create my configured promotion
    And I enable the promotion
    And I am on the homepage
    And I add a NAP product priced over the basket threshold 800 to my shopping bag
    And I go to Shopping Bag page
    When I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK non London shipping address
    And I click proceed to purchase from the shipping address page
    Then Free shipping is applied to shipping option Next Business Day, Nominated day, Standard


  @ecomm @channelSpecific @promotions=INTL @nap @NonChannelized
  Scenario: A global free shipping promotion is configured with Premier shipping restriction. Shipping Cost is displayed as FREE on the shipping options against the Premier shipping options only.
    Given I am on intl
    Given I configure a present global free shipping promotion
    And I set a BASKET_THRESHOLD rule with a value of GBP:500
    And I create my configured promotion
    And I restrict the promotion to shipping method Premier
    And I enable the promotion
    And I am on the homepage
    And I add a NAP product priced over the basket threshold 800 to my shopping bag
    And I go to Shopping Bag page
    When I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    Then Free shipping is applied to shipping option Daytime, Evening


  @ecomm @channelSpecific @nap @NonChannelized @promotions=AM
  Scenario: A global free shipping promotion is configured with Premier shipping restriction. Shipping Cost is displayed as FREE on the shipping options against the Premier shipping options only.
    Given I am on am
    Given I configure a present global free shipping promotion
    And I set a BASKET_THRESHOLD rule with a value of USD:500
    And I create my configured promotion
    And I restrict the promotion to shipping method Premier
    And I enable the promotion
    And I go to Shopping Bag page
    And I add a NAP product priced over the basket threshold 800 to my shopping bag
    And I go to Shopping Bag page
    When I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a US NY shipping address
    And I click proceed to purchase from the shipping address page
    Then Free shipping is applied to shipping option Premier Daytime, Premier Evening


  @ecomm @channelSpecific @nap @NonChannelized @promotions=APAC
  Scenario: A global free shipping promotion is configured with Premier shipping restriction. Customer has added a product over the basket threshold. Shipping Cost is displayed as FREE on the shipping options against the Premier shipping options only.
    Given I am on apac
    Given I configure a present global free shipping promotion
    And I set a BASKET_THRESHOLD rule with a value of HKD:500
    And I create my configured promotion
    And I restrict the promotion to shipping method Premier
    And I enable the promotion
    And I go to Shopping Bag page
    And I add a NAP product priced over the basket threshold 800 to my shopping bag
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


  @ecomm @promotions=INTL @nap @NonChannelized
  Scenario: A free shipping promotion is configured with  shipping restriction. Shipping Cost is displayed as FREE on the shipping options against the Premier shipping option
    Given I am on intl
    Given I configure a present global free shipping promotion
    And I set a BASKET_THRESHOLD rule with a value of GBP:500, EUR:600
    And I create my configured promotion
    And I restrict the promotion to shipping method Standard, Next Day
    And I enable the promotion
    And I am on the homepage
    And I add a NAP product priced over the basket threshold 800 to my shopping bag
    And I go to Shopping Bag page
    When I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a shipping address for country France
    And I click proceed to purchase from the shipping address page
    Then Free shipping is applied to shipping option Standard, Next Business Day

  ############### END: Shipping Cost displays as FREE on Shipping Options page ##################################