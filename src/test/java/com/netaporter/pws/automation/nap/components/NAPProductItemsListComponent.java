package com.netaporter.pws.automation.nap.components;

import com.netaporter.pws.automation.shared.components.ProductItemsListComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NAPProductItemsListComponent extends ProductItemsListComponent {

    // use on the payment and order confirmation pages
    protected void readOrderItemListFromPage() {
        orderItemList = new ArrayList<Map>();
        List<WebElement> orderItemRows = webBot.findElements(By.cssSelector("table.shopping_bag_items tbody tr"));

        for(WebElement row : orderItemRows) {
            Map<String, String> rowMap = new HashMap<String, String>();
            int index = 0;
            for(WebElement col : row.findElements(By.tagName("td"))) {
                if(index == 0)
                    rowMap.put(tableHeadersList.get(index), col.findElement(By.tagName("img")).getAttribute("alt"));
                else
                    rowMap.put(tableHeadersList.get(index), col.getText());
                index++;
            }
            orderItemList.add(rowMap);
        }
    }

    protected void readItemHeaders() {
        tableHeadersList = new ArrayList<String>();
        List<WebElement> tableHeaders = webBot.findElements(By.cssSelector("table.shopping_bag_items thead th"));
        for(WebElement e : tableHeaders)
            tableHeadersList.add(e.getText().toLowerCase());
    }

}
