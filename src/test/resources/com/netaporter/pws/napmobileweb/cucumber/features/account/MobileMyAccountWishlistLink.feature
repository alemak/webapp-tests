Feature: Link to wishlist from mobile My Account page
  Check link to wishlist is correct in My Account page

  @known-failure @wishlistV3 @nap @Channelized @COM-1710
  Scenario: Migrated user accesses wishlist link from mobile my account page
    Given I submit valid details on the mobile registration form
    And I go to the mobile my account page
    When I click on the wishlist link on the mobile My Account page
    Then I am sent to the mobile all items wish list page

  @nap @Channelized @COM-1710
  Scenario: Non-migrated user access wishlist description link from mobile my account page
    Given I submit valid details on the mobile registration form
    And I go to the mobile my account page
    When I click on the wishlist link on the mobile My Account page
    Then I am taken to the legacy mobile wish list page

