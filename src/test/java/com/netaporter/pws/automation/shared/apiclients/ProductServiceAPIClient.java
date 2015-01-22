package com.netaporter.pws.automation.shared.apiclients;

import com.netaporter.productservice.solr.client.SolrClient;
import com.netaporter.test.client.product.dsl.ProductDsl;
import com.netaporter.test.client.product.dsl.ProductSearchCriteria;
import com.netaporter.test.utils.dataaccess.database.LegacyWebAppProductDatabaseClient;
import com.netaporter.test.utils.enums.SalesChannelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: J.Christian@net-a-porter.com
 * Date: 03/09/2013
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ProductServiceAPIClient {

    @Autowired
    private SolrClient solrClient;

    public static final SalesChannelEnum getNAPSalesChannel(String region) {
        SalesChannelEnum channel = SalesChannelEnum.NAP_INTL;
        if(region.equals("AM")) {
            channel = SalesChannelEnum.NAP_AM;
        } else if (region.equals("APAC")) {
            channel = SalesChannelEnum.NAP_APAC;
        }
        return channel;
    }

    public List<String> getInStockAndVisibleSkus(String region, Integer numberOfSkus, ProductDsl.ProductAvailability productAvailability,
                                                 ProductDsl.ProductCategory category) throws Throwable {
        return getInStockAndVisibleSkus(region, numberOfSkus, productAvailability, category, Collections.EMPTY_LIST);
    }


        public List<String> getInStockAndVisibleSkus(String region, Integer numberOfSkus, ProductDsl.ProductAvailability productAvailability,
                                         ProductDsl.ProductCategory category, List<String> ignoreSkus) throws Throwable {
        SalesChannelEnum channel = getNAPSalesChannel(region);

        ProductSearchCriteria criteria = ProductSearchCriteria
                .availabilities(new ProductDsl.ProductChannelAvailability(channel, productAvailability))
                .inCategory(category)
                .ignoreSkus(ignoreSkus)
                .maxResults(numberOfSkus)
                .ensureDbAndPsStockAgree(false);

            List<String> skuList = solrClient.findSkus(criteria);

        for(int i=0; i < skuList.size(); i++) {
            System.out.println(category + ":" + (i+1) + "/" + numberOfSkus + " = " + skuList.get(i));
        }

        return skuList;
    }


    public String getSKUWithChannelAvailability(ProductDsl.ProductCategory category,
                                                List<String> ignoreSkus,
                                                ProductDsl.ProductChannelAvailability... productAvailabilities) throws Throwable {


        ProductSearchCriteria criteria = ProductSearchCriteria
                .availabilities(productAvailabilities)
                .inCategory(category)
                .ignoreSkus(ignoreSkus)
                //.maxResults(numberOfSkus)
                .ensureDbAndPsStockAgree(false);

        return solrClient.findSku(criteria);
    };



}
