package com.netaporter.pws.automation.nap.pages;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.netaporter.pws.automation.nap.components.PageNavigationComponent;
import com.netaporter.pws.automation.shared.pages.IProductListPage;
import com.netaporter.pws.automation.shared.pages.ListingPageRegistry;
import com.netaporter.pws.automation.shared.utils.Consts;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by a.makarenko on 7/24/14.
 */
@Component()
@Scope("cucumber-glue")
public class NapListingPageRegistry extends ListingPageRegistry {

    private static final List<String> CATEGORIES_LIST = Lists.newArrayList("Clothing", "Bags", "Shoes", "Accessories");
    private static final List<String> SALE_CATEGORIES_LIST = Lists.newArrayList("Clothing", "Bags", "Shoes", "Accessories", "Lingerie");
    private static final List<String> SPORT_CATEGORIES_LIST = Lists.newArrayList("All_Sportswear", "Gym/Crossfit", "Run", "Yoga/Dance", "Tennis", "Swim/Surf", "Equestrian", "Après", "Golf", "Accessories", "Outdoor");

    private static final List<String> CUSTOM_LISTS = Lists.newArrayList("Last_Week", "Back_In_Stock");
    private static final Map<String, String> BOUTIQUE_DETAILS_MAP = ImmutableMap.of("wedding", "To_Have_and_to_Hold", "vacation", "Day-to-night_Dressing", "Gifts", "Irresistible_Gifts");
    private static final List<String> SEARCH_LISTS = Lists.newArrayList("red","leather");
    private static final List<String> WHATSNEW_LISTS = Lists.newArrayList("Whats-New");

    public IProductListPage createAndGoToAProductListPage(ListingPageType pageType, String secondLevelCategory, boolean isMultiplePageRequired) {

        switch (pageType) {

            case DESIGNER:
                DesignerAToZPage designerAToZPage = (DesignerAToZPage) pageRegistry.lookupPage("designerAToZ");
                designerAToZPage.go();

                NAPProductListPage napProductListPage = designerAToZPage.gotoAnyDesignerListingPage(isMultiplePageRequired);
                pageAdded(napProductListPage.getPageName(), napProductListPage);

                return napProductListPage;

            case CUSTOM:
                final IProductListPage customListingPage = createAndGoToProductListPage(pageType.name(), CUSTOM_LISTS, "Shop/List/", null, isMultiplePageRequired);
                return customListingPage;

            case CATEGORY:
                final IProductListPage categoryListingPage = createAndGoToProductListPage(pageType.name(), CATEGORIES_LIST, "Shop/", secondLevelCategory, isMultiplePageRequired);
                return categoryListingPage;

            case BOUTIQUES:
                final IProductListPage boutiquesListingPage = createAndGoToProductListPage(pageType.name(), BOUTIQUE_DETAILS_MAP.values(), "Shop/List/", null, isMultiplePageRequired);
                return boutiquesListingPage;

            case SEARCH:
                final IProductListPage searchListingPage = createAndGoToProductListPage(pageType.name(), SEARCH_LISTS, "Shop/Search?keywords=", "red+leather", isMultiplePageRequired);
                return searchListingPage;

            case WHATS_NEW:
                final IProductListPage whatsNewListingPage = createAndGoToProductListPage(pageType.name(), WHATSNEW_LISTS, "Shop/", secondLevelCategory, isMultiplePageRequired);
                return whatsNewListingPage;

            case SALE:
                final IProductListPage saleListingPage = createAndGoToProductListPage(pageType.name(), SALE_CATEGORIES_LIST, "/Sale/", secondLevelCategory, isMultiplePageRequired);
                return saleListingPage;

            case SPORT:
                final IProductListPage sportListingPage = createAndGoToProductListPage(pageType.name(), SPORT_CATEGORIES_LIST, "Sport/", secondLevelCategory, isMultiplePageRequired);
                return sportListingPage;


            default:
                throw new RuntimeException("Unknown page type");
        }
    }

    private IProductListPage createAndGoToProductListPage(String listingPageType, Collection<String> listingPageCategories, String pathPrefix, String pathSuffix, boolean isMultiplePageRequired) {
        List<String> newPageNames = createShuffledList(listingPageCategories);

        for (String category : newPageNames) {
            IProductListPage productListPage;
            //if we have a suffix (the name of the category) we go to it, otherwise we go to any second level category
            if (pathSuffix==null) {
                productListPage = createAndGoToNapProductListPage(listingPageType, Consts.GENERIC_LISTING_PAGE_NAME, pathPrefix + category);
            }
            else {
                productListPage = createAndGoToNapProductListPage(listingPageType, Consts.GENERIC_LISTING_PAGE_NAME, pathPrefix +  pathSuffix);
            }
// View all is not displayed on the WhatsNew page for now, but will be revisited in the future
//            if (listingPageCategories.contains(WHATSNEW_LISTS.get(0)))
//                productListPage.getPageNavigationComponent().selectLink(PageNavigationComponent.PageLinkOption.VIEW_60);
//
//            if (!isMultiplePageRequired || productListPage.isMultiplePages()) {
//                if (listingPageCategories.contains(WHATSNEW_LISTS.get(0)))
//                    productListPage.getPageNavigationComponent().selectLink(PageNavigationComponent.PageLinkOption.VIEW_ALL);
            return productListPage;
//           }
        }
//        assumeTrue("Could not find required "+isMultiplePageRequired+" multiple page "+pageType+" listing page type", false);
        throw new IllegalStateException("Can't find required " + listingPageType + " listing page");
    }

    private IProductListPage createAndGoToNapProductListPage(String listingPageType, String pageName, String path) {

        if (ListingPageType.SALE.name().equalsIgnoreCase(listingPageType)) {
            NAPSaleListingPage productListPage = new NAPSaleListingPage(pageName, path, webBot);
            productListPage.setWebBot(webBot);
            productListPage.go();

            pageAdded(pageName, productListPage);
            return productListPage;
        }
        else if (ListingPageType.SPORT.name().equalsIgnoreCase(listingPageType) || ListingPageType.WHATS_NEW.name().equalsIgnoreCase(listingPageType)) {
            NAPAWSListingPage productListPage = new NAPAWSListingPage(pageName, path, webBot);
            productListPage.setWebBot(webBot);
            productListPage.go();

            pageAdded(pageName, productListPage);
            return productListPage;
        }
        else{
            NAPProductListPage productListPage = new NAPProductListPage(pageName, path, webBot);
            productListPage.setWebBot(webBot);
            productListPage.go();

            pageAdded(pageName, productListPage);
            return productListPage;
        }
    }

    private List<String> createShuffledList(Collection<String> pageNameList) {
        List<String> newPageNames = Lists.newArrayList(pageNameList);

        Collections.shuffle(newPageNames);
        return newPageNames;
    }

    private static String getARandomCustomList() {
        Collections.shuffle(CUSTOM_LISTS);
        return CUSTOM_LISTS.get(0);
    }

    private static String getAnRandomCategory() {
        Collections.shuffle(CATEGORIES_LIST);
        return CATEGORIES_LIST.get(0);
    }
}
