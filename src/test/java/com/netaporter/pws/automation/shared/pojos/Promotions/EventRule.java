package com.netaporter.pws.automation.shared.pojos.Promotions;

import java.util.ArrayList;
import java.util.List;

public class EventRule {

    public enum RuleType {
        EXCLUDE_DESIGNER ("EXCLUDE_DESIGNER"),
        INCLUDE_DESIGNER ("INCLUDE_DESIGNER"),
        EXCLUDE_SEASON ("EXCLUDE_SEASON"),
        INCLUDE_SEASON ("INCLUDE_SEASON"),
        INCLUDE_GROUP_VOUCHER_FOR_FIRST_ORDER_FREE_SHIPPING ("INCLUDE_GROUP_VOUCHER_FOR_FIRST_ORDER_FREE_SHIPPING"),
        INCLUDE_SHIPPING_COUNTRY ("INCLUDE_SHIPPING_COUNTRY"),
        EXCLUDE_CATEGORY ("EXCLUDE_CATEGORY"),
        INCLUDE_CATEGORY ("INCLUDE_CATEGORY"),
        BASKET_THRESHOLD ("BASKET_THRESHOLD");

        private String name;

        private RuleType(String s) {
            name = s;
        }
    }

    private Integer eventId;
    private Integer eventRuleId;
    private RuleType ruleType;
    private List<String> ruleValues = new ArrayList<String>();

    public List<String> getRuleValues() {
        return ruleValues;
    }

    public void setRuleValues(List<String> ruleValues) {
        this.ruleValues = ruleValues;
    }

    public EventRule(RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getEventRuleId() {
        return eventRuleId;
    }

    public void setEventRuleId(Integer eventRuleId) {
        this.eventRuleId = eventRuleId;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }

}