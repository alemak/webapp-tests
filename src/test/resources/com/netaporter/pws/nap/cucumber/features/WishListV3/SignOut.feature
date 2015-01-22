@wishlistV3 @desktop
Feature: Wishlist V3 Sign Out
  As a registered NAP customer
  I want to be able to sign out on a wishlist page
  So that I can securely leave my browsing session

  @COM-1698
  Scenario: Clicking the sign out on the all-items page signs you out and takes you to home page
    Given I am a quickly registered user on intl
    And I navigate to the All Items page for wishlist
    When I sign out from the current page
    Then I am currently on the home page
    Then I am false signed in

  @COM-1698
  Scenario: Clicking the sign out on the default wishlist page signs you out and takes you to home page
    Given I am a quickly registered user on intl
    And I navigate to the All Items page for wishlist
    And I should be able to navigate to the Default Wishlist page
    When I sign out from the current page
    Then I am currently on the home page
    Then I am false signed in