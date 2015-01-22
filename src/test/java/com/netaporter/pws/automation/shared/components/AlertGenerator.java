package com.netaporter.pws.automation.shared.components;

import com.netaporter.test.client.product.impl.HybridProductDataAccess;
import com.netaporter.test.utils.enums.RegionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: drossi
 * Date: 11/02/13
 * Time: 17:28
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AlertGenerator {

    @Autowired
    HybridProductDataAccess dataAccess;

    public static final SimpleDateFormat ALERT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static class AlertAT {

        public static enum PRODUCT_ALERT_TYPES {BACK, LOW, SALE, SALE_BACK, SALE_LOW}

        ;

        int alertId;
        int customerId;
        String sku;
        PRODUCT_ALERT_TYPES product_alert_type;
        Date expiry;
        Date updated;
        Date created;
        Date viewed_timestamp;
        boolean viewed;

        public int getAlertId() {
            return alertId;
        }

        public void setAlertId(int alertId) {
            this.alertId = alertId;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public PRODUCT_ALERT_TYPES getProduct_alert_type() {
            return product_alert_type;
        }

        public void setProduct_alert_type(PRODUCT_ALERT_TYPES product_alert_type) {
            this.product_alert_type = product_alert_type;
        }

        public Date getExpiry() {
            return expiry;
        }

        public void setExpiry(Date expiry) {
            this.expiry = expiry;
        }

        public Date getUpdated() {
            return updated;
        }

        public void setUpdated(Date updated) {
            this.updated = updated;
        }

        public Date getCreated() {
            return created;
        }

        public void setCreated(Date created) {
            this.created = created;
        }

        public Date getViewed_timestamp() {
            return viewed_timestamp;
        }

        public void setViewed_timestamp(Date viewed_timestamp) {
            this.viewed_timestamp = viewed_timestamp;
        }

        public boolean isViewed() {
            return viewed;
        }

        public void setViewed(boolean viewed) {
            this.viewed = viewed;
        }

        public static AlertAT generateSimpleAlert(int customerId, String sku, PRODUCT_ALERT_TYPES type) {
            AlertAT newAlert = new AlertAT();
            newAlert.alertId = 0;
            newAlert.created = new Date();
            newAlert.customerId = customerId;
            newAlert.sku = sku;
            newAlert.product_alert_type = type;


            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(new Date());
            gc.add(Calendar.MINUTE, 20);
            newAlert.expiry = gc.getTime();
            newAlert.updated = new Date();
            newAlert.viewed_timestamp = null;

            return newAlert;
        }
    }

    public AlertGenerator.AlertAT insertAlertIntoDb(RegionEnum region, AlertGenerator.AlertAT alert) {
        String qry = "INSERT INTO `alert` \n" +
                "            (`viewed`, \n" +
                "             `expiry`, \n" +
                "             `updated`, \n" +
                "             `customer_id`, \n" +
                "             `type`, \n" +
                "             `product_alert_type`, \n" +
                "             `sku`, \n" +
                "             `created_dts`) \n" +
                "    VALUES      ('" + (alert.isViewed() ? 1 : 0) + "', \n" +
                "             '" + AlertGenerator.ALERT_DATE_FORMAT.format(alert.getExpiry()) + "', \n" +
                "             '" + AlertGenerator.ALERT_DATE_FORMAT.format(alert.getUpdated()) + "', \n" +
                "             '" + alert.getCustomerId() + "', \n" +
                "             'product', \n" +
                "             '" + alert.getProduct_alert_type().toString() + "', \n" +
                "             '" + alert.getSku() + "', \n" +
                "             '" + AlertGenerator.ALERT_DATE_FORMAT.format(alert.getCreated()) + "')";


        String id = dataAccess.getLegacyDBClient().executeUpdateAndReturnTheLastInsertedId(region, qry, "alert");
        alert.setAlertId(Integer.parseInt(id));
        return alert;
    }
}
