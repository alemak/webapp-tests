@wishlistV3 @desktop
Feature: Wishlist V3 No internal WOAS URLs in Wishlist HTML
  As a NAP infosec person
  I don't want to see internal WOAS URLs in our HTML
  So that I can feel happy that we aren't leaking details about our internal system to the public

  Scenario: No internal WOAS URLs in Wishlist HTML
    Given I am a quickly registered user on intl
    And I have 5 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
    And I create a new wishlist via the WOAS API called Clothing
    And I add skus 1 to 5 to my wishlist via the WOAS API
    When I view that specific wishlist via its direct url
    Then The page source should not contain any internal WOAS URLs
