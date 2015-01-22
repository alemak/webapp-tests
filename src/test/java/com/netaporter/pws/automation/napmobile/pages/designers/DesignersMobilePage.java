package com.netaporter.pws.automation.napmobile.pages.designers;

import com.netaporter.pws.automation.napmobile.pages.AbstractNapMobilePage;
import com.netaporter.test.utils.pages.regionalisation.RegionaliseWithRegionName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Scope("cucumber-glue")
public class DesignersMobilePage extends AbstractNapMobilePage {

    private static final String NAME = "Designers Mobile";
    private static final String PATH = "Shop/AZDesigners";

    /* Page Objects
    --------------------------------------*/
    By byDesignersByCategorySelect = By.id("sub-navigation-category-nav");

    /* Constructor
    --------------------------------------*/
    public DesignersMobilePage() {
        super(NAME, PATH);
        setRegionalisePathBehavior(new RegionaliseWithRegionName());
    }

    /* Getters
    --------------------------------------*/
    public By getByDesignersByCategorySelect() {
        return byDesignersByCategorySelect;
    }

    public Select getDesignersByCategorySelect() {
        return new Select(webBot.getDriver().findElement(getByDesignersByCategorySelect()));
    }

    /* Helpers
    --------------------------------------*/
    public boolean at() {
        return titleMobileComponent.titleMainHeadingText().equals("DESIGNERS");
    }

    public void designersByCategorySelectOptionByVisibleText(String value) {
        WebElement designersByCategorySelect = webBot.getDriver().findElement(getByDesignersByCategorySelect());
        webBot.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        getDesignersByCategorySelect().selectByVisibleText(value);
        WebDriverWait wait = new WebDriverWait(webBot.getDriver(), 30);
        wait.until(ExpectedConditions.stalenessOf(designersByCategorySelect));
        webBot.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public String designersByCategorySelectFirstSelectedOptionText() {
        return getDesignersByCategorySelect().getFirstSelectedOption().getText().trim();
    }



}
