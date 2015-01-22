package com.netaporter.pws.automation.nap.components;

import com.netaporter.test.utils.pages.component.AbstractPageComponent;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import static org.junit.Assert.*;

/**
 * Created by s.joshi on 03/09/2014.
 */
@Component
@Scope("cucumber-glue")
public class FooterComponent extends AbstractPageComponent {

    private By CONTACT_US = By.xpath(".//*[@id='footer-lists']/ul[1]/li[2]/a");
    private By SHIPPING_INFORMATION = By.xpath(".//*[@id='footer-lists']/ul[1]/li[3]/a");
    private By RETURN_AND_EXCHANGE = By.xpath(".//*[@id='footer-lists']/ul[1]/li[4]/a");
    private By PAYMENT_SECURITY = By.xpath(".//*[@id='footer-lists']/ul[1]/li[5]/a");
    private By GIFT_CARDS = By.xpath(".//*[@id='footer-lists']/ul[1]/li[6]/a");
    private By FAQS = By.xpath(".//*[@id='footer-lists']/ul[1]/li[7]/a");
    private By THE_COMPANY = By.xpath(".//*[@id='footer-lists']/ul[2]/li[2]/a");
    private By CAREERS = By.xpath(".//*[@id='footer-lists']/ul[2]/li[3]/a");
    private By AFFILIATES = By.xpath(".//*[@id='footer-lists']/ul[2]/li[4]/a");
    private By ADVERTISING = By.xpath(".//*[@id='footer-lists']/ul[2]/li[5]/a");
    private By TERMS_AND_CONDITION = By.xpath(".//*[@id='footer-lists']/ul[2]/li[6]/a");
    private By FOOTER_HEADER_ELEMENT = By.cssSelector("#helptitle>h1");
    private By GIFT_CARD = By.id("landing");
    private By ABOUT_US = By.id("main-heading");
    private By CAREERS_PAGE = By.className("left");
    private By AFFILIATES_ADVERTISE_PAGE = By.cssSelector("#heading>h1");


    public void clickFooterLink(String footerLink) throws InterruptedException {
        if (footerLink.equalsIgnoreCase("Contact Us")){
            webBot.click(CONTACT_US);
            Thread.sleep(2000);
            switchToHelpPopUp();

        }else if (footerLink.equalsIgnoreCase("Shipping Information")){
            webBot.click(SHIPPING_INFORMATION);
            Thread.sleep(2000);
            switchToHelpPopUp();

        }else if (footerLink.equalsIgnoreCase("Return and Exchanges")){
            webBot.click(RETURN_AND_EXCHANGE);
            Thread.sleep(2000);
            switchToHelpPopUp();

        }else if (footerLink.equalsIgnoreCase("Payment Security")){
            webBot.click(PAYMENT_SECURITY);
            Thread.sleep(2000);
            switchToHelpPopUp();

        }else if (footerLink.equalsIgnoreCase("Gift Cards")){
            webBot.click(GIFT_CARDS);
            Thread.sleep(2000);

        }else if (footerLink.equalsIgnoreCase("FAQs")){
            webBot.click(FAQS);
            Thread.sleep(2000);
            switchToHelpPopUp();

        }else if (footerLink.equalsIgnoreCase("The Company")){
            webBot.click(THE_COMPANY);
            Thread.sleep(2000);

        }else if (footerLink.equalsIgnoreCase("Careers")){
            webBot.click(CAREERS);
            Thread.sleep(2000);

        }else if (footerLink.equalsIgnoreCase("Affiliates")){
            webBot.click(AFFILIATES);
            Thread.sleep(2000);

        }else if (footerLink.equalsIgnoreCase("Advertising")){
            webBot.click(ADVERTISING);
            Thread.sleep(2000);

        }else if (footerLink.equalsIgnoreCase("Terms and Conditions")){
            webBot.click(TERMS_AND_CONDITION);
            Thread.sleep(2000);
            switchToHelpPopUp();
        }
    }

    public void switchToHelpPopUp(){
        webBot.switchToPopUpWindow("help-content");
    }

    public void isCorrectFooterPageVisible(String footerLink) {

        if (footerLink.equalsIgnoreCase("Contact Us")){
            assertTrue("Actual URL displayed is: " +webBot.getCurrentUrl(), webBot.getCurrentUrl().contains("Help/Contact-Us"));
            assertTrue("Actual header displayed is: " +webBot.findElement(FOOTER_HEADER_ELEMENT).getText(), webBot.findElement(FOOTER_HEADER_ELEMENT).getText().equalsIgnoreCase("Contact Us"));

        }else if (footerLink.equalsIgnoreCase("Shipping Information")){
            assertTrue("Actual URL displayed is: " + webBot.getCurrentUrl(), webBot.getCurrentUrl().contains("Help/ShippingRatesAndPolicies"));
            assertTrue("Actual header displayed is: " +webBot.findElement(FOOTER_HEADER_ELEMENT).getText(), webBot.findElement(FOOTER_HEADER_ELEMENT).getText().equalsIgnoreCase("Shipping Information"));

        }else if (footerLink.equalsIgnoreCase("Return and Exchanges")){
            assertTrue("Actual URL displayed is: " + webBot.getCurrentUrl(), webBot.getCurrentUrl().contains("Help/ReturnsAndExchanges"));
            assertTrue("Actual header displayed is: " +webBot.findElement(FOOTER_HEADER_ELEMENT).getText(), webBot.findElement(FOOTER_HEADER_ELEMENT).getText().equalsIgnoreCase("RETURNS & EXCHANGES"));

        }else if (footerLink.equalsIgnoreCase("Payment Security")){
            assertTrue("Actual URL displayed is: " + webBot.getCurrentUrl(), webBot.getCurrentUrl().contains("Help/PaymentAndWebsiteSecurity"));
            assertTrue("Actual header displayed is: " +webBot.findElement(FOOTER_HEADER_ELEMENT).getText(), webBot.findElement(FOOTER_HEADER_ELEMENT).getText().equalsIgnoreCase("Payment security"));

        }else if (footerLink.equalsIgnoreCase("Gift Cards")){
            assertTrue("Actual URL displayed is: " + webBot.getCurrentUrl(), webBot.getCurrentUrl().contains("Content/giftvoucherlogin"));
            assertTrue("Gift card landing page is not displayed", webBot.findElement(GIFT_CARD).isDisplayed());

        }else if (footerLink.equalsIgnoreCase("FAQs")){
            assertTrue("Actual URL displayed is: " + webBot.getCurrentUrl(), webBot.getCurrentUrl().contains("Help/FrequentlyAskedQuestions"));
            assertTrue("Actual header displayed is: " +webBot.findElement(FOOTER_HEADER_ELEMENT).getText(), webBot.findElement(FOOTER_HEADER_ELEMENT).getText().equalsIgnoreCase("Frequently Asked Questions"));

        }else if (footerLink.equalsIgnoreCase("The Company")){
            assertTrue("Actual URL displayed is: " + webBot.getCurrentUrl(), webBot.getCurrentUrl().contains("About-Us/Our-Company"));
            assertTrue("Actual header displayed is: " +webBot.findElement(ABOUT_US).getText(), webBot.findElement(ABOUT_US).getText().equalsIgnoreCase("About Us"));

        }else if (footerLink.equalsIgnoreCase("Careers")){
            assertTrue("Actual URL displayed is: " + webBot.getCurrentUrl(), webBot.getCurrentUrl().contains("careers"));
            assertTrue("Career page has some element missing ", webBot.findElement(CAREERS_PAGE).isDisplayed());

        }else if (footerLink.equalsIgnoreCase("Affiliates")){
            assertTrue("Actual URL displayed is: " + webBot.getCurrentUrl(), webBot.getCurrentUrl().contains("Affiliates"));
            assertTrue("Actual header displayed is: " +webBot.findElement(AFFILIATES_ADVERTISE_PAGE).getText(), webBot.findElement(AFFILIATES_ADVERTISE_PAGE).getText().equalsIgnoreCase("Join the NET‑A‑PORTER"));

        }else if (footerLink.equalsIgnoreCase("Advertising")){
            assertTrue("Actual URL displayed is: " + webBot.getCurrentUrl(), webBot.getCurrentUrl().contains("advertisewithus"));
            assertTrue("Actual header displayed is: " +webBot.findElement(AFFILIATES_ADVERTISE_PAGE).getText(), webBot.findElement(AFFILIATES_ADVERTISE_PAGE).getText().equalsIgnoreCase("Advertise with us"));


        }else if (footerLink.equalsIgnoreCase("Terms and Conditions")){
            assertTrue("Actual URL displayed is: " + webBot.getCurrentUrl(), webBot.getCurrentUrl().contains("Help/TermsAndConditions"));
            assertTrue("Actual header displayed is: " +webBot.findElement(FOOTER_HEADER_ELEMENT).getText(), webBot.findElement(FOOTER_HEADER_ELEMENT).getText().equalsIgnoreCase("Terms and Conditions"));
        }

    }
}
