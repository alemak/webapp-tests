Feature: Link to wishlist from My Account page
  Check link to wishlist is correct in My Account page

  @known-failure @wishlistV3 @nap @Channelized @COM-1710
  Scenario: Non-Migrated user access wishlist link from my account page
    Given I submit valid details on the registration form
    And I go to My Account page
    When I click on the wishlist title link on the My Account page
    Then I am taken to the wish list page

  @known-failure @wishlistV3 @nap @Channelized @COM-1710
  Scenario: Migrated user access wishlist link from my account page
    Given I submit valid details on the registration form
    And I go to My Account page
    When I click on the wishlist title link on the My Account page
    Then I am sent to the all items wish list page

  @nap @Channelized @COM-1710
  Scenario: Non-migrated user access wishlist description link from my account page
    Given I submit valid details on the registration form
    And I go to My Account page
    When I click on the wishlist description link on the My Account page
    Then I am taken to the wish list page

  @nap @Channelized @COM-1710
  Scenario: Migrated user access wishlist description link from my account page
    Given I submit valid details on the registration form
    And I go to My Account page
    When I click on the wishlist description link on the My Account page
    Then I am sent to the all items wish list page

