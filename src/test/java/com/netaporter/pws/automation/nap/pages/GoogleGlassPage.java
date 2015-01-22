package com.netaporter.pws.automation.nap.pages;

import com.netaporter.test.utils.pages.driver.WebDriverUtil;
import com.netaporter.test.utils.pages.regionalisation.RegionaliseWithCountrySlashLanguage;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class GoogleGlassPage extends AbstractNapPage {

    static Logger logger  = Logger.getLogger(GoogleGlassPage.class);
    private static final String PAGE_NAME = "Google Glass";
    //TODO: Remove device parameter when Glass goes responsive
    private static final String PATH = "product/495274";

    public GoogleGlassPage() {
        super(PAGE_NAME, PATH);
        setRegionalisePathBehavior(new RegionaliseWithCountrySlashLanguage());
    }
}