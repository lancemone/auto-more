package com.transsnet.more.runTest;


import com.beust.jcommander.internal.Lists;
import com.transsnet.more.appium.AppiumServerManager;
import com.transsnet.more.appium.Driver;
import com.transsnet.more.beans.ApkInfo;
import com.transsnet.more.beans.DeviceInfo;
import com.transsnet.more.cases.news.SplashTest;
import com.transsnet.more.util.AppUtil;
import com.transsnet.more.util.DevicesUtil;
import lombok.extern.slf4j.Slf4j;
import org.testng.TestNG;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * @Class: RunTest
 * @Auther: Lance Mone
 * @Date: 2020/11/9 15:54
 * @Description:
 */

@Slf4j
public class RunTest {

    public static Driver driver;
    public static AppiumServerManager appiumServerManager;

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
        TestNG testNG = new TestNG();
        String xmlFile = null;
        try {
            File directory = new File("");//设定为当前文件夹
            xmlFile = Paths.get(directory.getCanonicalPath(), "testng.xml").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Test suite file " + xmlFile);
        List<String> suites = Lists.newArrayList();
        suites.add(xmlFile);
        testNG.setTestSuites(suites);
//        testNG.setTestClasses(new Class[]{SplashTest.class});
        testNG.run();
    }
}
