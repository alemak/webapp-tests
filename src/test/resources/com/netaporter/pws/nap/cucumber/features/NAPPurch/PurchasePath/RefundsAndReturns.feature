@nap @Channelized
Feature: As NAP, I want to make the refund or return option available to customers only if this option is applicable to the order items

  #cannot get returnable items from solr directly, so we mark them as non returnable in the order_item table of the webDB after purchase

  Scenario: Customer can return a returnable item from the My Orders page
    Given I am a seaview registered default user
    And I sign in with the correct details
    And I have successfully purchased an item
    And I mark the item as returnable in the webDB
    And I dispatch the order
    When I go to Customer Orders page
    Then the previously completed order is displayed as DISPATCHED
    And I click on the previously completed order number
    Then the Create Return/Exchange button is visible
    And I click the Create Return/Exchange button
    And I choose to refund the item for any reason
    And I choose the store credit refund method
    And I confirm the return
    Then the Confirm Order Return page is shown
    And the item is marked for return in the db


  Scenario: Customer cannot return a nonreturnable item from the My Orders page
    Given I am a seaview registered default user
    And I sign in with the correct details
    And I have successfully purchased an item
    And I mark the item as nonreturnable in the webDB
    And I dispatch the order
    When I go to Customer Orders page
    Then the previously completed order is displayed as DISPATCHED
    When I click on the previously completed order number
    Then the Create Return/Exchange button is not visible


  Scenario: Customer can return a returnable item from a mixed bag of returnable and non returnable items
    Given I am a seaview registered default user
    And I sign in with the correct details
    And I have purchased several items
    And I mark the item as nonreturnable in the webDB
    And I dispatch the order
    When I go to Customer Orders page
    Then the previously completed order is displayed as DISPATCHED
    When I click on the previously completed order number
    Then the Create Return/Exchange button is visible
    When I click the Create Return/Exchange button
    Then the refundable product will be displayed
    And I choose to refund the item for any reason
    And I choose the store credit refund method
    And I confirm the return
    Then the Confirm Order Return page is shown
    And the item is marked for return in the db


# cannot get a returnable product from solr currently
#  Scenario: Product non-returnable messaging is displayed on the basket, order summary and order confirmation page
#    Given I am a seaview registered default user
#    And I sign in with the correct details
#    When I add a nonreturnable item to the shopping bag
#    And I go to Shopping Bag page
#    Then the nonreturnable product warning message is displayed in the shopping bag page
#    When I proceed to purchase
#    And I enter an United Kingdom Premier
#    When I click proceed to purchase from the shipping options page
#    Then the nonreturnable product warning message is displayed in the payment summary page
#    When I pay by VISA_DEBIT
    Then the nonreturnable product warning message is displayed in the order confirmation page


#  Scenario: Return/Exchange button is available only to Customer Care when the returnable column is set to CC_ONLY