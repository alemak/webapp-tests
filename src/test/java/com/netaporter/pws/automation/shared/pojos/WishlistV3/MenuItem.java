package com.netaporter.pws.automation.shared.pojos.WishlistV3;

import java.util.Comparator;

/**
 * Date: 14/05/2013
 * Time: 14:51
 */
public class MenuItem {

    private String text;
    private String wishlistId;
    private String href;
    private boolean ticked;

    private boolean isPrivate;

    public boolean isAllItemsLink() {
        return text.equals("/wishlist/all-items");
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(String wishlistId) {
        this.wishlistId = wishlistId;
    }

    public boolean isTicked() {
        return ticked;
    }

    public void setTicked(boolean ticked) {
        this.ticked = ticked;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public static class AlphabeticalComparator implements Comparator<MenuItem> {
        //@Override
        public int compare(MenuItem o1, MenuItem o2) {
            return o1.getText().toLowerCase().compareTo(o2.getText().toLowerCase());
        }
    }
}