@nap @NonChannelized @purch
Feature: Purchases are blocked due to HAZMAT restrictions applied to the shipping-selected country.


  @ecomm @shippingRestrictions @channelSpecific
  Scenario Outline: Anonymous user attempts to ship a Hazmat restricted item to a restricted country and is blocked from purchasing.
     Given I have selected <country> from the <channel> Channel
     And I go to Shopping Bag page
     And I have added a Hazmat product restricted for country code <countryCode> to my shopping bag
     When I go to Shopping Bag page
     And I proceed to purchase
     And I continue to the Shipping page as an anonymous user
     And I enter an address from <shippingCountry>
     And I proceed to the Payment page
     And I attempt to pay by AMERICAN_EXPRESS
     Then I should be blocked from purchasing and prompted with an shipping restriction message

  Examples:
    | country         | channel  | countryCode  | shippingCountry |
    | United Kingdom  | intl     | RU           | Russia          |
    | United States   | am       | BR           | Brazil          |


  @ecomm @shippingRestrictions @channelSpecific
  Scenario Outline: A signed in user attempts to ship a Hazmat restricted item to a restricted country and is blocked from purchasing.
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I am a seaview registered default user
    When I sign in with the correct details
    And I have added a Hazmat product restricted for country code <countryCode> to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I enter an address from <shippingCountry>
    And I proceed to the Payment page
    And I attempt to pay by AMERICAN_EXPRESS
    Then I should be blocked from purchasing and prompted with an shipping restriction message

  Examples:
    | country         | channel  | countryCode  | shippingCountry |
    | United Kingdom  | intl     | RU           | Russia          |
    | United States   | am       | BR           | Brazil          |


  @ecomm @shippingRestrictions @channelSpecific
  Scenario Outline: Anonymous user can purchase a Hazmat restricted item when shipping to a non-restricted country
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    When I have added a Hazmat product restricted for country code <countryCode> to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I continue to the Shipping page as an anonymous user
    And I enter an address from <shippingCountry>
    And I proceed to the Payment page
    And I pay by AMERICAN_EXPRESS
    Then Order Confirmation page is displayed
    And the Order Confirmation page should display the product(s)

  Examples:
    | country         | channel  | countryCode  | shippingCountry |
    | United Kingdom  | intl     | RU           | United Kingdom  |
    | United States   | am       | BR           | United States   |


  @ecomm @shippingRestrictions @channelSpecific
  Scenario Outline: Anonymous user selects non-restricted country to ship items to, then changes the address to restricted country on the payment page and is blocked from purchasing.
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I have added a Hazmat product restricted for country code <countryCode> to my shopping bag
    When I go to Shopping Bag page
    And I proceed to purchase
    And I continue to the Shipping page as an anonymous user
    And I enter an address from <shippingCountry>
    And I proceed to the Payment page
    And I click on Change shipping address link from Payment page
    And I click on Edit shipping address from Shipping options page
    And I enter an address from <restrictedShippingCountry>
    And I proceed to the Payment page
    And I attempt to pay by AMERICAN_EXPRESS
    Then I should be blocked from purchasing and prompted with an shipping restriction message

  Examples:
    | country         | channel  | countryCode  | shippingCountry | restrictedShippingCountry |
    | United Kingdom  | intl     | RU           | United Kingdom  | Russia                    |
    | United States   | am       | BR           | United States   | Brazil                    |


  @ecomm @shippingRestrictions @channelSpecific
  Scenario Outline: Anonymous user selects restricted country to ship items to, then changes the address to non-restricted country on the payment page and is allowed to purchase.
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I have added a Hazmat product restricted for country code <countryCode> to my shopping bag
    When I go to Shopping Bag page
    And I proceed to purchase
    And I continue to the Shipping page as an anonymous user
    And I enter an address from <restrictedShippingCountry>
    And I proceed to the Payment page
    And I click on Change shipping address link from Payment page
    And I click on Edit shipping address from Shipping options page
    And I enter an address from <shippingCountry>
    And I proceed to the Payment page
    And I pay by AMERICAN_EXPRESS
    Then Order Confirmation page is displayed
    And the Order Confirmation page should display the product(s)

  Examples:
    | country         | channel  | countryCode  | shippingCountry | restrictedShippingCountry |
    | United Kingdom  | intl     | RU           | United Kingdom  | Russia                    |
    | United States   | am       | BR           | United States   | Brazil                    |


  @ecomm @shippingRestrictions @channelSpecific
  Scenario Outline: A signed in user selects non-restricted country to ship items to, then adds a new address from a restricted country on the payment page and is blocked from purchasing.
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I am a seaview registered default user
    And I sign in with the correct details
    When I have added a Hazmat product restricted for country code <countryCode> to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I enter an address from <shippingCountry>
    And I proceed to the Payment page
    And I click on Change shipping address link from Payment page
    And I click on Add shipping address from Shipping options page
    And I enter an address from <restrictedShippingCountry>
    And I proceed to the Payment page
    And I attempt to pay by AMERICAN_EXPRESS
    Then I should be blocked from purchasing and prompted with an shipping restriction message

  Examples:
    | country         | channel  | countryCode  | shippingCountry | restrictedShippingCountry |
    | United Kingdom  | intl     | RU           | United Kingdom  | Russia                    |
    | United States   | am       | BR           | United States   | Brazil                    |


  @ecomm @shippingRestrictions @channelSpecific
  Scenario Outline: A signed in user selects to ship items to a restricted country then changes to a non restricted address on the payment page and is allowed to purchase.
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I am a seaview registered default user
    And I sign in with the correct details
    When I have added a Hazmat product restricted for country code <countryCode> to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I enter an address from <restrictedShippingCountry>
    And I proceed to the Payment page
    And I click on Change shipping address link from Payment page
    And I click on Add shipping address from Shipping options page
    And I enter an address from <shippingCountry>
    And I proceed to the Payment page
    And I pay by AMERICAN_EXPRESS
    Then Order Confirmation page is displayed
    And the Order Confirmation page should display the product(s)

  Examples:
    | country         | channel  | countryCode  | shippingCountry | restrictedShippingCountry |
    | United Kingdom  | intl     | RU           | United Kingdom  | Russia                    |
    | United States   | am       | BR           | United States   | Brazil                    |


  @ecomm @shippingRestrictions @channelSpecific
  Scenario Outline: A registered user selects to ship items to a restricted country by express checkout and is blocked from purchasing.
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I am a seaview registered default user
    When I have added a Hazmat product restricted for country code <countryCode> to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I continue to the Shipping page with express checkout
    And I enter an address from <restrictedShippingCountry>
    And I proceed to the Payment page
    And I attempt to pay by AMERICAN_EXPRESS
    Then I should be blocked from purchasing and prompted with an shipping restriction message

  Examples:
    | country         | channel  | countryCode  | restrictedShippingCountry |
    | United Kingdom  | intl     | RU           | Russia                    |
    | United States   | am       | BR           | Brazil                    |


  @ecomm @shippingRestrictions @channelSpecific
  Scenario Outline: A registered user selects to ship items to a non-restricted country by express checkout and is allowed to purchase.
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I am a seaview registered default user
    When I have added a Hazmat product restricted for country code <countryCode> to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I continue to the Shipping page with express checkout
    And I enter an address from <shippingCountry>
    And I proceed to the Payment page
    And I pay by AMERICAN_EXPRESS
    Then Order Confirmation page is displayed
    And the Order Confirmation page should display the product(s)

  Examples:
    | country         | channel  | countryCode  | shippingCountry |
    | United Kingdom  | intl     | RU           | United Kingdom  |
    | United States   | am       | BR           | United States   |


  @ecomm @shippingRestrictions @channelSpecific
  Scenario Outline: A registered user selects to ship items to a non-restricted country by express checkout, then changes the address to a restricted country on the payment page and is blocked from purchasing.
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I am a seaview registered default user
    When I have added a Hazmat product restricted for country code <countryCode> to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I continue to the Shipping page with express checkout
    And I enter an address from <shippingCountry>
    And I proceed to the Payment page
    And I click on Change shipping address link from Payment page
    And I click on Edit shipping address from Shipping options page
    And I enter an address from <restrictedShippingCountry>
    And I proceed to the Payment page
    And I attempt to pay by AMERICAN_EXPRESS
    Then I should be blocked from purchasing and prompted with an shipping restriction message

  Examples:
    | country         | channel  | countryCode  | shippingCountry | restrictedShippingCountry |
    | United Kingdom  | intl     | RU           | United Kingdom  | Russia                    |
    | United States   | am       | BR           | United States   | Brazil                    |


  @ecomm @shippingRestrictions @channelSpecific
  Scenario Outline: A registered user selects to ship items to a restricted country by express checkout, and changes the address to a non-restricted country on the payment page and is allowed to purchase.
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I am a seaview registered default user
    When I have added a Hazmat product restricted for country code <countryCode> to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I continue to the Shipping page with express checkout
    And I enter an address from <restrictedShippingCountry>
    And I proceed to the Payment page
    And I click on Change shipping address link from Payment page
    And I click on Edit shipping address from Shipping options page
    And I enter an address from <shippingCountry>
    And I proceed to the Payment page
    And I pay by AMERICAN_EXPRESS
    Then Order Confirmation page is displayed
    And the Order Confirmation page should display the product(s)

  Examples:
    | country         | channel  | countryCode  | shippingCountry | restrictedShippingCountry |
    | United Kingdom  | intl     | RU           | United Kingdom  | Russia                    |
    | United States   | am       | BR           | United States   | Brazil                    |


  @ecomm @shippingRestrictions @channelSpecific
  Scenario Outline: A registered user selects a non-restricted country to ship items to by express checkout, then adds a new address from a restricted country on the payment page and is blocked from purchasing.
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I am a seaview registered default user
    When I have added a Hazmat product restricted for country code <countryCode> to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I continue to the Shipping page with express checkout
    And I enter an address from <shippingCountry>
    And I proceed to the Payment page
    And I click on Change shipping address link from Payment page
    And I click on Add shipping address from Shipping options page
    And I enter an address from <restrictedShippingCountry>
    And I proceed to the Payment page
    And I attempt to pay by AMERICAN_EXPRESS
    Then I should be blocked from purchasing and prompted with an shipping restriction message

  Examples:
    | country         | channel  | countryCode  | shippingCountry | restrictedShippingCountry |
    | United Kingdom  | intl     | RU           | United Kingdom  | Russia                    |
    | United States   | am       | BR           | United States   | Brazil                    |


  @ecomm @shippingRestrictions @channelSpecific
  Scenario Outline: A registered user selects to ship items to by express checkout to a restricted country, then adds a new non-restricted address on the payment page and is allowed to purchase.
    Given I have selected <country> from the <channel> Channel
    And I go to Shopping Bag page
    And I am a seaview registered default user
    When I have added a Hazmat product restricted for country code <countryCode> to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I continue to the Shipping page with express checkout
    And I enter an address from <restrictedShippingCountry>
    And I proceed to the Payment page
    And I click on Change shipping address link from Payment page
    And I click on Add shipping address from Shipping options page
    And I enter an address from <shippingCountry>
    And I proceed to the Payment page
    And I pay by AMERICAN_EXPRESS
    Then Order Confirmation page is displayed
    And the Order Confirmation page should display the product(s)

  Examples:
    | country         | channel  | countryCode  | shippingCountry | restrictedShippingCountry |
    | United Kingdom  | intl     | RU           | United Kingdom  | Russia                    |
    | United States   | am       | BR           | United States   | Brazil                    |


  @ecomm @shippingRestrictions @channelSpecific
  Scenario Outline: A signed in user tries to ship to a saved restricted address and is blocked from purchasing.
    Given I have selected <country> from the <channel> Channel
    And I am a seaview registered default user
    When I sign in with the correct details
    And I have a shipping address for <restrictedShippingCountry> saved
    And I have added a Hazmat product restricted for country code <countryCode> to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I am taken to the Payment page
    And I attempt to pay by AMERICAN_EXPRESS
    Then I should be blocked from purchasing and prompted with an shipping restriction message

  Examples:
    | country         | channel  | countryCode  | restrictedShippingCountry |
    | United Kingdom  | intl     | RU           | Russia                    |
    | United States   | am       | BR           | Brazil                    |


  @ecomm @shippingRestrictions @channelSpecific
  Scenario Outline: A signed in user tries to ship to a saved non-restricted address and is allowed.
    Given I have selected <country> from the <channel> Channel
    And I am a seaview registered default user
    When I sign in with the correct details
    And I have a shipping address for <shippingCountry> saved
    And I have added a Hazmat product restricted for country code <countryCode> to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I am taken to the Payment page
    And I pay by AMERICAN_EXPRESS
    Then Order Confirmation page is displayed
    And the Order Confirmation page should display the product(s)

  Examples:
  | country         | channel  | countryCode  | shippingCountry |
  | United Kingdom  | intl     | RU           | United Kingdom  |
  | United States   | am       | BR           | United States   |


  @ecomm @shippingRestrictions @channelSpecific
  Scenario Outline: A signed in user has a non restricted shipping address saved, then edits the address to a restricted country on the payment page and is blocked from purchasing.
    Given I have selected <country> from the <channel> Channel
    And I am a seaview registered default user
    When I sign in with the correct details
    And I have a shipping address for <shippingCountry> saved
    And I have added a Hazmat product restricted for country code <countryCode> to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I am taken to the Payment page
    And I click on Change shipping address link from Payment page
    And I click on Edit shipping address from Shipping options page
    And I enter an address from <restrictedShippingCountry>
    And I proceed to the Payment page
    And I attempt to pay by AMERICAN_EXPRESS
    Then I should be blocked from purchasing and prompted with an shipping restriction message

  Examples:
  | country         | channel  | countryCode  | shippingCountry | restrictedShippingCountry |
  | United Kingdom  | intl     | RU           | United Kingdom  | Russia                    |
  | United States   | am       | BR           | United States   | Brazil                    |


  @ecomm @shippingRestrictions @channelSpecific
  Scenario Outline: A signed in user has a restricted shipping address saved, then edits the address to a non-restricted on the payment page and is allowed to purchase.
    Given I have selected <country> from the <channel> Channel
    And I am a seaview registered default user
    When I sign in with the correct details
    And I have a shipping address for <restrictedShippingCountry> saved
    And I have added a Hazmat product restricted for country code <countryCode> to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I am taken to the Payment page
    And I click on Change shipping address link from Payment page
    And I click on Edit shipping address from Shipping options page
    And I enter an address from <shippingCountry>
    And I proceed to the Payment page
    And I pay by AMERICAN_EXPRESS
    Then Order Confirmation page is displayed
    And the Order Confirmation page should display the product(s)

  Examples:
    | country         | channel  | countryCode  | shippingCountry | restrictedShippingCountry |
    | United Kingdom  | intl     | RU           | United Kingdom  | Russia                    |
    | United States   | am       | BR           | United States   | Brazil                    |


  @ecomm @shippingRestrictions @channelSpecific
  Scenario Outline: A signed in user has a non-restricted shipping address saved, then adds a new restricted address on the payment page and is blocked from purchasing.
    Given I have selected <country> from the <channel> Channel
    And I am a seaview registered default user
    When I sign in with the correct details
    And I have a shipping address for <shippingCountry> saved
    And I have added a Hazmat product restricted for country code <countryCode> to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I am taken to the Payment page
    And I click on Change shipping address link from Payment page
    And I click on Add shipping address from Shipping options page
    And I enter an address from <restrictedShippingCountry>
    And I proceed to the Payment page
    And I attempt to pay by AMERICAN_EXPRESS
    Then I should be blocked from purchasing and prompted with an shipping restriction message

  Examples:
    | country         | channel  | countryCode  | shippingCountry | restrictedShippingCountry |
    | United Kingdom  | intl     | RU           | United Kingdom  | Russia                    |
    | United States   | am       | BR           | United States   | Brazil                    |


  @ecomm @shippingRestrictions @channelSpecific
  Scenario Outline: A signed in user has a restricted shipping address saved, then adds a new non-restricted address on the payment page and is allowed to purchase.
    Given I have selected <country> from the <channel> Channel
    And I am a seaview registered default user
    When I sign in with the correct details
    And I have a shipping address for <restrictedShippingCountry> saved
    And I have added a Hazmat product restricted for country code <countryCode> to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I am taken to the Payment page
    And I click on Change shipping address link from Payment page
    And I click on Add shipping address from Shipping options page
    And I enter an address from <shippingCountry>
    And I proceed to the Payment page
    And I pay by AMERICAN_EXPRESS
    Then Order Confirmation page is displayed
    And the Order Confirmation page should display the product(s)

  Examples:
    | country         | channel  | countryCode  | shippingCountry | restrictedShippingCountry |
    | United Kingdom  | intl     | RU           | United Kingdom  | Russia                    |
    | United States   | am       | BR           | United States   | Brazil                    |