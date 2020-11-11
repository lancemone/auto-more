package com.transsnet.more.appium;

import com.transsnet.more.beans.ApkInfo;
import com.transsnet.more.beans.DeviceInfo;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

/**
 * @Class: Driver
 * @Auther: Lance Mone
 * @Date: 2020/11/11 14:31
 * @Description:
 */

@Slf4j
public class Driver {

    public static AppiumDriver driver;
    private static int devicesHeight;
    private static int deviceWidth;
    private static final int APP_START_WAIT_TIME = 20;

    public static void appRelaunch(DeviceInfo deviceInfo, ApkInfo apkInfo, String port) {
        log.info("Restart app...");
        try {
            driver = null;
            Driver.prepareFotAppium(deviceInfo, apkInfo, port);
        } catch (Exception e) {
            log.error("Fail to relaunch app");
            e.printStackTrace();
        }
    }

    public static void startActivity(ApkInfo apkInfo) {
        log.info("启动应用程序:" + apkInfo.getApkPn());
        Activity activity = new Activity(apkInfo.getApkPn(), apkInfo.getApkMainActivity());
        ((AndroidDriver)driver).startActivity(activity);
        log.info(apkInfo.getApkPn() + ":" + apkInfo.getApkMainActivity() + "Started");
    }

    public static void setDriver(Driver driver2){
        driver2 = driver2;
    }

    public static AppiumDriver prepareFotAppium(DeviceInfo deviceInfo, ApkInfo apkInfo, String port) throws Exception {
        log.info("apk package name: " + apkInfo.getApkPn());
        // 启动 Appiumm
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM, "Android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceInfo.getDevice_name());
        capabilities.setCapability(MobileCapabilityType.UDID, deviceInfo.getDevice_sn());
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, deviceInfo.getDevice_osVersion());
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 1800);

        // 配置测试apk
        capabilities.setCapability(MobileCapabilityType.APP, apkInfo.getApkPath());
        capabilities.setCapability("appPackage", apkInfo.getApkPn());
        capabilities.setCapability("appActivity", apkInfo.getApkMainActivity());
        capabilities.setCapability("sessionOverride", true);
        capabilities.setCapability("unicodeKeyboard",true); //支持中文输入
        capabilities.setCapability("resetKeyboard",true); //重置输入法为系统默认

        String url = "http://127.0.1:" + port + "/wd/hub";
        log.info("appium连接地址: " + url);
        driver = new AndroidDriver(new URL(url), capabilities);
        return driver;
    }
}
