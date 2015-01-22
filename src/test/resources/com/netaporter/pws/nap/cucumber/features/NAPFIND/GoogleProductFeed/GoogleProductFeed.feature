@NonChannelized @nap
Feature: Validate google product Feed


  Scenario Outline: Validate Google product feed: availability check
      # change no of products threshold on "The product feed contains more than 50 products"
      #( gdata-productfeed-nap-apac.hk.zh.xml contains 400-500 products at the time of writing these tests)
      Given I am on <channel>
      And I retrieve a product feed <feedName>
      Then The product feed contains more than 50 products
      When I randomly pick 10 product and availability pairs from the feed
      Then I can find these products have the same stock info on the website
      When I randomly pick one product of each availability values from the feed
      Then I can find these products have the same stock info on the website

  Examples:
      | channel |      feedName                             |
      | intl    |     gdata-productfeed-nap-intl.de.de.xml  |
      | intl    |     gdata-productfeed-nap-intl.fr.fr.xml  |
      | intl    |     gdata-productfeed-nap-intl.gb.en.xml  |
      | apac    |     gdata-productfeed-nap-apac.au.en.xml  |
      |  am     |     gdata-productfeed-nap-am.us.en.xml    |


  Scenario Outline: Validated that price and currency in the feed are the same as the ones from the website
      Given I am on <channel>
      And I am on change country page
      And I change my country to <country>
      When I retrieve a product feed <feedName>
      And I randomly pick 10 product and price pairs from the feed
      Then I can find these products have the same price info on the website

  Examples:
      | channel | country         |     feedName                             |
      | intl    | United Kingdom  |    gdata-productfeed-nap-intl.gb.en.xml  |
      | intl    | Germany         |    gdata-productfeed-nap-intl.de.de.xml  |
      | intl    | France          |    gdata-productfeed-nap-intl.fr.fr.xml  |
      |  am     | United States   |    gdata-productfeed-nap-am.us.en.xml    |
      | apac    | Australia       |    gdata-productfeed-nap-apac.au.en.xml  |


  Scenario Outline: Validated that size info from the feed matches the one in the website
      Given I am on <channel>
      When I retrieve a product feed <feedName>
      And I randomly pick 10 product and size pairs from the feed
      Then I can find these products have the same size info on the website

  Examples:
      | channel |      feedName                             |
      | intl    |     gdata-productfeed-nap-intl.gb.en.xml  |
      | intl    |     gdata-productfeed-nap-intl.de.de.xml  |
      | intl    |     gdata-productfeed-nap-intl.fr.fr.xml  |
      |  am     |     gdata-productfeed-nap-am.us.en.xml    |
      | apac    |     gdata-productfeed-nap-apac.au.en.xml  |

#   these often fail due to unavailable products in the gfeed. scenario above mostly covers this anyway
#Scenario Outline: Validated that all types of size info from the feed matches the one in the website
#    Given I am on <channel>
#    When I retrieve a product feed <feedName>
#    And I randomly pick one product of each size values from the feed
#    Then I can find these products have the same size info on the website
#
#  Examples:
#    | channel |      feedName                             |
#    | intl    |     gdata-productfeed-nap-intl.gb.en.xml  |
#    | intl    |     gdata-productfeed-nap-intl.de.de.xml  |
#    | intl    |     gdata-productfeed-nap-intl.fr.fr.xml  |
#    |  am     |     gdata-productfeed-nap-am.us.en.xml    |
#    | apac    |     gdata-productfeed-nap-apac.au.en.xml  |