@wishlistV3 @desktop
Feature: Wishlist V3 Item Navigation
  As a registered NAP customer
  I want to be able to click on items in a wishlist
  So that I can view the full details of the item

  @COM-1449 @sanity
  Scenario: The product and designer links can be opened in a new tab
    Given I am a quickly registered user on intl
    And I have 5 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Clothing
    And I add skus 1 to 5 to my wishlist via the WOAS API
    When I view that specific wishlist via its direct url
    Then Product links to the product details should be real links that can be opened in a new tab
    And Designer links to the product details should be real links that can be opened in a new tab


