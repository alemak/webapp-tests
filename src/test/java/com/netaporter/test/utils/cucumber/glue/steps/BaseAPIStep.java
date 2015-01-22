package com.netaporter.test.utils.cucumber.glue.steps;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.netaporter.test.utils.cucumber.steps.BaseStep;
import com.netaporter.test.utils.pages.driver.WebDriverUtil;
import com.netaporter.wishlist.woas.client.WoasClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: J.Christian@net-a-porter.com
 * Date: 02/05/2013
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
public class BaseAPIStep extends BaseStep {

    @Autowired
    protected WoasClient woasAPIClient;

    @Autowired
    protected WebDriverUtil webBot;

    private static final String REQUEST_SPEC_BUIDLER = "requestSpecBuilder";
    private static final String RESPONSE_SPEC_BUILDER = "responseSpecBuilder";

    public RequestSpecBuilder getRequestSpecBuilder() {
        RequestSpecBuilder requestSpecBuilder = (RequestSpecBuilder) scenarioSession.getData(REQUEST_SPEC_BUIDLER);

        if(requestSpecBuilder == null) {
            requestSpecBuilder = new RequestSpecBuilder();
            scenarioSession.putData(REQUEST_SPEC_BUIDLER, requestSpecBuilder);
        }

        return requestSpecBuilder;
    }

    public ResponseSpecBuilder getResponseSpecBuilder() {
        ResponseSpecBuilder respSpec = (ResponseSpecBuilder) scenarioSession.getData(RESPONSE_SPEC_BUILDER);

        if(respSpec == null) {
            respSpec = new ResponseSpecBuilder();
            scenarioSession.putData(RESPONSE_SPEC_BUILDER, respSpec);
        }

        return respSpec;
    }
}
