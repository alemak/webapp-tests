@nap @NonChannelized @purch
Feature: ShippingOptions.feature Offer shipping method based on location

    @region=APAC
  Scenario Outline: Offer shipping method based on country
    Given I am on any country of the APAC website
    When I choose to deliver to <country> for the order when checking out
    Then Its shipping sku is <shippingSku>

  Examples:
    | country        | shippingSku  |
    | Australia      | 9000314-001  |
    | New Zealand    | 9000313-001  |
    | India          | 9000313-001  |
    | Indonesia      | 9000313-001  |
    | China          | 9000312-001  |
    | Japan          | 9000312-001  |
    | Malaysia       | 9000312-001  |
    | Philippines    | 9000312-001  |
    | Singapore      | 9000312-001  |
    | South Korea    | 9000312-001  |
    | Taiwan ROC     | 9000312-001  |
    | Thailand       | 9000312-001  |
    | Vietnam        | 9000312-001  |


   @ecomm  @region=APAC
  Scenario Outline: NAP APAC offers express and premier shipping methods for different Hong Kong areas.
  Only areas that are part of Hong Kong Island or Kowloon are eligible for premier -
  New Territories and Outlying Islands are not.
    Given I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out the first name field with Jane
    And I fill out the last name field with Doe
    And I select country Hong Kong on the address form
    And I fill out the first address line field with Test Street
    And I fill out the state field with <area>
    And I fill out the telephone field on the address form with 0123546789
    When I click on the continue button
    Then Its shipping sku are <shippingSku1> and <shippingSku2> and <shippingSku3>

  Examples:
    | area           | shippingSku1  | shippingSku2  | shippingSku3  |
    # region = Hong Kong Island
    | Aberdeen       | 9000324-001   | 9000323-001   | 9000311-001   |
    # region = Kowloon
    | Cheung Sha Wan | 9000324-001   | 9000323-001   | 9000311-001   |
    # region = New Territories
    | Fanling        | 9000311-001   |               |               |
    # region = Outlying Island
    | Cheung Chau    | 9000311-001   |               |               |


  @nap @NonChannelized @region=AM
  Scenario Outline: Check that the correct shipping methods are displayed depending on shipping address on AM channel
    Given I go to Home page
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    When I enter an <address>
    Then I should see <shippingMethods> shipping methods
    When I select the <desiredShippingMethod> NAP shipping method
    And I proceed to the Payment page
    Then the shipping method from the NAP payment summary page is <expectedShippingMethod>
    When I pay by VISA_DEBIT
    Then the shipping options from the NAP order confirmation page is <expectedShippingMethod>

  Examples:
  #shipping methods need to be in the same order as on the website
    | address     | shippingMethods                                                              | desiredShippingMethod      | expectedShippingMethod |
    | US Premier  | Premier Daytime 10am-5pm,Premier Evening 6pm-9pm,Next Business Day,Standard  | Premier Daytime, 10am-5pm  | Premier Daytime        |
    | US Midwest  | Standard,Next Business Day                                                   | Next Business Day          | Next Business Day      |
    | US Alaska   | Express, Standard                                                            | Express                    | Express                |


  @nap @NonChannelized @region=INTL
  Scenario Outline: Check that Next Day Shipping is displayed correctly for France, Germany and UK, but not Belgium
    Given I go to Home page
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    When I enter an <address>
    Then I should see <shippingMethod> shipping methods
    And I should see <NextDayCutOffMessage> cut off message
    When I select the <selectedShippingMethod> NAP shipping method
    And I proceed to the Payment page
    Then the shipping method from the NAP payment summary page is <selectedShippingMethod>
    When I pay by VISA_DEBIT
    Then the shipping options from the NAP order confirmation page is <selectedShippingMethod>

  Examples:
    | address                    | shippingMethod                                                                    | selectedShippingMethod | NextDayCutOffMessage                                                             |
    | France                     | Next Business Day,Standard                                                        | Next Business Day      | Next Business Day orders placed after 4pm on Friday will be delivered on Tuesday |
    | Germany                    | Next Business Day,Standard                                                        | Next Business Day      | Next Business Day orders placed after 4pm on Friday will be delivered on Tuesday |
    | United Kingdom Non Premier | Next Business Day,Nominated day,Standard                                          | Next Business Day      | Next Business Day orders placed after 3pm on Friday will be delivered on Tuesday |
    | United Kingdom Premier     | Daytime 10am-5pm 7 days a week,Evening 6pm-9pm Mon-Fri,Next Business Day,Standard | Next Business Day      | Next Business Day orders placed after 3pm on Friday will be delivered on Tuesday |
    | United Kingdom Standard    | Next Business Day, Standard                                                       | Next Business Day      | Next Business Day orders placed after 3pm on Friday will be delivered on Tuesday |
    | Belgium                    | Standard,Express                                                                  | Express                | empty message                                                                    |


  @nap @NonChannelized @region=APAC
  Scenario Outline: Check that Next Day Shipping is displayed correctly for Australia (Sydney and Melbourne)
    Given I go to Home page
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    When I enter an <address>
    Then I should see <shippingMethod> shipping methods
    And I should see <NextDayCutOffMessage> cut off message
    When I select the <selectedShippingMethod> NAP shipping method
    And I proceed to the Payment page
    Then the shipping method from the NAP payment summary page is <selectedShippingMethod>
    When I pay by VISA_DEBIT
    Then the shipping options from the NAP order confirmation page is <selectedShippingMethod>

  Examples:
    | address                    | shippingMethod                                                                    | selectedShippingMethod | NextDayCutOffMessage                                                                |
    | Australia Next Day         | Next Business Day,Express                                                         | Next Business Day      | Next Business Day orders placed after 1:30pm on Friday will be delivered on Tuesday |
    | Australia Express          | Express                                                                           | Express                | empty message                                                                       |


  @ecomm @allChannels @packaging
  Scenario Outline: Customer is shopping on the INTL site, with browsing country set to UK and shipping to Russia using Russian courier
    Given I am on <channel>
    And I am on change country page
    And I change my country to <browsing country>
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase as an anonymous user
    And I fill out the first name field with John
    And I fill out the last name field with Doe
    And I select country Russia on the address form
    And I fill out the first address line field with Russian address
    And I fill out the town field with Russian town
    And I fill out the telephone field on the address form with 123456789
    When I click proceed to purchase from the shipping options page
    And I save the shipping option details
    Then Shipping option Express is available
    And The shipping sku is <shipping-sku>
    And The shipping cost is <cost>
    And The shipping option notes contains <notes>

  Examples:
    |channel|browsing country|shipping-sku|cost   |notes                               |
    |intl   |United Kingdom  |900000-001  |£20    |Estimated delivery: 3-4 working days|
    |intl   |France          |900000-001  |€23.66 |Estimated delivery: 3-4 working days|
#need a way to disable existing promotions for a specific channel in the @Before
#    |am     |United States   |900014-001  |$55    |Estimated delivery: 4-6 working days|
#    |apac   |Hong Kong       |9000321-001 |$271.34|Estimated delivery: 3-5 working days|


  @packaging
  Scenario Outline: Check that the price in the shopping bag is always the lowest price available
    Given I am on <channel>
    And I am on change country page
    And I change my country to <browsing country>
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I record the shipping price from the shopping bag
    And I proceed to purchase as an anonymous user
    When I enter an <address>
    Then the shipping price from the shopping bag is the lowest shipping price from the shipping options page

  Examples:
    | channel | browsing country | address                    |
    | intl    | Germany          | Germany                    |
    | intl    | France           | France                     |
    | intl    | United Kingdom   | United Kingdom Premier     |
    | intl    | United Kingdom   | United Kingdom Non Premier |
#need a way to disable existing promotions for a specific channel in the @Before
#    | am      | United States    | US Premier                 |