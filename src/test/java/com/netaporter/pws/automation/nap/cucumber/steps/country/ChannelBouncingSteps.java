package com.netaporter.pws.automation.nap.cucumber.steps.country;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.Cookies;
import com.jayway.restassured.response.Response;
import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Value;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;


/**
 * Created with IntelliJ IDEA.
 * User: jgchristian
 * Date: 07/11/2013
 * Time: 19:24
 * To change this template use File | Settings | File Templates.
 */
public class ChannelBouncingSteps extends BaseNapSteps{

    @Value("#{webDriverUtilConfig.baseUrl}")
    private String baseUrl;

    public static final String RESPONSE_COOKIES = "BOUNCE_RESPONSE_COOKIES";
    public static final String RESPONSE = "BOUNCE_RESPONSE";

    private static final String QUERY_STRING = "?trackedPageStr=HOME&serverRand=9026016&exclude=t&rand=8287313524633.646";

    @When("^I request trackpage.nap from a default INTL country with GEO header set to (.*)$")
    public void requestTrackPageFromDefaultINTLChannelWithGEOHeader(String geoCountryISO){
        requestTrackPageFromDefaultINTLChannelWithGEOHeaderOptionalSwitchedCookie(geoCountryISO, getTrackPageURL("intl", false), false, "intl");
    }

    @When("^I request https trackpage.nap from a default INTL country with GEO header set to (.*)$")
    public void requestHTTPSTrackPageFromDefaultINTLChannelWithGEOHeader(String geoCountryISO){
        requestTrackPageFromDefaultINTLChannelWithGEOHeaderOptionalSwitchedCookie(geoCountryISO, getTrackPageURL("intl", true), false, "intl");
    }

    @When("^I request the redirected URL with the switched cookie set, GEO header set to (.*) and country_iso as GB and channel as set in the response$")
    public void requestTrackPageWithSwitchedCookieSetFoINTLandGBWithGEOHeader(String geoCountryISO){
        String url = getResponse().getHeader("Location");
        String channel = getResponseCookies().getValue("channel");
        requestTrackPageFromDefaultINTLChannelWithGEOHeaderOptionalSwitchedCookie(geoCountryISO, url, true, channel);
    }

    private void requestTrackPageFromDefaultINTLChannelWithGEOHeaderOptionalSwitchedCookie(String geoCountryISO, String url, boolean setSwitchedCookie, String channelCookieValue){
        RequestSpecBuilder reqSpec = new RequestSpecBuilder();
        reqSpec.addHeader("User-Agent", "Mozilla/5.0 (Actually rest assured test client)");
        reqSpec.addHeader("GEO", "COUNTRIES:" + geoCountryISO.toUpperCase());
        reqSpec.addCookie("channel",channelCookieValue);
        reqSpec.addCookie("country_iso","GB");
        if (setSwitchedCookie) {
            reqSpec.addCookie("wps2", "1");
        }

        Response response = given().redirects().follow(false).spec(reqSpec.build()).expect().get(url);
        scenarioSession.putData(RESPONSE, response);
        scenarioSession.putData(RESPONSE_COOKIES, response.getDetailedCookies());
    }

    private String getTrackPageURL(String channel, boolean https) {
        String url = baseUrl + channel + "/trackpage.nap" + QUERY_STRING;
        if (!https) {
            url = url.replaceFirst("https", "http");
        }
        return url;
    }

    @Given("^I make a request to (.*) from a default INTL country with GEO header set to (.*)$")
    public void requestURL(String path, String geoCountryISO){
        String url = baseUrl + path;

        RequestSpecBuilder reqSpec = new RequestSpecBuilder();
        reqSpec.addHeader("User-Agent", "Mozilla/5.0 (Actually rest assured test client)");
        reqSpec.addHeader("GEO", "COUNTRIES:" + geoCountryISO.toUpperCase());
        reqSpec.addCookie("channel","intl");
        reqSpec.addCookie("country_iso","GB");

        Response response = given().redirects().follow(false).spec(reqSpec.build()).expect().get(url);
        scenarioSession.putData(RESPONSE, response);
        scenarioSession.putData(RESPONSE_COOKIES, response.getDetailedCookies());
    }




    @Then("^the channel cookie is returned as (.*)$")
    public void checkChannelCookieMatches(String channel) {
        assertThat("channel cookie set to " + channel, getResponseCookies().getValue("channel").equals(channel));
    }

    @Then("^the channel cookie is not in the response$")
    public void checkChannelCookieNotInResponse() {
        assertThat("channel cookie not in response", !getResponseCookies().hasCookieWithName("channel"));
    }

    @Then("^the country_iso cookie is returned as (.*)$")
    public void checkCountryISOCookieMatches(String countryISO) {
        assertThat("country_iso cookie set to " + countryISO, getResponseCookies().getValue("country_iso").equals(countryISO));
    }

    @Then("^the country_iso cookie is not in the repsonse$")
    public void checkCountryISONotInRepsonse() {
        assertThat("country_iso not in the repsonse", !getResponseCookies().hasCookieWithName("country_iso"));
    }

    @Then("^the channel switched cookie is in response$")
    public void checkSwitchedCookieSet() {
        assertThat("'wps2' channel switched cookie set", getResponseCookies().getValue("wps2").equals("1"));
    }

    @Then("^the channel switched cookie is not in response$")
    public void checkSwitchedCookieNotSet() {
        assertThat("'wps2' channel switched cookie not set", !getResponseCookies().hasCookieWithName("wps2"));
    }

    @Then("^the response is a (http|https) 302 to trackpage.nap on (.*)$")
    public void checkRedirect(String protocol, String redirectChannel) {
        assertThat("302 response", getResponse().getStatusCode()==302);

        String expectedLocation = baseUrl + redirectChannel + "/trackpage.nap" + QUERY_STRING;
        if (protocol.equals("http")) {
            expectedLocation = expectedLocation.replaceFirst("https", "http");
        }

        assertEquals("Expected location: " + expectedLocation, getResponse().getHeader("Location"), expectedLocation);
    }

    private Response getResponse() {
        return (Response) scenarioSession.getData(RESPONSE);
    }

    private Cookies getResponseCookies() {
        return (Cookies) scenarioSession.getData(RESPONSE_COOKIES);
    }
}
