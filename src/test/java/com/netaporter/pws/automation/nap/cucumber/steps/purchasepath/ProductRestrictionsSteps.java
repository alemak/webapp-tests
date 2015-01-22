package com.netaporter.pws.automation.nap.cucumber.steps.purchasepath;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.shared.pojos.Product;
import com.netaporter.pws.automation.shared.steps.purchasePath.BasePurchasePathStep;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: andres
 * Date: 07/03/13
 * Time: 15:03
 */
public class ProductRestrictionsSteps extends BasePurchasePathStep {

    public static final String HAZMAT_PRODUCTS_KEY = "hazmatProducts";

    protected void removeHazmatRestriction() {
        Set<ProductRestriction> hazmatProducts = (Set<ProductRestriction>) scenarioSession.getData(HAZMAT_PRODUCTS_KEY);

        if (hazmatProducts != null) {
            for(ProductRestriction productRestriction : hazmatProducts) {
                // TODO: Products don't actually get created properly further down the code, so we're getting pid from sku
                String pid = productRestriction.getProduct().getSku().split("-")[0];
                productDataAccess.getLegacyDBClient().derestrictProductAsHazmat(webBot.getRegionEnum(), pid, productRestriction.getCountryCode());
            }
        }
    }

    protected void saveHazmatRestrictionInScenarioSession(Product product, String country_code) {
        Set<ProductRestriction> products = (Set<ProductRestriction>) scenarioSession.getData(HAZMAT_PRODUCTS_KEY);
        if (products == null) {
            products = new HashSet<ProductRestriction>();
            scenarioSession.putData("hazmatProducts", products);
        }
        products.add(new ProductRestriction(product, country_code));
    }

    private class ProductRestriction {
        private Product product;
        private String country_code;

        public ProductRestriction(Product product, String country_code) {
            this.product = product;
            this.country_code = country_code;
        }

        public Product getProduct() {
            return this.product;
        }
        public String getCountryCode() {
            return this.country_code;
        }
    }
}
