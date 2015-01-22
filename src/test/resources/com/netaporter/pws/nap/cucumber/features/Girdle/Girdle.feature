@girdleSanity @Channelized @napeval @nap

  Feature: Sanity tests for girdle functionality - checks country/currency/language, top nav, footer, mini shopping bag, service message banner, search and girdle attributes

    Scenario: Listing App - Check that Clothing TopNav dropdown works correctly
      Given I visit the home page
      And I pick a Clothing category in the product listing page
      When I navigate to the sport landing page using the TopNav
      Then Girdle attributes are present
      When I hover over the Clothing link from the TopNav
      Then the Clothing TopNav dropdown is displayed
      When I click the first link in the Clothing TopNav dropdown
      Then I am taken to the NAP Shop/List listing page

    Scenario Outline: user is taken to correct url based on the http header - country, language and deviceType parameters in http header
      When I navigate to the sports landing page using a url param with country <countryCode> language <languageCode> and deviceType d
      Then the country displayed in the top nav is <country>
      And the language locale is <language>

      Examples:
      | countryCode  | country        | languageCode | language |
      | gb           | Royaume-Uni    | fr           | Français |
      | fr           | Frankreich     | de           | Deutsch  |
      | de           | Germany        | en           | English  |
      | ru           | 俄罗斯          | zh           | 中文      |
      | us           | United States  | en           | English  |
      | hk           | Hong Kong      | de           | Deutsch  |

#    Scenario: Product App - Mini shopping bag is visible in product page app
#      Given I visit the home page
#      And I navigate to 495274 product detail page
#      When I click glass add to bag
#      Then I should see mini bag overlay with correct information
#      And shopping bag counter increases
#      When I click proceed to purchase in mini bag
#      Then I should see Google Glass product in the shopping bag

#    Scenario: Product App - Sign in link redirect to sign in page in product page app
#      Given I visit the home page
#      And I navigate to 495274 product detail page
#      Then Girdle attributes are present
#      When I click Sign in link in app page
#      Then I am on the sign in page

    Scenario: Listing App - Check that sport desktop app layout do not display error message
      Given I visit the home page
      When I pick a Clothing category in the product listing page
      And I am currently on a Golf sport listing page
      When I force to display app layout
      Then Girdle attributes are present
      And product list are displayed

    Scenario: Listing App - Search for valid keywords that should return correct results
      Given I visit the home page
      Given I am currently on a Tennis sport listing page
      When I search for a beach keyword
      Then I see expected search results

#    Scenario: Product App - Search for valid keywords that should return correct results
#      Given I visit the home page
#      And I navigate to 495274 product detail page
#      When I search for a leather bag keyword
#      Then I see expected search results
#
#    Scenario Outline: Product App - Footer components exists and redirects to correct page
#      Given I visit the home page
#      And I navigate to 495274 product detail page
#      When I click on <footerLinks> in the Footer
#      Then respective pages are visible
#
#    Examples:
#      |   footerLinks           |
#      |    Contact us           |
#      | Shipping Information    |
#      | Return and Exchanges    |
#      |  Payment security       |
#      |       FAQs              |
#      |  Terms and Conditions   |
#      |     Gift Cards          |
#      |    The Company          |
#      |      Careers            |
#      |     Affiliates          |
#      |     Advertising         |

#       Scenario: Product App - Click action on NAP logo redirect to live site homepage
#      Given I visit the home page
#      And I navigate to 495274 product detail page
#      When I click NAP logo
#      Then I am on the home page


