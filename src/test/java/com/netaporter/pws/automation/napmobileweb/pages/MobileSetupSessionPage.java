package com.netaporter.pws.automation.napmobileweb.pages;

import com.netaporter.pws.automation.shared.pages.ISetupSessionPage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: a.michael@london.net-a-porter.com
 * Date: 18/11/2013
 * Time: 12:41PM
 * (C) DevilRacing666
 */
@Component
@Scope("cucumber-glue")
public class MobileSetupSessionPage extends AbstractMobileNapPage implements ISetupSessionPage {

    private static final String PAGE_NAME = "Session Setup";
    private static final String PATH = "SetupSession";


    public MobileSetupSessionPage() {
        super(PAGE_NAME, PATH);
    }
}
