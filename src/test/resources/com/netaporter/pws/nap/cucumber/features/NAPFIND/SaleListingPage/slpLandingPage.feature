@napfind @Channelized @slp @nap
Feature: Check that the sale landing page is displayed correctly and all links work correctly in all languages
  As NAP
  I want the sale landing page to work correctly
  So users can navigate to the desired categories

  Scenario Outline: all links on the sale landing page work correctly on all languages for desktop site
    Given I go to Home page
    And I go to Change Country page
    And I change my language to <language>
    And I navigate to the sale landing page directly
    When I click the <link> link from the sale landing page
    Then I am on the <pageName> sale listing page
    And the URL contains the correct <link> name
    And the listing page has at least one product

  Examples:
    | language  | link                    | pageName     |
    | English   | Clothing                | Clothing     |
    | English   | Dresses                 | Clothing     |
    | English   | Bags                    | Bags         |
    | English   | Clutch Bags             | Bags         |
    | English   | Shoes                   | Shoes        |
    | English   | Pumps                   | Shoes        |
    | English   | Accessories             | Accessories  |
    | English   | Jewelry                 | Accessories  |
    | English   | Lingerie                | Lingerie     |
    | English   | Bras                    | Lingerie     |
    | German    | Clothing                | KLEIDUNG     |
    | German    | Dresses                 | KLEIDUNG     |
    | German    | Bags                    | TASCHEN      |
    | German    | Shoes                   | SCHUHE       |
    | German    | Accessories             | ACCESSOIRES  |
    | German    | Lingerie                | Lingerie     |
    | French    | Clothing                | VÊTEMENTS    |
    | French    | Dresses                 | VÊTEMENTS    |
    | French    | Bags                    | SACS         |
    | French    | Shoes                   | CHAUSSURES   |
    | French    | Accessories             | ACCESSOIRES  |
    | French    | Lingerie                | Lingerie     |
    | Chinese   | Clothing                | 服装         |
    | Chinese   | Dresses                 | 服装         |
    | Chinese   | Bags                    | 包袋         |
    | Chinese   | Shoes                   | 鞋履         |
    | Chinese   | Accessories             | 配饰         |
    | Chinese   | Lingerie                | 内衣         |