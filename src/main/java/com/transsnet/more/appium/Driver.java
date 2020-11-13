package com.transsnet.more.appium;

import com.transsnet.more.beans.ApkInfo;
import com.transsnet.more.beans.DeviceInfo;
import com.transsnet.more.beans.Locator;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @Class: Driver
 * @Auther: Lance Mone
 * @Date: 2020/11/11 14:31
 * @Description:
 */

@Slf4j
public class Driver {

    private static AndroidDriver androidDriver;
    private static int devicesHeight;
    private static int deviceWidth;
    private static final int APP_START_WAIT_TIME = 20;

    private static Driver driver;

    private Driver() {}

    public static Driver getInstance() {
        if (androidDriver == null) {
            driver = new Driver();
        }
        return driver;
    }

    public void appRelaunch(DeviceInfo deviceInfo, ApkInfo apkInfo, AppiumDriverLocalService service) {
        log.info("Restart app...");
        try {
            androidDriver = null;
            prepareFotAndroidDriver(deviceInfo, apkInfo, service);
        } catch (Exception e) {
            log.error("Fail to relaunch app");
            e.printStackTrace();
        }
    }

    public void startActivity(ApkInfo apkInfo) {
        log.info("启动应用程序:" + apkInfo.getApkPn());
        Activity activity = new Activity(apkInfo.getApkPn(), apkInfo.getApkMainActivity());
        if (androidDriver == null) {
            log.error("androidDriver is null");
        }else {
            androidDriver.startActivity(activity);
            log.info(apkInfo.getApkPn() + ":" + apkInfo.getApkMainActivity() + " Started");
        }
    }

    public void setAndroidDriver(Driver driver2){
        driver2 = driver2;
    }

    public void quit() {
        androidDriver.quit();
    }

    public void sleep(double seconds){
        try {
            Thread.sleep((int)(seconds * 1000));
        }catch (Exception e){
            log.info("Fail to sleep!!!!");
        }
    }

    /**
     * @param locator 元素信息
     * @return List<MobileElement> 根据元素查找的一组元素信息
     */
//    public static List<MobileElement> findElements(Locator locator) {
//        List<MobileElement> elements = null;
//        try {
//            elements = new
//        }
//    }

    public WebElement findElement(Locator locator) {
        WebElement element = null;
        try {
            log.info(String.format("通过[%s]查找元素:%s", locator.getType(), locator.getElement()));
            switch (locator.getType()) {
                case id:
                    element = androidDriver.findElement(By.id(locator.getElement()));
                    break;
                case name:
                    element = androidDriver.findElement(By.name(locator.getElement()));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return element;
    }

    public void prepareFotAndroidDriver(DeviceInfo deviceInfo, ApkInfo apkInfo, AppiumDriverLocalService service) throws Exception {
        log.info("apk package name: " + apkInfo.getApkPn());
        // 启动 Appiumm
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM, "Android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceInfo.getDevice_name());
        capabilities.setCapability(MobileCapabilityType.UDID, deviceInfo.getDevice_sn());
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, deviceInfo.getDevice_osVersion());
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true); // 覆盖安装
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 1800);

        // 配置测试apk
        capabilities.setCapability(MobileCapabilityType.APP, apkInfo.getApkPath());
        capabilities.setCapability("appPackage", apkInfo.getApkPn());
        capabilities.setCapability("appActivity", apkInfo.getApkMainActivity());
        capabilities.setCapability("sessionOverride", true);
        capabilities.setCapability("unicodeKeyboard",true); //支持中文输入
        capabilities.setCapability("resetKeyboard",true); //重置输入法为系统默认

        log.info("appium连接地址: " + service.getUrl());
        androidDriver =  new AndroidDriver(service.getUrl(), capabilities);
//        return androidDriver;
    }

}
