package com.transsnet.more.cases;

import com.transsnet.more.appium.AppiumServerManager;
import com.transsnet.more.appium.Driver;
import com.transsnet.more.beans.ApkInfo;
import com.transsnet.more.beans.DeviceInfo;
import com.transsnet.more.util.AppUtil;
import com.transsnet.more.util.DevicesUtil;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.*;

/**
 * @Class: BaseCase
 * @Auther: Lance Mone
 * @Date: 2020/11/13 10:47
 * @Description:
 */

@Slf4j
public abstract class BaseCase {
    public static Driver driver;
    public static AppiumServerManager appiumServerManager;

    @BeforeSuite
    public void setupSuite() {
       log.info("setupSuite");
       appiumServerManager = new AppiumServerManager();
       appiumServerManager.startDefaultServer();
       ApkInfo apkInfo = AppUtil.getInstance().getApkInfo();
       DeviceInfo deviceInfo = DevicesUtil.getConnectedDevices().get(0);
       if (driver == null) {
           driver = Driver.getInstance();
       }
       try {
           log.info("初始化appium driver");
           driver.prepareFotAndroidDriver(deviceInfo, apkInfo, appiumServerManager.getCurrentServer());
           log.info("启动程序入口页面");
           driver.startActivity(apkInfo);
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    @BeforeTest
    public void setUpTest() {
//        driver = Driver.getInstance();
        log.info("setUpTest");
    }

    @BeforeClass
    public void setUpClass() {
        log.info("setUpClass");
        beforeClass();
    }

    public abstract void beforeClass();

    @BeforeMethod
    public void setUpMethod() {
        log.info("setUpMethod");
    }

    @AfterSuite
    public void tearDownSuite() {
        log.info("tearDownSuite");
        log.info("退出appium driver");
        driver.quit();
        log.info("关闭appium server");
        appiumServerManager.stopAppiumServer();
    }

    @AfterTest
    public void tearDownTest() {
        log.info("tearDownTest");
    }

    @AfterClass
    public void tearDownClass() {
        log.info("tearDownClass");
    }

    @AfterMethod
    public void tearDownMethod() {
        log.info("tearDownMethod");
    }
}
