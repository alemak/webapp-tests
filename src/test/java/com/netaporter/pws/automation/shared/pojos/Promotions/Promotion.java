package com.netaporter.pws.automation.shared.pojos.Promotions;

import java.util.*;

public class Promotion {

    public static final String FULLPRICEANDMARKDOWN_PRICEGROUP = "Full price and mark down";
    public static final String FULLPRICE_PRICEGROUP = "Full price";
    public static final String MARKDOWN_PRICEGROUP = "Mark down";

    private static final Map<String, Integer> priceGroups = new HashMap<String, Integer>() {
        {
            put(FULLPRICEANDMARKDOWN_PRICEGROUP, 1);
            put(FULLPRICE_PRICEGROUP, 2);
            put(MARKDOWN_PRICEGROUP, 3);
        }
    };

    public enum PromotionType {
        PERCENTAGE_DISCOUNT ("percentage_discount"),
        FREE_SHIPPING("free_shipping");

        private String type;

        @Override
        public String toString(){
            return type;
        }

        private PromotionType(String s) {
            type = s;
        }
    }

    private String internalTitle;
    private String title;
    private String subtitle;
    private Integer eventId;
    private Date startDate;
    private Date endDate;
    private Boolean enabled;
    private PromotionType promotionType;
    private Integer percentageDiscount;
    private Integer priceGroup;
    private List<EventRule> eventRules = new ArrayList<EventRule>();
    private String visibleId;

    public String getVisibleId() {
        return visibleId;
    }

    public void setVisibleId(String visibleId) {
        this.visibleId = visibleId;
    }

    public List<EventRule> getEventRules() {
        return eventRules;
    }

    public void setEventRules(ArrayList<EventRule> eventRules) {
        this.eventRules = eventRules;
    }

    public String getPriceGroup() {
        return this.priceGroup.toString();
    }

    public void setPriceGroup(String priceGroup) {
        this.priceGroup = priceGroups.get(priceGroup);
    }

    public Integer getPercentageDiscount() {
        return percentageDiscount;
    }

    public void setPercentageDiscount(Integer percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(PromotionType promotionType) {
        this.promotionType = promotionType;
    }

    public String getInternalTitle() {
        return internalTitle;
    }

    public void setInternalTitle(String internalTitle) {
        this.internalTitle = internalTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}