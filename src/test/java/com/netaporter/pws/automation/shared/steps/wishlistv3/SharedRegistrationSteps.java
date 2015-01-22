package com.netaporter.pws.automation.shared.steps.wishlistv3;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.Cookie;
import com.jayway.restassured.response.Cookies;
import com.jayway.restassured.response.Response;
import com.netaporter.pws.automation.shared.utils.CustomerDetailsUtil;
import com.netaporter.test.utils.messaging.ProductCollectorMessageSender;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import static com.jayway.restassured.RestAssured.given;
import static com.netaporter.pws.automation.nap.components.RegisterAccountForm.DEFAULT_REGISTRATION_PASSWORD;
import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: a.kogan@london.net-a-porter.com
 * Date: 30/10/2013
 * Time: 14:53
 */
public class SharedRegistrationSteps extends SharedWishlistBaseSteps {
    public static final String REGISTERED_EMAIL_ADDRESS = "registeredEmailAddress";
    public static final String REGISTRATION_COOKIES = "registerationCookies";
    @Value("#{webDriverUtilConfig.baseUrl}")
    private String baseUrl;
    private @Value("${userAgentOverride}") String userAgentOverride;

    @Given("^I am a quickly registered user on (intl|am|apac)$")
    public void quickRegistration(String region) throws Throwable {
        // First check we are on a https page as we are about to try and set secure cookies in the browser
        String currentUrl = webBot.getCurrentUrl();
        if (!currentUrl.startsWith("https")) {
            fail("Current web page is not https.  In order to copy the secure cookies across to the browser, we need " +
                    "to be on a secured page.  Update your test suite to start on or navigate to a secure page" +
                    "before executing quick registration.  Current url: " + currentUrl);
        }


        /*
         TODO Review this.  A bit of a hack to improve speed registering users.  Awaiting Seaview client from proper solution.
         */
        String emailAddress = CustomerDetailsUtil.generateEmailAddress();
        Map<String,String> formParams = new HashMap<String,String>();
        formParams.put("email", emailAddress);
        formParams.put("firstName", "Test");
        formParams.put("lastName", "User");
        formParams.put("lastName", "User");
        formParams.put("password", DEFAULT_REGISTRATION_PASSWORD);
        formParams.put("verifyPassword", DEFAULT_REGISTRATION_PASSWORD);
        formParams.put("country", "GB");

        RequestSpecBuilder reqSpec = new RequestSpecBuilder();
        reqSpec.addFormParameters(formParams);
        reqSpec.addHeader("Content-Type", "application/x-www-form-urlencoded");

        // Need this as device cookie is returned
        if (userAgentOverride != null & !userAgentOverride.equals("${userAgentOverride}")){
            reqSpec.addHeader("User-Agent",userAgentOverride);
        }

        setRegion(region);
        System.setProperty("region",region.toUpperCase());
       // database.setRegion(region.toUpperCase());

        Response response = given().log().all().spec(reqSpec.build()).config(RestAssured.config()).expect()
                .post(baseUrl.replace("http:","https:") + region + "/lightRegistration.nap");
        scenarioSession.putData(REGISTRATION_COOKIES, response.getDetailedCookies());
        scenarioSession.putData(REGISTERED_EMAIL_ADDRESS, emailAddress);

        Cookies cookies = (Cookies) scenarioSession.getData(REGISTRATION_COOKIES);
        Iterator<Cookie> iter = cookies.iterator();
        while(iter.hasNext()) {
            Cookie next = iter.next();
            if (webBot.getCookie(next.getName()) != null) {
                webBot.deleteCookieNamed(next.getName());
            }
            webBot.addCookie(convertCookie(next));
        }
    }

   private org.openqa.selenium.Cookie convertCookie(Cookie cookie) {
        return new org.openqa.selenium.Cookie(cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath(), cookie.getExpiryDate(), cookie.isSecured() );
    }


}