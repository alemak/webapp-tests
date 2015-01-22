@nap @slp @napfind
Feature: Sale Listing Page Data
  As a customer
  In order to shop the sale
  I want to view correct product data on a listing page

   @Channelized
  Scenario Outline:  ensure sale listing page has at least one product and information: designer, markdown, original and discount price
    Given I visit the home page
    And I navigate to the sale landing page directly
    When I click the <link> link from the sale landing page
    Then the listing page has at least one product
    And the <information> for each should be displayed

  Examples:
    | link        | information    |
    | Clothing    | original price |
    | Clothing    | discount       |
    | Clothing    | markdown price |
    | Clothing    | designer       |
    | Bags        | original price |
    | Bags        | discount       |
    | Bags        | markdown price |
    | Bags        | designer |
    | Shoes       | original price |
    | Shoes       | discount       |
    | Shoes       | markdown price |
    | Shoes       | designer       |
    | Accessories | original price |
    | Accessories | discount       |
    | Accessories | markdown price |
    | Accessories | designer       |
    | Lingerie    | original price |
    | Lingerie    | discount       |
    | Lingerie    | markdown price |
    | Lingerie    | designer       |


    @NonChannelized
  Scenario Outline: validate that the currency and products in the list match the country in the URL
    Given I navigate to the sale landing page using a url param with country <countryCode> language en and deviceType d
    And the country locale is <country>
    When I click the <link> link from the sale landing page
    Then the listing page has at least one product
    And the products are displayed in the <currencySymbol> currency

  Examples:
    | countryCode | country        | currencySymbol | link        |
    | gb          | United Kingdom | £              | Clothing    |
    | gb          | United Kingdom | £              | Bags        |
    | gb          | United Kingdom | £              | Shoes       |
    | gb          | United Kingdom | £              | Accessories |
    | gb          | United Kingdom | £              | Lingerie    |
    | us          | United States  | $              | Clothing    |
    | us          | United States  | $              | Bags        |
    | us          | United States  | $              | Shoes       |
    | us          | United States  | $              | Accessories |
    | us          | United States  | $              | Lingerie    |
    | au          | Australia      | $              | Clothing    |
    | au          | Australia      | $              | Bags        |
    | au          | Australia      | $              | Shoes       |
    | au          | Australia      | $              | Accessories |
    | au          | Australia      | $              | Lingerie    |
    | cn          | China          | $              | Clothing    |
    | cn          | China          | $              | Bags        |
    | cn          | China          | $              | Shoes       |
    | cn          | China          | $              | Accessories |
    | cn          | China          | $              | Lingerie    |
    | de          | Germany        | €              | Clothing    |
    | de          | Germany        | €              | Bags        |
    | de          | Germany        | €              | Shoes       |
    | de          | Germany        | €              | Accessories |
    | de          | Germany        | €              | Lingerie    |
    | fr          | France         | €              | Clothing    |
    | fr          | France         | €              | Bags        |
    | fr          | France         | €              | Shoes       |
    | fr          | France         | €              | Accessories |
    | fr          | France         | €              | Lingerie    |


   @NonChannelized
  Scenario Outline: validate that the information for each product is displayed in all languages
    Given I visit the home page
    And I navigate to the sale landing page using a url param with country us language <languageCode> and deviceType d
    When I click the <link> link from the sale landing page
    Then the <information> for each should be displayed

  Examples:
    | languageCode | information    | link        |
    | de           | markdown price | Clothing    |
    | de           | markdown price | Lingerie    |
    | de           | markdown price | Shoes       |
    | de           | designer       | Accessories |
    | de           | markdown price | Bags        |
    | zh           | markdown price | Clothing    |
    | zh           | discount       | Lingerie    |
    | zh           | original price | Shoes       |
    | zh           | markdown price | Accessories |
    | zh           | markdown price | Bags        |
    | fr           | markdown price | Clothing    |
    | fr           | designer       | Bags        |
    | fr           | markdown price | Shoes       |
    | fr           | markdown price | Accessories |
    | fr           | markdown price | Lingerie    |


  @NonChannelized
  Scenario Outline: validate that the information for each product is displayed in all countries
    Given I visit the home page
    And I navigate to the sale landing page using a url param with country <countryCode> language en and deviceType d
    When I click the <link> link from the sale landing page
    Then the <information> for each should be displayed

  Examples:
    | countryCode | information      | link        |
    | de           | markdown price  | Clothing    |
    | de           | discount        | Lingerie    |
    | de           | original price  | Shoes       |
    | de           | markdown price  | Accessories |
    | de           | markdown price  | Bags        |
    | cn           | markdown price  | Clothing    |
    | cn           | discount        | Lingerie    |
    | cn           | original price  | Shoes       |
    | cn           | markdown price  | Accessories |
    | cn           | markdown price  | Bags        |
    | us           | discount        | Clothing    |
    | us           | markdown price  | Bags        |
    | us           | original price  | Shoes       |
    | us           | markdown price  | Accessories |
    | us           | markdown price  | Lingerie    |


   @NonChannelized
  Scenario Outline: validated that the user can go from the sale listing page to a product details page in all languages
    Given I visit the home page
    And I navigate to the sale landing page using a url param with country us language <languageCode> and deviceType d
    And I click the <category> link from the sale landing page
    When I click on a random product from the listing page
    Then I am taken to the correct IN_STOCK product details page


  Examples:
    | languageCode | category     |
    | en           | Clothing     |
    | en           | Bags         |
    | en           | Shoes        |
    | en           | Accessories  |
    | en           | Lingerie     |
    | fr           | Clothing     |
    | fr           | Bags         |
    | fr           | Shoes        |
    | fr           | Accessories  |
    | fr           | Lingerie     |
    | de           | Clothing     |
    | de           | Bags         |
    | de           | Shoes        |
    | de           | Accessories  |
    | de           | Lingerie     |
    | zh           | Clothing     |
    | zh           | Bags         |
    | zh           | Shoes        |
    | zh           | Accessories  |
    | zh           | Lingerie     |