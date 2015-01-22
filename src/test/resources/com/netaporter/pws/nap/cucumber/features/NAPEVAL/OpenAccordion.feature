@Channelized @napeval

Feature: Selecting a size in the drop down on the product page opens the size & fit accordion

  Scenario: Size & Fit accordion opens upon selecting a size in the drop down
   When I navigate to IN_STOCK product detail page for category CLOTHING
   When I select size of the product
   Then the Size & Fit accordion is open
