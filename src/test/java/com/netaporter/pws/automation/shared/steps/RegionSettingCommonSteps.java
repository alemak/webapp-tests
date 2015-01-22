package com.netaporter.pws.automation.shared.steps;

import com.netaporter.test.utils.cucumber.steps.LegacyWebAppBaseStep;
import com.netaporter.test.utils.enums.RegionEnum;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: a.makarenko@london.net-a-porter.com
 * Date: 29/07/2013
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
public class RegionSettingCommonSteps extends LegacyWebAppBaseStep {
        Logger logger  = Logger.getLogger(getClass());
        static String regionProperty;

    @Given("^I am on (am|intl|apac)$")
    //NB! - using this method overrides the region system property. Do not use in channelized tests and
    //bear in mind that this will set the default region for all the tests after usage. Use @Before and @After hooks
    //to restore the system property rather than calling this method directly
    public void I_am_on_region(String region) throws Throwable {
        checkCanRunForRegion(region);
        setRegion(region);
        System.setProperty("region", region.toUpperCase());
    }

    private void checkCanRunForRegion(String region) {
        String regionsAvailable = System.getProperty("regions");
        if (regionsAvailable != null) {
            List<String> rs = Arrays.asList(regionsAvailable.toLowerCase().split(" *, *"));
            if (!rs.contains(region.toLowerCase())) {
                // Have to log the message, rather than throw it in the PendingException because Cucumber discards
                // the message in the PendingException :-(
                logger.info("region " + region + " not deployed, skipping test");
                throw new PendingException();
            }
        }
    }
    
    @Before("@region=INTL,@region=APAC,@region=AM")
        public void beforeRegScenario(Scenario scenario) throws NoSuchMethodException {
            regionProperty = System.getProperty("region");
            for(String tagname: scenario.getSourceTagNames()){
                if(tagname.contains("@region=")){
                    String reg = tagname.replaceFirst("@region=","");
                    setRegion(RegionEnum.valueOf(reg).name());
                    System.setProperty("region", RegionEnum.valueOf(reg).name());
                    break;
                }
            }
          }
    @After("@region=INTL," +
               "@region=APAC," +
               "@region=AM")
        public void afterIntlScenario(){
            restoreRegion();
        }
 

        private void restoreRegion() {
            if(regionProperty!=null)
                System.setProperty("region",regionProperty);
        }

    }
