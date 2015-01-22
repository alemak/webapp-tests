package com.netaporter.pws.automation.nap.enums;

/**
 * Created by ocsiki on 29/09/2014.
 */
public enum ProductFilterEnum {

        // TODO: refactor after all CLP are migrated to AWS
      //Legacy listing pages
        COLOR("colour-filter", ".//*[@id='colour-filter']/ul/li[@class='selected']/a[2]/span", ".//*[@id='colour-filter']/ul/li[@class='selected']/a[2]/span", "colourFilter="),
        DESIGNER("designer-filter", ".//*[@id='designer-filter']/div[2]/div/div[1]/ul/li[@class='selected']/a[2]/span", ".//*[@id='designer-filter']/div[2]/div/div[1]/ul/li[@class='selected']", "designerFilter="),
        SIZE("size-filter", ".//*[@id='size-filter']/div[2]/ul/li", null, "sizeFilter=000");


    // AWS listing pages
//    COLOR("colour-filter", ".//*[@id='colour-filter']/ul/li[@class='selected']/a[2]/span", ".//*[@id='colour-filter']/ul/li[@class='selected']/a[2]/span", "colourfilter="),
//    DESIGNER("designer-filter", ".//*[@id='designer-filter']/div/div/div[1]/ul/li[@class='selected']/a/div/span", ".//*[@id='designer-filter']/div/div/div[1]/ul/li[@class='selected']", "designerfilter="),
//    SIZE("size-filter", ".//*[@id='size-filter']/div[2]/ul/li", null, "sizefilter=000");

        private String domIdOrClass;
        private String domXPathForSelectedName;
        private String domXPathForSelectedId;
        private String urlPrefix;

        private static final String SELECTED_CLASS = "[@class='selected']";

        ProductFilterEnum(String domIdOrClass, String domXPathForName, String domXPathForId, String urlPrefix) {
            this.domIdOrClass = domIdOrClass;
            this.domXPathForSelectedName = domXPathForName;
            this.domXPathForSelectedId = domXPathForId;
            this.urlPrefix = urlPrefix;
        }

        public String getDomXPathForName(boolean isSelected) {
            if (isSelected) {
                return domXPathForSelectedName;
            }

            return domXPathForSelectedName.replace(SELECTED_CLASS+"/a[2]/span", "");
        }

        public String getDomXPathForId(boolean isSelected) {
            if (isSelected) {
                return domXPathForSelectedId;
            }

            return domXPathForSelectedId.replace(SELECTED_CLASS, "");
        }

        public String getDomIdOrClass() {
            return domIdOrClass;
        }

        public String getUrlPrefix() {
            return urlPrefix;
        }
}

