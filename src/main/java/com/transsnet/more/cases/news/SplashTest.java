package com.transsnet.more.cases.news;

import com.transsnet.more.cases.BaseCase;
import com.transsnet.more.pageObject.SplashPageObject;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @Class: SplashTest
 * @Auther: Lance Mone
 * @Date: 2020/11/12 17:44
 * @Description:
 */

@Slf4j
//@org.testng.annotations.Test
public class SplashTest extends BaseCase {

    SplashPageObject splashPageObject = new SplashPageObject();

    @Test
    public void testTitle() {
        log.info("testTitle");
        System.out.println("testTitle");
        driver.sleep(10);
        WebElement element = driver.findElement(splashPageObject.pageTitle());
        String text = element.getText();
        Assert.assertEquals(text, splashPageObject.pageTitle().getText());
    }

    @Test
    public void testOne() {
        System.out.println("hello");
    }

    @Override
    public void beforeClass() {

    }
}
