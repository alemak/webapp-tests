package com.netaporter.pws.automation.shared.pojos.WishlistV3;

/**
 * Created with IntelliJ IDEA.
 * User: c.dawson@london.net-a-porter.com
 * Date: 23/12/2013
 * Time: 11:28
 * To change this template use File | Settings | File Templates.
 */
public class AddedToWishlistPopup {

    private String imageSource;
    private String popupLinkDetails;
    private String addedText;
    private String addedTextLinkDetails;
    private String showWishlistText;
    private String showWishlistLinkDetails;

    public AddedToWishlistPopup(){
    }

    public AddedToWishlistPopup(String wishlistId, String pid, String baseUrl){

        String wishlistLink = baseUrl + "wishlist/" + wishlistId;

        this.setImageSource(baseUrl + "images/products/" + pid + "/" + pid +"_in_s.jpg");
        this.setPopupLinkDetails(wishlistLink);
        this.setAddedText("ADDED TO WISH LIST");
        this.setAddedTextLinkDetails(wishlistLink);
        this.setShowWishlistText("SHOW WISH LIST");
        this.setShowWishlistLinkDetails(wishlistLink);
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getPopupLinkDetails() {
        return popupLinkDetails;
    }

    public void setPopupLinkDetails(String popupLinkDetails) {
        this.popupLinkDetails = popupLinkDetails;
    }

    public String getAddedText() {
        return addedText;
    }

    public void setAddedText(String addedText) {
        this.addedText = addedText;
    }

    public String getAddedTextLinkDetails() {
        return addedTextLinkDetails;
    }

    public void setAddedTextLinkDetails(String addedTextLinkDetails) {
        this.addedTextLinkDetails = addedTextLinkDetails;
    }

    public String getShowWishlistText() {
        return showWishlistText;
    }

    public void setShowWishlistText(String showWishlistText) {
        this.showWishlistText = showWishlistText;
    }

    public String getShowWishlistLinkDetails() {
        return showWishlistLinkDetails;
    }

    public void setShowWishlistLinkDetails(String getShowWishlistLinkDetails) {
        this.showWishlistLinkDetails = getShowWishlistLinkDetails;
    }
}
