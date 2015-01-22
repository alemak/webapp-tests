Feature: Delivered Duty Paid (DDP)

  #these tests fail very often because of data issues. add @nap tag back when we figure out a way to fix this or remove them completely as PSP probably handle these cases
  @NonChannelized
  Scenario Outline: If there is a tax/duty assigned to a country then there must be an additional amount added in the purchase path
    Given I am on <channel>
    And I add an expensive product to my shopping bag
    And I proceed to purchase as an anonymous user
    And I fill in shipping address with <countryTo> and <state> and <postcode> in the address form
    And I proceed to the Payment page
    Then Customer pays duty: <payDuty>

  Examples:
    | channel | countryTo            |   state     | postcode   | payDuty |
    | am      | United States        | New York    |  10001     | false   |
    | apac    | Australia            | test state  |  123       | true    |
    | intl    | Austria              | test state  |  123       | false   |
    | intl    | Bahrain              | test state  |  123       | true    |
    | intl    | Belgium              | test state  |  123       | false   |
    | intl    | Brunei               | test state  |  123       | true    |
    | am	  | Brazil		         | test state  |  123       | true    |
    | intl	  | Brazil		         | test state  |  123       | false   |
    | intl    | Bulgaria             | test state  |  123       | false   |
    | am      | Canada               | Alberta     |  123       | true    |
    | am      | Chile                | test state  |  123       | true    |
    | apac    | China                | test state  |  123       | true    |
    | intl    | Croatia              | test state  |  123       | false   |
    | intl    | Cyprus               | test state  |  123       | false   |
    | intl    | Czech Republic       | test state  |  123       | false   |
    | intl    | Denmark              | test state  |  123       | false   |
    | intl    | Egypt                | test state  |  123       | true    |
    | intl    | Estonia              | test state  |  123       | false   |
    | intl    | Finland              | test state  |  123       | false   |
    | intl    | France               | test state  |  123       | false   |
    | intl    | Germany              | test state  |  123       | false   |
    | intl    | Greece               | test state  |  123       | false   |
    | intl    | Hungary              | test state  |  123       | false   |
    | apac    | India                | test state  |  123       | true    |
    | intl    | Ireland              | test state  |  123       | false   |
    | intl    | Italy                | test state  |  123       | false   |
    | apac    | Japan                | test state  |  123       | true    |
    | intl    | Jordan               | test state  |  123       | true    |
    | intl    | Kuwait               | test state  |  123       | true    |
    | intl    | Latvia               | test state  |  123       | false   |
    | intl    | Lithuania            | test state  |  123       | false   |
    | intl    | Luxembourg           | test state  |  123       | false   |
    | apac    | Malaysia             | test state  |  123       | true    |
    | intl    | Malta                | test state  |  123       | false   |
    | intl    | Monaco               | test state  |  123       | false   |
    | intl    | Netherlands          | test state  |  123       | false   |
    | apac    | New Zealand          | test state  |  123       | true    |
    | intl    | Norway               | test state  |  123       | true    |
    | intl    | Oman                 | test state  |  123       | true    |
    | apac    | Philippines          | test state  |  123       | true    |
    | intl    | Poland               | test state  |  123       | false   |
    | intl    | Portugal             | test state  |  123       | false   |
    | am      | Puerto Rico          | test state  |  123       | true    |
    | intl    | Qatar                | test state  |  123       | true    |
    | intl    | Romania              | test state  |  123       | false   |
    | intl    | San Marino           | test state  |  123       | false   |
    | intl    | Saudi Arabia         | test state  |  123       | true    |
    | apac    | Singapore            | test state  |  123       | true    |
    | intl    | Slovakia             | test state  |  123       | false   |
    | intl    | Slovenia             | test state  |  123       | false   |
    | intl    | South Africa         | test state  |  123       | true    |
    | apac    | South Korea          | test state  |  123       | true    |
    | intl    | Spain                | test state  |  123       | false   |
    | intl    | Sweden               | test state  |  123       | false   |
    | intl    | Switzerland          | test state  |  123       | true    |
    | apac    | Thailand             | test state  |  123       | true    |
    | intl    | United Arab Emirates | test state  |  123       | true    |
    | intl    | United Kingdom       | test state  |  w60js     | false   |
    | intl    | United States        | New York    |  10001     | true    |
    | am      | United States        | New York    |  10001     | false   |
    | apac    | United States        | New York    |  10001     | true    |
    | am      | Venezuela            | test state  |  123       | true    |
    | apac    | Hong Kong            | Central     |  123       | false   |
    | apac    | Indonesia            | test state  |  123       | false   |
    | apac    | Taiwan ROC           | test state  |  123       | false   |
    | apac    | Vietnam              | test state  |  123       | false   |


