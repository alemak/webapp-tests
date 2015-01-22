package com.netaporter.test.utils.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ocsiki on 31/12/14.
 */
@Component
public class ExporterMessageSender {

    private String setSingleSkuOrderDispatchTemplate =
            "{" +
                    "  \"@type\" : \"order\"," +
                    "  \"orderNumber\" : 99999," +
                    "  \"status\": \"Dispatched\"," +
                    "  \"trackingUri\": \"http://track.dhl-usa.com/TrackByNbr.asp?type=fasttrack&ShipmentNumber=9981606051\"," +
                    "  \"shippingMethod\": \"International DDU\"," +
                    "  \"returnCutoffDate\": \""+getValidReturnDate()+"\"," +
                    "  \"orderItems\" : " +
                    "    [" +
                    "      {" +
                    "        \"sku\" : \"11111\"," +
                    "        \"xtLineItemId\" : \"100\"," +
                    "        \"status\" : \"Dispatched\"," +
                    "        \"unitPrice\" : \"100\"," +
                    "        \"tax\" : \"10\"," +
                    "        \"duty\" :\"5\"," +
                    "        \"returnable\": \"Y\"" +
                    "      }" +
                    "    ]" +
                    "}";

    private String setThreeSkuOrderDispatchTemplate =
            "{" +
                    "  \"@type\" : \"order\"," +
                    "  \"orderNumber\" : 99999," +
                    "  \"status\": \"Dispatched\"," +
                    "  \"trackingUri\": \"http://track.dhl-usa.com/TrackByNbr.asp?type=fasttrack&ShipmentNumber=9981606051\"," +
                    "  \"shippingMethod\": \"International DDU\"," +
                    "  \"returnCutoffDate\": \""+getValidReturnDate()+"\"," +
                    "  \"orderItems\" : " +
                    "    [" +
                    "      {" +
                    "        \"sku\" : \"11111\"," +
                    "        \"xtLineItemId\" : \"100\"," +
                    "        \"status\" : \"Dispatched\"," +
                    "        \"unitPrice\" : \"100\"," +
                    "        \"tax\" : \"10\"," +
                    "        \"duty\" :\"5\"," +
                    "        \"returnable\": \"Y\"" +
                    "      }," +
                    "      {" +
                    "        \"sku\" : \"22222\"," +
                    "        \"xtLineItemId\" : \"101\"," +
                    "        \"status\" : \"Dispatched\"," +
                    "        \"unitPrice\" : \"200\"," +
                    "        \"tax\" : \"20\"," +
                    "        \"duty\" :\"10\"," +
                    "        \"returnable\": \"Y\"" +
                    "      }," +
                    "      {" +
                    "        \"sku\" : \"33333\"," +
                    "        \"xtLineItemId\" : \"102\"," +
                    "        \"status\" : \"Dispatched\"," +
                    "        \"unitPrice\" : \"300\"," +
                    "        \"tax\" : \"30\"," +
                    "        \"duty\" :\"15\"," +
                    "        \"returnable\": \"Y\"" +
                    "      }" +
                    "    ]" +
                    "}";


    @Autowired
    @Qualifier("exporterJmsProducerTemplate")
    private JmsTemplate template;

    public void sendSingleSkuOrderDispatchedMessage(final String orderConfirmationNumber, final String sku) throws JMSException {
        template.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(setSingleSkuOrderDispatchTemplate
                        .replaceFirst("99999", orderConfirmationNumber)
                        .replaceFirst("11111", sku));
                message.setJMSType("OrderMessage");
                message.setJMSExpiration(0);
                return message;
            }
        });
    }

    public void sendThreeSkuOrderDispatchedMessage(final String orderConfirmationNumber, final ArrayList<String> skuList) throws JMSException {
        template.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(setThreeSkuOrderDispatchTemplate
                        .replaceFirst("99999", orderConfirmationNumber)
                        .replaceFirst("11111", skuList.get(0))
                        .replaceFirst("22222", skuList.get(1))
                        .replaceFirst("33333", skuList.get(2)));
                message.setJMSType("OrderMessage");
                message.setJMSExpiration(0);
                return message;
            }
        });
    }

    private String getValidReturnDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Date returnDate = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000+0000");
        return dateFormat.format(returnDate);
    }
}
