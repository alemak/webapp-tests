package com.netaporter.pws.automation.shared.pages;

import com.netaporter.test.utils.pages.IPage;
import com.netaporter.test.utils.pages.IPageRegistryListener;
import com.netaporter.test.utils.pages.PageRegistry;
import com.netaporter.test.utils.pages.driver.WebDriverUtil;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component("listingPageRegistry")
@Scope("cucumber-glue")
public class ListingPageRegistry implements IPageRegistryListener {
    static Logger logger  = Logger.getLogger(ListingPageRegistry.class);

    @Autowired
    protected
    PageRegistry pageRegistry;

    @Autowired
    protected
    WebDriverUtil webBot;

    private Map<String, IProductListPage> pageNameToProductListPageObject = new HashMap<String, IProductListPage>();

    private List<IProductListPage> multipleSizeProductListPageObjects = new ArrayList<IProductListPage>();
    private static final String PYTHON_BAG_SEARCH_PAGE = "Pyhton Bag Search Results";

    public enum ListingPageType {DESIGNER, CATEGORY, CUSTOM, WHATS_NEW, BOUTIQUES, SEARCH, SPORT, SALE}

    @PostConstruct
    public void registerPage() {
        pageRegistry.setPageRegistryListener(this);
    }

    @Override
    public void pageAdded(String name, IPage page) {
        if (page instanceof IProductListPage) {
            pageNameToProductListPageObject.put(name, (IProductListPage) page);

            IProductListPage iProductListPage = (IProductListPage) page;
            if (iProductListPage.isMultipleSize()) {
                multipleSizeProductListPageObjects.add(iProductListPage);
            }
        }
    }

    public IProductListPage lookupProductListPage(String pageName) {
        IProductListPage page = pageNameToProductListPageObject.get(pageName);
        if (page == null) {
            throw new RuntimeException("Unable to find Page Object for Product List Page Name: " + pageName);
        }
        return page;
    }

    public IProductListPage anyProductListPage() {
        List<IProductListPage> productListPages =
                new ArrayList<IProductListPage>(pageNameToProductListPageObject.values());

        removePythonSearchPage(productListPages);
        int c = 0;
        Collections.shuffle(productListPages);
        for(IProductListPage page:productListPages){
            if(c++ >= 10) break;
            logger.debug("Opening page "+ page.getPageName());
            page.go();
            try{
                if(page.getNumberOfResultsFromHeader()>5){
                    return page;
                }
            }catch (PageElementNotFoundException penfe){
                logger.debug("No Products Found On: " + page.getPageName() + " page. Will try another listing page.");
            }
        }

        throw new RuntimeException("No Listing Page Found");
    }

    private void removePythonSearchPage(List<IProductListPage> productListPages) {
        for (IProductListPage productListPage : productListPages) {
            if (productListPage.getPageName().equals(PYTHON_BAG_SEARCH_PAGE)) {
                productListPages.remove(productListPage);
                break;
            }
        }
    }

    public IProductListPage anyMultipleSizedProductListPage() {
        Random rand = new Random();
        int index = rand.nextInt(multipleSizeProductListPageObjects.size());
        return multipleSizeProductListPageObjects.get(index);
    }

    public IProductListPage getAnyProductListingPageExceptDesigner() {
        List<IProductListPage> productListPages =
                new ArrayList<IProductListPage>(pageNameToProductListPageObject.values());

        for (IProductListPage productListPage : productListPages) {
            if (productListPage.getPageName().equals(PYTHON_BAG_SEARCH_PAGE)) {
                productListPages.remove(productListPage);
                break;
            }
        }
        int numberOfPages = productListPages.size();
        Random rand = new Random();
        int index = rand.nextInt(numberOfPages);
        return productListPages.get(index);
    }
}