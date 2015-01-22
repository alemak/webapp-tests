Feature: Account Details API
  An API that allows us to retrieve account info and logged in state


  #TODO: Rather than register users via the website (slow), this script could call in to Seaview directly

  Scenario Outline: Remembered user details returned correctly by API
    Given I am on <channel>
    And I submit valid details on the registration form
    And I log in with the user just registered via the API on <channel>
    Then the response code should be 200
    And the content type is JSON
    And the API response should state the user is REMEMBERED
    And the API response should contain a customer id and an account ID
    And the API response should contain the personal details supplied at registration
    And the API response contains no errors
    When I access my account details via the API without the session ID

  Examples:
    | channel |
    | intl    |
    | am      |
    | apac    |


  @COM-1767
  Scenario Outline: Logged in user details returned correctly by API
    Given I am on <channel>
    And I submit valid details on the registration form
    And I log in with the user just registered via the API on <channel>
    Then the response code should be 200
    And the content type is JSON
    And the API response should state the user is SIGNED_IN
    And the API response should contain a customer id and an account ID
    And the API response should contain the personal details supplied at registration
    And the API response should contain the wishlist migrated false status
    And the API response contains no errors
    When I access my account details via the API

  Examples:
    | channel |
    | intl    |
    | am      |
    | apac    |

  Scenario Outline: Logged out user details not returned by API; previously logged in
    Given I am on <channel>
    And I submit valid details on the registration form
    And I log in with the user just registered via the API on <channel>
    And I logout via the API on <channel>
    Then the response code should be 200
    And the content type is JSON
    And the API response should state the user is not signed in
    And the API response should not contain any personal details
    When I access my account details via the API

  Examples:
    | channel |
    | intl    |
    | am      |
    | apac    |





  Scenario Outline: Logged out user details not returned by API
    Given I logout via the API on <channel>
    Then the response code should be 200
    And the content type is JSON
    And the API response should state the user is not signed in
    And the API response should not contain any personal details
    When I access my account details via the API

  Examples:
    | channel |
    | intl    |
    | am      |
    | apac    |


  @COM-1767
  Scenario Outline: Logged in and wishlist-migrated user details returned correctly by API
    Given I am on <channel>
    And I submit valid details on the registration form
    And I log in with the user just registered via the API on <channel>
    Then the response code should be 200
    And the content type is JSON
    And the API response should state the user is SIGNED_IN
    And the API response should contain a customer id and an account ID
    And the API response should contain the personal details supplied at registration
    And the API response should contain the wishlist migrated true status
    And the API response contains no errors
    When I access my account details via the API

  Examples:
    | channel |
    | intl    |
    | am      |
    | apac    |

  @COM-1767
  Scenario Outline: Logged in and wishlist-migrated user details returned correctly by JSONP API
    Given I am on <channel>
    And I submit valid details on the registration form
    And I log in with the user just registered via the API on <channel>
    Then the response code should be 200
    And the JSONP API response should contain the wishlist migrated true status
    When I access my account details via the JSONP API

  Examples:
    | channel |
    | intl    |
    | am      |
    | apac    |

  @COM-1767
  Scenario Outline: Logged in and non-wishlist-migrated user details returned correctly by JSONP API
    Given I am on <channel>
    And I submit valid details on the registration form
    And I log in with the user just registered via the API on <channel>
    Then the response code should be 200
    And the JSONP API response should contain the wishlist migrated false status
    When I access my account details via the JSONP API

  Examples:
    | channel |
    | intl    |
    | am      |
    | apac    |