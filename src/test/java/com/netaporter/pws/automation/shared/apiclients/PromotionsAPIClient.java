package com.netaporter.pws.automation.shared.apiclients;

import com.netaporter.pws.automation.shared.pojos.Promotions.Promotion;
import com.netaporter.pws.automation.shared.utils.IPromotionsAPI;
import com.netaporter.test.client.product.pojos.ShippingMethod;
import org.springframework.stereotype.Component;

import java.util.List;

// @Component
// In order for PromotionServiceSteps to use this implementation instead of the database one (PromotionsUtil.java).
// 1. uncomment the above line of code @Component
// 2. comment out the @Component line in dataaccess.database.PromotionsUtil
// Spring will then automatically use this class implementation.
// nb: With both @Component lines uncommented, spring will throw exception: "expected single matching bean but found 2: promotionsUtil,promotionsAPIClient"
public class PromotionsAPIClient implements IPromotionsAPI {
    @Override
    public void enablePromotion(Integer promotionId) {
        // TODO - when promotions service exists
    }

    @Override
    public void disablePromotion(Integer promotionId) {
        // TODO - when promotions service exists
    }

    @Override
    public void enablePromotions(List<Integer> promotionIds) {
        // TODO - when promotions service exists
    }

    @Override
    public void disablePromotions(List<Integer> promotionIds) {
        // TODO - when promotions service exists
    }

    @Override
    public List<Integer> disableAllEnabledPromotions() {
        // TODO - when promotions service exists
        return null;
    }

    @Override
    public Promotion createPromotion(Promotion promotion) {
        // TODO - when promotions service exists
        return null;
    }

    @Override
    public void deletePromotion(Integer promotionId) {
        // TODO - when promotions service exists
    }

    @Override
    public void deleteShippingRestrictionsInPromotion(Integer promotionId) {
        // TODO - when promotions service exists
    }

    @Override
    public void addCustomerToPromotion(Integer customerId, Integer promotionId) {
        // TODO - when promotions service exists
    }

    @Override
    public void removeCustomerFromPromotion(Integer customerId, Integer promotionId) {
        // TODO - when promotions service exists
    }

    @Override
    public void addProductToPromotion(Integer productId, Integer promotionId) {
        // TODO - when promotions service exists
    }

    @Override
    public void removeProductFromPromotion(Integer productId, Integer promotionId) {
        // TODO - when promotions service exists
    }

    @Override
    public Boolean promotionIsAppliedToOrder(Integer orderId, Integer promotionId) {
        // TODO - when promotions service exists
        return null;
    }

    @Override
    public void addShippingMethodRestrictionToPromotion(Promotion promotion, ShippingMethod shippingMethod) {
        // TODO - when promotions service exists
    }

    @Override
    public Boolean checkIsPromotionRestrictedByShippingMethod(Promotion promotion) {
        // TODO - when promotions service exists
        return null;
    }

    @Override
    public void addShippingCountryRestrictionToPromotion(Promotion promotion, String countryCode) {
        // TODO - when promotions service exists
    }
}
