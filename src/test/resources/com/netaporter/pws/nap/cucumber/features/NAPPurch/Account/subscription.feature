@seaview  @purch
Feature: See subscriptions
  As a registered customer
  I want to see my subscriptions
  So that I can manage them

 @nap @Channelized
   Scenario Outline: Link to subscription page
   Given I am a seaview registered default user
   And I sign in with the correct details
   And MySubscription is active in the web dB
   And MySubscriptions link is displayed
   When I select the MySubscriptions link
   And I click on <link> subscription link
   Then I can see the porter subscription

   Examples:
   |link  |
   |review|
   |change|
   |view  |












