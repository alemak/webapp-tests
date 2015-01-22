package com.netaporter.pws.automation.nap.cucumber.steps.apis.session;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.response.Response;
import com.netaporter.test.utils.cucumber.glue.steps.BaseAPIStep;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: c.dawson@london.net-a-porter.com
 * Date: 12/08/2013
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class SessionSteps extends BaseAPIStep {

    @Value("#{webDriverUtilConfig.baseUrl}")
    private String baseUrl;

    @And("^I attempt to hit a SetupSession wishlist url with the GEO header set to (GB|CN|US)$")
    public void attempt_to_hit_setupsession_wishlist_url(String countryCode) throws Throwable {

        String httpsRedirect;
        if(baseUrl.contains("https")) {
            httpsRedirect = "&httpsRedirect=true";
        } else {
            httpsRedirect = "&httpsRedirect=false";
        }

        String setupSessionURL = baseUrl + "SetupSession?redirect=/wishlist/setupsession-wishlist" + httpsRedirect;
        RequestSpecBuilder request = getRequestSpecBuilder().addHeader("GEO", "COUNTRIES:"+ countryCode);
        Response response = given().redirects().follow(false).log().all().spec(request.build()).config(RestAssured.config()).expect().body(anything()).get(setupSessionURL);
        scenarioSession.putData("response", response);
        scenarioSession.putData("country code", countryCode);
        scenarioSession.putData("setupSessionURL", setupSessionURL);
    }


    @And("^A ([0-9]+) status code is returned$")
    public void check_status_code(int statusCode) throws Throwable {
        Response response = (Response) scenarioSession.getData("response");
        getResponseSpecBuilder().expectStatusCode(statusCode).build().validate(response);
    }


    @And("^The SetupSession redirect response is returned with the correct details$")
    public void assert_setupSession_response() throws Throwable {

        Map<String, String> expCookieDetails = getCountryCookieData();
        Response response = (Response) scenarioSession.getData("response");
        String setupsessionUrl = scenarioSession.getData("setupSessionURL").toString();
        String expLocationHeader = setupsessionUrl.substring(0, setupsessionUrl.lastIndexOf("&")).replace("SetupSession?redirect=/wishlist", "wishlist");

        //note - Rest Assured cannot check header or cookie values are not null so am doing this as another check a bottom of this method
        getResponseSpecBuilder()
                .expectCookie(expCookieDetails.get("JSESSIONID"))
                .expectCookie("channel", equalTo(expCookieDetails.get("channel")))
                .expectCookie("country_iso", equalTo(expCookieDetails.get("country_iso")))
                .expectCookie("lang_time")
                .expectCookie("lang_iso", equalTo(expCookieDetails.get("lang_iso")))
                .expectCookie("deviceType", equalTo(expCookieDetails.get("deviceType")))
                .expectHeader("Location", equalTo(expLocationHeader))
                .build()
                .validate(response);

        //check cookie values that are not null that couldn't be checked above
        assertThat("",response.getCookie(expCookieDetails.get("JSESSIONID")), notNullValue());
        assertThat("",response.getCookie("lang_time"), notNullValue());
    }


    private Map<String, String> getCountryCookieData() {

        String countryCode = scenarioSession.getData("country code").toString();
        Map<String, String> cookies = new HashMap<String, String>();

        if (countryCode.equals("GB")) {
            cookies.put("JSESSIONID", "JSESSIONID_INTL");
            cookies.put("channel", "intl");
            cookies.put("country_iso", countryCode);
            cookies.put("lang_iso", "en");
        } else if (countryCode.equals("US")) {
            cookies.put("JSESSIONID", "JSESSIONID_AM");
            cookies.put("channel", "am");
            cookies.put("country_iso", countryCode);
            cookies.put("lang_iso", "en");
        } else if (countryCode.equals("CN")) {
            cookies.put("JSESSIONID", "JSESSIONID_APAC");
            cookies.put("channel", "apac");
            cookies.put("country_iso", countryCode);
            cookies.put("lang_iso", "zh");
        } else {
            fail("The country code '" + countryCode + "' isn't one of the three allowed values of GB, CN or US");
        }
        cookies.put("deviceType", "Desktop");

        return cookies;
    }


}
