package com.netaporter.pws.automation.nap.pages;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.netaporter.pws.automation.nap.util.WebElementDataExtractingFunctions;
import com.netaporter.pws.automation.shared.utils.Consts;
import com.netaporter.test.utils.pages.driver.WaitTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * User: x.qi@london.net-a-porter.com
 * Date: 07/05/2013
 */
@Component
@Scope("cucumber-glue")
public class DesignerAToZPage extends AbstractNapPage {

    private static final Set<String> DESIGNERS_WITH_MULTIPLE_PAGES_OF_PRODUCTS = Sets.newHashSet("Bottega Veneta", "Gucci", "Jimmy Choo", "Lanvin", "Miu Miu", "Saint Laurent", "Stella McCartney" );
    private By FIRST_DESIGNER_LINK_LOCATOR = By.xpath(".//*[@id='atoz-page-container']/div[2]/ul/li[9]/a");
    private By DESIGNER_LINKS_LOCATOR = By.xpath(".//*[@class='designer_list_col']/ul/li/a[@href]");

    public DesignerAToZPage() {
        super("designerAToZ", "Shop/AZDesigners");
    }

    public NAPProductListPage gotoAnyDesignerListingPage(boolean isMultiplePageRequired) {
        List<String> links = getDesignerLinks(isMultiplePageRequired);
        Collections.shuffle(links);

        for (String link : links) {
            NAPProductListPage napProductListPage = gotoDesignerListingPage(link);

            if (!isMultiplePageRequired || napProductListPage.isMultiplePages())     {
                if (!napProductListPage.isEmpty()) {
                    return napProductListPage;
                }
            }
        }
        throw new IllegalStateException("Can't find required designer listing page, so can't continue");
    }

    private List<String> getDesignerLinks(boolean multiplePageRequired) {
        List<WebElement> elements = webBot.findElements(DESIGNER_LINKS_LOCATOR, WaitTime.FOUR);

        List<String> allLinks = Lists.transform(elements, WebElementDataExtractingFunctions.extractHrefAttribute);

        if (multiplePageRequired) {
            return Lists.newArrayList(Iterables.filter(allLinks, bigDesignerFilter));
        }
        else {
            return Lists.newArrayList(allLinks);
        }
    }

    private NAPProductListPage gotoDesignerListingPage(String link) {
        int startingIndexOfRelativePath = link.indexOf("Shop");
        NAPProductListPage napProductListPage = new NAPProductListPage(Consts.GENERIC_LISTING_PAGE_NAME, link.substring(startingIndexOfRelativePath), webBot);

        napProductListPage.setWebBot(webBot);

        webBot.goToPage(napProductListPage);
        return napProductListPage;
    }

    private Predicate<String> bigDesignerFilter = new Predicate<String>() {
        public boolean apply(String link) {
            String designerBits = link.substring(link.lastIndexOf("/") + 1).replace("_", " ");

            return DESIGNERS_WITH_MULTIPLE_PAGES_OF_PRODUCTS.contains(designerBits);
        }
    };

    public void clickOnFirstDesignerInAZPage() {
        webBot.findElement(FIRST_DESIGNER_LINK_LOCATOR, WaitTime.FOUR).click();
    }
}