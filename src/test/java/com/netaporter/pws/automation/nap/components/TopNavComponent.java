package com.netaporter.pws.automation.nap.components;

import com.netaporter.test.utils.pages.component.AbstractPageComponent;
import com.netaporter.test.utils.pages.driver.WaitTime;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by ocsiki on 20/06/2014.
 */

@Component
@Scope("cucumber-glue")
public class TopNavComponent extends AbstractPageComponent {

    private By TOP_NAV_LOCATOR = By.id("top-nav-btn-links");
    private By TOP_NAV_DROPDOWN_LOCATOR = By.className("dd-wrapper");

    private By WHATS_NEW_LOCATOR = By.id("whats");
    private By SPORTER_LOCATOR = By.id("sport");
    private By CLOTHING_LOCATOR = By.id("clothing");
    private By BEAUTY_LOCATOR = By.className("beauty");
    private By LINGERIE_LOCATOR = By.className("lingerie");
    private By SALE_BUTTON_LOCATOR = By.xpath(".//*[@id='sale-btn']");

    private By FIRST_LINK_IN_WHATS_NEW_DROPDOWN_LOCATOR = By.xpath(".//*[@id='whats']/div/div[1]/div/div/div[1]/div[2]/ul/li[1]/a");
    private By EXCLUSIVES_IN_CLOTHING_DROPDOWN_LOCATOR = By.xpath(".//*[@id='clothing']/div/div[1]/div/div/div[1]/div[2]/ul/li[2]/a");


    public boolean isTopNavDropDownDisplayed(String category) {
        WebElement topNavElement = webBot.findElement(By.id(category.toLowerCase()));
        WebElement topNavDropDown = topNavElement.findElement(By.className("dd-wrapper"));
        return topNavDropDown.isDisplayed();
    }

    public boolean isSaleDisplayedInTopNav() {
        return isLinkDisplayedInTopNav(SALE_BUTTON_LOCATOR);
    }

    private WebElement getTopNavElement() {
        return webBot.findElement(TOP_NAV_LOCATOR, WaitTime.FOUR);
    }

    public boolean isLingerieDisplayedInTopNav() {
        return isLinkDisplayedInTopNav(LINGERIE_LOCATOR);
    }
    public boolean isClothingDisplayedInTopNav() {
        return isLinkDisplayedInTopNav(CLOTHING_LOCATOR);
    }

    public boolean isBeautyLinkDisplayedInTopNav() {
        return isLinkDisplayedInTopNav(BEAUTY_LOCATOR);
    }

    public boolean isWhatsNewDisplayedInTopNav() {
        return isLinkDisplayedInTopNav(SPORTER_LOCATOR);
    }

    private boolean isLinkDisplayedInTopNav(By linkLocator) {
        try {
            getTopNavElement().findElement(linkLocator);
        }
        catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public void checkThatLinkIsBetweenPrevLinkAndNextLinkInTopNav(String linkName, String linkNamePrevious, String linkNameNext) {
        final List<WebElement> topNavLinks = webBot.findElement(TOP_NAV_LOCATOR).findElements(By.xpath("li"));
        boolean linkExist = false;
        for (int i =0;i<topNavLinks.size();i++){
            final String id = extractIdFromLinkElement(topNavLinks.get(i));
            if (linkName.equalsIgnoreCase(id)) {
                final int previousLinkIndex = i - 1;
                final int nextLinkIndex = i + 1;
                if ("the_left_edge".equalsIgnoreCase(linkNamePrevious)){
                    assertFalse("The " + linkName + " link is not the first element in the TopNav bar", previousLinkIndex >= 0);
                }
                else {
                    assertTrue("There is no element before "+linkName, previousLinkIndex >=0);
                    assertThat(linkNamePrevious.toLowerCase(), is(extractIdFromLinkElement(topNavLinks.get(previousLinkIndex))));
                }
                assertThat(linkNameNext.toLowerCase(), is(extractIdFromLinkElement(topNavLinks.get(nextLinkIndex))));
                linkExist = true;
            }
        }
        assertTrue(linkName+" does not exist in the Top Nav bar", linkExist);
    }


    private String extractIdFromLinkElement(WebElement topNavLink) {
        return topNavLink.getAttribute("id");
    }

    public boolean isLinkRemovedFromTopNav(String linkName) {
        for (WebElement topNavLink : webBot.findElement(TOP_NAV_LOCATOR).findElements(By.xpath("li"))) {
            if (extractIdFromLinkElement(topNavLink).equalsIgnoreCase(linkName))
                return false;
        }
        return true;
    }

    public void clickLinkInClothingDropdown() {
        webBot.findElement(EXCLUSIVES_IN_CLOTHING_DROPDOWN_LOCATOR, WaitTime.FOUR).click();
    }

    public void clickFirstLinkInWhatsNewTopNavDropdwn() {
        webBot.findElement(FIRST_LINK_IN_WHATS_NEW_DROPDOWN_LOCATOR).click();
    }

    public boolean isSporterDisplayedInTopNav() {
        return isLinkDisplayedInTopNav(SPORTER_LOCATOR);
    }
}