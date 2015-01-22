package com.netaporter.pws.automation.nap.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: o_csiki
 * Date: 13/09/13
 */

@Component
@Scope("cucumber-glue")
public class MyFavouriteDesignersPage extends AbstractNapPage {

    private static final String PAGE_NAME = "My Favourite Designers";
    private static final String PATH = "customerpreferences.nap";

    private By CHOSEN_DESIGNERS_ELEMENTS = By.name("choosenDesigners");
    private By SUBMIT_BUTTON_LOCATOR = By.xpath(".//*[@id='updatedesigner']/div[2]/input[1]");

    public MyFavouriteDesignersPage() {
        super(PAGE_NAME, PATH);
    }

    public List<WebElement> getAllListedDesignerElements() {
        return webBot.findElements(CHOSEN_DESIGNERS_ELEMENTS);
    }

    public List<String> getSelectedDesignerName() {
        List<WebElement> allListedDesignerElements = getAllListedDesignerElements();
        List<String> allSelectedDesignerNames = new ArrayList<String>();

        for (WebElement allListedDesignerElement : allListedDesignerElements) {
            if (allListedDesignerElement.isSelected()) {
                allSelectedDesignerNames.add(allListedDesignerElement.findElement(By.xpath("../label")).getText());
            }
        }

        if (allSelectedDesignerNames.size()!=0)
            return allSelectedDesignerNames;
        else throw new IllegalStateException("There is no selected designer in the Favourite Designer list.");
    }

    public void clickSubmitButton() {
        webBot.click(SUBMIT_BUTTON_LOCATOR);
    }
}
