Feature: Purchase path payment validations for NAP mobile

  @ecomm
  Scenario: Mobile purchase path
    Given I am on the homepage
    And I add a random product to my bag
    And I go to the shopping bag page
    And I click purchase now from the shopping bag page
    And I sign in anonymously within the purchase path
    And I fill out a UK non London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page

  # all fields have not been filled out
    When I click purchase now from the payment page
    Then Card type has error message Please select your card type
    And Card number has error message Please enter your card number
    And Card name has error message Please enter the name on your card
    And Card security number has error message Please enter a valid security number
    And Card expiry date has error message Please enter a valid expiry date

  # blank card holder name is invalid
    When I click purchase now from the payment page
    Then Card name has error message Please enter the name on your card

  # card number invalid characters test
    When I fill in the card number as abcd efg
    And I change focus on the payment page
    Then Card number has error message Your card number contains invalid characters

  # too few numbers test with no card type selected
    When I fill in the card number as 1234 5678 901
    And I change focus on the payment page
    Then Card number has error message Your card number must be between 12 and 19 characters

  # too many numbers test with no card type selected
    When I fill in the card number as 1234 5678 9012 3456 7892
    And I change focus on the payment page
    Then Card number has error message Your card number must be between 12 and 19 characters

  # too few numbers test with Visa card type selected
    When I select a card type of Visa
    And I fill in the card number as 1234 5678 9012
    And I change focus on the payment page
    Then Card number has error message Your card number must be 16 characters

  # too many numbers test with Visa card type selected
    When I select a card type of Visa
    And I fill in the card number as 1234 5678 9012 3456 7892
    And I change focus on the payment page
    Then Card number has error message Your card number must be 16 characters

  # too few numbers test with AMEX card type selected
    When I select a card type of American Express
    And I fill in the card number as 1234 5678 9012
    And I change focus on the payment page
    Then Card number has error message Your card number must be 15 characters

  # too many numbers test with AMEX card type selected
    When I select a card type of American Express
    And I fill in the card number as 1234 5678 9012 3456 7892
    And I change focus on the payment page
    Then Card number has error message Your card number must be 15 characters

  # too few numbers test with Mastercard card type selected
    When I select a card type of MasterCard
    And I fill in the card number as 1234 5678 9012
    And I change focus on the payment page
    Then Card number has error message Your card number must be 16 characters

  # too many numbers test with Mastercard card type selected
    When I select a card type of MasterCard
    And I fill in the card number as 1234 5678 9012 3456 7892
    And I change focus on the payment page
    Then Card number has error message Your card number must be 16 characters

  # invalid card number for card type Visa
    When I select a card type of Visa
    And I fill in the card number as 3434563145632132
    And I change focus on the payment page
    Then Card number has error message Your card number is invalid

  # invalid card number for card type AMEX
    When I select a card type of American Express
    And I fill in the card number as 004000563145632
    And I change focus on the payment page
    Then Card number has error message Your card number is invalid

  # invalid card number (Luhn check)
    When I select a card type of MasterCard
    And I fill in the card number as 0000 9601 2200 1999
    And I change focus on the payment page
    Then Card number has error message Your card number is invalid

  # security number invalid characters test
    When I fill in the security number as abc
    And I change focus on the payment page
    Then Card security number has error message Your security number contains invalid characters

  # invalid security number for American Express (4 digits)
    When I select a card type of American Express
    And I fill in the security number as 123
    And I change focus on the payment page
    Then Card security number has error message Your security number must be 4 digits long

  # invalid security number for Visa (3 digits)
    When I select a card type of Visa
    And I fill in the security number as 1234
    And I change focus on the payment page
    Then Card security number has error message Your security number must be 3 digits long

  # invalid security number for Mastercard (3 digits)
    When I select a card type of MasterCard
    And I fill in the security number as 1234
    And I change focus on the payment page
    Then Card security number has error message Your security number must be 3 digits long

  # expiry dates in the past are invalid
    When I fill in the expiry month 01 and expiry year 13
    And I change focus on the payment page
    Then Card expiry date has error message Please enter a valid expiry date

  @ecomm @region=INTL
  Scenario: Payment details Javascript validations for Maestro and JCB
    Given I am on the homepage
    And I add a random product to my bag
    And I go to the shopping bag page
    And I click purchase now from the shopping bag page
    And I sign in anonymously within the purchase path
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page

  # too few numbers test with Maestro card type selected
    When I select a card type of Maestro
    And I fill in the card number as 1234 5678 901
    And I change focus on the payment page
    Then Card number has error message Your card number must be between 12 and 19 characters

  # too many numbers test with Maestro card type selected
    When I select a card type of Maestro
    And I fill in the card number as 1234 5678 9012 3456 7892
    And I change focus on the payment page
    Then Card number has error message Your card number must be between 12 and 19 characters

  # too few numbers test with JCB card type selected
    When I select a card type of JCB
    And I fill in the card number as 1234 5678 9012
    And I change focus on the payment page
    Then Card number has error message Your card number must be 16 characters

  # too many numbers test with JCB card type selected
    When I select a card type of JCB
    And I fill in the card number as 1234 5678 9012 3456 7892
    And I change focus on the payment page
    Then Card number has error message Your card number must be 16 characters

  # invalid security number for Maestro (3 digits)
    When I select a card type of Maestro
    And I fill in the security number as 1234
    And I change focus on the payment page
    Then Card security number has error message Your security number must be 3 digits long

  # invalid security number for JCB (3 digits)
    When I select a card type of JCB
    And I fill in the security number as 1234
    And I change focus on the payment page
    Then Card security number has error message Your security number must be 3 digits long