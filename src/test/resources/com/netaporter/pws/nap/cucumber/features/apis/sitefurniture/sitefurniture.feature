@napeval
Feature: Site Furniture API
  An API that allows other presentation tier services get template HTML for header, footer and generic body content.


  #
  #
  #
  # API spec here: http://confluence.net-a-porter.com/display/COM/Webapp+-+Site+Furniture+API+Reference


  Scenario: Site Furniture API requests ignored as last-page-viewed when changing channel
    Given I am on any Product Listing page
    And I stash the webpage path I'm currently on
    And I access the site furniture api via the browser
    When I am on change country page
    And I change my country to France
    Then I am returned to the webpage path that I stashed


  Scenario: Site Furniture API response matches schema
    Given I am making a site-furniture API request with the intl channel specified in the URL path
    Then the response code should be 200
    And the content type is JSON
    And the response code should match the Site Furniture JSON Schema
    When I access the site furniture api



  Scenario Outline: A user fetches site furniture with channel specified in URL
    Given I am making a site-furniture API request with the <channel> channel specified in the URL path
    Then the response code should be 200
    And the content type is JSON
    And the response contains the standard NAP header and footer
    And the response contains the body tag with <channel>
    And the response contains the body tag with lang-en
    And the response contains the head tag with <default_country> specified as the country name
    And the response contains the expected handlebars tags
    And the response contains the body with CoreMetrics setup but no specific tags
  # TODO JIRA-1351 And the response contains the body without the trackPage.nap tags
    When I access the site furniture api

  Examples:
    | channel | default_country |
    | intl    | United Kingdom  |
    | am      | United States   |
    | apac    | Hong Kong       |




  Scenario Outline: A user fetches site furniture with country and language cookies specified with the channel in the URL path
    Given I am making a site-furniture API request with the <channel> channel specified in the URL path
    And I am making an API request with a cookie set for the <country> country
    And I am making an API request with a cookie set for the <language> language
    Then the response code should be 200
    And the content type is JSON
    And the response contains the standard NAP header and footer
    And the response contains the body tag with <channel>
    And the response contains the body tag with lang-<language>
    And the response contains the head tag with <expected_country_text> specified as the country name
    And the response contains the expected handlebars tags
    And the response contains the body with CoreMetrics setup but no specific tags
  # TODO JIRA-1351 And the response contains the body without the trackPage.nap tags
    When I access the site furniture api

  Examples:
    | country | language | channel          | expected_country_text |
    | ES      | zh       | intl             | 西班牙                 |
    | JP      | fr       | apac             | Japon                 |
    | BR      | de       | am               | Brasilien             |



  Scenario Outline: A user fetches site furniture with channel, country and language cookies specified without the channel in the URL path
    Given I am making a site-furniture API request without specifying the channel
    And I am making an API request with a cookie set for the <channel> channel
    And I am making an API request with a cookie set for the <country> country
    And I am making an API request with a cookie set for the <language> language
    Then the response code should be 200
    And the content type is JSON
    And the response contains the standard NAP header and footer
    And the response contains the body tag with <channel>
    And the response contains the body tag with lang-<language>
    And the response contains the head tag with <expected_country_text> specified as the country name
    And the response contains the expected handlebars tags
    And the response contains the body with CoreMetrics setup but no specific tags
  # TODO JIRA-1351 And the response contains the body without the trackPage.nap tags
    When I access the site furniture api

  Examples:
  | country | language | channel          | expected_country_text |
  | ES      | zh       | intl             | 西班牙                 |
  | JP      | fr       | apac             | Japon                 |
  | BR      | de       | am               | Brasilien             |



  Scenario Outline: A user fetches mobile site furniture with channel specified in URL
    Given I am making a site-furniture API request with the <channel> channel specified in the URL path
    And I am requesting the mobile version of it
    Then the response code should be 200
    And the content type is JSON
    And the response contains the standard NAP header and footer
    And the response contains the body tag with <channel>
    And the response contains the body tag with lang-en
    And the response contains the footer tag with <default_country> specified as the country name
    And the response contains the expected handlebars tags
    And the response contains the body with CoreMetrics setup but no specific tags
    And the response contains the mobile version of the header, layout and footer
  # TODO JIRA-1351 And the response contains the body without the trackPage.nap tags
    When I access the site furniture api

  Examples:
    | channel | default_country |
    | intl    | United Kingdom  |
    | am      | United States   |
    | apac    | Hong Kong       |



  Scenario Outline: A user fetches mobile site furniture with country and language cookies specified with the channel in the URL path
    Given I am making a site-furniture API request with the <channel> channel specified in the URL path
    And I am requesting the mobile version of it
    And I am making an API request with a cookie set for the <country> country
    And I am making an API request with a cookie set for the <language> language
    Then the response code should be 200
    And the content type is JSON
    And the response contains the standard NAP header and footer
    And the response contains the body tag with <channel>
    And the response contains the body tag with lang-<language>
    And the response contains the footer tag with <expected_country_text> specified as the country name
    And the response contains the expected handlebars tags
    And the response contains the body with CoreMetrics setup but no specific tags
    And the response contains the mobile version of the header, layout and footer
  # TODO JIRA-1351 And the response contains the body without the trackPage.nap tags
    When I access the site furniture api

  Examples:
    | country | language | channel          | expected_country_text |
    | ES      | zh       | intl             | 西班牙                 |
    | JP      | fr       | apac             | Japon                 |
    | BR      | de       | am               | Brasilien             |


  Scenario Outline: A user fetches mobile site furniture with channel, country and language cookies specified without the channel in the URL path
    Given I am making a site-furniture API request without specifying the channel
    And I am requesting the mobile version of it
    And I am making an API request with a cookie set for the <channel> channel
    And I am making an API request with a cookie set for the <country> country
    And I am making an API request with a cookie set for the <language> language
    Then the response code should be 200
    And the content type is JSON
    And the response contains the standard NAP header and footer
    And the response contains the body tag with <channel>
    And the response contains the body tag with lang-<language>
    And the response contains the footer tag with <expected_country_text> specified as the country name
    And the response contains the expected handlebars tags
    And the response contains the body with CoreMetrics setup but no specific tags
    And the response contains the mobile version of the header, layout and footer
  # TODO JIRA-1351 And the response contains the body without the trackPage.nap tags
    When I access the site furniture api

  Examples:
    | country | language | channel          | expected_country_text |
    | ES      | zh       | intl             | 西班牙                 |
    | JP      | fr       | apac             | Japon                 |
    | BR      | de       | am               | Brasilien             |



  Scenario Outline: A user fetches the site furniture with channel specified in URL and param mobile=false
    Given I am making a site-furniture API request with the <channel> channel specified in the URL path
    And I am NOT requesting the mobile version of it
    Then the response code should be 200
    And the content type is JSON
    And the response contains the standard NAP header and footer
    And the response contains the body tag with <channel>
    And the response contains the body tag with lang-en
    And the response contains the footer tag with <default_country> specified as the country name
    And the response contains the expected handlebars tags
    And the response contains the body with CoreMetrics setup but no specific tags
    And the response does not contain the mobile version of the header, layout and footer
  # TODO JIRA-1351 And the response contains the body without the trackPage.nap tags
    When I access the site furniture api

  Examples:
    | channel | default_country |
    | intl    | United Kingdom  |
    | am      | United States   |
    | apac    | Hong Kong       |

