@nap @NonChannelized @napeval
Feature: CurrencyChecking.feature Correct selling currency for different APAC countries
  As a business
  We want the different selling currencies for different counties for the NAP APAC site
  US$ for China, AU$ for Australia, HK$ for Hong Kong, and US$ for other countries (use Indian for testing)


  @region=APAC
  Scenario Outline: Products are priced in correct currency in Product listing page
    Given I have selected <country> from the <channel> Channel
    When I am on any Product Listing page
    Then I should see products priced in <currency>

  Examples:
    | channel | country   | currency   |
    | apac    | China     | USD $      |
    | apac    | Australia | AUD $      |
    | apac    | Hong Kong | HKD $      |
    | apac    | India     | USD $      |


  @region=APAC
  Scenario Outline: Product is priced in correct currency in Product details page
    Given I have selected <country> from the <channel> Channel
    When I am on any Product pages
    Then I should see the product priced in <currency>

  Examples:
    | channel | country   | currency   |
    | apac    | China     | USD $      |
    | apac    | Australia | AUD $      |
    | apac    | Hong Kong | HKD $      |
    | apac    | India     | USD $      |