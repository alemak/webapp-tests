package com.netaporter.pws.automation.shared.pojos;

import com.netaporter.test.client.product.pojos.SearchableProduct;

public class Product implements Comparable {

    private SearchableProduct searchableProduct;
    private String sku;

    public Product(SearchableProduct searchableProduct, String sku) {
        this.searchableProduct = searchableProduct;
        this.sku = sku;
    }

    public SearchableProduct getSearchableProduct() {
        return searchableProduct;
    }

    public void setSearchableProduct(SearchableProduct searchableProduct) {
        this.searchableProduct = searchableProduct;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    //@Override
    public int compareTo(Object o) {
        Product p = (Product) o;
        return this.getSku().compareTo(p.getSku());
    }

    public String getPid() {

        if (searchableProduct != null && searchableProduct.getId() != null) {
            return searchableProduct.getId() + "";
        }

        return sku.split("-")[0];
    }
}
