package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.shared.pages.ISetupSessionPage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: J.Christian@net-a-porter.com
 * Date: 21/08/2013
 * Time: 22:55
 * To change this template use File | Settings | File Templates.
 */
@Component
@Scope("cucumber-glue")
public class SetupSessionPage extends AbstractNapPage implements ISetupSessionPage {

    private static final String PAGE_NAME = "Session Setup";
    private static final String PATH = "SetupSession";


    public SetupSessionPage() {
        super(PAGE_NAME, PATH);
    }
}
