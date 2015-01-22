@responsive @Channelized
Feature: Responsive Header Tests

#################################################################
# Display logo and strap line on Net-A-Porter (EVAL-407)
#################################################################

  Scenario: Redirect to Homepage on click
    When I navigate to IN_STOCK product detail page for category CLOTHING
    When I click on NAP logo or strap line
    Then I am on the home page

  Scenario Outline: Logo and strapline is displayed correctly
    When I navigate to IN_STOCK product detail page for category BAGS
    When I resize the width to <width>
    Then I should see NAP logo
    And The strapline should be <visible>

  Examples:
    | width | visible |
    | 1080  | true    |
    | 320   | false   |

#################################################################
#  Display Country and Currency in Net-A-Porter header (EVAL-408)
#################################################################

  Scenario Outline: Country/Currency in header is displayed correctly
    Given I am on <channel>
    And I go to Home page
    When I navigate to 413447 product detail page
    And I resize the width to <width>
    Then <country> and <currency> are <visible> in the header

  Examples:
    |  channel  | country             | currency | width | visible |
    |   intl    | United Kingdom      | GBP      | 1080  | true    |
    |    am     | United States       | USD      | 1080  | true    |
    |    am     | United States       | USD      | 320   | false   |


##########################################################
# Feature: Country and Currency Display Links (EVAL-409)
##########################################################

  Scenario: Drop-down list appears on click for desktop
    Given I navigate to 507155 product detail page
    When I click the change country
    Then change country drop down list appears

  Scenario: All supported countries listed
    Given I navigate to 507155 product detail page
    When I click the change country
    Then All supported countries are listed

  Scenario Outline: Flag and currency changes with country
    Given I navigate to 507155 product detail page
    And I click the change country
    When I switch my country to <country>
    Then the <countryflag> icon is visible
    And the currency changes to one corresponding with the chosen <country>

  Examples:
    | country               |   countryflag   |
    | Germany               |     DE          |
    | Nepal                 |     NP          |
    | France                |     FR          |


#####################################################
##Feature: Display channel swap message (EVAL-411)
#####################################################

  Scenario Outline: Display message on change channel on desktop
    Given I am on <channel>
    And I go to Home page
    And I navigate to 507155 product detail page
    And I click the change country
    When I switch my country to <country>
    Then country change message true
    And I confirm to change country
    And My country is now <country>

  Examples:
    | channel  | country               |
    | apac     | United States         |
    | intl     | Brazil                |
    | am       | United Kingdom        |
    | apac     | Germany               |
    | intl     | Australia             |
    | am       | China                 |

  Scenario Outline: Do not display message on same channel on desktop
    Given I am on <channel>
    And I go to Home page
    And I navigate to 507155 product detail page
    And I click the change country
    When I switch my country to <country>
    Then country change message false
    And My country is now <country>

  Examples:
    | channel  | country               |
    | apac     | China                 |
    | intl     | Germany               |
    | am       | Canada                |

  Scenario Outline: Country and currency choice unchanged in the header when navigating
    Given I am on <channel>
    And I go to Home page
    And I navigate to 507155 product detail page
    And I click the change country
    And I switch my country to <country>
    And I confirm to change country
    When I am currently on a All_Sportswear sport listing page
    Then the country displayed in the top nav is <country>

  Examples:
    | channel  | country               |
    | apac     | France                |
    | intl     | United States         |


  ################################################################
  #Feature: Display Language in the Net-A-Porter header (EVAL-415)
  ################################################################

  Scenario: Drop-down list appears on click for desktop
    Given I go to Home page
    And I navigate to 507155 product detail page
    When I click the language link
    Then a drop down list appears with four languages

  Scenario Outline: Language label changes when a new language chosen
    Given I go to Home page
    And I navigate to 507155 product detail page
    When I click the language link
    When I switch my language to <language>
    Then correct language label is displayed on header in responsive page

  Examples:
    |   language   |
    |    Français  |
    |    Deutsch   |
    |    中文       |
    |   English    |

  Scenario Outline: Language choice unchanged when navigating to another page on the desktop site
    Given I go to Home page
    And I navigate to 507155 product detail page
    When I click the language link
    When I switch my language to <language>
    And I am currently on a All_Sportswear sport listing page
    Then correct language label is displayed on header in unresponsive page

  Examples:
    |   language   |
    |    Français  |
    |    Deutsch   |
    |    中文       |
    |   English    |

  Scenario Outline: Language choice unchanged when navigating to another page on the desktop site
    Given I go to Home page
    And I navigate to 507155 product detail page
    When I click the language link
    When I switch my language to <language>
    And I am a registered user
    When I sign in with the correct details
    Then correct language label is displayed on header in unresponsive page

  Examples:
    |   language   |
    |    Français  |
    |    Deutsch   |
    |    中文       |
    |   English    |


  #################################################
  #Feature: Shopping bag: display Link (EVAL-420)
  #################################################

  Scenario: Clicking on shopping shopping bag icon redirects to shopping bag page
    Given I go to Home page
    And I navigate to 507155 product detail page
    When I click the NAP shopping bag icon
    Then I should be on the Shopping Bag page

  #########################################################
  #Feature: Header: display customer care number (EVAL-462)
  #########################################################

  Scenario Outline: Display specific customer care phone number
    When I navigate to IN_STOCK product page for category CLOTHING using url parameters with country <countryCode> language <languageCode> and device d
    Then I am provided with the correct customer service number <contactNumber>

  Examples:
    |countryCode  | languageCode      | contactNumber       |
    |  gb         |     en            | 0330 022 5701       |
    |  gb         |     fr            | 0330 022 5700       |
    |  gb         |     de            | 0330 022 5700       |
    |  gb         |     zh            | 0330 022 5700       |
    |  ru         |     en            | 8 800 100 6443      |
    |  ru         |     fr            | 8 800 100 6443      |
    |  ru         |     de            | 8 800 100 6443      |
    |  ru         |     zh            | 8 800 100 6443      |
    |  de         |     de            | 0800 589 0723       |
    |  de         |     zh            | 0800 589 0723       |
    |  de         |     en            | 0800 589 0723       |
    |  de         |     fr            | 0800 589 0723       |
    |  fr         |     en            | 0805 109 917        |
    |  fr         |     fr            | 0805 109 917        |
    |  fr         |     de            | 0805 109 917        |
    |  fr         |     zh            | 0805 109 917        |
    |  be         |     de            | 0800 49413          |
    |  be         |     zh            | 0800 49413          |
    |  be         |     fr            | 0800 49413          |
    |  be         |     en            | 0800 49413          |
    |  at         |     en            | 0800 006632         |
    |  at         |     fr            | 0800 006632         |
    |  at         |     de            | 0800 006632         |
    |  at         |     zh            | 0800 006632         |
    |  it         |     de            | 800 148 251         |
    |  it         |     zh            | 800 148 251         |
    |  it         |     fr            | 800 148 251         |
    |  it         |     en            | 800 148 251         |
    |  gr         |     en            | 0800 128 264        |
    |  gr         |     fr            | 0800 128 264        |
    |  gr         |     de            | 0800 128 264        |
    |  gr         |     zh            | 0800 128 264        |
    |  ch         |     de            | 0800 002417         |
    |  ch         |     zh            | 0800 002417         |
    |  ch         |     en            | 0800 002417         |
    |  ch         |     fr            | 0800 002417         |
    |  es         |     en            | 800 600 261         |
    |  es         |     fr            | 800 600 261         |
    |  es         |     de            | 800 600 261         |
    |  es         |     zh            | 800 600 261         |
    |  nl         |     de            | +44 203 471 5951    |
    |  nl         |     zh            | +44 203 471 5953    |
    |  nl         |     en            | +44 330 022 5700    |
    |  nl         |     fr            | +44 203 471 5950    |
    |  br         |     en            | 0800 76 21268       |
    |  br         |     fr            | 0800 76 21268       |
    |  br         |     de            | 0800 76 21268       |
    |  br         |     zh            | 0800 76 21268       |
    |  us         |     en            | +1 877 678 9627     |
    |  us         |     fr            | +1 212 901 3149     |
    |  us         |     de            | +1 212 901 3170     |
    |  us         |     zh            | +1 212 901 3210     |
    |  hk         |     en            | 5808 1362           |
    |  hk         |     fr            | 5808 1362           |
    |  hk         |     de            | 5808 1362           |
    |  hk         |     zh            | 5808 1362           |
    |  cn         |     en            | 400 810 2684        |
    |  cn         |     fr            | 400 810 2684        |
    |  cn         |     de            | 400 810 2684        |
    |  cn         |     zh            | 400 810 2684        |
    |  jp         |     en            | 00531122556         |
    |  jp         |     fr            | 00531122556         |
    |  jp         |     de            | 00531122556         |
    |  jp         |     zh            | 00531122556         |
    |  kr         |     en            | 0079844341351       |
    |  kr         |     fr            | 0079844341351       |
    |  kr         |     de            | 0079844341351       |
    |  kr         |     zh            | 0079844341351       |
    |  tw         |     en            | 00801136430         |
    |  tw         |     fr            | 00801136430         |
    |  tw         |     de            | 00801136430         |
    |  tw         |     zh            | 00801136430         |
    |  sg         |     en            | 800 12 06 161       |
    |  sg         |     fr            | 800 12 06 161       |
    |  sg         |     de            | 800 12 06 161       |
    |  sg         |     zh            | 800 12 06 161       |
    |  au         |     en            | 1 800 638680        |
    |  au         |     fr            | 1 800 638680        |
    |  au         |     de            | 1 800 638680        |
    |  au         |     zh            | 1 800 638680        |
    |  bd         |     en            | +44 330 022 5700    |
    |  bd         |     fr            | +44 203 471 5950    |
    |  bd         |     de            | +44 203 471 5951    |
    |  bd         |     zh            | +86 400 810 2684    |




  ######################################################################################
  #Feature: Shopping bag: update Item Number and shopping mini bag (EVAL-422 & EVAL-423)
  ######################################################################################
#  To be uncommented when add to bag button is added in responsive layout

#  Scenario: Guest user - Shopping bag number increase basket icon on header and mini shopping bag display product information
#    Given I go to Home page
#    And I navigate to 507155 product detail page
#    When I click the Add to Basket Button
#    Then I should see mini bag overlay with correct information
#    And The number on the shopping bag icon should increased

  #  To be uncommented when add to bag button is added in responsive layout
#  Scenario: Guest user - Shopping bag number decrease basket icon on header
#    Given I add 2 in stock products directly to my bag
#    And I should see that product in the shopping bag
#    When I remove product from my shopping bag
#    And I navigate to 507155 product detail page
#    Then The number on the shopping bag icon should decreased
#
#    Scenario: Shopping bag number increases by ONE on mobile
#      Given I am viewing a product page on mobile
#      When I click the Add to Basket Button
#      Then The number on the display link increments by ONE
#
#    Scenario: Shopping bag number decreases on mobile
#      Given I am viewing the shopping bag page on mobile
#      When I delete a product from my shopping bag
#      Then The number on the display link decrements by ONE

#
##Feature: Contact Us (EVAL-412)
#
#  Scenario: Email address opens email client
#    Given I am on the desktop site viewing Contact Us in header
#    When I click the email address
#    Then My email client opens
#
#  Scenario Outline: IP specific customer care phone number
#    Given I am viewing the site in a particular <country>
#    When I go to Contact Us in the header
#    Then I am provided with the appropriate <contactNumber>
#
#  Examples:
#    | country | contactNumber |
#    | uk      | 0800 044 5700 |
#    | france  | 0805 109 917  |
#    | us      | +1 877 678 9627|
#    | hong kong| +44 330 022 5700 |
#
#
#
#  #Mobile brings up native drum
#
##Feature: Top Ten Countries (EVAL-413)
#
#  Scenario: Show the top ten countries at the top of the list
#    Given I am on the desktop view
#    When I click the CC links
#    And the drop down appears
#    Then the top ten countries are displayed at the top of the list
#
##Feature: Display Language in the Net-A-Porter header (EVAL-414, EVAL-415, EVAL-417)
#
#  Scenario: Language in header on desktop view
#    Given I am on desktop view
#    When I go to the top of the page
#    Then language is visible in the header
#
#  Scenario: Drop-down list appears on click for desktop 
#    Given I am on the desktop view of the site
#     When I click the Language link 
#    Then a drop down list appears
#
#  Scenario: Language label changes when a new language chosen 
#    Given I am on the desktop view of the site 
#    When I change language
#     Then the drop list disappears 
#    And the Language label updates to the chosen language
#
#  Scenario: Language choice unchanged when navigating to another page on the desktop site 
#    Given I am on the desktop view of the site 
#    When I go to navigate to another page on the site 
#    Then the language choice remains unchanged
#
#  Scenario: Language choice unchanged when clicking outside the drop down 
#    Given I am on the desktop view of the site 
#    And I have a clicked the Language link 
#    When I click anywhere on the current page 
#    Then the drop down list disappears 
#    And the language choice remains unchanged
#
#  Scenario: Language in footer on mobile view
#    Given I am on the mobile view of the site
#    When I go to the top of the bottom of the page
#    And I expand the Site Preference section
#    Then the language is visible in the footer
#
#  Scenario: Native selector appears on tap in mobile view
#    Given I am on the mobile view of the site
#    When I tap the Language link
#    Then a native selector list appears
#
#  Scenario: Language label changes when a new language chosen
#    Given I am on the mobile view of the site
#    When I change language
#    Then the native selector closes
#    And the chosen language is displayed
#
#  Scenario: Language choice unchanged when navigating to another page on the mobile view
#    Given I am on the mobile view of the site
#    When I go to navigate to another page on the site
#    Then the language choice remains unchanged
#
#  Scenario: Language choice unchanged when clicking outside the drop down
#    Given I am on the mobile view of the site
#    And I have a clicked the Language link
#    When I click anywhere on the current page
#    Then the native selector disappears
#    And the language choice remains unchanged
#
##Feature: Cookie Message (EVAL-419)
#
##Depends on business rules
#
#  Scenario: Cookie Message appears
#    Given I have not visited the site before
#    When I access the site
#    Then a cookie message appears
#
#  Scenario: Cookie Message does not appear if already shown
#    Given The cookie message has already been shown
#    When I access the site
#    Then no cookie message is displayed
#
#  Scenario: Cross to dismiss on mobile
#    Given I am on mobile view
#    And the cookie message is displayed
#    When I click the cross
#    Then the message disappears
#
#  Scenario: Button to dismiss on Desktop
#    Given I am on desktop view
#    And the cookie message is displayed
#    When I click the button
#    Then the message disappears
#
#

#
