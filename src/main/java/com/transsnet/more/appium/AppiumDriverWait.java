package com.transsnet.more.appium;

import com.transsnet.more.beans.Locator;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

/**
 * @Class: AppiumDriverWait
 * @Auther: Lance Mone
 * @Date: 2020/11/12 22:40
 * @Description:
 */

public class AppiumDriverWait extends FluentWait<AndroidDriver> {

    public AppiumDriverWait(AndroidDriver driver) {
        this(driver,5,5);
    }

    public static  AppiumDriverWait getInstance(AndroidDriver driver, Locator locator){
        return new AppiumDriverWait(driver,locator.getTimout());
    }

    public static  AppiumDriverWait getInstance(AndroidDriver driver,int seconds){
        return new AppiumDriverWait(driver,seconds);
    }

    public AppiumDriverWait(AndroidDriver driver, long timeOutInSeconds) {
        this(driver,timeOutInSeconds,5);
    }

    public AppiumDriverWait(AndroidDriver driver, long timeOutInSeconds, long duration) {
        super(driver);
        withTimeout(Duration.ofSeconds(timeOutInSeconds));
        pollingEvery(Duration.ofSeconds(duration));
    }
}
