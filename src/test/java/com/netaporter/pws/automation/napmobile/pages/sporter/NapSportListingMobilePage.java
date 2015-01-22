package com.netaporter.pws.automation.napmobile.pages.sporter;

import com.netaporter.pws.automation.napmobile.pages.AbstractNapMobilePage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by a.mosincat on 29/07/2014.
 */

@Component
@Scope("cucumber-glue")
public class NapSportListingMobilePage extends AbstractNapMobilePage {

    public NapSportListingMobilePage(String pageName, String path) {
        super(pageName,path);
    }

  public boolean at() {
    return titleMobileComponent.titleMainHeadingText().equals("Sport");
    }
}