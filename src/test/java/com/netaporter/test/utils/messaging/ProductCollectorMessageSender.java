package com.netaporter.test.utils.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * Created by j.christian on 14/04/2014.
 */
@Component
public class ProductCollectorMessageSender {

    private String setDiscountTemplate =
            "{" +
            "  \"product_id\" : 99999," +
            "  \"business_id\": 1," +
            "  \"business_name\": \"NAP\"," +
            "  \"channel_name\": \"NAP Intl\"," +
            "  \"channel_id\": 1," +
            "  \"markdowns\" : " +
            "    [" +
            "      {" +
            "        \"percentage\" : 51.23," +
            "        \"category\" : \"None\"," +
            "        \"start_date\" :\"2010-01-01T00:00:00Z\"," +
            "        \"end_date\": \"2020-01-01T00:00:00Z\"" +
            "      }" +
            "    ]" +
            "}";

    @Autowired
    @Qualifier("pCollJmsProducerTemplate")
    private JmsTemplate template;

    public void sendDiscountNAPIntlMessage(final String pid) throws JMSException  {
        template.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(setDiscountTemplate.replaceFirst("99999", ""+pid));
                message.setStringProperty("type","product_channel");
                message.setIntProperty("business_id",1);
                message.setStringProperty("business_name","NAP");
                message.setStringProperty("content-type", "json");
                message.setIntProperty("live",1);
                message.setIntProperty("staging",0);
                message.setJMSType("product_channel");
                message.setJMSExpiration(0);
                message.setIntProperty("channel_id",1);
                message.setStringProperty("chanel_name", "NAP Intl");
                return message;
            }
        });
    }

}
