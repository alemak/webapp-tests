package com.netaporter.pws.automation.shared.pages.purchasePath;

import com.netaporter.pws.automation.shared.pojos.UserOption;

import com.netaporter.test.utils.pages.AbstractPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class PurchasePathShippingMethodPage extends AbstractPage {

    public PurchasePathShippingMethodPage() {
        super("Purchase path shipping method", "");
    }

    public void clickEditShippingAddress() {
        webBot.findElement(By.xpath("//a[@title='Edit Shipping Address']")).click();
    }

    public void clickProceedToPurchase() {
        webBot.findElement(By.name("_eventId_proceedToPurchase")).click();
    }

    // SHIPPING OPTION TYPES+++++++++++++++++++++++++++++++++++++++++++++++++

    public List<String> getAllShippingOptionTypes() {
        List<String> optionTypes = new ArrayList<String>();
        WebElement shippingMethodDiv = webBot.findElement(By.id("shipping_method"));

        List<WebElement> xs = shippingMethodDiv.findElements(By.cssSelector("h4"));
        for(WebElement x : xs)
            optionTypes.add(x.getText().trim());

        return optionTypes;
    }

    // SHIPPING OPTION TYPES+++++++++++++++++++++++++++++++++++++++++++++++++

    // SHIPPING OPTIONS ++++++++++++++++++++++++++++++++++++++++++++++++++

    public String getSelectedShippingOption() {
        String ret = null;

        for(UserOption o : getShippingOptions()) {
            if(o.isSelected()) {
                ret = o.getOptionName();
            }
        }

        return ret;
    }

    public void setSelectedShippingOption(String optionName) {
        boolean found = false;
        for(UserOption o : getShippingOptions()) {
            if(o.getOptionName().startsWith(optionName)) {
                webBot.findElement(By.id(o.getDescription())).click();
                found = true;
            }
        }

        if(!found) {
            throw new RuntimeException("Shipping option " + optionName + " not found on page");
        }
    }

    public List<UserOption> getShippingOptions() {
        List<UserOption> shippingOptions = new ArrayList<UserOption>();

        WebElement shippingDiv = webBot.findElement(By.id("shipping_method"));
        List<WebElement> shippingOptionRows = shippingDiv.findElements(By.cssSelector("div.field_row"));
        for(WebElement e : shippingOptionRows) {
            String option = e.findElement(By.cssSelector("label")).getText();
            String checked = e.findElement(By.cssSelector("input")).getAttribute("checked");
            String inputId = e.findElement(By.cssSelector("input")).getAttribute("id");
            String cost = e.findElement(By.className("price")).getText();
            boolean selected = (checked != null) ? true : false;
            shippingOptions.add(new UserOption(option, selected, inputId, null, cost));
        }

        return shippingOptions;
    }

    public String getSelectedShippingMethodSKU() {
        return webBot.findElement(By.name("selectedShippingMethodId")).getAttribute("value");
    }

    public String getShippingNotes() {
        return webBot.findElement(By.className("notes")).getText();
    }

    // SHIPPING OPTIONS +++++++++++++++++++++++++++++++++++++++++++++++++++

    // PACKAGING OPTIONS +++++++++++++++++++++++++++++++++++++++++++++++++++
    public List<String> getAvailablePackagingOptions() {
        List<String> values = new ArrayList<String>();
        for(UserOption o : getPackagingOptions())
            values.add(o.getOptionName());

        return values;
    }

    public List<UserOption> getAvailablePackagingUserOptions() {
        return getPackagingOptions();
    }

    public String getSelectedPackagingOption() {
        String ret = null;

        for(UserOption o : getPackagingOptions()) {
            if(o.isSelected()) {
                ret = o.getOptionName();
            }
        }

        return ret;
    }

    public void setSelectedPackagingOption(String optionName) {
        boolean found = false;
        for(UserOption o : getPackagingOptions()) {
            if(o.getOptionName().startsWith(optionName)) {
                webBot.findElement(By.id(o.getDescription())).click();
                found = true;
            }
        }

        if(!found) {
            throw new RuntimeException("Packaging option " + optionName + " not found on page");
        }
    }

    private List<UserOption> getPackagingOptions() {
        List<UserOption> packagingOptions = new ArrayList<UserOption>();

        WebElement packagingDiv = webBot.findElement(By.id("packaging_options_info"));
        List<WebElement> packagingOptionRows = packagingDiv.findElements(By.cssSelector("div.packaging_option"));
        for(WebElement e : packagingOptionRows) {
            String option = e.findElement(By.tagName("label")).getText();
            String checked = e.findElement(By.tagName("input")).getAttribute("checked");
            String inputId = e.findElement(By.tagName("input")).getAttribute("id");
            String url = e.findElement(By.tagName("img")).getAttribute("src");
            boolean selected = (checked != null) ? true : false;
            packagingOptions.add(new UserOption(option, selected, inputId, url));
        }

        return packagingOptions;
    }
    // PACKAGING OPTIONS +++++++++++++++++++++++++++++++++++++++++++++++++++

}