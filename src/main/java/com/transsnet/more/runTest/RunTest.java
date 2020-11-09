package com.transsnet.more.runTest;


import com.beust.jcommander.internal.Lists;
import com.transsnet.more.interfaces.ToolsPath;
import com.transsnet.more.interfaces.impl.ToolsPathImpl;
import com.transsnet.more.util.DevicesUtil;
import lombok.extern.slf4j.Slf4j;
import org.testng.TestNG;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

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

            while((s= br.readLine())!=null && !s.contains("udid")){
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
        System.out.println("" + DevicesUtil.getConnectedDevices());
    }
}
