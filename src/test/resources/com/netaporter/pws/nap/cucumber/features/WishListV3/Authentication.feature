@wishlistV3 @desktop
Feature: Wishlist V3 Authentication
  As a non signed or remembered user,
  I want to be directed back to the page I was on after successfully logging in
  So that I can continue where I left


  @COM-1539 @sanity
  Scenario: Non logged in or remembered user directly hits sign in URL with query params with 'redirect' to All-Items wishlist page and 'httpsRedirect' is true
    Given I am a quickly registered user on intl
    And I sign out
    And I clear my cookies
    And I directly hit the signin URL with query params redirect = /wishlist/all-items and httpsRedirect = true
    When I sign in with the correct details on the Sign In page
    Then I should be on the wishlist page called All-Items
    And I should see the wishlist page title called All Items
    And I should see the wishlist header called ALL ITEMS
    And The current url should be https

  @COM-1539
  Scenario: Logged in user directly hits sign in URL with query params 'redirect' to All-Items wishlist page and 'httpsRedirect' is true
    Given I am a quickly registered user on intl
    And I directly hit the signin URL with query params redirect = /wishlist/all-items and httpsRedirect = true
    When I sign in with the correct details on the Sign In page
    Then I should be on the wishlist page called All-Items
    And I should see the wishlist page title called All Items
    And I should see the wishlist header called ALL ITEMS
    And The current url should be https

  @COM-1539
  Scenario: A remembered user directly hits sign in URL with query params 'redirect' to All-Items wishlist page and 'httpsRedirect' is true
    Given I am a quickly registered user on intl
    And I sign out
    And I directly hit the signin URL with query params redirect = /wishlist/all-items and httpsRedirect = true
    When I sign in with the correct details on the Sign In page
    Then I should be on the wishlist page called All-Items
    And I should see the wishlist page title called All Items
    And I should see the wishlist header called ALL ITEMS
    And The current url should be https

  @COM-1539 @intl
  Scenario: Non logged in or remembered user directly hits the sign in url with query params 'redirect' to Shopping Bag page and 'httpsRedirect' is false
    Given I am a quickly registered user on intl
    And I sign out
    And I clear my cookies
    And I directly hit the signin URL with query params redirect = /intl/shoppingbag.nap and httpsRedirect = false
    When I sign in with the correct details on the Sign In page
    Then I should be on the Shopping Bag page
    And The current url should be http

  @COM-1539
  Scenario: Ensure Non logged in or remembered user cannot be redirected to another domain eg google
    Given I am a quickly registered user on intl
    And I sign out
    And I clear my cookies
    And I directly hit the signin URL with query params redirect = /www.google.com and httpsRedirect = false
    When I sign in with the correct details on the Sign In page
    Then I should be on This Page Cannot Be Found page

  @COM-1539
  Scenario: Non logged in or remembered user directly hits the sign in url with query params 'redirect' to Shopping Bag page and 'httpsRedirect' is left blank
    Given I am a quickly registered user on intl
    And I sign out
    And I clear my cookies
    And I directly hit the signin URL with query params redirect = /intl/shoppingbag.nap and httpsRedirect = blank
    When I sign in with the correct details on the Sign In page
    Then I should be on the Shopping Bag page
    And The current url should be http

  @COM-1539
  Scenario: Non logged in or remembered user directly hits the sign in url when query params 'redirect' is left empty and 'httpsRedirect' is false
    Given I am a quickly registered user on intl
    And I sign out
    And I clear my cookies
    And I directly hit the signin URL with query params redirect = blank and httpsRedirect = false
    When I sign in with the correct details on the Sign In page
    Then I am on the my account page
    And The current url should be https


  @COM-1544 @COM-1652 @sanity
  Scenario: Correct cookie session details are set for someone from United Kingdom when accessing a wishlist url direct from a browser
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called No Session Wishlist test
    And I sign out
    And I clear my cookies
    When I view that specific wishlist via its direct url
    Then The cookie called JSESSIONID_INTL has a value that is not null
    And The cookie called channel has the value intl
    And The cookie called country_iso has the value GB
    And The cookie called lang_time has a value that is not null
    And The cookie called lang_iso has the value en
    And The cookie called deviceType has the value Desktop

#  Below two tests commented out as need to send custom headers which isn't supported very well by Selenium - therefore being tested manually
#
#  @COM-1544 @COM-1652
#  Scenario: Correct cookie session details are set for someone from the United States when accessing a wishlist url direct from a browser
#    Given I am a quickly registered user on intl
#    And I create a new wishlist via the WOAS API called No Session Wishlist test
#    And I sign out
#    And I clear my cookies
#    And I have a cookie set with name GEO and the value is COUNTRIES:US
#    When I view that specific wishlist via its direct url
#    Then The cookie called JSESSIONID_AM has a value that is not null
#    And The cookie called channel has the value am
#    And The cookie called country_iso has the value US
#    And The cookie called lang_time has a value that is not null
#    And The cookie called lang_iso has the value en
#    And The cookie called deviceType has the value Desktop

#  @COM-1544 @COM-1652
#  Scenario: Correct cookie session details are set for someone from China when accessing a wishlist url direct from a browser
#    Given I am a quickly registered user on intl
#    And I create a new wishlist via the WOAS API called No Session Wishlist test
#    And I sign out
#    And I clear my cookies
#    And I have a cookie set with name GEO and the value is COUNTRIES:CN
#    When I view that specific wishlist via its direct url
#    Then The cookie called JSESSIONID_AM has a value that is not null
#    And The cookie called channel has the value apac
#    And The cookie called country_iso has the value CN
#    And The cookie called lang_time has a value that is not null
#    And The cookie called lang_iso has the value zh
#    And The cookie called deviceType has the value Desktop

  @COM-1544 @COM-1652
  Scenario: Correct session details are set for a new user in United Kingdom with no session accesses the SetupSession redirect URL for a wishlist
    Given I clear my cookies
    When I attempt to hit a SetupSession wishlist url with the GEO header set to GB
    Then A 302 status code is returned
    And The SetupSession redirect response is returned with the correct details

  @COM-1544 @COM-1652
  Scenario: Correct session details are set for a new user in China with no session accesses the SetupSession redirect URL for a wishlist
    Given I clear my cookies
    When I attempt to hit a SetupSession wishlist url with the GEO header set to CN
    Then A 302 status code is returned
    And The SetupSession redirect response is returned with the correct details

  @COM-1544 @COM-1652
  Scenario: Correct session details are set for a new user in America with no session accesses the SetupSession redirect URL for a wishlist
    Given I clear my cookies
    When I attempt to hit a SetupSession wishlist url with the GEO header set to US
    Then A 302 status code is returned
    And The SetupSession redirect response is returned with the correct details

  @COM-1651 @sanity @COM-2194
  Scenario: Access custom wishlist via URL after JSESSION expires and user is therefore remembered
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Logged Out Test
    And I delete the JSESSIONID cookie
    When I view that specific wishlist via its direct url
    Then  I should see the correct custom wishlist page title
    And  I should see the correct wishlist header

  @COM-1651
  Scenario: Access All Items wishlist via URL after JSESSION expires and user is therefore remembered
    Given I am a quickly registered user on intl
    And I navigate to the All Items page for wishlist
    And I delete the JSESSIONID cookie
    When I view that specific wishlist via its direct url
    Then I should see the correct wishlist page title
    And  I should see the correct wishlist header

  @COM-1651
  Scenario: Access All Items Wishlist via direct url after JSESSIONID and remembered cookies deleted
    Given I am a quickly registered user on intl
    And I navigate to the All Items page for wishlist
    And I delete the JSESSIONID cookie
    And I delete the cookie called remembered_7070
    When I view that specific wishlist via its direct url
    Then I am taken to the NAP SIGN IN page
    And I sign in with the correct details on the Sign In page
    And I should see the correct wishlist page title
    And I should see the correct wishlist header


  @COM-1651
  Scenario: Access a custom wishlist wishlist via direct URL after JSESSIONID and remembered cookies deleted
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Logged Out Test
    And I delete the JSESSIONID cookie
    And I delete the cookie called remembered_7070
    When I view that specific wishlist via its direct url
    Then I am taken to the NAP SIGN IN page

  @COM-1741 @sanity
    Scenario: I cannot view another users wishlist
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called User 1 Wishlist
    And I sign out
    And I submit valid details on the registration form
    When I view that specific wishlist via its direct url
    Then I should be on This Page Cannot Be Found page

  @COM-2169 @sanity
  Scenario: I cannot view a wishlist when not logged in but am prompted to log in
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called User 1 Wishlist
    And I sign out
    When I view that specific wishlist via its direct url
    Then I am on the NAP SIGN IN page


