@purch
Feature: The webapp works around data corruption caused by infosec's anti-script-injection filter

#  Background:
#    Given I am a seaview registered default user
#    And I sign in with the correct details
#    And I have a shipping address of:
#      | first name | Mr & Mrs                   |
#      | last name  | Krankee                    |
#      | address 1  | 1 Hook, Line & Sinker Road |
#      | town       | London                     |
#      | postcode   | SW7 9QP                    |
#      | country    | United Kingdom             |
#      | telephone  | 02079460000                |
#    And I add 1 in stock products directly to my bag
#    And I go to Shopping Bag page
#    And I proceed to purchase

  Scenario: I can pay with PayPal
    When I click on payment option PAYPAL
    And I click purchase now from the payment page
    And I am on the Paypal login page
    And I login to Paypal with user email nappaypalqa@nap.com and password drowssap
    And I am logged in to PayPal
    And I click on the Pay Now button to confirm the Paypal payment
    Then Order Confirmation page is displayed
