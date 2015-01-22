@wishlistV3 @desktop
Feature: Wishlist V3 Delete Wishlist
  As a WishlistV3 user
  I want to be able to delete a wishlist
  So that I can curate my collection of lists

  @COM-1464
  Scenario: Delete Wish List button available on custom list
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called DELETEME
    When I view that specific wishlist via its direct url
    And I click the Manage Wish List button
    Then I should see a Delete Wish List Link

  @COM-1464 @sanity
  Scenario: Delete Wish List button not available on default list
    Given I am a quickly registered user on intl
    When I browse to the default wishlist page
    And I click the Manage Wish List button
    Then I should not see a Delete Wish List Link

  @COM-1464 @sanity
  Scenario: Delete Wish List button not available on all items list
    Given I am a quickly registered user on intl
    When I navigate to the All Items page for wishlist
    Then I should not see a Delete Wish List Link

  @COM-1464
  Scenario: Delete Wish List button clicked and overlay shown
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called DELETEME
    And I view that specific wishlist via its direct url
    And I click the Manage Wish List button
    When I click the Delete Wish List Link
    Then I should see the Delete Wish List confirmation overlay for wishlist named DELETEME

  @COM-1464 @sanity
  Scenario: Delete Wish List completed
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called DELETEME
    And I view that specific wishlist via its direct url
    And I store the current wishlist details
    And I click the Manage Wish List button
    And I click the Delete Wish List Link
    When I click the Delete Wish List button in the Delete Wish List overlay
    Then I am sent to the all items wish list page
    And the nav menu should not contain that wishlist with name DELETEME
    And I cannot access the stored wishlist

  @COM-1464 @known-failure @COM-2226
  Scenario: Delete Wish List cancelled
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called DELETEME
    And I view that specific wishlist via its direct url
    And I store the current wishlist details
    And I click the Manage Wish List button
    And I click the Delete Wish List Link
    When I click the Cancel button in the Delete Wishlist overlay
    And I click the Cancel button in the Manage Wishlist overlay
    Then the nav menu should contain that wishlist with name DELETEME
    And I can access the stored wishlist

  @COM-1464
  Scenario: Delete Wish List closed by X button
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called DELETEME
    And I view that specific wishlist via its direct url
    And I store the current wishlist details
    And I click the Manage Wish List button
    And I click the Delete Wish List Link
    When I click the X close button in the Delete Wishlist overlay
    And I click the X close button in the Manage Wishlist overlay
    Then the nav menu should contain that wishlist with name DELETEME
    And I can access the stored wishlist

  @COM-1464
  Scenario: Delete Wish List closed by glass pane click
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called DELETEME
    And I view that specific wishlist via its direct url
    And I store the current wishlist details
    And I click the Manage Wish List button
    And I click the Delete Wish List Link
    When I click the glass pane outside the Delete Wishlist overlay
    And I click the Cancel button in the Manage Wishlist overlay
    Then the nav menu should contain that wishlist with name DELETEME
    And I can access the stored wishlist
