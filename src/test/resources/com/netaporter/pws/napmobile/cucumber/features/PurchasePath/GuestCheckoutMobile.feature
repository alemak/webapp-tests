Feature: Validate the guest checkout journey on mobile web


Scenario: Make a purchase as a guest user
  Given I navigate to the Shop Bags Mobile page
  When I click on a product from the mobile listing page
  And I add the product to the shopping bag from the mobile product details page
  And I click the shopping bag icon from the mobile product details page
  And I click proceed to purchase from the mobile shopping bag page
  And I sign in as guest in the mobile purchase path
  And I add an UK Premier address on mobileweb
  And I continue to the mobile shipping options page
  And I click proceed to purchase on the mobile shipping options page
  And I pay on mobileweb by VISA_DEBIT
  Then I see the order confirmation message on the confirmation page