package com.netaporter.pws.automation.shared.pojos.WishlistV3;

import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Date: 22/05/2013
 * Time: 10:10
 */
public class NavMenu {

    private LinkedList<MenuItem> allMenuItems;

    public NavMenu(List<MenuItem> allMenuItems) {
        this.allMenuItems = new LinkedList(allMenuItems);
    }

    public MenuItem getDefaultWishlistMenuItem() {
        return allMenuItems.getFirst();
    }

    public MenuItem getAllWishlistItemsMenuItem() {
        return allMenuItems.getLast();
    }

    public List<MenuItem> getUserMenuItems() {
        return allMenuItems.subList(0, allMenuItems.size());
    }

    public List<MenuItem> getUserPopupMenuItems() {
        return allMenuItems.subList(1, allMenuItems.size());
    }

    public MenuItem getFirstUserMenuItemWithName(String listName) throws Throwable {
        for(MenuItem item: getUserMenuItems()) {
            if(item.getText().equals(listName)) {
                return item;
            }
        }

        return null;
    }

    public List<MenuItem> getUserMenuItemsWithName(String listName) {
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        for(MenuItem item: getUserMenuItems()) {
            if(item.getText().equals(listName)) {
                menuItems.add(item);
            }
        }

        return menuItems;
    }


    public LinkedList<MenuItem> getAllMenuItems() {
        return allMenuItems;
    }
}
