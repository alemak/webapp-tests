@purch
Feature: Packaging and shipping methods


  @ecomm @allChannels @packaging @nap @NonChannelized @region=INTL
  Scenario: Available packaging options are NAP and discreet only
    Given I go to Home page
    Given I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    When I go to Shipping page as an anonymous user
    Then Packaging option NET-A-PORTER Signature Packaging (FREE) is available
    And Packaging option Discreet Packaging (FREE) is available


  @ecomm @allChannels @packaging @nap @NonChannelized @region=INTL
  Scenario Outline: Packaging options selection is displayed correctly at payment
    Given I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I go to Shipping page as an anonymous user
    When I select to use packaging option <packaging>
    And I proceed to the Payment page
    Then My selected packaging is <packagingLabel>

  Examples:
    | packaging                               | packagingLabel                    |
    | NET-A-PORTER Signature Packaging (FREE) | NET-A-PORTER Signature Packaging  |
    | Discreet Packaging (FREE)               | Discreet Packaging                |


  @ecomm @allChannels @packaging @nap @NonChannelized  @region=INTL
  Scenario: Bridal packaging option is available if wedding dress in shopping bag
    Given I add a wedding dress to my shopping bag
    When I go to Shipping page as an anonymous user
    Then Packaging option Wedding Packaging (FREE) is available
    And Packaging option NET-A-PORTER Signature Packaging (FREE) is available
    And Packaging option Discreet Packaging (FREE) is available