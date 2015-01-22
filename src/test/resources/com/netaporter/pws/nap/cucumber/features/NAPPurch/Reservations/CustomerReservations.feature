 @nap @Channelized @purch
Feature:  CustomerReservations.feature: Purchasing Reserved Items

  Background:
    Given I am a seaview registered default user


  @ecomm @allChannels @purchasePath @reservations @storeSourceOfOrder
  Scenario: The IP address and user agent are stored against the basket
    And I sign in with the correct details
    When I reserve a product with a quantity of 1
    And I add a reserved product to my shopping bag
    And I verify that the added product is a reserved item
    And I go to Shopping Bag page
    Then My device identifier and user agent are recorded against my basket items


  @ecomm @allChannels @purchasePath @reservations
  Scenario: A registered user buys a reserved item
    And I sign in with the correct details
    When I reserve a product with a quantity of 1
    And I add a reserved product to my shopping bag
    And I verify that the added product is a reserved item
    And I go to Shopping Bag page
    And I proceed to purchase
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And The payment page shows my shipping and billing addresses are the same
    And I pay by VISA_CREDIT_CARD
    Then My order is confirmed
    And The reserved product is redeemed
    And The stock level remains unchanged for the reserved item


  @ecomm @allChannels @purchasePath @reservations
  Scenario: A registered user buys an expired reserved item which is in stock
    And I sign in with the correct details
    When I reserve a product with a quantity of 1
    And I add a reserved product to my shopping bag
    And My Reservation expires
    And I go to Shopping Bag page
    And I proceed to purchase
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And The payment page shows my shipping and billing addresses are the same
    And I pay by AMERICAN_EXPRESS
    Then My order is confirmed
    And The reserved product is not redeemed
    And I verify that the stock level is decremented by 1 for the reserved item


  @ecomm @allChannels @purchasePath @reservations
  Scenario: When the reservation expires and the products goes out of stock, the customers sees the items as out of stock on the Shopping Bag page
            The user adds a reserved item to his shopping bag.
            In the meantime, the reservation expires and the product goes out of stock.
            The user next goes to his Shopping Bag, the product now shows as out of stock.
    And I sign in with the correct details
    When I reserve a product with a quantity of 1
    And I add a reserved product to my shopping bag
    And My Reservation expires
    And My reserved item goes out of stock
    And I go to Shopping Bag page
    Then The product shows out of stock in the Shopping Bag page
    And I want to reset the stock level of my reserved item to its original level


  @ecomm @allChannels @purchasePath @reservations
  Scenario: When the reservation expires and the products remains in stock, the customers can still purchase the item
            The user adds a reserved item to his shopping bag, and proceeds to purchase.
            However while on the payment page, the item reservation expires and the item remains in stock.
            The user is able to purchase the item and the stock level is decremented accordingly.
    And I sign in with the correct details
    When I reserve a product with a quantity of 1
    And I add a reserved product to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I fill out a UK non London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And My Reservation expires
    And I pay by VISA_DEBIT
    Then My order is confirmed
    And The reserved product is not redeemed
    And I verify that the stock level is decremented by 1 for the reserved item


  @ecomm @allChannels @purchasePath @reservations
  Scenario: Customer cannot purchase when the reservation expires and the product goes out of stock even when in the Payment page
            The user adds a reserved item to his shopping bag, and proceeds to purchase.
            However while on the payment page, the item reservation expires and the item is no longer in stock.
            The user is not able to purchase the item and is taken to his Shopping Bag page with the item showing out of stock.
    And I sign in with the correct details
    When I reserve a product with a quantity of 1
    And I add a reserved product to my shopping bag
    And I go to Shopping Bag page
    And I proceed to purchase
    And I fill out a UK non London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And My Reservation expires
    And My reserved item goes out of stock
    And I attempt to pay by AMERICAN_EXPRESS
    Then The Order Confirmation page is NOT displayed
    And The Shopping Bag page is displayed
    And The product shows out of stock in the Shopping Bag page
    And The reserved product is not redeemed
    And I want to reset the stock level of my reserved item to its original level


  # BUYING THE RESERVED ITEM(S) FROM THE PRODUCT DETAILS PAGE

  @ecomm @allChannels @purchasePath @reservations
  Scenario: A registered user has one reserved item. He then buys an item with the same sku as his reserved item directly.
    And I sign in with the correct details
    And I reserve a product with a quantity of 1
    When I add a product to my shopping bag with the same sku as the reserved item
    And I verify that the added product is a reserved item
    And I go to Shopping Bag page
    And I proceed to purchase
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And The payment page shows my shipping and billing addresses are the same
    And I pay by AMERICAN_EXPRESS
    Then My order is confirmed
    And The reserved product is redeemed
    And The stock level remains unchanged for the reserved item


  @ecomm @allChannels @purchasePath @reservations
  Scenario: A registered user has one reserved item. He then buys two items with the same sku as the reserved item directly.
    And I sign in with the correct details
    When I reserve a product with a quantity of 1
    And I add a product to my shopping bag with the same sku as the reserved item
    And I verify that the basket item quantity is 1
    And I verify that the added product is a reserved item
    And I add a product to my shopping bag with the same sku as the reserved item
    And I verify that the basket item quantity is 2
    And I verify that the added product is a reserved item
    And I go to Shopping Bag page
    And I proceed to purchase
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And The payment page shows my shipping and billing addresses are the same
    And I pay by VISA_CREDIT_CARD
    Then My order is confirmed
    And The reserved product is redeemed
    And I verify that the stock level is decremented by 1 for the reserved item


# A KNOWN USER BUYS THE RESERVED ITEM(S) FROM THE PRODUCT DETAILS PAGE ANONYMOUSLY

  @ecomm @allChannels @purchasePath @reservations
  Scenario: A registered user has one reserved item. He then buys as a known anonymous user an item with the same sku as the reserved item directly.
    And I sign in with the correct details
    When I reserve a product with a quantity of 1
    And I add a product to my shopping bag with the same sku as the reserved item
    And I go to Shopping Bag page
    And I proceed to purchase
    And I fill out a UK non London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And I pay by VISA_CREDIT_CARD
    Then My order is confirmed
    And The reserved product is redeemed
    And The stock level remains unchanged for the reserved item


  @ecomm @allChannels @purchasePath @reservations
  Scenario: A registered user has one reserved item. He then adds an item with the same sku as the reserved item directly and fully signs-in in the purchase path to complete his purchase.
    And I sign in with the correct details
    And I signout
    When I reserve a product with a quantity of 1
    And I add a product to my shopping bag with the same sku as the reserved item
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in using the default customer within the purchase path
    And I go to Shopping Bag page
    And I proceed to purchase
    And I fill out a UK non London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And I pay by MASTER_CARD
    Then My order is confirmed
    And The reserved product is redeemed
    And The stock level remains unchanged for the reserved item


#  USER BUYS RESERVED ITEM FROM PRODUCT DETAILS PAGE AND RESERVATION EXPIRES

  @ecomm @allChannels @purchasePath @reservations
  Scenario: Customer can still purchase a reserved item after the reservation expires while the product is in the bag, if there is enough stock
            A registered user has one reserved item. He then adds an item with the same sku as his reserved item
            directly to his shopping bag and the reserved item expires.
            The user then purchases the item as a non-reserved item assuming that the item is in stock.
    And I sign in with the correct details
    Given I reserve a product with a quantity of 1
    When I add a product to my shopping bag with the same sku as the reserved item
    And I verify that the added product is a reserved item
    And I go to Shopping Bag page
    And My Reservation expires
    And I proceed to purchase
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And The payment page shows my shipping and billing addresses are the same
    And I pay by AMERICAN_EXPRESS
    Then My order is confirmed
    And The reserved product is not redeemed
    And I verify that the stock level is decremented by 1 for the reserved item


  @ecomm @allChannels @purchasePath @reservations
  Scenario: Customer can still purchase a reserved item after the reservation expires before addin the item to bag, if there is enough stock
            A registered user has one reserved item. He signs in and adds an item with the same sku as his reserved item
            directly to his shopping bag and the reservation expires.
            The user then purchases the item as a non-reserved item assuming that the item is in stock.
    And I sign in with the correct details
    Given I reserve a product with a quantity of 1
    And My Reservation expires
    When I add a product to my shopping bag with the same sku as the reserved item
    And I verify that the basket item reserved quantity column is 0
    And I go to Shopping Bag page
    And I proceed to purchase
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And The payment page shows my shipping and billing addresses are the same
    And I pay by VISA_DEBIT
    Then My order is confirmed
    And The reserved product is not redeemed
    And I verify that the stock level is decremented by 1 for the reserved item


  @ecomm @allChannels @purchasePath @reservations
  Scenario: A registered user has one reserved item. While not logged in he adds an item with the same sku as his reserved item
            directly to his shopping bag and the reservation expires.
            The user then purchases the item as a non-reserved item anonymously assuming that the item is in stock.
    And I sign in with the correct details
    And I signout
    Given I reserve a product with a quantity of 1
    When I add a product to my shopping bag with the same sku as the reserved item
    And I go to Shopping Bag page
    And My Reservation expires
    And I proceed to purchase
    And I sign in using the default customer within the purchase path
    And I go to Shopping Bag page
    And I proceed to purchase
    And I fill out a UK non London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And I pay by MASTER_CARD
    Then My order is confirmed
    And The reserved product is not redeemed
    And I verify that the stock level is decremented by 1 for the reserved item


  @ecomm @allChannels @purchasePath @reservations
  Scenario: A registered user signs in and adds a reserved item directly to his shopping bag, and proceeds to purchase.
            However while on the payment page, the item reservation expires and the item goes out of stock.
            The user is NOT able to purchase the item and a error message is displayed.
    And I sign in with the correct details
    Given I reserve a product with a quantity of 1
    When I add a product to my shopping bag with the same sku as the reserved item
    And I go to Shopping Bag page
    And I proceed to purchase
    And I fill out a UK non London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And My Reservation expires
    And My reserved item goes out of stock
    And I attempt to pay by VISA_CREDIT_CARD
    Then The Order Confirmation page is NOT displayed
    And The Shopping Bag page is displayed
    And The product shows out of stock in the Shopping Bag page
    And The reserved product is not redeemed
    And I want to reset the stock level of my reserved item to its original level


  @ecomm @allChannels @purchasePath @reservations
  Scenario: A registered user who is not signed in adds a reserved item to his shopping bag, and proceeds to purchase anonymously.
            However while on the payment page, the item reservation expires and the item goes out of stock.
            The user is NOT able to purchase the item and a error message is displayed.
    And I sign in with the correct details
    And I signout
    Given I reserve a product with a quantity of 1
    When I add a product to my shopping bag with the same sku as the reserved item
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously using the default customer within the purchase path
    And I fill out a UK non London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And My Reservation expires
    And My reserved item goes out of stock
    And I attempt to pay by AMERICAN_EXPRESS
    Then The Order Confirmation page is NOT displayed
    And The Shopping Bag page is displayed
    And The product shows out of stock in the Shopping Bag page
    And The reserved product is not redeemed
    And I want to reset the stock level of my reserved item to its original level


#  BUY DIFFERENT SKUS FROM THE PRODUCT PAGE - RESERVATION IS NOT USED

  @ecomm @allChannels @purchasePath @reservations
  Scenario: A registered customer can purchase a non reserved item and not impact the reserved item
            A registered user has one reserved item.
            The user signs in and adds an item with a different sku to his reserved item directly to his shopping bag.
            The user then purchases the item as a non-reserved item assuming that the item is in stock.
    And I sign in with the correct details
    Given I reserve a product with a quantity of 1
    When I add a product identified as NON-RESERVED to my shopping bag with a different sku to the reserved item
    And I verify that the basket item reserved quantity column is 0
    And I go to Shopping Bag page
    And I proceed to purchase
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And The payment page shows my shipping and billing addresses are the same
    And I pay by VISA_CREDIT_CARD
    Then My order is confirmed
    And The reserved product is not redeemed
    And The stock level remains unchanged for the reserved item
    And I verify that the stock level is decremented by 1 for product identified as NON-RESERVED


  @ecomm @allChannels @purchasePath @reservations
  Scenario: Anonymous customer purhchases a non-reserved item without impacting the reserved item
            A registered user has one reserved item.
            The user is not signed in and adds an item with a different sku to his reserved item directly to his shopping bag.
            The user then signs in anonymously and purchases the item as a non-reserved item.
    And I sign in with the correct details
    And I signout
    Given I reserve a product with a quantity of 1
    When I add a product identified as NON-RESERVED to my shopping bag with a different sku to the reserved item
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously using the default customer within the purchase path
    And I fill out a UK non London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And I pay by MASTER_CARD
    Then My order is confirmed
    And The reserved product is not redeemed
    And The stock level remains unchanged for the reserved item
    And I verify that the stock level is decremented by 1 for product identified as NON-RESERVED


#    MULTIPLE SKUS
  @ecomm @allChannels @purchasePath @reservations
  Scenario: Registered customer purchases two reserved skus (same sku)
          A registered user has two reserved items of the same sku.
          The user is logged in.
          The user then buys two items with the same sku as the reserved item directly.
    And I sign in with the correct details
    When I reserve a product with a quantity of 2
    And I add a product to my shopping bag with the same sku as the reserved item
    And I verify that the basket item quantity is 1
    And I verify that the added product is a reserved item
    And I add a product to my shopping bag with the same sku as the reserved item
    And I verify that the basket item quantity is 2
    And I verify that the added product is a reserved item
    And I go to Shopping Bag page
    And I proceed to purchase
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And The payment page shows my shipping and billing addresses are the same
    And I pay by AMERICAN_EXPRESS
    Then My order is confirmed
    And The reserved product is redeemed
    And The stock level remains unchanged for the reserved item

  @ecomm @allChannels @purchasePath @reservations
  Scenario: A registered customer purchases 2 reserved skus and 1 non-reserved sku (same sku)
            A registered user has two reserved items.
            The user is logged in.
            He then buys three items with the same sku as the reserved item directly.
    And I sign in with the correct details
    When I reserve a product with a quantity of 2
    And I add a product to my shopping bag with the same sku as the reserved item
    And I verify that the basket item quantity is 1
    And I verify that the added product is a reserved item
    And I add a product to my shopping bag with the same sku as the reserved item
    And I verify that the basket item quantity is 2
    And I verify that the added product is a reserved item
    And I add a product to my shopping bag with the same sku as the reserved item
    And I verify that the basket item quantity is 3
    And I verify that the added product is a reserved item
    And I go to Shopping Bag page
    And I proceed to purchase
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And The payment page shows my shipping and billing addresses are the same
    And I pay by VISA_CREDIT_CARD
    Then My order is confirmed
    And The reserved product is redeemed
    And I verify that the stock level is decremented by 1 for the reserved item


  @ecomm @allChannels @purchasePath @reservations
  Scenario: A registered customer purchases 1 out of 2 reserved skus.
            A registered user has two reserved items with the same sku. The user is logged in.
            He then buys one item with the same sku as the reserved item from the product details page.
    And I sign in with the correct details
    When I reserve a product with a quantity of 2
    And I add a product to my shopping bag with the same sku as the reserved item
    And I verify that the basket item quantity is 1
    And I verify that the added product is a reserved item
    And I go to Shopping Bag page
    And I proceed to purchase
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And The payment page shows my shipping and billing addresses are the same
    And I pay by VISA_CREDIT_CARD
    Then My order is confirmed
    And The reserved product is redeemed 1 times
    And The stock level remains unchanged for the reserved item

  @ecomm @allChannels @purchasePath @reservations
  Scenario: Anonymous customer purchases 1 out of 2 reserved skus.
            A registered user has two reserved items with the same sku. The user is not logged in.
            He then buys one item with the same sku as the reserved item from the product details page.
    And I sign in with the correct details
    And I signout
    When I reserve a product with a quantity of 2
    And I add a product to my shopping bag with the same sku as the reserved item
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously using the default customer within the purchase path
    And I fill out a UK non London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And I pay by MASTER_CARD
    Then My order is confirmed
    And The reserved product is redeemed 1 times
    And The stock level remains unchanged for the reserved item


  @ecomm @allChannels @purchasePath @reservations
  Scenario: Registered customer purchases 1 out of 2 reserved skus after the reservation has expired.
            A registered user has two reserved items with the same sku.
            The user is logged in and there is main stock for these products.
            The user adds an item with the same sku as his reserved item directly to his shopping bag and both reservations expire.
            The user then purchases the item as a non-reserved item.
    And I sign in with the correct details
    Given I reserve a product with a quantity of 2
    When I add a product to my shopping bag with the same sku as the reserved item
    And I add a product to my shopping bag with the same sku as the reserved item
    And I view my shopping bag
    And My Reservation expires
    And I proceed to purchase
    And I fill out a UK London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And The payment page shows my shipping and billing addresses are the same
    And I pay by AMERICAN_EXPRESS
    Then My order is confirmed
    And The reserved product is not redeemed
    And I verify that the stock level is decremented by 2 for the reserved item


  @ecomm @allChannels @purchasePath @reservations
  Scenario: Anonymous customer purchases 1 out of 2 reserved skus after the reservation has expired.
            A registered user who is not signed in adds two reserved items directly to his shopping bag.
            The user then proceeds to purchase anonymously.
            However while on the payment page, the item reservation expires.
            The product remains in stock.
            The user is able to buy the two items and the stock quantity of the reserved item decreased by two.
    And I sign in with the correct details
    And I signout
    Given I reserve a product with a quantity of 2
    When I add a product to my shopping bag with the same sku as the reserved item
    And I add a product to my shopping bag with the same sku as the reserved item
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously using the default customer within the purchase path
    And I fill out a UK non London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And My Reservation expires
    And I pay by VISA_DEBIT
    Then My order is confirmed
    And The reserved product is not redeemed
    And I verify that the stock level is decremented by 2 for the reserved item


  @ecomm @allChannels @purchasePath @reservations
  Scenario: Anonymous customer cannot purchase reserved skus after they have gone out of stock.
            A registered user who is not signed in adds two reserved items directly to his shopping bag.
            The user then proceeds to purchase anonymously.
            However while on the payment page, the item reservation expires.
            The item goes out of stock.
            The user is NOT able to purchase the item and a error message is displayed.
    And I sign in with the correct details
    And I signout
    Given I reserve a product with a quantity of 2
    When I add a product to my shopping bag with the same sku as the reserved item
    And I add a product to my shopping bag with the same sku as the reserved item
    And I go to Shopping Bag page
    And I proceed to purchase
    And I sign in anonymously using the default customer within the purchase path
    And I fill out a UK non London shipping address
    And I click proceed to purchase from the shipping address page
    And I click proceed to purchase from the shipping options page
    And My Reservation expires
    And My reserved item goes out of stock
    And I attempt to pay by MASTER_CARD
    Then The Order Confirmation page is NOT displayed
    And The Shopping Bag page is displayed
    And The product shows out of stock in the Shopping Bag page
    And The reserved product is not redeemed
    And I want to reset the stock level of my reserved item to its original level