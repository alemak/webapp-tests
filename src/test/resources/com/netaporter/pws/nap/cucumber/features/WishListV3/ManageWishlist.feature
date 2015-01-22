@wishlistV3 @desktop
Feature: Wishlist V3 Manage List
  As a registered NAP customer
  I want to be able to Manage wishlists
  So that I can have an up to date name for my collection of the latest items I would like to buy

  @COM-1465
  Scenario: Manage Wish List Button available on custom list
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Manage Me
    When I view that specific wishlist via its direct url
    Then I should see a Manage Wish List Button

  @COM-1465 @sanity
  Scenario: Manage Wish List Button not available on default list
    Given I am a quickly registered user on intl
    When I browse to the default wishlist page
    Then I should see a Manage Wish List Button

  @COM-1465 @sanity
  Scenario: Manage Wish List Button not available on all items list
    Given I am a quickly registered user on intl
    When I navigate to the All Items page for wishlist
    Then I should not see the Manage Wish List Button

  @COM-1465
  Scenario: Manage Wish List Button clicked and overlay shown
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Manage Me
    And I view that specific wishlist via its direct url
    When I click the Manage Wish List button
    Then I should see the Manage Wish List overlay for wishlist named Manage Me

  @COM-1465
  Scenario: Manage Wish List completed
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Manage Me
    And I view that specific wishlist via its direct url
    And I store the current wishlist details
    And I click the Manage Wish List button
    And I specify a new wishlist name in the overlay as Hey You Managed Me
    When I click the Save Changes button in the Manage Wish List overlay
    Then I should not see the Manage Wish List overlay
    And the nav menu should contain that wishlist with name Hey You Managed Me
    And the nav menu should not contain that wishlist with name Manage Me
    And I am on the wish list page called Hey You Managed Me and it is selected in the nav bar
    And I should see the correct custom wishlist page title
    And I reload the current page
    And the nav menu should contain that wishlist with name Hey You Managed Me
    And the nav menu should not contain that wishlist with name Manage Me
    And I am on the wish list page called Hey You Managed Me and it is selected in the nav bar
    And I should see the correct custom wishlist page title

  @COM-1465 @COM-1968
  Scenario: Manage Wish List to Chinese completed
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Manage Me
    And I view that specific wishlist via its direct url
    And I store the current wishlist details
    And I click the Manage Wish List button
    And I specify a new wishlist name in the overlay as 的这款单肩包集经典
    When I click the Save Changes button in the Manage Wish List overlay
    Then I should not see the Manage Wish List overlay
    And the nav menu should contain that wishlist with name 的这款单肩包集经典
    And the nav menu should not contain that wishlist with name Manage Me
    And I am on the wish list page called 的这款单肩包集经典 and it is selected in the nav bar
    And I should see the correct custom wishlist page title
    And I reload the current page
    And the nav menu should contain that wishlist with name 的这款单肩包集经典
    And the nav menu should not contain that wishlist with name Manage Me
    And I am on the wish list page called 的这款单肩包集经典 and it is selected in the nav bar
    And I should see the correct custom wishlist page title

  @COM-1465
  Scenario: Manage Wish List with no change - submitting same name again
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Manage Me
    And I view that specific wishlist via its direct url
    And I store the current wishlist details
    And I click the Manage Wish List button
    And I specify a new wishlist name in the overlay as Manage Me
    When I click the Save Changes button in the Manage Wish List overlay
    Then I should not see the Manage Wish List overlay
    And the nav menu should contain that wishlist with name Manage Me
    And I am on the wish list page called Manage Me and it is selected in the nav bar


  @COM-1465
  Scenario: Manage Wish List with no change - leaving name as is
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Manage Me
    And I view that specific wishlist via its direct url
    And I store the current wishlist details
    And I click the Manage Wish List button
    When I click the Save Changes button in the Manage Wish List overlay
    Then I should not see the Manage Wish List overlay
    And the nav menu should contain that wishlist with name Manage Me
    And I am on the wish list page called Manage Me and it is selected in the nav bar

  @COM-1465
  Scenario: Manage Wish List with minumum number of characters
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Manage Me
    And I view that specific wishlist via its direct url
    And I store the current wishlist details
    And I click the Manage Wish List button
    And I specify a new wishlist name in the overlay as 1
    When I click the Save Changes button in the Manage Wish List overlay
    Then I should not see the Manage Wish List overlay
    And the nav menu should contain that wishlist with name 1
    And the nav menu should not contain that wishlist with name Manage Me
    And I am on the wish list page called 1 and it is selected in the nav bar

  @COM-1465 @sanity
  Scenario: Manage Wish List with maxiumum number of characters
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Manage Me
    And I view that specific wishlist via its direct url
    And I store the current wishlist details
    And I click the Manage Wish List button
    And I specify a new wishlist name in the overlay as 123456789012345678901234
    When I click the Save Changes button in the Manage Wish List overlay
    Then I should not see the Manage Wish List overlay
    And the nav menu should contain that wishlist with name 123456789012345678901234
    And the nav menu should not contain that wishlist with name Manage Me
    And I am on the wish list page called 123456789012345678901234 and it is selected in the nav bar

  @COM-1465
  Scenario: Manage Wish List with no characters
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Manage Me
    And I view that specific wishlist via its direct url
    And I store the current wishlist details
    And I click the Manage Wish List button
    And I specify an empty new wishlist name in the overlay
    When I click the Save Changes button in the Manage Wish List overlay
    Then I should still see the Manage Wish List overlay with no name with the text field taking focus



  @COM-1465
  Scenario: Manage Wish List with whitespace
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Manage Me
    And I view that specific wishlist via its direct url
    And I store the current wishlist details
    And I click the Manage Wish List button
    And I specify whitespace as the new wishlist name in the overlay
    When I click the Save Changes button in the Manage Wish List overlay
    Then I should still see the Manage Wish List overlay with whitespace as the name and with the text field taking focus


  @COM-1465
  Scenario: Manage Wish List with name exceeding max characters
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Manage Me
    And I view that specific wishlist via its direct url
    And I store the current wishlist details
    And I click the Manage Wish List button
    And I specify a new wishlist name in the overlay as 1234567890123456789012345
    Then I should still see the Manage Wish List overlay with name 123456789012345678901234 with the text field taking focus


  @COM-1465
  Scenario: Manage Wish List with same name as another list
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called Manage Me 1
    And I create a new wishlist via the WOAS API called Manage Me 2
    And I view that specific wishlist via its direct url
    And I store the current wishlist details
    And I click the Manage Wish List button
    And I specify a new wishlist name in the overlay as Manage Me 1
    When I click the Save Changes button in the Manage Wish List overlay
    Then I should not see the Manage Wish List overlay
    And the nav menu should not contain that wishlist with name Manage Me 2
    Then the nav menu should now contain 2 wishlists with the name Manage Me 1



  @COM-1465
  Scenario: Manage Wish List cancelled
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called ManageME
    And I view that specific wishlist via its direct url
    And I store the current wishlist details
    And I click the Manage Wish List button
    When I click the Cancel button in the Manage Wishlist overlay
    Then the nav menu should contain that wishlist with name ManageME
    And I can access the stored wishlist

  @COM-1465
  Scenario: Manage Wish List closed by X button
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called ManageME
    And I view that specific wishlist via its direct url
    And I store the current wishlist details
    And I click the Manage Wish List button
    When I click the X close button in the Manage Wishlist overlay
    Then the nav menu should contain that wishlist with name ManageME
    And I can access the stored wishlist

  @COM-1465
  Scenario: Manage Wish List closed by glass pane click
    Given I am a quickly registered user on intl
    And I create a new wishlist via the WOAS API called ManageME
    And I view that specific wishlist via its direct url
    And I store the current wishlist details
    And I click the Manage Wish List button
    When I click the glass pane outside the Manage Wishlist overlay
    Then the nav menu should contain that wishlist with name ManageME
    And I can access the stored wishlist
