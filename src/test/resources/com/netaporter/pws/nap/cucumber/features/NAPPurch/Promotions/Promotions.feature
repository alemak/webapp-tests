  @nap @NonChannelized @purch
  Feature: Promotions.feature Promotions tests


    @ecomm @allChannels @criticalPath @promotions=INTL
    Scenario: Customer receives a percentage discount from an enabled global basket threshold promotion
      Given I am on intl
      Given I configure a present global percentage discount promotion with a discount of 99 percent
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
      And The product that is no. 1 on the list, has a 99% discount
      When I pay by AMERICAN_EXPRESS
      Then My order is confirmed
      And The promotion is applied to my confirmed order


    @ecomm @allChannels @criticalPath @promotions=INTL
    Scenario: Customer does not receive a percentage discount from a past enabled global basket threshold promotion
      Given I configure a past global percentage discount promotion with a discount of 99 percent
      And I set a BASKET_THRESHOLD rule with a value of USD:500, GBP:500, HKD:500
      And I create my configured promotion
      And I enable the promotion
      And I go to Home page
      And I add a NAP product priced over the basket threshold 200 to my shopping bag
      And I go to Shopping Bag page
      And I proceed to purchase
      And I sign in anonymously within the purchase path
      And I fill out a UK non London shipping address
      And I click proceed to purchase from the shipping address page
      And I click proceed to purchase from the shipping options page
      And The product that is no. 1 on the list, has no discount
      When I pay by AMERICAN_EXPRESS
      Then My order is confirmed
      And The promotion is not applied to my confirmed order


    @ecomm @allChannels @criticalPath @promotions=INTL
    Scenario: Customer does not a receive percentage discount from a present enabled promotion as basket threshold has not been reached
      Given I configure a present global percentage discount promotion with a discount of 99 percent
      And I set a BASKET_THRESHOLD rule with a value of USD:500, GBP:500, HKD:5000
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
      And The product that is no. 1 on the list, has no discount
      When I pay by AMERICAN_EXPRESS
      Then My order is confirmed
      And The promotion is not applied to my confirmed order


    @ecomm @allChannels @criticalPath @promotions=INTL
    Scenario: Customer receives a percentage discount only on the product specified in an enabled promotion
      Given I configure a present global percentage discount promotion with a discount of 99 percent
      And I set a BASKET_THRESHOLD rule with a value of USD:200, GBP:200, HKD:200
      And I create my configured promotion
      And I add a product priced over the basket threshold 200 to the promotion
      And I enable the promotion
      And I go to Home page
      And I add a NAP product priced under the basket threshold 150 to my shopping bag
      And I add a product identified as promotionPid to my bag
      And I go to Shopping Bag page
      And I proceed to purchase
      And I sign in anonymously within the purchase path
      And I fill out a UK non London shipping address
      And I click proceed to purchase from the shipping address page
      And I click proceed to purchase from the shipping options page
      And The product that is no. 2 on the list, has a 99% discount
      And The product that is no. 1 on the list, has no discount
      When I pay by AMERICAN_EXPRESS
      Then My order is confirmed
      And The promotion is applied to my confirmed order


    @ecomm @allChannels @criticalPath @promotions=INTL
    Scenario: Customer receives free shipping from an enabled global basket threshold promotion
      Given I configure a present global free shipping promotion
      And I set a BASKET_THRESHOLD rule with a value of USD:200, GBP:200, HKD:200
      And I create my configured promotion
      And I enable the promotion
      And I go to Home page
      And I add a NAP product priced over the basket threshold 200 to my shopping bag
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


    @ecomm @allChannels @criticalPath @promotions=INTL
    Scenario: Customer does not receive free shipping from an enabled global basket threshold promotion as basket threshold has not been reached.
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


    @ecomm @allChannels @criticalPath @promotions=INTL
    Scenario: An enabled global free shipping promotion with basket threshold restriction of EUR:200 has been configured.
    Free shipping will not be applied to a customer not shopping in Euros.
      Given I configure a present global free shipping promotion
      And I set a BASKET_THRESHOLD rule with a value of EUR:200
      And I create my configured promotion
      And I enable the promotion
      And I go to Home page
      And I add a NAP product priced over the basket threshold 200 to my shopping bag
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


      @ecomm @allChannels @promotions=INTL
    Scenario: An enabled percentage discount promotion with basket threshold restriction and event product and event customer
    has been configured. Customer using the site is in the event customer list and has added an event product to their basket.
    Customer has reached the basket threshold and receives the percentage discount only on products from the event product list.
      Given I configure a present global percentage discount promotion with a discount of 99 percent
      And I set a BASKET_THRESHOLD rule with a value of USD:200, GBP:200, HKD:200
      And I create my configured promotion
      And I go to Home page
      And I add a NAP product priced over the basket threshold 200 to the promotion
      And I enable the promotion
      And I go to Home page
      And I am a seaview registered default user
      And I sign in with the correct details
      And I add the logged in customer to the promotion
      And I add a product identified as promotionPid to my bag
      And I go to Shopping Bag page
      And I proceed to purchase
      And I fill out a UK London shipping address
      And I click proceed to purchase from the shipping address page
      And I click proceed to purchase from the shipping options page
      And The product that is no. 1 on the list, has a 99% discount
      When I pay by AMERICAN_EXPRESS
      Then My order is confirmed
      And The promotion is applied to my confirmed order


    @ecomm @allChannels @promotions=INTL
    Scenario: An enabled percentage discount promotion with basket threshold restriction and event product and event customer
    has been configured. The currency used is incorrect for the site. The customer is in the event customer list and
    has added an event product to their basket. The customer has reached the basket threshold and does not receive any
    percentage discount.
      Given I configure a present global percentage discount promotion with a discount of 99 percent
      And I set a BASKET_THRESHOLD rule with a value of EUR:200
      And I create my configured promotion
      And I add a NAP product priced over the basket threshold 200 to the promotion
      And I enable the promotion
      And I go to Home page
      And I am a seaview registered default user
      And I sign in with the correct details
      And I add the logged in customer to the promotion
      And I add a NAP product priced over the basket threshold 200 to my shopping bag
      And I go to Shopping Bag page
      And I proceed to purchase
      And I fill out a UK London shipping address
      And I click proceed to purchase from the shipping address page
      And I click proceed to purchase from the shipping options page
      And The product that is no. 1 on the list, has no discount
      When I pay by AMERICAN_EXPRESS
      Then My order is confirmed
      And The promotion is not applied to my confirmed order


    @ecomm @allChannels @promotions=INTL
    Scenario: An enabled percentage discount promotion with basket threshold restriction and event product and event customer
    has been configured. Customer is in the event customer list and has added an event product priced under basket threshold
    to their basket, and another product which does not belong to the event product list and is over the basket threshold value.
    Customer receives discount only on the product which is in the event product list.
      Given I configure a present global percentage discount promotion with a discount of 99 percent
      And I set a BASKET_THRESHOLD rule with a value of USD:200, GBP:200, HKD:200
      And I create my configured promotion
      And I add a NAP product priced under the basket threshold 150 to the promotion
      And I enable the promotion
      And I go to Home page
      And I am a seaview registered default user
      And I sign in with the correct details
      And I add the logged in customer to the promotion
      And I add a product identified as promotionPid to my bag
      And I add a NAP product priced over the basket threshold 200 to my shopping bag
      And I go to Shopping Bag page
      And I proceed to purchase
      And I fill out a UK London shipping address
      And I click proceed to purchase from the shipping address page
      And I click proceed to purchase from the shipping options page
      And The product that is no. 2 on the list, has no discount
      And The product that is no. 1 on the list, has a 99% discount
      When I pay by AMERICAN_EXPRESS
      Then My order is confirmed
      And The promotion is applied to my confirmed order


    @ecomm @allChannels @promotions=INTL
    Scenario: An enabled free shipping promotion with basket threshold restriction and event customer
    has been configured. Customer using the site is in the event customer list and has added a product over the basket
    threshold to their basket. The customer receives free shipping.
      Given I configure a present global free shipping promotion
      And I set a BASKET_THRESHOLD rule with a value of USD:200, GBP:200, HKD:200
      And I create my configured promotion
      And I enable the promotion
      And I go to Home page
      And I am a seaview registered default user
      And I sign in with the correct details
      And I add the logged in customer to the promotion
      And I add a NAP product priced over the basket threshold 200 to my shopping bag
      And I go to Shopping Bag page
      And I proceed to purchase
      And I fill out a UK London shipping address
      And I click proceed to purchase from the shipping address page
      And I click proceed to purchase from the shipping options page
      And Free shipping is applied
      When I pay by AMERICAN_EXPRESS
      Then My order is confirmed
      And The promotion is applied to my confirmed order


    @ecomm @allChannels @promotions=INTL
    Scenario: An enabled free shipping promotion with basket threshold restriction and event customer
    has been configured. Customer using the site is NOT in the event customer list and has added a product over the basket
    threshold to their basket. The customer does not receive free shipping.
      Given I configure a present global free shipping promotion
      And I set a BASKET_THRESHOLD rule with a value of USD:200, GBP:200, HKD:200
      And I create my configured promotion
      And I enable the promotion
      And I go to Home page
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


    @ecomm @allChannels @promotions=INTL
    Scenario: An enabled free shipping promotion with basket threshold restriction and event customer
    has been configured. Customer using the site is NOT in the event customer list and has added a product over the basket
    threshold to their basket. The customer does not receive free shipping.
      Given I configure a present global free shipping promotion
      And I set a BASKET_THRESHOLD rule with a value of USD:200, GBP:200, HKD:200
      And I create my configured promotion
      And I enable the promotion
      And I go to Home page
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


    @ecomm @allChannels @promotions=INTL
    Scenario: An enabled global percentage discount promotion with basket threshold restriction of EUR:200 has been configured.
    Percentage discount will not be applied to a customer not shopping in Euros.
      Given I configure a present global percentage discount promotion with a discount of 99 percent
      And I set a BASKET_THRESHOLD rule with a value of EUR:200
      And I create my configured promotion
      And I enable the promotion
      And I go to Home page
      And I add a NAP product priced over the basket threshold 200 to my shopping bag
      And I go to Shopping Bag page
      And I proceed to purchase
      And I sign in anonymously within the purchase path
      And I fill out a UK non London shipping address
      And I click proceed to purchase from the shipping address page
      And I click proceed to purchase from the shipping options page
      And The product that is no. 1 on the list, has no discount
      When I pay by AMERICAN_EXPRESS
      Then My order is confirmed
      And The promotion is not applied to my confirmed order