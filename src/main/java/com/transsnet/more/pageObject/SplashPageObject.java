package com.transsnet.more.pageObject;

import com.transsnet.more.beans.Locator;

/**
 * @Class: SplashPage
 * @Auther: Lance Mone
 * @Date: 2020/11/12 20:55
 * @Description:
 */

public class SplashPageObject extends BasePageObject{

    public SplashPageObject() {
        getLocatorMap();
    }

    public Locator pageTitle() {
        return getLocator("pageTitle");
    }

    public Locator countryName() {
        return getLocator("countryName");
    }
}
