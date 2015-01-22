package com.netaporter.pws.automation.shared.steps.wishlistv3;

import com.netaporter.pws.automation.shared.pages.ISetupSessionPage;
import com.netaporter.pws.automation.shared.pages.IWishlistV3Page;
import com.netaporter.test.utils.cucumber.steps.LegacyWebAppBaseStep;
import com.netaporter.wishlist.woas.client.WoasClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 */
public class SharedWishlistBaseSteps extends LegacyWebAppBaseStep {

    /**
     * Will either be the mobile or desktop implementation of the page
     */
    @Autowired
    protected IWishlistV3Page wishListV3Page;

    @Autowired
    protected WoasClient woasAPIClient;
    @Autowired
    protected ISetupSessionPage setupSessionPage;
}
