package com.netaporter.pws.automation.shared.pojos;


import org.apache.log4j.Logger;

public class UserOption {

    static Logger logger = Logger.getLogger(UserOption.class);

    String optionName;
    boolean selected;
    String description;
    String url;
    String cost;

    public UserOption(String optionName, boolean selected, String description) {
        this.optionName = optionName;
        this.selected = selected;
        this.description = description;
    }

    public UserOption(String optionName, boolean selected, String description, String url) {
        this.optionName = optionName;
        this.selected = selected;
        this.description = description;
        this.url = url;
    }

    public UserOption(String optionName, boolean selected, String description, String url, String cost) {
        this.optionName = optionName;
        this.selected = selected;
        this.description = description;
        this.url = url;
        this.cost = cost;
    }

    public String getOptionName() { return optionName; }
    public String getDescription() { return description; }
    public boolean isSelected() { return selected; }
    public String getUrl() { return url; }
    public String getCost() { return cost; }

    public boolean isFree() {
        boolean isFree = cost.contains("FREE");
        logger.info("Shipping option "+optionName+(isFree ? " is ": "is not ")+"free.\n");
        return isFree;
    }

}
