package com.netaporter.test.utils.cucumber.glue.steps;

import com.jayway.restassured.http.ContentType;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class CommonAPISteps extends BaseAPIStep {

    @Given("^I am making an API request with a cookie set for the (intl|am|apac) channel$")
    public void I_am_making_an_API_request_with_a_cookies_set_for_the_channel(String channel) throws Throwable {
        getRequestSpecBuilder().addCookie("channel", channel);
    }


    @Given("^I am making an API request with a cookie set for the (.*) country$")
    public void I_am_making_an_API_request_with_a_cookies_set_for_the_country(String country) throws Throwable {
        getRequestSpecBuilder().addCookie("country_iso", country);
    }

    @Given("^I am making an API request with a cookie set for the (.*) language$")
    public void I_am_making_an_API_request_with_a_cookies_set_for_the_language(String language) throws Throwable {
        getRequestSpecBuilder().addCookie("lang_iso", language).addCookie("lang_time", ""+System.currentTimeMillis());
    }

    @Then("^the response code should be (\\d+)$")
    public void the_response_code_should_be(int responseCode) throws Throwable {
        getResponseSpecBuilder().expectStatusCode(responseCode);
    }

    @Then("^the content type is JSON$")
    public void the_content_type_is_JSON() throws Throwable {
        getResponseSpecBuilder().expectContentType(ContentType.JSON);
    }

}
