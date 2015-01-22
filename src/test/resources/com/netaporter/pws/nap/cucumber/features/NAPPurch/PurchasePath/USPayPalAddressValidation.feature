@purch
Feature: USPayPalAddressValidation.feature  United States PayPal Address Validation
    In order to successfully pay with PayPal
    As a customer of net-a-porter.com
    When I provide an invalid US shipping address then I should see a validation message

 @ecomm @allChannels @purchasePath @paypal @nap @NonChannelized @region=AM
Scenario Outline: Ensure a validation message is displayed in selected language when using an invalid US address
   Given I change language to <language>
   When I add 1 in stock products directly to my bag
   And I go to Shopping Bag page
   And I proceed to purchase
   And I attempt to purchase anonymously using an invalid US shipping address and reach the payment page
   And I review my order summary details on the payment page
   And I click on payment option PAYPAL
   When I click purchase now from the payment page
   Then I verify the error message <errorMessage> is displayed on the payment page

Examples:
   |language| errorMessage                                                                                                                                          |
   |English | PayPal has been unable to confirm your shipping address. Please check that the city, state and ZIP code are correct.                                  |
   |French  | La reconnaissance de votre adresse de livraison par PayPal a échoué. Merci de vérifier la validité de la ville, le pays et le code postal renseignés. |
   |German  | Leider konnte PayPal Ihre Lieferadresse nicht bestätigen. Bitte stellen Sie sicher, dass Sie Ihre Stadt, Ihren Bundesstaat sowie Ihre Postleitzahl richtig eingegeben haben.|
   |Chinese | PayPal 无法识别您的配送地址。请核对您所输入的城市、州/省和邮编是否正确                                                                                        |