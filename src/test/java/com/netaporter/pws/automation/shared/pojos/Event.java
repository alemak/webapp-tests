package com.netaporter.pws.automation.shared.pojos;

import com.netaporter.test.client.product.pojos.SearchableProduct;

import java.util.List;

public class Event {

    private Integer id;
    private List<SearchableProduct> searchableProducts;

    public Event(Integer id) {
        this.id = id;
    }

    public Event(Integer id, List<SearchableProduct> searchableProducts) {
        this.id = id;
        this.searchableProducts = searchableProducts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<SearchableProduct> getSearchableProducts() {
        return searchableProducts;
    }

    public void setSearchableProducts(List<SearchableProduct> searchableProducts) {
        this.searchableProducts = searchableProducts;
    }
}
