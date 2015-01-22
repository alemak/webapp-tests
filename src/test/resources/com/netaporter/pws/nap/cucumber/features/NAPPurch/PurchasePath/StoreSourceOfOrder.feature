 @nap @Channelized @purch
Feature:  Store source of order

  @ecomm @allChannels @purchasePath @storeSourceOfOrder
  Scenario Outline: An anonymous user uses a mobile device to complete his purchase
    Given I use a mobile device with the following app <mobileAppName>-<mobileAppVersion>
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    When I sign in anonymously within the purchase path
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And The payment page shows my shipping and billing addresses are the same
    And I pay by AMERICAN_EXPRESS
    And My order is confirmed
    And the Order Confirmation page should display the product(s)
    Then the source of the order has been stored as <mobileAppName>-<mobileAppVersion>

  Examples:
    | mobileAppName | mobileAppVersion |
    | napapp        | 1.0              |
    | napapp        | null             |
    | null          | 1.0              |

  @ecomm @allChannels @purchasePath @storeSourceOfOrder
  Scenario Outline: An anonymous user  uses a desktop to complete his purchase.
    Given I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    When I sign in anonymously within the purchase path
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And The payment page shows my shipping and billing addresses are the same
    And I pay by AMERICAN_EXPRESS
    And My order is confirmed
    And the Order Confirmation page should display the product(s)
    Then the source of the order has been stored as <mobileAppName>-<mobileAppVersion>

  Examples:
    | mobileAppName | mobileAppVersion |
    | null          | null             |

  @ecomm @allChannels @purchasePath @storeSourceOfOrder
  Scenario Outline: A registered user uses a mobile device to complete his purchase
    Given I use a mobile device with the following app <mobileAppName>-<mobileAppVersion>
    And I am a seaview registered default user
    And I sign in with the correct details
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    When I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And The payment page shows my shipping and billing addresses are the same
    And I pay by AMERICAN_EXPRESS
    And My order is confirmed
    And the Order Confirmation page should display the product(s)
    Then the source of the order has been stored as <mobileAppName>-<mobileAppVersion>

  Examples:
    | mobileAppName | mobileAppVersion |
    | napapp        | 1.0              |
    | napapp        | null             |
    | null          | 1.0              |

  @ecomm @allChannels @purchasePath @storeSourceOfOrder
  Scenario: A registered user uses a desktop to complete his purchase
    Given I am a seaview registered default user
    And I sign in with the correct details
    And I add 1 in stock products directly to my bag
    And I go to Shopping Bag page
    And I proceed to purchase
    When I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And The payment page shows my shipping and billing addresses are the same
    And I pay by AMERICAN_EXPRESS
    And My order is confirmed
    And the Order Confirmation page should display the product(s)
    Then the source of the order has been stored as null-null

