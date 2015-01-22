@purch
Feature: Do not display Maestro as a payment option
  As NAP
  Do not show Maestro as a payment option when selected country is HK.

  @nap @NonChannelized @region=APAC
  Scenario Outline: Maestro payment option for different APAC countries
    Given I have selected <country> from the apac Channel
    And I have several items in the Shopping Bag
    When I go to Shipping page as an anonymous user
    And I proceed to the Payment page
    Then Maestro is a payment option: <isAnOption>

  Examples:
    | country   | isAnOption |
    | Hong Kong | false      |
    | China     | true       |
    | Australia | true       |