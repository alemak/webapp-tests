@wishlistV3 @desktop
Feature: Wishlist V3 Change Country And Language From Wishlist
  As a signed in NAP user,
  I want to be redirected back to where I was when I change my language and/or country
  So that I can continue where I left off


#  change countries on INTL, tests on All Items wishlist and custom wishlists


  ## Known-failure: COM-1881
  @COM-1537
  Scenario: Change country from All Items wishlist within INTL channel
    Given I clear my cookies
    And I am a signed in user on intl
    And I click the wishlist link on the header
    When I open the My Preferences overlay by clicking the change country link
    And I change my country to France
    Then I should be on the wishlist page called All Items
    And I should see the correct wishlist page title
    And I should see the correct wishlist header
    And I should see the site furniture header for the country France
    And I should be on the same url as before I updated my country and language preferences

  ## Known-failure: COM-1881
  @COM-1537 @COM-1961 @sanity
  Scenario: Change country from a new custom wishlist within INTL channel
    Given I clear my cookies
    And I am a signed in user on intl
    And I create a new wishlist via the WOAS API called INTL COUNTRY TEST
    And I click the wishlist link on the header
    And I click the wishlist navigation menu item called INTL COUNTRY TEST
    When I open the My Preferences overlay by clicking the change country link
    And I change my country to France
    Then I should be on the wishlist page called INTL COUNTRY TEST
    And I should see the correct custom wishlist page title
    And I should see the correct wishlist header
    And I should see the site furniture header for the country France
    And I should be on the same url as before I updated my country and language preferences


#  change countries when on AM - just All Items wishlist
## Known-failure: COM-1881
  @COM-1537 @COM-1961
  Scenario: Change country from All Items wishlist within AM channel
    Given I clear my cookies
    And I am a signed in user on am
    And I open the My Preferences overlay by clicking the change country link
    And I change my country to United States
    And I click the wishlist link on the header
    When I open the My Preferences overlay by clicking the change country link
    And I change my country to Mexico
    Then I should be on the wishlist page called All Items
    And I should see the correct wishlist page title
    And I should see the correct wishlist header
    And I should see the site furniture header for the country Mexico
    And I should be on the same url as before I updated my country and language preferences


#  change countries when on APAC - just for new custom wishlist
## Known-failure: COM-1881
  @COM-1537 @COM-1961
  Scenario: Change country from All Items wishlist within APAC channel
    Given I am a signed in user on apac
    And I open the My Preferences overlay by clicking the change country link
    And I change my country to Hong Kong
    And I create a new wishlist via the WOAS API called APAC COUNTRY TEST
    And I click the wishlist link on the header
    And I click the wishlist navigation menu item called APAC COUNTRY TEST
    When I open the My Preferences overlay by clicking the change language link
    And I change my country to Japan
    Then I should be on the wishlist page called APAC COUNTRY TEST
    And I should see the correct custom wishlist page title
    And I should see the correct wishlist header
    And I should see the site furniture header for the country Japan
    And I should be on the same url as before I updated my country and language preferences

# tests for changing channels which shouldn't return to wishlist tests

  @COM-1537 @COM-1961
  Scenario: Changing channel from INTL to AM does not return you to the wishlist
    Given I clear my cookies
    And I am a signed in user on intl
    And I click the wishlist link on the header
    And the country displayed in the top nav is United Kingdom
    When I open the My Preferences overlay by clicking the change country link
    And I change my country to United States
    Then I am sent to the all items wish list page
    And the country displayed in the top nav is United States

  @COM-1537 @COM-1961
  Scenario: Changing channel from AM to INTL does not return you to the wishlist
    Given I clear my cookies
    And I am a signed in user on am
    And I open the My Preferences overlay by clicking the change country link
    And I change my country to United States
    And I click the wishlist link on the header
    And the country displayed in the top nav is United States
    When I open the My Preferences overlay by clicking the change country link
    And I change my country to United Kingdom
    Then I am sent to the all items wish list page
    And the country displayed in the top nav is United Kingdom

  @COM-1537 @COM-1961
  Scenario: Changing channel from APAC to INTL does not return you to the wishlist
    Given I clear my cookies
    And I am a signed in user on apac
    And I open the My Preferences overlay by clicking the change country link
    And I change my country to Hong Kong
    And I click the wishlist link on the header
    And the country displayed in the top nav is Hong Kong
    When I open the My Preferences overlay by clicking the change country link
    And I change my country to United Kingdom
    Then I am sent to the all items wish list page
    And the country displayed in the top nav is United Kingdom

  # Language
  @COM-1537
  Scenario Outline: Change language from All Items page
    Given I am a signed in user on <channel>
    And I navigate to the All Items page for wishlist
    When I open the My Preferences overlay by clicking the change language link
    And I change my language to <language>
    Then I should be on the wishlist page called <all items>
    And I should see the wishlist page title called <all items>
    And I should see the wishlist header called <all items>
    And I should see the site furniture header in <native language name>

  Examples:
    | channel | language | native language name | all items         |
    | intl    | French   | Français             | Tous les articles |
    | am      | German   | Deutsch              | Alle Artikel      |
    | apac    | Chinese  | 中文                  | 所有产品           |

  @COM-1537
  Scenario Outline: Change language from a new custom wishlist
    Given I am a signed in user on <channel>
    And I wait 3 seconds
    And I create a new wishlist via the WOAS API called Custom Lang WL
    And I wait 3 seconds
    And I navigate to the All Items page for wishlist
    And I click the wishlist navigation menu item called Custom Lang WL
    When I open the My Preferences overlay by clicking the change language link
    And I change my language to <language>
    Then I should be on the wishlist page called Custom Lang WL
    And I should see the custom wishlist page title in <language> called Custom Lang WL
    And I should see the wishlist header called Custom Lang WL
    And I should see the site furniture header in <native language name>

  Examples:
  | channel | language | native language name |
  | intl    | French   | Français             |
  | am      | German   | Deutsch              |
  | apac    | Chinese  | 中文                  |
