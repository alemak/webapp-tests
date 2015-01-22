package com.netaporter.pws.automation.napmobile.components.header;

import com.netaporter.pws.automation.napmobile.components.AbstractNapMobilePageComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("cucumber-glue")
public class HeaderMobileComponent extends AbstractNapMobilePageComponent {

    /* Page Objects
    --------------------------------------*/
    By byHeaderLogoLink = By.cssSelector(".logo a");

    By byHeaderLogoLinkImage = By.id(".logo a img");

    By byHeaderBasketQuantity = By.cssSelector("#header-basket a:nth-child(1)");

    By byHeaderBasketLink = By.cssSelector("#header-basket a:nth-child(2)");

    By byHeaderBasketLinkImage = By.cssSelector("#header-basket a:nth-child(2) img");

    By byMobileTopNavLinks = By.cssSelector("#mobile-top-nav ul li a");

    private By HEADER_SHOPPING_BASKET_ELEMENT = By.className("");

    /* Constructor
    --------------------------------------*/
    HeaderMobileComponent() {}

    /* Getters
    --------------------------------------*/
    public By getByHeaderLogoLink() {
        return byHeaderLogoLink;
    }
    public WebElement getHeaderLogoLink() {
        return webBot.findElement(getByHeaderLogoLink());
    }

    public By getByHeaderLogoLinkImage() {
        return byHeaderLogoLinkImage;
    }
    public WebElement getHeaderLogoLinkImage() {
        return webBot.findElement(getByHeaderLogoLinkImage());
    }

    public By getByHeaderBasketQuantity() {
        return byHeaderBasketQuantity;
    }

    public WebElement getHeaderBasketQuantity() {
        return webBot.getDriver().findElement(getByHeaderBasketQuantity());
    }

    public By getByHeaderBasketLink() {
        return byHeaderBasketLink;
    }
    public WebElement getHeaderBasketLink() {
        return webBot.getDriver().findElement(getByHeaderBasketLink());
    }

    public By getByHeaderBasketLinkImage() {
        return byHeaderBasketLinkImage;
    }

    public WebElement getHeaderBasketLinkImage() {
        return webBot.getDriver().findElement(getByHeaderBasketLinkImage());
    }

    public By getByMobileTopNavLinks() {
        return byMobileTopNavLinks;
    }

    public List<WebElement> getMobileTopNavLinks() {
        return webBot.getDriver().findElements(getByMobileTopNavLinks());
    }


}
