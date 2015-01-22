package com.netaporter.pws.automation.nap.cucumber.steps.apis.sitefurniture;

import com.netaporter.test.utils.cucumber.glue.steps.BaseAPIStep;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static com.netaporter.test.utils.assertion.html.HtmlTextXPathMatcher.mustachedHtmlContainsTextAtXPath;
import static com.netaporter.test.utils.assertion.html.HtmlTextXPathMatcher.mustachedHtmlHasXPath;
import static com.netaporter.test.utils.assertion.json.JSONSchemaMatcher.matchesJSONSchema;
import static org.hamcrest.Matchers.*;

//import static com.netaporter.test.utils.assertion.html.HtmlTextXPathMatcher.mustachedHtmlContainsTextAtXPath;
//import static com.netaporter.test.utils.assertion.html.HtmlTextXPathMatcher.mustachedHtmlHasXPath;
//import static com.netaporter.test.utils.assertion.json.JSONSchemaMatcher.matchesJSONSchema;


public class SiteFurnitureSteps extends BaseAPIStep {

    public static final String CHANNEL = "channel";

    public static String getSiteFurnitureURL(String channel, String baseUrl) {
        String url = baseUrl + getSiteFurniturePath(channel);
        return url;
    }

    public static String getSiteFurniturePath(String channel) {
        String path = "";

        if (channel != null) {
            path += channel + "/";
            path += "api/site-furniture.nap";
        } else {
            path += "webapi/site-furniture.nap";
        }

        return path;
    }

    // TODO factor out to a proper schema file
    private static final String jsonSchema = "{\n" +
            "    \"title\": \"Site Furniture API response schema\",\n" +
            "    \"type\": \"object\",\n" +
            "    \"properties\": {\n" +
            "        \"blocks\": {\n" +
            "            \"type\": \"object\",\n" +
            "            \"properties\": {\n" +
            "                \"header\": {\n" +
            "                    \"type\": \"string\"\n" +
            "                },\n" +
            "                \"footer\": {\n" +
            "                    \"type\": \"string\"\n" +
            "                },\n" +
            "                \"layout\": {\n" +
            "                    \"type\": \"string\"\n" +
            "                }\n" +
            "            },\n" +
            "            \"required\": [\"header\", \"footer\", \"layout\"]\n" +
            "        }\n" +
            "    },\n" +
            "    \"required\": [\"blocks\"]\n" +
            "}";

    @Value("#{webDriverUtilConfig.baseUrl}")
    private String baseUrl;

    @Given("^I am making a site-furniture API request with the (am|intl|apac) channel specified in the URL path$")
    public void I_am_making_an_API_request_for_the_webapp_API(String channel) throws Throwable {
        scenarioSession.putData(CHANNEL, channel);
    }

    @Given("^I am making a site-furniture API request without specifying the channel$")
    public void I_am_making_an_API_request_for_the_webapp_API_without_sepcifying_the_channel() throws Throwable {
        // Do not set the channel
    }

    @And("^I am requesting the mobile version of it$")
         public void i_am_requesting_the_mobile_version_of_it() {
        getRequestSpecBuilder().addQueryParam("mobile", true);
    }

    @And("^I am NOT requesting the mobile version of it$")
    public void i_am_not_requesting_the_mobile_version_of_it() {
        getRequestSpecBuilder().addQueryParam("mobile", false);
    }

    @Then("^the response code should match the Site Furniture JSON Schema$")
    public void the_response_code_should_match_the_json_schema() throws Throwable {
        getResponseSpecBuilder().expectBody(matchesJSONSchema(jsonSchema));

    }


    @Then("^the response contains the standard NAP header and footer$")
    public void the_response_contains_the_standard_NAP_header_and_footer() throws Throwable {
        getResponseSpecBuilder().expectBody("blocks.header", startsWith("<div id=\"header\">"))
                .expectBody("blocks.footer", startsWith("<div id=\"footer\">"));
    }

    @Then("^the response contains the body tag with (am|intl|apac)$")
    public void the_response_contains_the_body_tag_with_channel(String channel) throws Throwable {
        getResponseSpecBuilder().expectBody("blocks.layout", containsString("<body class=\"" + channel + " "));
    }

    @Then("^the response contains the body tag with lang-(en|fr|de|zh)$")
    public void the_response_contains_the_body_tag_with_lang(String language) throws Throwable {
        getResponseSpecBuilder().expectBody("blocks.layout", containsString(" lang-" + language + "\">"));
    }

    @Then("^the response contains the head tag with (.*) specified as the country name$")
    public void the_response_contains_the_head_tag_with_country_name(String countryName) throws Throwable {
        getResponseSpecBuilder().expectBody("blocks.header", containsString(countryName));
    }

    @Then("^the response contains the footer tag with (.*) specified as the country name$")
    public void the_response_contains_the_footer_tag_with_country_name(String countryName) throws Throwable {
        getResponseSpecBuilder().expectBody("blocks.footer", containsString(countryName));
    }

    @Then("^the response contains the body with CoreMetrics setup but no specific tags$")
    public void the_response_contains_the_body_with_CoreMetrics_setup_but_to_specific_tags() throws Throwable {
        getResponseSpecBuilder()
                // Check coremetrics scripts included
                .expectBody("blocks.layout", containsString("eluminate.js"))
                .expectBody("blocks.layout", containsString("techprops.js"))
                .expectBody("blocks.layout", containsString("cmdatatagutils.js"))

                // Check coremetrics initiated
                .expectBody("blocks.layout", containsString("cmSetProduction();"))
                .expectBody("blocks.layout", containsString("cm_TrackImpressions=\"S\";"))

                // Check no specific coremetrics tags are included
                .expectBody("blocks.layout", not(containsString("cmCreate")))
                .expectBody("blocks.header", not(containsString("cmCreate")))
                .expectBody("blocks.footer", not(containsString("cmCreate")));

    }

    @Then("^the response contains the body without the trackPage.nap tags$")
    public void the_response_contains_the_body_without_the_trackPage_nap_tags() throws Throwable {
        getResponseSpecBuilder()
                .expectBody("blocks.layout", not(containsString("trackpage.nap")))
                .expectBody("blocks.header", not(containsString("trackpage.nap")))
                .expectBody("blocks.footer", not(containsString("trackpage.nap")));
    }


    @Then("^the response contains the expected handlebars tags$")
    public void the_response_contains_the_expected_handlebars_tags() throws Throwable {
        Map<String, Object> fixtureData = new HashMap<String, Object>();
        fixtureData.put("header", "MY_HEADER");
        fixtureData.put("footer", "MY_FOOTER");
        fixtureData.put("content", "MY_CONTENT");
        fixtureData.put("title", "MY_TITLE");

        List<String> styles = new ArrayList();
        styles.add("MY/STYLE_1.CSS");
        styles.add("MY/STYLE_2.CSS");
        fixtureData.put("styles", styles);

        List<String> scripts = new ArrayList();
        scripts.add("MY/SCRIPT_1.JS");
        scripts.add("MY/SCRIPT_2.JS");
        fixtureData.put("scripts", scripts);

        fixtureData.put("bootstrap", "MY_BOOTSTRAP");

        getResponseSpecBuilder()
                .expectBody("blocks.layout", mustachedHtmlContainsTextAtXPath("//*[@id='main']", "MY_HEADER", fixtureData))
                .expectBody("blocks.layout", mustachedHtmlContainsTextAtXPath("//*[@id='main']", "MY_FOOTER", fixtureData))
                .expectBody("blocks.layout", mustachedHtmlContainsTextAtXPath("//*[@id='content']", "MY_CONTENT", fixtureData))
                .expectBody("blocks.layout", mustachedHtmlContainsTextAtXPath("//title", "MY_TITLE", fixtureData))

                .expectBody("blocks.layout", mustachedHtmlHasXPath("//head/link[@href=\"MY/STYLE_1.CSS\"]", fixtureData))
                .expectBody("blocks.layout", mustachedHtmlHasXPath("//head/link[@href=\"MY/STYLE_2.CSS\"]", fixtureData))

                .expectBody("blocks.layout", mustachedHtmlHasXPath("//body/script[text()=\"MY_BOOTSTRAP\"]", fixtureData))
                .expectBody("blocks.layout", mustachedHtmlHasXPath("//body/script[@src=\"MY/SCRIPT_1.JS\"]", fixtureData))
                .expectBody("blocks.layout", mustachedHtmlHasXPath("//body/script[@src=\"MY/SCRIPT_2.JS\"]", fixtureData))

                // Ensure no handlebars tags in header or footer
                .expectBody("blocks.header", not(containsString("{{")))
                .expectBody("blocks.footer", not(containsString("{{")));

    }


    @Then("^the response contains the mobile version of the header, layout and footer$")
    public void the_response_contains_the_mobile_version_of_the_header_layout_and_footer() throws Throwable {
        getResponseSpecBuilder()
                .expectBody("blocks.header", containsString("mobile-top-nav"))
                //.expectBody("blocks.layout", containsString("mobile-top-nav"))
                .expectBody("blocks.footer", containsString("mobile-footer-nav"));
    }

    @Then("^the response does not contain the mobile version of the header, layout and footer$")
    public void the_response_does_not_contain_the_mobile_version_of_the_header_layout_and_footer() throws Throwable {
        getResponseSpecBuilder()
                .expectBody("blocks.header", not(containsString("mobile-top-nav")))
                .expectBody("blocks.layout", not(containsString("mobile-top-nav")))
                .expectBody("blocks.footer", not(containsString("mobile-footer-nav")));
    }


    @When("^I access the site furniture api$")
    public void I_access_the_site_furniture_api() throws Throwable {

        String url = getSiteFurnitureURL(
                (String) scenarioSession.getData(CHANNEL), baseUrl);

        given(getRequestSpecBuilder().build(), getResponseSpecBuilder().build()).get(url);
    }




}
