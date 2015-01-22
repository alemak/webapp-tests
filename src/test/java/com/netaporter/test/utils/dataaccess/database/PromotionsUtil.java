package com.netaporter.test.utils.dataaccess.database;

import com.netaporter.pws.automation.shared.pojos.Promotions.EventRule;
import com.netaporter.pws.automation.shared.pojos.Promotions.Promotion;
import com.netaporter.pws.automation.shared.utils.IPromotionsAPI;
import com.netaporter.test.client.product.impl.HybridProductDataAccess;
import com.netaporter.test.client.product.pojos.ShippingMethod;
import com.netaporter.test.utils.pages.driver.WebDriverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
@Scope("prototype")
public class PromotionsUtil implements IPromotionsAPI {

    @Autowired
    public HybridProductDataAccess dataAccess;

    @Autowired
    public WebDriverUtil webBot;

    private static final String LEGACY_RULE_VALUE_TEXT = "'MIGRATED TO EventRuleValue TABLE'";
    private static final String ENABLE_PROMOTION_SQL = "update event_detail set enabled = 1 where id = ";
    private static final String DISABLE_PROMOTION_SQL = "update event_detail set enabled = 0 where id = ";
    private static final String SELECT_ENABLED_PROMOTIONS_SQL = "select id from event_detail where enabled = 1";
    private static final String DELETE_PROMOTION_SQL = "delete from event_detail where id = ";
    private static final String UPDATE_EVENT_DETAIL_VISIBLE_ID_SQL = "update event_detail set visible_id = ";
    private static final String INSERT_EVENT_DETAIL_CUSTOMER = "insert into event_customer (event_id, customer_id) values ";
    private static final String DELETE_EVENT_DETAIL_CUSTOMER = "delete from event_customer where event_id = ";
    private static final String INSERT_EVENT_DETAIL_PRODUCT = "insert into event_product (event_id, product_id) values ";
    private static final String DELETE_EVENT_DETAIL_PRODUCT = "delete from event_product where event_id = ";
    private static final String INSERT_SHIPPING_RESTRICTION = "insert into event_shippingoptions (event_id, shippingoption_id) values ";
    private static final String DELETE_SHIPPING_RESTRICTION = "delete from event_shippingoptions where event_id = ";
    private static final String SELECT_SHIPPING_RESTRICTION = "select * from event_shippingoptions where event_id = ";
    private static final String INSERT_SHIPPING_COUNTRY_RESTRICTION = "insert into event_rule_value (event_rule_id, rule_value) values ";

    public class PromotionNotFoundException extends RuntimeException {
        public PromotionNotFoundException(Integer promotionId) {
            super("Promotion id: " + promotionId + " does not exist in the database.");
        }

        public PromotionNotFoundException(String message) {
            super(message);
        }

        public PromotionNotFoundException(Throwable throwable) {
            super(throwable);
        }

        public PromotionNotFoundException(String message, Throwable throwable) {
            super(message, throwable);
        }
    }

    @Override
    public void enablePromotion(Integer promotionId) {
        if(dataAccess.getLegacyDBClient().executeUpdate(webBot.getRegionEnum(),ENABLE_PROMOTION_SQL + promotionId) == 0)
            throw new PromotionNotFoundException(promotionId);
    }

    @Override
    public void disablePromotion(Integer promotionId) {
        if(dataAccess.getLegacyDBClient().executeUpdate(webBot.getRegionEnum(), DISABLE_PROMOTION_SQL + promotionId) == 0)
            throw new PromotionNotFoundException(promotionId);
    }

    @Override
    public void enablePromotions(List<Integer> promotionIds) {
        String failuresList = "";

        for(Integer id : promotionIds) {
            if(dataAccess.getLegacyDBClient().executeUpdate(webBot.getRegionEnum(), ENABLE_PROMOTION_SQL + id) == 0)
                failuresList+= id + ", ";
        }

        if(failuresList.length() > 0)
            throw new PromotionNotFoundException("The following promotion id's " + failuresList + " do not exist in the database.");
    }

    @Override
    public void disablePromotions(List<Integer> promotionIds) {
        String failuresList = "";

        for(Integer id : promotionIds) {
            if(dataAccess.getLegacyDBClient().executeUpdate(webBot.getRegionEnum(), DISABLE_PROMOTION_SQL + id) == 0)
                failuresList+= id + ", ";
        }

        if(failuresList.length() > 0)
            throw new PromotionNotFoundException("The following promotion id's " + failuresList + " do not exist in the database.");
    }

    @Override
    public List<Integer> disableAllEnabledPromotions() {
        List<Map> results = dataAccess.getLegacyDBClient().executeSelect(webBot.getRegionEnum(), SELECT_ENABLED_PROMOTIONS_SQL);
        List<Integer> enabledPromotions = new ArrayList<Integer>(results.size());

        for(Map m : results)
            enabledPromotions.add((Integer) m.get("id"));

        disablePromotions(enabledPromotions);
        return enabledPromotions;
    }

    @Override
    public Promotion createPromotion(Promotion promotion) {

        // insert the promotion and immediately fix its visible_id (that contains the primary key as a string)
        String query = generatePromotionSqlInsert(promotion);
        Integer promotionId = Integer.parseInt(dataAccess.getLegacyDBClient().executeUpdateAndReturnTheLastInsertedId(webBot.getRegionEnum(), query, "event_detail"));
        promotion.setEventId(promotionId);
        // correct the visible_id with the promotion id as a suffix
        promotion.setVisibleId("EVT-" + promotionId);
        query = UPDATE_EVENT_DETAIL_VISIBLE_ID_SQL + prepareStringVarForDb(promotion.getVisibleId()) + " where id = " + promotionId;
        dataAccess.getLegacyDBClient().executeUpdate(webBot.getRegionEnum(), query);

        for(EventRule eventRule : promotion.getEventRules()) {
            eventRule.setEventId(promotionId);
            query = generateEventRuleSqlInsert(eventRule);
            Integer eventRuleId = Integer.parseInt(dataAccess.getLegacyDBClient().executeUpdateAndReturnTheLastInsertedId(webBot.getRegionEnum(), query, "event_rule"));
            eventRule.setEventRuleId(eventRuleId);
            for(String ruleValue : eventRule.getRuleValues()) {
                query = generateEventRuleValueSqlInsert(eventRuleId, ruleValue);
                dataAccess.getLegacyDBClient().executeUpdate(webBot.getRegionEnum(), query);
            }
        }
        return promotion;
    }


    @Override
    public void deletePromotion(Integer promotionId) {
        // delete the event and rely on cascade deletes
        if(dataAccess.getLegacyDBClient().executeUpdate(webBot.getRegionEnum(), DELETE_PROMOTION_SQL + promotionId) == 0)
            throw new PromotionNotFoundException(promotionId);
    }

    @Override
    public void deleteShippingRestrictionsInPromotion(Integer promotionId) {
        //delete any shipping restrictions as there is no cascade delete
        if(dataAccess.getLegacyDBClient().executeUpdate(webBot.getRegionEnum(), DELETE_SHIPPING_RESTRICTION + promotionId) == 0)
            throw new PromotionNotFoundException(promotionId);
    }

    @Override
    public void addCustomerToPromotion(Integer customerId, Integer promotionId) {
        String query = INSERT_EVENT_DETAIL_CUSTOMER + "(" + promotionId + "," + customerId + ");";
        if(dataAccess.getLegacyDBClient().executeUpdate(webBot.getRegionEnum(), query) == 0)
            throw new RuntimeException("Customer or Promotion not found");
    }

    @Override
    public void removeCustomerFromPromotion(Integer customerId, Integer promotionId) {
        String query = DELETE_EVENT_DETAIL_CUSTOMER + promotionId + " and customer_id = " + customerId + ";";
        if(dataAccess.getLegacyDBClient().executeUpdate(webBot.getRegionEnum(), query) == 0)
            throw new RuntimeException("Customer or Promotion not found");
    }

    @Override
    public void addProductToPromotion(Integer productId, Integer promotionId) {
        String query = INSERT_EVENT_DETAIL_PRODUCT + "(" + promotionId + "," + productId + ");";
        if(dataAccess.getLegacyDBClient().executeUpdate(webBot.getRegionEnum(), query) == 0)
            throw new RuntimeException("Product or Promotion not found");
    }

    @Override
    public void removeProductFromPromotion(Integer productId, Integer promotionId) {
        String query = DELETE_EVENT_DETAIL_PRODUCT + promotionId + " and product_id = " + productId + ";";
        if(dataAccess.getLegacyDBClient().executeUpdate(webBot.getRegionEnum(), query) == 0)
            throw new RuntimeException("Product or Promotion not found");
    }

    @Override
    public Boolean promotionIsAppliedToOrder(Integer orderId, Integer promotionId) {
        String query = "Select count(*) as counter from event_applied where order_id =" + orderId +
                " and promotion_id = " + promotionId;
        return (Long) dataAccess.getLegacyDBClient().executeSelect(webBot.getRegionEnum(), query).get(0).get("counter") > 0;
    }

    public String generateEventRuleValueSqlInsert(Integer eventRuleId, String ruleValue) {
        return "insert into event_rule_value (event_rule_id, rule_value) values (" +
                eventRuleId + "," +
                prepareStringVarForDb(ruleValue) + ")";
    }

    public String generateEventRuleSqlInsert(EventRule eventRule) {
        return "insert into event_rule (event_id, rule_type, rule_value) values (" +
                eventRule.getEventId() + ", " +
                prepareStringVarForDb(eventRule.getRuleType().name()) + ", " +
                LEGACY_RULE_VALUE_TEXT + ");";
    }

    public String generatePromotionSqlInsert(Promotion promotion) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return "insert into event_detail (visible_id, event_type_id, internal_title, title, subtitle, " +
                "start_date, end_date, enabled, discount_type, discount_percentage, price_group_id, created_by, " +
                "created, last_modified_by, last_modified, restrict_x_weeks, restrict_by_weeks) values (" +
                "'temp"+new Random().nextInt()+"', " +
                "1, " +
                prepareStringVarForDb(promotion.getInternalTitle()) + ", " +
                prepareStringVarForDb(promotion.getTitle()) + ", " +
                prepareStringVarForDb(promotion.getSubtitle()) + ", " +
                prepareStringVarForDb(sdf.format(promotion.getStartDate())) + ", " +
                prepareStringVarForDb(sdf.format(promotion.getEndDate())) + ", " +
                "0, " +
                prepareStringVarForDb(promotion.getPromotionType().toString()) + ", " +
                promotion.getPercentageDiscount() + ", " +
                prepareStringVarForDb(promotion.getPriceGroup()) + ", " +
                "-1, " +
                "now(), " +
                "-1, " +
                "now(), " +
                "0, " +
                "0);";
    }

    public void addShippingMethodRestrictionToPromotion(Promotion promotion, ShippingMethod shippingMethod) {
        String query = INSERT_SHIPPING_RESTRICTION + "(" + promotion.getEventId() + "," + shippingMethod.getId() + ");";
        if(dataAccess.getLegacyDBClient().executeUpdate(webBot.getRegionEnum(), query) == 0) {
            throw new RuntimeException("Shipping method or Promotion not found");
        }
    }

    private String prepareStringVarForDb(String variableValue) {
        return "'" + variableValue + "'";
    }

    public Boolean checkIsPromotionRestrictedByShippingMethod(Promotion promotion) {
        String query = SELECT_SHIPPING_RESTRICTION + "(" + promotion.getEventId() + ");";
        List<Map> queryResult = dataAccess.getLegacyDBClient().executeSelect(webBot.getRegionEnum(), query);

        return (queryResult.size() > 0);
    }

    public void addShippingCountryRestrictionToPromotion(Promotion promotion, String countryCode) {
        //get(0) on the assumption that all event rules have the same ruleID
        String query = INSERT_SHIPPING_COUNTRY_RESTRICTION + "(" + promotion.getEventRules().get(0).getEventRuleId() + ",'" + countryCode + "');";
        if(dataAccess.getLegacyDBClient().executeUpdate(webBot.getRegionEnum(), query) == 0) {
            throw new RuntimeException("Country Code or Promotion not found");
        }
    }

}