package com.transsnet.more.runTest;


import com.transsnet.more.appium.Driver;
import com.transsnet.more.beans.ApkInfo;
import com.transsnet.more.beans.DeviceInfo;
import com.transsnet.more.util.AppUtil;
import com.transsnet.more.util.DevicesUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Class: RunTest
 * @Auther: Lance Mone
 * @Date: 2020/11/9 15:54
 * @Description:
 */

@Slf4j
public class RunTest {

    public static String getUdid(String fileName){
        String udid = null;

        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String s = "";

            while(true){
                if ((s = br.readLine()) == null || s.contains("udid")) break;
            }
            br.close();

            int index = s.lastIndexOf("=") + 3;
            int indexEnd = s.lastIndexOf("\"");
            udid = s.substring(index,indexEnd);
            //log.info(udid);
        }catch(Exception e){
            log.error("Fail to get device udid from test suite xml");
            e.printStackTrace();
        }

        return udid;
    }

    // 使用TestNG执行用例
    public static void main(String[] args) {
//        TestNG testNG = new TestNG();
//        String xmlFile = args[0];
//        log.info("Test suite file " + xmlFile);
//        String udid = getUdid(xmlFile);
//        log.info("Device udid " + udid);
//        List<String> suites = Lists.newArrayList();
//        suites.add(xmlFile);
//        testNG.setTestSuites(suites);
//        testNG.run();

        AppiumDriver driver;
        ApkInfo apkInfo = AppUtil.getInstance().getApkInfo();
        DeviceInfo deviceInfo = DevicesUtil.getConnectedDevices().get(0);
        log.info(apkInfo.toString());
//        try {
//            Driver.driver = Driver.prepareFotAppium(deviceInfo, apkInfo, "4723");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Driver.startActivity(apkInfo);
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

        String url = "http://127.0.1:" + "4723" + "/wd/hub";
        log.info("appium连接地址: " + url);
        try {
            driver = new AppiumDriver(new URL(url), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
