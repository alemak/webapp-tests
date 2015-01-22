package com.netaporter.pws.automation.napmobile.components.productlist;

import com.netaporter.pws.automation.napmobile.components.AbstractNapMobilePageComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class TitleMobileComponent extends AbstractNapMobilePageComponent {

    /* Page Objects
    --------------------------------------*/
    By byTitleMainHeading = By.id("main-heading");

    By byTitleHeading = By.cssSelector("#product-list-area .title h1");

    By byTitleSubHeading = By.cssSelector("#product-list-area .title h2");

    /* Constructor
    --------------------------------------*/
    public TitleMobileComponent() { }

    /* Getters
    --------------------------------------*/
    public By getByTitleHeading() {
        return byTitleHeading;
    }

    public WebElement getTitleHeading() {
        return webBot.getDriver().findElement(getByTitleHeading());
    }

    public By getByTitleMainHeading() {
        return byTitleMainHeading;
    }

    public WebElement getTitleMainHeading() {
        return webBot.getDriver().findElement(getByTitleMainHeading());
    }

    public By getByTitleSubHeading() {
        return byTitleSubHeading;
    }

    public WebElement getTitleSubHeading() {
        return webBot.getDriver().findElement(getByTitleSubHeading());
    }

    /* Helpers
    --------------------------------------*/
    public int extractNumbersFromText(String text) {
        String total = text.replaceAll("[^0-9]", "");
        if (total.isEmpty()) {
            return -1;
        } else {
            return Integer.parseInt(total);
        }
    }

    public int numberOfResultsFromHeading() {
        return extractNumbersFromText(getTitleHeading().getText());
    }

    public int numberOfResultsFromSubHeading() {
        return extractNumbersFromText(titleSubHeadingText());
    }

    public int numberOfResultsFromPage() {
        int heading = numberOfResultsFromHeading();
        if (heading == -1) {
            return numberOfResultsFromSubHeading();
        } else {
            return heading;
        }
    }

    public String titleMainHeadingText() {
        return getTitleMainHeading().getText().trim();
    }

    public String titleSubHeadingText() {
        return getTitleSubHeading().getText().trim();
    }

    public String titleSubListText() {
        String[] text = titleSubHeadingText().split("\\(");
        return text[0].trim();
    }
}
