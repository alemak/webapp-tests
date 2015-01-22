@purch
Feature: Return to Saved Payment Card
    In order to return to a saved payment card from the purchase path
    As a customer of net-a-porter.com
    I should be allowed to return back to my saved payment cards

  @ecomm @allChannels @purchasePath @nap @NonChannelized
  Scenario Outline: Ensure functionality is available to return to saved cards in all available languages
    Given I change language to <language>
    When I am on <channel>
    And I am a seaview registered default user
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    When I sign in using the default customer within the purchase path
    And I fill out a UK London shipping address in <language>
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    Then I verify the option to go back to saved cards 'is not' displayed to the user
    And I pay with payment card <cardType> and verify the payment was successful
    And My order is confirmed
    And the Order Confirmation page should display the product(s)

  #  verify that you can return to your saved cards
  #  ===========================================================
    When I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    When I use a different payment method
    Then I verify the option to go back to saved cards 'is' displayed to the user
    When I return to my saved cards
    And I click purchase now from the payment page
    Then I verify the payment was successful and retry payment if not successful using my saved card
    And My order is confirmed
    And the Order Confirmation page should display the product(s)

    Examples:
      | channel | cardType          | language  |
      | intl    | VISA_CREDIT_CARD  | English   |
      | intl    | VISA_CREDIT_CARD  | French    |
      | intl    | VISA_CREDIT_CARD  | German    |
      | intl    | VISA_CREDIT_CARD  | Chinese   |

