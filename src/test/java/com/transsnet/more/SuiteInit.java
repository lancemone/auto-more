package com.transsnet.more;

import com.transsnet.more.appium.Driver;
import com.transsnet.more.beans.ApkInfo;
import com.transsnet.more.beans.DeviceInfo;
import com.transsnet.more.util.AppUtil;
import com.transsnet.more.util.DevicesUtil;
import io.appium.java_client.android.AndroidDriver;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

/**
 * @Class: SuiteInit
 * @Auther: Lance Mone
 * @Date: 2020/11/11 17:42
 * @Description:
 */

@Slf4j
public class SuiteInit {

    @BeforeSuite
    public static void beforeSuite() {
        log.debug("初始化appium driver");
        ApkInfo apkInfo = AppUtil.getInstance().getApkInfo();
        DeviceInfo deviceInfo = DevicesUtil.getConnectedDevices().get(0);
        try {
            Driver.driver = (AndroidDriver) Driver.prepareFotAppium(deviceInfo, apkInfo, String.valueOf(4723));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterSuite
    public static void afterSuite() {
        Driver.driver.quit();
    }
}
