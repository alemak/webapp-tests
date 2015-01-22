Feature: Display a pop up window to first-time user on Nap apac.
  The pop up window contains a link allows customer to update preferences.

  @nap @NonChannelized
  Scenario Outline: Visit home page for the first time
    Given I am on <channel>
    And I visit the home page
    Then I <shouldSee> first time user message
    And The apacFirstTimeUserMessageHasBeenShown cookie <shouldBeSet>

  Examples:
    |channel| shouldSee    | shouldBeSet   |
    | apac  | true         | true          |
    | intl  | false        | false         |
    |  am   | false        | false         |

  @nap @NonChannelized @region=APAC
  Scenario: Non-first time user visit APAC home
    Given I visit the home page on the apac website
    And I visit the home page on the apac website
    Then I false first time user message