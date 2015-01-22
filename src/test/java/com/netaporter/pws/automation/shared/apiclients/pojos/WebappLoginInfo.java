package com.netaporter.pws.automation.shared.apiclients.pojos;

import com.jayway.restassured.builder.RequestSpecBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: a.kogan@london.net-a-porter.com
 * Date: 06/03/2013
 * Time: 10:54
 */
@Component("webappLoginInfo")
@Scope("prototype")
public class WebappLoginInfo {

    @Value("${webapp.cookie.jsessionid.usechannel}")
    private String useChannel = "true";

    private String channel;
    private String jSessionId;
    private String remembered;

    public static final WebappLoginInfo NOT_LOGGED_IN = new WebappLoginInfo("", "", "");

    public WebappLoginInfo(String channel, String jSessionId, String remembered) {
        this.channel = channel;
        this.jSessionId = jSessionId;
        this.remembered = remembered;
    }

    public String getChannel() {
        return channel;
    }

    public String getjSessionId() {
        return jSessionId;
    }

    public String getRemembered() {
        return remembered;
    }

    public void setAsRemembered() {
        jSessionId = null;
    }


    public RequestSpecBuilder builderWithLoginInfo() {
        return addLoginInfo(new RequestSpecBuilder());
    }

    public RequestSpecBuilder addLoginInfo(RequestSpecBuilder builder) {
        if (jSessionId != null) {
            builder.addCookie(getJSessionIdCookieName(), jSessionId);
        }

        return builder
                .addCookie("channel", channel)
                .addCookie("remembered_7070", remembered);
    }

    public String getJSessionIdCookieName() {
        if (usingChannel()) {
            return "JSESSIONID_" + channel.toUpperCase();
        } else {
            return "JSESSIONID";
        }
    }

    private boolean usingChannel() {
        // default to true, so look specifically for false
        return ! useChannel.trim().toLowerCase().equals("false");
    }
}
