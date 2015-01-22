@nap @napfind
Feature: All nap (webapp and listing app) links contain locale prefix

  Scenario Outline: All nap links in the Sport landing page contain locale
    Given I visit a <url> url
    Then All nap links contain locale with a few exceptions

  Examples:
  | url                 |
  | ca/en/d/Shop/Sport  |
  | ca/en/m/Shop/Sport  |
  | hk/zh/d/Shop/Sport  |
  | it/fr/m/Shop/Sport  |


  Scenario Outline: All nap links in any Sport listing page contain locale
    Given I visit a <url> url
    And I randomly select a sport category
    Then All nap links contain locale with a few exceptions


  Examples:
    | url                 |
    | ca/en/d/Shop/Sport  |
    | ca/en/m/Shop/Sport  |
    | hk/zh/d/Shop/Sport  |
    | it/fr/m/Shop/Sport  |


  Scenario Outline: All nap links in Google glass landing page contain locale
    Given I visit a <url> url
    Then All nap links contain locale with a few exceptions


  Examples:
    | url                                        |
    | ca/en/d/Shop/Designers/DVF_MADE_FOR_GLASS  |
    | ca/en/m/Shop/Designers/DVF_MADE_FOR_GLASS  |
    | hk/zh/d/Shop/Designers/DVF_MADE_FOR_GLASS  |
    | it/fr/m/Shop/Designers/DVF_MADE_FOR_GLASS  |