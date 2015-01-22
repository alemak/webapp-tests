package com.netaporter.pws.automation.shared.apiclients;

import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSender;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;
import com.netaporter.pws.automation.shared.apiclients.pojos.GetAccountResults;
import com.netaporter.pws.automation.shared.apiclients.pojos.WebappLoginInfo;
import com.netaporter.test.utils.cucumber.glue.steps.BaseAPIStep;
import com.netaporter.test.utils.enums.RegionEnum;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static com.netaporter.test.utils.assertion.objectcomparison.NapReflectionAssert.assertReflectionEqualsNAP;
import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: a.kogan@london.net-a-porter.com
 * Date: 05/03/2013
 * Time: 14:21
 */
@Component
public class AccountDetailsAPIClient {

    private Gson gson = new Gson();
    private static final Pattern SET_COOKIE_PATTERN = Pattern.compile("([^=]+)=([^;]+);.*");

    private final static String PATH_LOGIN = "webapi/auth/login.json";
    private final static String PATH_LOGOUT_NEEDS_CHANNEL = "/j_spring_security_logout"; // add channel in-front of this
    private final static String PATH_GET_ACCOUNT = "webapi/account/customer.json";

    @Autowired
    private BeanFactory beanFactory;

    @Value("${baseUrl}")
    private String baseUrlHTTP;

    @Value("${webapp.cookie.jsessionid.usechannel}")
    private String useChannel = "true";

    public Response getAccount(WebappLoginInfo loginInfo, ResponseSpecification responseSpec) {
        return this.getAccount(loginInfo, responseSpec, false);
    }

    public Response getAccount(WebappLoginInfo loginInfo, ResponseSpecification responseSpec, boolean jsonp) {
        // Security seems to be set up to force JSONP request to be refered from our domain.

        RequestSpecBuilder rsb;
        if (loginInfo == null) {
            rsb = new RequestSpecBuilder();
        } else {
            rsb = loginInfo.builderWithLoginInfo();
        }

        if (jsonp) {
            rsb.addHeader("Referer", "http://naplabs.dave.net-a-porter.com/");
        }

        RequestSpecification requestSpec = rsb.build();
        //requestSpec.log().all();

        String url = getBaseURLHTTPS() + PATH_GET_ACCOUNT;
        if (jsonp) {
            url += "p?callback=test";
        }

        return given(requestSpec, responseSpec).get(url);
    }


    /**
     * Attempt a user log in to the webapp
     *
     * @param username the username
     * @param password the password
     * @return a WebappLoginInfo with the login information in case the login was successful, a <code>WebappLoginInfo.NOT_LOGGED_IN</code> otherwise
     */
    public WebappLoginInfo login(String username, String password, String channel) {
        RequestSpecification reqSpec = new RequestSpecBuilder()
                .addFormParam("j_username", username)
                .addFormParam("j_password", password)
                .addCookie("channel", channel)
                .addCookie("country_iso", "ZH")
                //.addFilter(new ResponseLoggingFilter())
                .build();

        Response response = given(reqSpec, new ResponseSpecBuilder().build()).post(getBaseURLHTTPS() + PATH_LOGIN);

        // We need to extract the cookies before the hacked redirection
        String jSessionId = extractJSessionId(response, channel);
        String remembered = response.getCookie("remembered_7070");

        response = post302Workaround(response);

        if (response.getBody().asString().contains("NOT_SIGNED_IN")) {
            return WebappLoginInfo.NOT_LOGGED_IN;
        } else {
            return (WebappLoginInfo) beanFactory.getBean("webappLoginInfo", channel, jSessionId, remembered);
        }
    }

    private String extractJSessionId(Response response, String channel) {
        if (usingChannel()) {
            return response.getCookie("JSESSIONID_" + channel.toUpperCase());
        } else {
            return response.getCookie("JSESSIONID");
        }
    }


    public WebappLoginInfo logout() {
        return logout(null, null);
    }


    /**
     * Logs out from the webapp.
     * <br />
     * This method doesn't log out a specific user but returns an instance of WebappLoginInfo with information
     * of a non-logged-in user in the webapp.
     *
     * @return the WebappLoginInfo with the logged out information
     */
    public WebappLoginInfo logout(String currentSessionJSessionId, String currentSessionChannel) {
       RequestSpecification req;
        if ((currentSessionJSessionId != null) && (currentSessionChannel != null)) {
            if(usingChannel()) {
                req = given().cookie("JSESSIONID_" + currentSessionChannel.toUpperCase(), currentSessionJSessionId);
            } else {
                req = given().cookie("JSESSIONID", currentSessionJSessionId);
            }

        } else {
            req = given();
        }

        //req.log().all();
        //req.response().log().all();

        //Response response = req.get(baseUrl + pathLogout);
        Response response = req.get(getBaseURLHTTPS() + currentSessionChannel + PATH_LOGOUT_NEEDS_CHANNEL);


        String jSessionId = null;
        String channel = null;
        String remembered = null;
        List<Header> setCookieHeaders = response.getHeaders().getList("Set-Cookie");
        for (Header setCookieHeader : setCookieHeaders) {
            Matcher m = SET_COOKIE_PATTERN.matcher(setCookieHeader.getValue());
            if (m.matches()) {
                if (m.group(1).startsWith("JSESSIONID")) {
                    jSessionId = m.group(2);
                } else if ("channel".equals(m.group(1))) {
                    channel = m.group(2);
                } else if ("remembered_7070".equals(m.group(1))) {
                    remembered = m.group(2);
                }
            }
        }
        return (WebappLoginInfo) beanFactory.getBean("webappLoginInfo", channel, jSessionId, remembered);
    }

    /**
     * Attempt a user log in to the webapp and then time out his session and leave him as remembered
     *
     * @param username the username
     * @param password the password
     * @return a WebappLoginInfo with the login information in a "remembered state" in case the login was successful, a <code>WebappLoginInfo.NOT_LOGGED_IN</code> otherwise
     */
    public WebappLoginInfo remember(String username, String password, String channel) {
        WebappLoginInfo loginInfo = login(username, password, channel);
        if (!loginInfo.equals(WebappLoginInfo.NOT_LOGGED_IN)) {
            loginInfo.setAsRemembered();
        }
        return loginInfo;
    }


    /**
     * Workaround for client not redirecting when it receives a 302 after making a POST.
     * <br/>
     * See <a href="http://www.jayway.com/2012/10/17/what-you-may-not-know-about-http-redirects/">this blog post</a> and the <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html">HTTP specification</a>.
     *
     * @param response the response to check
     * @return the redirected response or the original response in case there was no redirection
     */
    private Response post302Workaround(Response response) {
        if (response.getStatusCode() == 302) {
            String location = response.getHeaders().get("Location").getValue();
            response = expect().log().all().given().get(location);
        }
        return response;
    }

    private String getBaseURLHTTPS() {
        return baseUrlHTTP.replaceFirst("http://", "https://");
    }

    private boolean usingChannel() {
        // default to true, so look specifically for false
        return ! useChannel.trim().toLowerCase().equals("false");
    }

}


