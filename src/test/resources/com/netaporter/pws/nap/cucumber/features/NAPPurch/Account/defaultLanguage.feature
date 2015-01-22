@nap @NonChannelized @purch
Feature: defaultLanguage.feature Test the default Language for customers after purchasing an item
  As a customer
  I want my default language remembered

  @ecomm @channelSpecific @region=INTL @defaultLanguage @criticalPath
  Scenario Outline: Anonymous customer chooses a language as the default language, completes a purchase and that default
    language is stored in the database
    Given I am on change country page
    And I change my language to <Language>
    And I add a random product to my bag
    And I view my shopping bag
    And I proceed to purchase
    And I sign in anonymously within the purchase path
    And I fill out a shipping address for country <Shipping country>
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And I click on payment option VISA_CREDIT_CARD
    And I fill in the card number as 4132220000000015
    And I fill in the card holder name as Mr Card Holder
    And I fill in the security number as 123
    And I fill in the expiry month 03 and expiry year 16
    And I click purchase now from the payment page
    Then My order is confirmed
    And My default language is stored as <Stored language>

  Examples:
   |Language |Shipping country |Stored language |
   |German   |Deutschland      |de              |
   |English  |France           |en              |
   |Chinese  |   法国          |zh              |
   |French   |Allemagne        |fr              |


  @ecomm @channelSpecific @region=INTL @defaultLanguage @criticalPath
  Scenario Outline: Registered customer chooses a language as the default language, completes a purchase and that default
    language is stored in the database
    Given I am on change country page
    And I change my language to <Language>
    And I am a seaview registered default user
    And I add a random product to my bag
    And I view my shopping bag
    And I proceed to purchase
    And I sign in using the default customer within the purchase path
    And I fill out a shipping address for country <Shipping country>
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And I click on payment option AMERICAN_EXPRESS
    And I fill in the card number as 343434100000006
    And I fill in the card holder name as Mr Card Holder
    And I fill in the security number as 3434
    And I fill in the expiry month 03 and expiry year 16
    And I click purchase now from the payment page
    Then My order is confirmed
    And My default language is stored as <Stored language>

  Examples:
   |Language |Shipping country |Stored language |
   |German   |Deutschland      |de              |
   |English  |France           |en              |
   |Chinese  |   法国          |zh              |
   |French   |Allemagne        |fr              |