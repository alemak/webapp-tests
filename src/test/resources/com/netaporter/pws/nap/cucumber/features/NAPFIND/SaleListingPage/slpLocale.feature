@napfind @slp @nap
Feature: Check that country, language and device are set correctly based on the following priority: cookie, http header, default to gb
  As a user
  I want to be taken to a locale that is relevant to me
  So I don't get confused

  @NonChannelized
  Scenario Outline: user is taken to correct country based on the cookie
    Given I set the country cookie value to <countryCode>
    When I navigate to the sale landing page directly
    Then the country displayed in the top nav is <country>
    And the country url parameter should be <countryCode>

  Examples:
    | countryCode    | country        |
    | gb             | United Kingdom |
    | fr             | France         |
    | de             | Germany        |
    | ru             | Russia         |
    | us             | United States  |
    | au             | Australia      |
    | hk             | Hong Kong      |


  @NonChannelized
  Scenario: the default country locale should be United Kingdom when the user has no cookie/http header, based on the ip that webDriver uses
    Given I navigate to the sale landing page directly
    Then the country displayed in the top nav is United Kingdom
    And the country url parameter should be gb


  @NonChannelized
  Scenario Outline: user is taken to the correct country locale based on the following priority: url > cookie
    Given I set the country cookie value to <countryCode1>
    When I navigate to the sale landing page using a url param with country <countryCode2> language en and deviceType d
    # TODO: more specific ?  locale in the header
    Then the country locale is <country>
    And the country url parameter should be <countryCode2>

  Examples:
    | countryCode1    | countryCode2   | country        |
    | fr              | gb             | United Kingdom |
    | au              | fr             | France         |
    | ru              | de             | Germany        |
    | gb              | ru             | Russia         |
    | hk              | us             | United States  |
    | us              | au             | Australia      |
    | de              | hk             | Hong Kong      |


  @NonChannelized
  Scenario Outline: user is taken to correct url based on the http header - country, language and deviceType parameters in http header
    Given I navigate to the sale landing page using a url param with country <countryCode> language <languageCode> and deviceType d
    Then the country displayed in the top nav is <country>
    And the language locale is <language>

  Examples:
    | countryCode  | country        | languageCode | language |
    | gb           | Royaume-Uni    | fr           | Français |
    | fr           | Frankreich     | de           | Deutsch  |
    | de           | Germany        | en           | English  |
    | ru           | 俄罗斯          | zh           | 中文      |
    | us           | United States  | en           | English  |
    | au           | Australie      | fr           | Français |
    | hk           | Hong Kong      | de           | Deutsch  |


  @Channelized
  Scenario Outline: language is set correctly based on the cookie
    Given I set the language cookie value to <languageCode>
    And I visit the home page
    When I navigate to the sale landing page directly
    Then the language locale is <language>
    And the language url parameter should be <languageCode>

  Examples:
    | languageCode  | language |
    | en            | English  |
    | fr            | Français |
    | de            | Deutsch  |
    | zh            | 中文      |

#   this needs a way to automatically remove favourite languages from chromedriver before starting the browser
#  @NonChannelized       @w
#  Scenario Outline: language is set correctly based on country
#    Given I set the country cookie value to <countryCode>
#    When I navigate to the sale landing page directly
#    Then the language locale is <language>
#
#  Examples:
#    | language | countryCode |
#    | English  | gb          |
#    | Français | fr          |
#    | Deutsch  | de          |
#    | English  | ru          |
#    | English  | us          |
#    | English  | au          |
#    | 中文      | cn          |


  @Channelized
  Scenario: the default language locale should be gb when the user has no cookie/http header
    Given I visit the home page
    And I navigate to the sale landing page directly
    Then the language locale is English
    And the language url parameter should be en


  @Channelized
  Scenario Outline: the deviceType url parameter is set correctly based on the cookie
    Given I visit the home page
    And I set the deviceType cookie value to <deviceTypeCookie>
    When I navigate to the sale landing page directly
    Then the deviceType url parameter should be <deviceType>

  Examples:
    | deviceTypeCookie    | deviceType |
    | Mobile              | m          |
    | Desktop             | d          |

#   this fails when run as part of the suite, but passes when run individually, needs further investigation
#    #check that trying to go to the sale landing page using a mobile user agent takes the user to the /m site
#  @userAgentOverride=iphone @Channelized
#  Scenario: the deviceType url parameter is set correctly based on the userAgent
#    Given I visit the home page
#    When I navigate to the sale landing page directly
#    Then the deviceType url parameter should be m


  @Channelized
  Scenario: the default deviceType should be d when the user has no cookie/http header
    Given I visit the home page
    When I navigate to the sale landing page directly
    Then the deviceType url parameter should be d


    #cookie should win over browser user agent
    #this fails when run as part of the suite, works fine when run independently
#  @userAgentOverride=iphone @Channelized
#  Scenario: the deviceType should be set based on this priority: cookie > userAgent
#    Given I visit the home page
#    And I set the deviceType cookie value to Desktop
#    When I navigate to the sale landing page directly
#    Then the deviceType url parameter should be d

# uncomment these if we can find a way to determine the region from the actual page, not from webbot which is unreliable
#  @region=INTL @NonChannelized
#  Scenario: channel should be set by the country
#     Given I set the country cookie value to au
#     When I navigate to the sale landing page directly
#     Then I am on the INTL region
#
#
#  @region=AM @NonChannelized  @w
#  Scenario: channel should be set by the country
#    Given I set the country cookie value to au
#    When I navigate to the sale landing page directly
#    Then I am on the AM region
#
#
#  @region=APAC @NonChannelized
#  Scenario: channel should be set by the country
#    Given I set the country cookie value to au
#    When I navigate to the sale landing page directly
#    Then I am on the APAC region