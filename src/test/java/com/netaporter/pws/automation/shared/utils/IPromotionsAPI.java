package com.netaporter.pws.automation.shared.utils;

import com.netaporter.pws.automation.shared.pojos.Promotions.Promotion;
import com.netaporter.test.client.product.pojos.ShippingMethod;

import java.util.List;

public interface IPromotionsAPI {

    public void enablePromotion(Integer promotionId);

    public void disablePromotion(Integer promotionId);

    public void enablePromotions(List<Integer> promotionIds);

    public void disablePromotions(List<Integer> promotionIds);

    public List<Integer> disableAllEnabledPromotions();

    public Promotion createPromotion(Promotion promotion);

    public void deletePromotion(Integer promotionId);

    public void deleteShippingRestrictionsInPromotion(Integer promotionId);

    public void addCustomerToPromotion(Integer customerId, Integer promotionId);

    public void removeCustomerFromPromotion(Integer customerId, Integer promotionId);

    public void addProductToPromotion(Integer productId, Integer promotionId);

    public void removeProductFromPromotion(Integer productId, Integer promotionId);

    public Boolean promotionIsAppliedToOrder(Integer orderId, Integer promotionId);

    public void addShippingMethodRestrictionToPromotion(Promotion promotion, ShippingMethod shippingMethod);

    public Boolean checkIsPromotionRestrictedByShippingMethod(Promotion promotion);

    public void addShippingCountryRestrictionToPromotion(Promotion promotion, String countryCode);

}
