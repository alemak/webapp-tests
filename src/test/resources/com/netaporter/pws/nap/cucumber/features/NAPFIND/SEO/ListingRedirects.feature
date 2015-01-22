@todo
Feature: listing page redirects

  Scenario Outline: Visiting different types of listing page urls
    Given I am on <channel>
    And I go to Home page
    And I visit a <url> url
    Then after all redirects the url should end with <route>
    And an error page is not shown
    And the page title should contain <title>

  Examples:
    |channel  | url              | route                  |title              |
    |intl     |Sport             | gb/en/d/Shop/Sport     |Luxury Sportswear  |
    |am       |Shop/Sport        | us/en/d/Shop/Sport     |Luxury Sportswear  |
    |apac     |gb/en/Sport       | gb/en/d/Shop/Sport     |Luxury Sportswear  |
    |intl     |gb/en/d/Sport     | gb/en/d/Shop/Sport     |Luxury Sportswear  |
    |intl     |gb/en/Shop/Sport  | gb/en/d/Shop/Sport     |Luxury Sportswear  |
    |intl     |Sale              | gb/en/d/Shop/Sale      |Sale               |
    |intl     |Shop/Sale         | gb/en/d/Shop/Sale      |Sale               |
    |intl     |gb/en/Sale        | gb/en/d/Shop/Sale      |Sale               |
    |intl     |gb/en/d/Sale      | gb/en/d/Shop/Sale      |Sale               |
    |intl     |gb/en/Shop/Sale   | gb/en/d/Shop/Sale      |Sale               |