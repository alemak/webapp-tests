@purch @nap @NonChannelized
Feature: Check that the currency is displayed correctly in the Purchase Path and Shopping Bag

    @region=APAC
  Scenario Outline: Product is priced in correct currency in Shopping Bag
    Given I have selected <country> from the <channel> Channel
    And I have added several items into Shopping Bag
    When I go to Shopping Bag page
    Then I should see the items priced in <currency>

  Examples:
    | channel | country   | currency   |
    | apac    | China     | USD $      |
    | apac    | Australia | AUD $      |
    | apac    | Hong Kong | HKD $      |
    | apac    | India     | USD $      |


   @region=APAC
  Scenario Outline: All costs are priced in correct currency through the purchase path
    Given I have selected <country> from the <channel> Channel
    And I have added several items into Shopping Bag
    And I go to Shopping Bag page
    When I go to Shipping page as an anonymous user
    Then I should see the shipping cost is priced in <currency>
    When I proceed to the Payment page
    Then I should see all payment costs are priced in <currency>
    And I pay by <card type>
    When Order Confirmation page is displayed
    Then I should see all costs are priced in <currency>

  Examples:
    | channel | country   | currency |  card type           |
    | apac    | China     |   USD $  |  VISA_CREDIT_CARD    |
    | apac    | Australia |   AUD $  |  VISA_CREDIT_CARD    |
    | apac    | Hong Kong |   HKD $  |  VISA_CREDIT_CARD    |
    | apac    | India     |   USD $  |  VISA_CREDIT_CARD    |


   @region=APAC
  Scenario Outline: Products are priced in correct currency in Order Summary page
    Given I have selected <country> from the <channel> Channel
    And I have registered
    And I have purchased several items
    When I am on a Order Summary page
    Then I should see all summary prices priced in <currency>

  Examples:
    | channel | country   | currency   |
    | apac    | China     | USD $      |
    | apac    | Australia | AUD $      |
    | apac    | Hong Kong | HKD $      |
    | apac    | India     | USD $      |