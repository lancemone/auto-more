package com.transsnet.more.appium;

import com.transsnet.more.constant.AllConstant;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @Class: AppiumServerManager
 * @Auther: Lance Mone
 * @Date: 2020/11/12 14:09
 * @Description:
 */

@Slf4j
public class AppiumServerManager {

    private AppiumDriverLocalService localService;

    public void startDefaultServer() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("noReset", "false");

        // Build the Appium service
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder()
                .withIPAddress(AllConstant.appiumIp)
                .usingPort(AllConstant.appiumPort)
                .withCapabilities(capabilities)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.LOG_LEVEL, AllConstant.appiumLogLever);
        localService = AppiumDriverLocalService.buildService(serviceBuilder);
        if (!checkPortIsRunning(AllConstant.appiumPort)) {
            log.info("启动appium 服务");
            localService.start();
        }else {
            log.error(AllConstant.appiumPort + "端口已被占用!");
        }
    }

    public void stopAppiumServer() {
        if (localService != null) {
            localService.stop();
        }
    }

    public AppiumDriverLocalService getCurrentServer() {
        return this.localService;
    }

    public boolean checkPortIsRunning(Integer port) {
        boolean isRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            isRunning = true;
        }finally {
            serverSocket = null;
        }
        return isRunning;
    }
}
