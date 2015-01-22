Feature: Wishlist V3 TempTest
  As a NAP Tester
  I want to be able to use cucumber to generate test data
  So that I can look cool and get all the girls

#
#    Scenario: Temp
#      Given I am signed in as antonio.michael@net-a-porter.com with password 123456
#      And I create a new wishlist via the WOAS API called Low Stock
#      And I have 10 LOW_STOCK and visible CLOTHING sku
#      And I add skus 1 to 20 to my wishlist via the WOAS API

#  Scenario: Temp2
#    Given I am signed in as antonio.michael@net-a-porter.com with password 123456
#    And I have 2 in stock skus from the same pid
#    And I store the details of products 1 to 2 from the product page
#    And I create a new wishlist via the WOAS API called aWishlist1
#    And I add skus 1 to 2 to my wishlist via the WOAS API
#
#  Scenario: Temp3
#    Given I am signed in as antonio.michael@net-a-porter.com with password 123456
#    And I have 30 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
#    And I have 30 IN_STOCK and visible BAGS sku that I force to have similar stock in the db
#    And I have 30 IN_STOCK and visible SHOES sku that I force to have similar stock in the db
#    And I have 30 IN_STOCK and visible LINGERIE sku that I force to have similar stock in the db
#    And I have 30 IN_STOCK and visible ACCESSORIES sku that I force to have similar stock in the db
#    And I have 30 IN_STOCK and visible BEAUTY sku that I force to have similar stock in the db
#    And I create a new wishlist via the WOAS API called Long List 2
#    And I quickly add skus 1 to 180 to my wishlist via the WOAS API
##    And I have 11 IN_STOCK and visible CLOTHING skus
##    And I have 11 IN_STOCK and visible BAGS skus
##    And I have 11 IN_STOCK and visible SHOES skus
##    And I have 11 IN_STOCK and visible LINGERIE skus
##    And I have 11 IN_STOCK and visible ACCESSORIES skus
##    And I have 11 IN_STOCK and visible BEAUTY skus
#    And I create a new wishlist via the WOAS API called SOLD OUT STUFF

#
##
#  Scenario: TEMPDELETE
#  Given I am a quickly registered user on intl
##    And I have 15 IN_STOCK and visible CLOTHING sku that I force to have similar stock in the db
#    And I have 15 IN_STOCK and visible LINGERIE skus that I force to have similar stock in the db
#    And I have 15 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
#    And I have 15 IN_STOCK and visible SHOES skus that I force to have similar stock in the db
#    And I have 15 IN_STOCK and visible ACCESSORIES skus that I force to have similar stock in the db
#    And I have 15 IN_STOCK and visible BEAUTY skus that I force to have similar stock in the db
#    And I create a new wishlist via the WOAS API called Toni 1.1
#    And I quickly add skus 1 to 30 to my wishlist via the WOAS API
#    And I create a new wishlist via the WOAS API called Toni 2.2
#    And I quickly add skus 31 to 60 to my wishlist via the WOAS API
#    And I create a new wishlist via the WOAS API called Toni 3.3
#    And I quickly add skus 61 to 90 to my wishlist via the WOAS API
#    When I view that specific wishlist via its direct url
#
#  Scenario: TEMPDELETE
#   Given I am signed in as antonio.michael@net-a-porter.com with password 123456
#    And I have 100 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
#    And I have 20 ON_SALE and visible CLOTHING skus that I force to have similar stock in the db
#    And I have 20 LOW_STOCK and visible CLOTHING skus that I force to have similar stock in the db
#    And I create a new wishlist via the WOAS API called Alerts2
#    And I quickly add skus 1 to 40 to my wishlist via the WOAS API


#  @known-failure @COM-834
#Scenario: Long running test. Checking for intermittent issues with services
#I am a quickly registered user on intlred user
#  And I have 5 IN_STOCK and visible CLOTHING skus
#  And I create a new wishlist via the WOAS API called Wishlist1
#  And I add skus 1 to 3 to my wishlist via the WOAS API
#  And I store the details of products 1 to 2 from the product page
#  And I create a new wishlist via the WOAS API called Wishlist2
#  And I add skus 4 to 5 to my wishlist via the WOAS API
#  And I store the details of products 4 to 5 from the product page
#  And I add skus 3 to 3 to my wishlist via the WOAS API
#  And I store the details of products 3 to 3 from the product page
#  And I navigate to the All Items page for wishlist
#  Then I should see the correct wishlist page title
#  And I should see the correct wishlist header
#  And I should see the wishlist items ordered by date added descending without duplicates
#  And I should see the correct size for each item
#  And I navigate to the All Items page for wishlist
#  And I should see the correct wishlist header
#  And I navigate to the All Items page for wishlist
#  And I should see the correct wishlist header
#
#  Scenario: djskhdshsdhdjs sdjdshsdjh
#    Given I am a quickly registered user on intl
##    And I have 2 SOLD_OUT and visible BAGS skus that I force to have similar stock in the db
#  And I store the details of products 1 to 1 from the https product page

##    And I have 20 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
#    And I have 20 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
#    And I have 20 IN_STOCK and visible SHOES skus that I force to have similar stock in the db
#    And I have 20 IN_STOCK and visible ACCESSORIES skus that I force to have similar stock in the db
#    And I have 20 IN_STOCK and visible LINGERIE skus that I force to have similar stock in the db
#    And I have 20 IN_STOCK and visible BEAUTY skus that I force to have similar stock in the db

#
#    And I have 2 ON_SALE and visible CLOTHING skus that I force to have similar stock in the db
#    And I have 2 ON_SALE and visible BAGS skus that I force to have similar stock in the db
#    And I have 2 ON_SALE and visible SHOES skus that I force to have similar stock in the db
#    And I have 2 ON_SALE and visible ACCESSORIES skus that I force to have similar stock in the db
#    And I have 2 ON_SALE and visible LINGERIE skus that I force to have similar stock in the db
#    And I have 2 ON_SALE and visible BEAUTY skus that I force to have similar stock in the db
#    And I have 2 LOW_STOCK and visible CLOTHING skus that I force to have similar stock in the db
#    And I have 2 LOW_STOCK and visible BAGS skus that I force to have similar stock in the db
#    And I have 2 LOW_STOCK and visible SHOES skus that I force to have similar stock in the db
#    And I have 2 LOW_STOCK and visible ACCESSORIES skus that I force to have similar stock in the db
#    And I have 2 LOW_STOCK and visible LINGERIE skus that I force to have similar stock in the db
#    And I have 2 LOW_STOCK and visible BEAUTY skus that I force to have similar stock in the db
#    And I have 2 SOLD_OUT and visible CLOTHING skus that I force to have similar stock in the db
#    And I have 2 SOLD_OUT and visible BAGS skus that I force to have similar stock in the db
#    And I have 2 SOLD_OUT and visible SHOES skus that I force to have similar stock in the db
#    And I have 2 SOLD_OUT and visible ACCESSORIES skus that I force to have similar stock in the db
#    And I have 2 SOLD_OUT and visible LINGERIE skus that I force to have similar stock in the db
#    And I have 2 SOLD_OUT and visible BEAUTY skus that I force to have similar stock in the db
#    And I have 2 IN_STOCK and visible CLOTHING skus that I force to have similar stock in the db
#    And I have 2 IN_STOCK and visible BAGS skus that I force to have similar stock in the db
#    And I have 2 IN_STOCK and visible SHOES skus that I force to have similar stock in the db
#    And I have 2 IN_STOCK and visible ACCESSORIES skus that I force to have similar stock in the db
#    And I have 2 IN_STOCK and visible LINGERIE skus that I force to have similar stock in the db
#    And I have 2 IN_STOCK and visible BEAUTY skus that I force to have similar stock in the db
#    And I create a new wishlist via the WOAS API called A Couple Of Each
#    And I quickly add skus 1 to 48 to my wishlist via the WOAS API

#  ScenarioI am a quickly registered user on intl quickly registered user
#    And I have 10 ON_SALE and visible CLOTHING sku that I force to have similar stock in the db
#    And I have 10 ON_SALE and visible LINGERIE sku that I force to have similar stock in the db
#    And I have 10 ON_SALE and visible BAGS sku that I force to have similar stock in the db
#    And I store the details of products 1 to 1 from the product page
#    When I select sku 1's size and click the new Add to Wish List button
#    And I store the details of products 2 to 2 from the product page
#    When I select sku 2's size and click the new Add to Wish List button
#    And I store the details of products 3 to 3 from the product page
#    When I select sku 3's size and click the new Add to Wish List button
#    And I store the details of products 4 to 4 from the product page
#    When I select sku 4's size and click the new Add to Wish List button
#    And I store the details of products 5 to 5 from the product page
#    When I select sku 5's size and click the new Add to Wish List button
#    And I store the details of products 6 to 6 from the product page
#    When I select sku 6's size and click the new Add to Wish List button
#    And I store the details of products 7 to 7 from the product page
#    When I select sku 7's size and click the new Add to Wish List button
#    And I store the details of products 8 to 8 from the product page
#    When I select sku 8's size and click the new Add to Wish List button
#    And I store the details of products 9 to 9 from the product page
#    When I select sku 9's size and click the new Add to Wish List button
#    And I store the details of products 10 to 10 from the product page
#    When I select sku 10's size and click the new Add to Wish List button
#    And I store the details of products 11 to 11 from the product page
#    When I select sku 11's size and click the new Add to Wish List button
#    And I store the details of products 12 to 12 from the product page
#    When I select sku 12's size and click the new Add to Wish List button
#    And I store the details of products 13 to 13 from the product page
#    When I select sku 13's size and click the new Add to Wish List button
#    And I store the details of products 14 to 14 from the product page
#    When I select sku 14's size and click the new Add to Wish List button
#    And I store the details of products 15 to 15 from the product page
#    When I select sku 15's size and click the new Add to Wish List button
#    And I store the details of products 16 to 16 from the product page
#    When I select sku 16's size and click the new Add to Wish List button
#    And I store the details of products 17 to 17 from the product page
#    When I select sku 17's size and click the new Add to Wish List button
#    And I store the details of products 18 to 18 from the product page
#    When I select sku 18's size and click the new Add to Wish List button
#    And I store the details of products 19 to 19 from the product page
#    When I select sku 19's size and click the new Add to Wish List button
#    And I store the details of products 20 to 20 from the product page
#    When I select sku 20's size and click the new Add to Wish List button
#    And I store the details of products 21 to 21 from the product page
#    When I select sku 21's size and click the new Add to Wish List button
#    And I store the details of products 22 to 22 from the product page
#    When I select sku 22's size and click the new Add to Wish List button
#    And I store the details of products 23 to 23 from the product page
#    When I select sku 23's size and click the new Add to Wish List button
#    And I store the details of products 24 to 24 from the product page
#    When I select sku 24's size and click the new Add to Wish List button
#    And I store the details of products 25 to 25 from the product page
#    When I select sku 25's size and click the new Add to Wish List button
#    And I store the details of products 26 to 26 from the product page
#    When I select sku 26's size and click the new Add to Wish List button
#    And I store the details of products 27 to 27 from the product page
#    When I select sku 27's size and click the new Add to Wish List button
#    And I store the details of products 28 to 28 from the product page
#    When I select sku 28's size and click the new Add to Wish List button
#    And I store the details of products 29 to 29 from the product page
#    When I select sku 29's size and click the new Add to Wish List button
#    And I store the details of products 30 to 30 from the product page
#    When I select sku 30's size and click the new Add to Wish List button
