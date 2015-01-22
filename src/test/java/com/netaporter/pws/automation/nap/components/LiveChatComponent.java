package com.netaporter.pws.automation.nap.components;

import com.google.common.base.Predicate;
import com.netaporter.test.utils.pages.component.AbstractPageComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.junit.Assert.fail;

/**
 * Created by s.joshi on 18/09/2014.
 */

@Component
@Scope("cucumber-glue")


public class LiveChatComponent extends AbstractPageComponent {

    private String FORCE_CHAT_PARAMETER_1 = "&ForcePHL=yes&forceInvite=yes";
    private String FORCE_CHAT_PARAMETER_2 = "?ForcePHL=yes&forceInvite=yes";
    private By START_CHAT_ELEMENT = By.cssSelector(".action-button.start-chat-button");
    private By CLOSE_CHAT_ELEMENT = By.xpath(".//*[@id='chat-window']/div[2]/div/div[3]");
    private By CONFIRM_CLOSE_BUTTON_YES = By.xpath(".//*[@id='chat-window']/div[3]/div[1]/div/div[2]/input[1]");
    private By CONFIRM_CLOSE_BUTTON_NO= By.xpath(".//*[@id='chat-window']/div[3]/div[1]/div/div[2]/input[2]");
    private By CHAT_SESSION_WINDOW_LOCATOR = By.className("chat-section");
    private By CHAT_TEXT_BOX_LOCATOR = By.className("chat-input");
    private By CHAT_MINIMISE_ICON = By.className("dynamic-button");
    private By CHAT_MINIMISE_LINK = By.cssSelector(".minimise-button.header-button");
    private By CHAT_MINIMISE_BAR_LOCATOR =By.id("chat-invite");


    public void forceLiveChatToAppear() throws Throwable {
        String liveChatUrl;
        if (webBot.getCurrentUrl().contains("?")){
            liveChatUrl = webBot.getCurrentUrl() + FORCE_CHAT_PARAMETER_1;
        }else {
            liveChatUrl = webBot.getCurrentUrl() + FORCE_CHAT_PARAMETER_2;
        }
        webBot.getDriver().get(liveChatUrl);
        waitForElementToNotBeVisible("#chat-invite");
    }

    public void loadChatSession ()throws Throwable {
        webBot.click(START_CHAT_ELEMENT);
        Thread.sleep(1000);

    }

    public void forceAndLoadNewChatSession() throws Throwable{
        forceLiveChatToAppear();
        loadChatSession();
    }

    public void closeChatWindowButton() throws Throwable{
        webBot.click(CLOSE_CHAT_ELEMENT);
        Thread.sleep(500);
    }

    public void minimiseChatWindow() throws Throwable{
        webBot.click(CHAT_MINIMISE_LINK);
    }

    public void closeChatOptionsToCloseChat(String closeChatOptions) throws Throwable {
        if (closeChatOptions.equals("yes")) {
            webBot.click(CONFIRM_CLOSE_BUTTON_YES);
        } else if (closeChatOptions.equals("no")) {
            webBot.click(CONFIRM_CLOSE_BUTTON_NO);
        }
    }

    public boolean isComponentActivated(String component) {

        if (component.equals("chat session")) {
            waitSecondsForElementToBeVisible(CHAT_SESSION_WINDOW_LOCATOR,4);
            return webBot.findElement(CHAT_SESSION_WINDOW_LOCATOR).isDisplayed();

        } else if (component.equals("chat invite")) {
            return webBot.findElement(START_CHAT_ELEMENT).getAttribute("value").contains("Chat now");

        }else if (component.equals("confirm close")){
            return (webBot.findElement(CONFIRM_CLOSE_BUTTON_YES).isDisplayed() && webBot.findElement(CONFIRM_CLOSE_BUTTON_NO).isDisplayed());

        }return false;
    }

    public boolean isChatTextBoxVisible() throws InterruptedException {
//        Thread.sleep(1000);
        webBot.waitForJQueryCompletion();
        return webBot.findElement(CHAT_TEXT_BOX_LOCATOR).isDisplayed();
    }

    public  boolean isChatMinimised(){
        return (webBot.findElement(CHAT_MINIMISE_BAR_LOCATOR).getAttribute("class").contains("is-collapsed"));
    }

    public void waitSecondsForElementToBeVisible(By locator, int maxSeconds) {
        webBot.waitUntil(elementDisplayed(locator), maxSeconds);
    }

    private Predicate<WebDriver> elementDisplayed(final By locator) {
        return new Predicate<WebDriver>() {
            @Override public boolean apply(WebDriver driver) {
                return webBot.findElement(locator).isDisplayed();
            }
        };
    }

    public boolean isChatIconVisible() {
        return webBot.findElement(CHAT_MINIMISE_ICON,2).isDisplayed();
    }

    public void waitForElementToNotBeVisible(String cssSelector) throws Throwable{
        // Return fast if the element doesn't exist
        if (!webBot.exists(null, By.cssSelector(cssSelector))) {
            return;
        }

        System.out.println("Waiting for element to disappear "+cssSelector);
        for(int i = 0; i <= 100 ; i++) {
            // find it each time to prevent selenium reference going stale
            WebElement e = webBot.findElement(By.cssSelector(cssSelector));
            if (!e.isDisplayed()) {
                System.out.println("By is not visible");
                return;
            } else {
                Thread.sleep(100);
            }
            System.out.print(".");
        }
        fail("By did not become invisible: "+cssSelector);
    }
}
