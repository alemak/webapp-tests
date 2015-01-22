package com.netaporter.pws.automation.napmobile.components.productlist;

import com.netaporter.pws.automation.napmobile.components.AbstractNapMobilePageComponent;
import com.netaporter.pws.automation.napmobile.util.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Scope("cucumber-glue")
public class FilterMobileComponent extends AbstractNapMobilePageComponent {

    /* Page Objects
    --------------------------------------*/
    By byAccordionLinkHeading = By.cssSelector(".accordion-link h2");

    By byAccordionControl = By.className("mobile-accordion-control");

    By byAccordionContent = By.className("accordion-content");

    By byCategorySelect = By.id("category-select");

    By byFilterApply = By.id("designer-select-submit");

    By byFilterReset = By.cssSelector("#reset a");

    By byColourSelect = By.id("colour-select");

    By byDesignerSelect = By.id("designer-select");

    By bySizeSelect = By.id("size-select");

    /* Constructor
    --------------------------------------*/
    public FilterMobileComponent() { }

    /* Getters
    --------------------------------------*/
    public By getByAccordionLinkHeading() {
        return byAccordionLinkHeading;
    }

    public WebElement getAccordionLinkHeading() {
        return webBot.getDriver().findElement(getByAccordionLinkHeading());
    }

    public By getByAccordionControl() {
        return byAccordionControl;
    }

    public WebElement getAccordionControl() {
        return webBot.getDriver().findElement(getByAccordionControl());
    }

    public By getByAccordionContent() {
        return byAccordionContent;
    }

    public WebElement getAccordionContent() {
        return webBot.getDriver().findElement(getByAccordionContent());
    }

    public By getByFilterApply() {
        return byFilterApply;
    }

    public WebElement getFilterApply() {
        return webBot.getDriver().findElement(getByFilterApply());
    }

    public By getByFilterReset() {
        return byFilterReset;
    }

    public WebElement getFilterReset() {
        return webBot.getDriver().findElement(getByFilterReset());
    }

    public By getByCategorySelect() {
        return byCategorySelect;
    }
    public Select getCategorySelect() {
        return new Select(webBot.getDriver().findElement(getByCategorySelect()));
    }

    public By getByColourSelect() {
        return byColourSelect;
    }

    public Select getColourSelect() {
        return new Select(webBot.getDriver().findElement(getByColourSelect()));
    }

    public By getByDesignerSelect() {
        return byDesignerSelect;
    }

    public Select getDesignerSelect() {
        return new Select(webBot.getDriver().findElement(getByDesignerSelect()));
    }

    public By getBySizeSelect() {
        return bySizeSelect;
    }

    public Select getSizeSelect() {
        return new Select(webBot.getDriver().findElement(getBySizeSelect()));
    }

    /* Helpers
    --------------------------------------*/
    public String accordionLinkHeaderText() {
        return getAccordionLinkHeading().getText().trim();
    }

    public String accordionControlText() {
        WebElement accordionControl = getAccordionControl();
        WebDriverWait wait = new WebDriverWait(webBot.getDriver(), 30);
        wait.until(ExpectedConditions.visibilityOf(accordionControl));
        return accordionControl.getText().trim();
    }

    public void accordionControlClick() {
        WebElement accordionControl = getAccordionControl();
        WebDriverWait wait = new WebDriverWait(webBot.getDriver(), 30);
        wait.until(ExpectedConditions.elementToBeClickable(accordionControl));
        accordionControl.click();
    }

    public boolean accordionContentDisplayed() {
        return getAccordionContent().isDisplayed();
    }

    public void filterApply() {
        getFilterApply().submit();
        webBot.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(webBot.getDriver(), 30);
        wait.until(ExpectedConditions.stalenessOf(getFilterApply()));
        webBot.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void filterResetClick() {
        getFilterReset().click();
        webBot.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(webBot.getDriver(), 30);
        wait.until(ExpectedConditions.stalenessOf(getFilterReset()));
        webBot.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public int categoryFilterSelectOptionsSize() {
        return getCategorySelect().getOptions().size();
    }

    public List categoryFilterSelectOptionsContainValidCategoryValues() {
        List<Category> enumCategories = Arrays.asList(Category.values());
        List<String> stringCategories = new ArrayList<String>();
        for (Category c : enumCategories) {
            stringCategories.add(c.toString());
        }
        List<String> invalid = new ArrayList<String>();
        int s = categoryFilterSelectOptionsSize();
        for (int i = 0; i < s; i++) {
            String t = getCategorySelect().getOptions().get(i).getText().trim();
            if ( ! stringCategories.contains(t)) invalid.add(t);
        }
        return invalid;
    }

    public List<String> categoryFilterAllSelectedOptionText() {
        WebDriverWait wait = new WebDriverWait(webBot.getDriver(), 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(getByCategorySelect()));
        List<WebElement> options = getCategorySelect().getAllSelectedOptions();
        List<String> optionsText = new ArrayList<String>();
        for (WebElement option : options) {
            optionsText.add(option.getText().trim());
        }
        return optionsText;
    }

    public void categoryFilterSelectOptionByVisibleText(String value) {
        getCategorySelect().selectByVisibleText(value);
    }

    public void applySubListParentFilterOptionByVisibleText(String subListParent) {
        categoryFilterSelectOptionByVisibleText(subListParent);
        filterApply();
        if (webBot.getDriver().getWindowHandles().size() > 1) {
            webBot.getDriver().switchTo().window("1");
        }
    }

    public void applySubListSiblingFilterOptionByVisibleText(String subListSibling) {
        categoryFilterSelectOptionByVisibleText(subListSibling);
        filterApply();
        if (webBot.getDriver().getWindowHandles().size() > 1) {
            webBot.getDriver().switchTo().window("1");
        }
    }

    public void applySubListChildFilterOptionByVisibleText(String subListChild) {
        String space = " ";
        String prefix = space + space;
        categoryFilterSelectOptionByVisibleText(prefix + subListChild);
        filterApply();
        if (webBot.getDriver().getWindowHandles().size() > 1) {
            webBot.getDriver().switchTo().window("1");
        }
    }

    public void applySubListFilterOptionByVisibleText(String subList) {
        categoryFilterSelectOptionByVisibleText(subList);
        filterApply();
        if (webBot.getDriver().getWindowHandles().size() > 1) {
            webBot.getDriver().switchTo().window("1");
        }
    }

    public void applyCategoryFilterOptionByVisibleText(Category category) {
        categoryFilterSelectOptionByVisibleText(category.toString());
        filterApply();
        if (webBot.getDriver().getWindowHandles().size() > 1) {
            webBot.getDriver().switchTo().window("1");
        }
    }

    public void colourFilterDeselectAllOptions() {
        getColourSelect().deselectAll();
    }

    public void colourFilterSelectOptionByIndex(List<Integer> options) {
        for (Integer option : options) {
            getColourSelect().selectByIndex(option);
        }
    }

    public List<String> colourFilterGetAllSelectedOptionsText() {
        List<String> colours = new ArrayList<String>();
        List<WebElement> options = getColourSelect().getAllSelectedOptions();
        for (WebElement option : options) {
            colours.add(option.getText());
        }
        return colours;
    }

    public List<String> applyColourFilterOptionByIndex(List<Integer> options) {
        colourFilterSelectOptionByIndex(options);
        List<String> colours = colourFilterGetAllSelectedOptionsText();
        filterApply();
        if (webBot.getDriver().getWindowHandles().size() > 1) {
            webBot.getDriver().switchTo().window("1");
        }
        return colours;
    }

    public int colourFilterSelectOptionsSize() {
        return getColourSelect().getOptions().size();
    }

    public boolean colourFilterIsMultipleSelect() {
        return getColourSelect().isMultiple();
    }

    public String colourFilterFirstSelectedOptionText() {
        return getColourSelect().getFirstSelectedOption().getText().trim();
    }

    public Collection<String> coloursInUrl() {
        String url = webBot.getCurrentUrl();
        String[] filter = url.split("colourFilter=");
        String[] urlColours = filter[1].split(";");
        Collection<String> colours = new HashSet<String>();
        for (String urlColour : urlColours) {
            String colour = urlColour.replace("_", " ");
            colours.add(colour);
        }
        return colours;
    }

    public boolean onlySelectedColoursAreInUrl(Collection<String> selectedColours) {
        Collection<String> urlColours = coloursInUrl();
        return selectedColours.equals(urlColours);
    }

    public void designerFilterDeselectAllOptions() {
        getDesignerSelect().deselectAll();
    }

    public void designerFilterSelectOptionByIndex(List<Integer> options) {
        for (Integer option : options) {
            getDesignerSelect().selectByIndex(option);
        }
    }

    public List<String> designerFilterGetAllSelectedOptionsText() {
        List<String> designers = new ArrayList<String>();
        List<WebElement> options = getDesignerSelect().getAllSelectedOptions();
        for (WebElement option : options) {
            designers.add(option.getText());
        }
        return designers;
    }

    public List<String> applyDesignerFilterOptionByIndex(List<Integer> options) {
        designerFilterSelectOptionByIndex(options);
        List<String> designers = designerFilterGetAllSelectedOptionsText();
        filterApply();
        if (webBot.getDriver().getWindowHandles().size() > 1) {
            webBot.getDriver().switchTo().window("1");
        }
        return designers;
    }

    public int designerFilterSelectOptionsSize() {
        return getDesignerSelect().getOptions().size();
    }

    public boolean designerFilterIsMultipleSelect() {
        return getDesignerSelect().isMultiple();
    }

    public String designerFilterFirstSelectedOptionText() {
        return getDesignerSelect().getFirstSelectedOption().getText().trim();
    }

    public String sizeFilterFirstSelectedOptionText() {
        return getSizeSelect().getFirstSelectedOption().getText().trim();
    }

    public int sizeFilterSelectOptionsSize() {
        return getSizeSelect().getOptions().size();
    }

    public boolean sizeFilterIsMultipleSelect() {
        return getSizeSelect().isMultiple();
    }

    public void sizeFilterDeselectAllOptions() {
        getSizeSelect().deselectAll();
    }

    public void sizeFilterSelectOptionByIndex(List<Integer> options) {
        for (Integer option : options) {
            getSizeSelect().selectByIndex(option);
        }
    }

    public List<String> sizeFilterGetAllSelectedOptionsText() {
        List<String> sizes = new ArrayList<String>();
        List<WebElement> options = getSizeSelect().getAllSelectedOptions();
        for (WebElement option : options) {
            sizes.add(option.getText());
        }
        return sizes;
    }

    public List<String> applySizeFilterOptionByIndex(List<Integer> options) {
        sizeFilterSelectOptionByIndex(options);
        List<String> sizes = sizeFilterGetAllSelectedOptionsText();
        filterApply();
        if (webBot.getDriver().getWindowHandles().size() > 1) {
            webBot.getDriver().switchTo().window("1");
        }
        return sizes;
    }

    public Collection<String> sizesInUrl() {
        String url = webBot.getCurrentUrl();
        String[] filter = url.split("sizeFilter=");
        String[] urlSizes = filter[1].split(";");
        Collection<String> sizes = new HashSet<String>();
        for (String urlSize : urlSizes) {
            String[] pieces = urlSize.split("_");
            sizes.add(pieces[1]);
        }
        return sizes;
    }

    public boolean onlySelectedSizesAreInUrl(Collection<String> selectedSizes) {
        Collection<String> urlSizes = sizesInUrl();
        return selectedSizes.equals(urlSizes);
    }
}
