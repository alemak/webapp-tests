@Channelized @napeval
Feature: Clicking the "View full size image link" takes the user to a page containing the full size product image

Scenario: View full size image
  Given I navigate to IN_STOCK product detail page for category CLOTHING
  When I click on the view full size image link
  Then the full size image overlay appears