#@nap
Feature: Force customers with regional GEO header to their country.  Hack for bug introduced Nov 2013
  As an NAP customer
  I want to be able to switch to my preferred country

  # Note: these tests will only pass on environments with this workaround applied, hence applying the @known-failure tag to all
  #
  #
  #
  #
  #

   @NonChannelized @known-failure
  Scenario Outline: http user on intl gb from non-intl region bounced back to their own region and kept on that region
  Given I request trackpage.nap from a default INTL country with GEO header set to <country_iso>
  Then the response is a http 302 to trackpage.nap on <channel>
  And the channel cookie is returned as <channel>
  And the country_iso cookie is not in the repsonse
  And the channel switched cookie is in response
  And I request the redirected URL with the switched cookie set, GEO header set to <country_iso> and country_iso as GB and channel as set in the response
  Then the channel cookie is not in the response
  And the country_iso cookie is returned as <country_iso>
  And the channel switched cookie is not in response

  Examples:
    | channel  | country_iso |
    | apac     | CN          |
    | apac     | AU          |
    | am       | US          |
    | am       | CA          |

   @NonChannelized @known-failure
  Scenario Outline: https user on intl gb from non-intl region bounced back to their own region and kept on that region
    Given I request https trackpage.nap from a default INTL country with GEO header set to <country_iso>
    Then the response is a https 302 to trackpage.nap on <channel>
    And the channel cookie is returned as <channel>
    And the country_iso cookie is not in the repsonse
    And the channel switched cookie is in response
    And I request the redirected URL with the switched cookie set, GEO header set to <country_iso> and country_iso as GB and channel as set in the response
    Then the channel cookie is not in the response
    And the country_iso cookie is returned as <country_iso>
    And the channel switched cookie is not in response

  Examples:
    | channel  | country_iso |
    | apac     | CN          |
    | apac     | AU          |
    | am       | US          |
    | am       | CA          |


   @NonChannelized @known-failure
  Scenario Outline: http user on intl gb from intl region stay on own channel and country
    Given I request trackpage.nap from a default INTL country with GEO header set to <country_iso>
    Then the channel cookie is not in the response
    And the country_iso cookie is not in the repsonse
    And the channel switched cookie is not in response

  Examples:
    | country_iso |
    | GB          |
    | FR          |

   @NonChannelized @known-failure
  Scenario Outline: https user on intl gb from intl region stay on own channel and country
    Given I request https trackpage.nap from a default INTL country with GEO header set to <country_iso>
    Then the channel cookie is not in the response
    And the country_iso cookie is not in the repsonse
    And the channel switched cookie is not in response

  Examples:
    | country_iso |
    | GB          |
    | FR          |



   @NonChannelized @known-failure
  Scenario Outline: no bouncing for urls other than track page
    Given I make a request to <path> from a default INTL country with GEO header set to <country_iso>
    Then the channel cookie is not in the response
    And the country_iso cookie is not in the repsonse
    And the channel switched cookie is not in response

  Examples:
    | country_iso | path                    |
    | CN          |                         |
    | AU          |                         |
    | US          |                         |
    | CN          | product/417866          |
    | AU          | product/417866          |
    | US          | product/417866          |
    | CN          | webapi/auth/status.json |
    | AU          | webapi/auth/status.json |
    | US          | webapi/auth/status.json |