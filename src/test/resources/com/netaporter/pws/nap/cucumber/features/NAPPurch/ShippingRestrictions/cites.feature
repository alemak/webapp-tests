  Feature: CITES Restrictions
   #test requires product service api
  @todo
  @productServiceApi @Channelized @pdp
  Scenario: The product page of a CITES restricted product displays a warning when opened from a CITES restricted country
    Given I visit a Cites restricted python bag product details page
    When I go to a country that product is restricted in
    Then a warning is displayed on the product details page

  #test requires product service api
  @todo
  @productServiceApi @Channelized @pdp
  Scenario: The product page of a CITES restricted product does not display a warning when opened from a non-CITES restricted country
    Given I visit a Cites restricted python bag product details page
    When I go to a country that product is not restricted in
    Then a warning should not be displayed on the product details page

  #test requires product service api
  @todo
  @productServiceApi @Channelized @pdp
  Scenario: Adding a CITES restricted product to the shopping bag from a CITES restricted country will display a warning on the shopping bag
    Given I visit a Cites restricted python bag product details page
    When I go to a country that product is restricted in
    And I add a product into my shopping bag from the product details page
    When I go to Shopping Bag page
    Then a warning is displayed on my shopping bag page

  #test requires product service api
  @todo
  @productServiceApi @Channelized @pdp
  Scenario: Adding a CITES restricted product to the shopping bag from a non-CITES restricted country will display a warning
    Given I visit a CITES restricted python bag product details page
    And I go to a country that product is not restricted in
    And I add a product into my shopping bag from the product details page
    When I go to Shopping Bag page
    Then a warning is not displayed on my shopping bag page

  #test requires product service api
  @todo
  @productServiceApi @Channelized
  Scenario: Purchasing a CITES restricted product will be blocked on the payment page for users from CITES restricted countries
    Given I am a seaview registered default user
    And I sign in with the correct details
    And I visit a CITES restricted python bag product details page
    And I add a product into my shopping bag from the product details page
    And I go to Shopping Bag page
    When I ship to restricted country
    And I proceed to the Payment page
    Then a warning is displayed in the payment page

  #test requires product service api
  @todo
  @productServiceApi @NonChannelized @region=INTL
  Scenario: Purchasing a CITES restricted product will not be blocked on the payment page for users from non-CITES restricted countries
    #only INTL: cant send exotic skins outside of the EU
    Given I am on intl
    And I am a seaview registered default user
    And I sign in with the correct details
    And I visit a CITES restricted python bag product details page
    And I add a product into my shopping bag from the product details page
    And I go to Shopping Bag page
    And I go to Shipping page as a signed in user and enter my address
    When I proceed to the Payment page
    Then a warning is not displayed in the payment page

  #test requires product service api
  @todo
  @productServiceApi @Channelized
  Scenario: Purchasing a CITES restricted product will be blocked on the payment page for all users trying to ship to CITES restricted countries
    Given I am a seaview registered default user
    And I sign in with the correct details
    And I visit a CITES restricted python bag product details page
    And I add a product into my shopping bag from the product details page
    And I go to Shopping Bag page
    And I ship to restricted country
    And I proceed to the Payment page
    #And I fill in my payment details using a valid credit card <card type>
    When I proceed to purchase on Payment page using a valid credit card VISA_CREDIT_CARD
    Then a warning is displayed in the payment page