Feature: ApacIntlOrders.feature Allow ex-INTL customers to view orders on the my orders page on APAC
  As an ex-INTL customer
  I want to see INTL orders in my orders page on APAC

  @nap @NonChannelized @region=INTL @purch
  Scenario: View INTL orders on APAC my orders page
    Given I have successfully registered on INTL channel website
    And I have successfully purchased an item
    And I signout
    When I login onto APAC channel
    And I go to Customer Orders page
    Then I can see the INTL order is listed
    When I click the INTL order
    Then I can see the item detail
