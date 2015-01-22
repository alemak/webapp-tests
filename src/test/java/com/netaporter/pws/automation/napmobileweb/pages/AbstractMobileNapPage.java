package com.netaporter.pws.automation.napmobileweb.pages;

import com.netaporter.test.utils.pages.AbstractPage;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import com.netaporter.test.utils.pages.regionalisation.RegionaliseWithRegionName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: J.Christian@net-a-porter.com
 * Date: 30/08/2013
 * Time: 09:16
 * To change this template use File | Settings | File Templates.
 */
public class AbstractMobileNapPage extends AbstractPage {


    public AbstractMobileNapPage(String pageName, String path) {
        super(pageName, path);
        setRegionalisePathBehavior(new RegionaliseWithRegionName());
    }

    public AbstractMobileNapPage(String pageName, String path, List<String> params) {
        super(pageName, path, params);
        setRegionalisePathBehavior(new RegionaliseWithRegionName());
    }

    public boolean isSignedIn() throws Throwable {
        waitForElementToExist(By.cssSelector("#header-inject-isauth[style=\"display: inline;\"]"));
        return true;
    }


    protected boolean doesElementExist(By element) {
        try{
            WebElement webElement = webBot.findElement(element);
            return webElement!=null;
        }catch (PageElementNotFoundException e){
            return false;
        }
    }

    /*
    Waits for a specific element to appear for up to 10 seconds (e.g. searchType=By.cssSelector(".popup-window"))
     */
    public void waitForElementToExist(By searchType) throws Throwable{
        System.out.println("Waiting for element to exist "+searchType);
        for(int i = 0; i <= 100 ; i++) {
            if (webBot.exists(null, searchType)) {
                System.out.println("Found");
                return;
            } else {
                Thread.sleep(100);
            }
            System.out.print(".");
        }
        fail("Could not find element: "+searchType);
    }

    /*
    Waits for a specific element to disappear for up to 10 seconds (e.g. searchType=By.cssSelector(".popup-window"))
     */
    public void waitForElementToNotExist(By searchType) throws Throwable{
        System.out.println("Waiting for element to not exist "+searchType);
        for(int i = 0; i <= 100 ; i++) {
            if (!(webBot.exists(null, searchType))) {
                System.out.println();
                return;
            } else {
                Thread.sleep(100);
            }
            System.out.print(".");
        }
        fail("By did not go away: "+searchType);
    }

}
