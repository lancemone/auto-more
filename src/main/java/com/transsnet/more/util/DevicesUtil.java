package com.transsnet.more.util;

import com.transsnet.more.beans.DeviceInfo;
import com.transsnet.more.constant.AdbCommand;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Class: DevicesUtil
 * @Auther: Lance Mone
 * @Date: 2020/11/9 17:08
 * @Description:
 */

@Slf4j
public class DevicesUtil {

    public static Pattern patternProduct = Pattern.compile("product:(.+?) ");
    public static Pattern patternModel = Pattern.compile("model:(.+?) ");
    public static Pattern patternDevice = Pattern.compile("device:(.+?) ");

    public static List<DeviceInfo> getConnectedDevices() {
        List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader;
        String line;
        String device_tpye;
        try {
            //执行adb device操作，查看pc当前连接手机或模拟器设备列表
            InputStream inputStream = AdbUtil.getInstance().adbCommand(AdbCommand.devices);
            if (inputStream != null) {
                reader = new BufferedReader(new InputStreamReader(inputStream));
                log.debug("控制台输出:");
                while ((line = reader.readLine()) != null) {
                    if (line.length() > 1) {
                        log.debug(line);
                        list.add(line);
                    }
                }
                if(list.get(0).equals("List of devices attached")) {
                    if (list.size() > 1) {
                        String device_sn;
                        String device_product;
                        String device_model;
                        String device_name;
                        if(!list.contains("device")){
                            Matcher matcher;
                            for (int i =1; i<list.size(); i++) {
                                String device = list.get(i);
                                if (device.contains("device")) {
                                    device_tpye = "device";
                                    //获取设备序列号
                                    device_sn = device.substring(0, device.indexOf(" "));
                                    //获取设备产品名
                                    matcher = patternProduct.matcher(device);
                                    if (matcher.find()) {
                                        device_product = matcher.group(1);
                                    } else {
                                        device_product = null;
                                    }
                                    //获取设备型号
                                    matcher = patternModel.matcher(device);
                                    if (matcher.find()) {
                                        device_model = matcher.group(1);
                                    } else {
                                        device_model = null;
                                    }
                                    //获取设备名称
                                    matcher = patternDevice.matcher(device);
                                    if (matcher.find()) {
                                        device_name = matcher.group(1);
                                    } else {
                                        device_name = null;
                                    }
                                    DeviceInfo deviceInfo = new DeviceInfo();
                                    deviceInfo.setDevice_sn(device_sn);
                                    deviceInfo.setDevice_state(device_tpye);
                                    deviceInfo.setDevice_product(device_product);
                                    deviceInfo.setDevice_model(device_model);
                                    deviceInfo.setDevice_name(device_name);
                                    deviceInfoList.add(deviceInfo);
                                }
                            }
                        }
                    }else{
                        log.debug("当前设备列表没有连接的设备，请检查！");
                    }
                }
            }else{
                log.debug("当前执行adb命令异常，请检查adb环境！");
            }
            if (!deviceInfoList.isEmpty()) {
                String device_os_version;
                String device_sdk_version;
                for (Iterator<DeviceInfo> deviceInfoIterator=deviceInfoList.listIterator(); deviceInfoIterator.hasNext();){
                    DeviceInfo deviceInfo = deviceInfoIterator.next();
                    device_os_version =  excuteShellByDevice2String(deviceInfo.getDevice_sn(), AdbCommand.getOsVersion);
                    device_sdk_version = excuteShellByDevice2String(deviceInfo.getDevice_sn(), AdbCommand.getOsApiVersion);
                    deviceInfo.setDevice_osVersion(device_os_version);
                    deviceInfo.setDevice_sdkVersion(device_sdk_version);
                    log.debug("device info: " + deviceInfo.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deviceInfoList;
    }

    public static InputStream excuteShellByDevice(String deviceSn, String command) {
        String commandByDevice = "-s " + deviceSn + " " + command;
        return AdbUtil.getInstance().adbCommand(commandByDevice);
    }

    public static String excuteShellByDevice2String(String deviceSn, String command) {
        String commandByDevice = "-s " + deviceSn + " " + command;
        return AdbUtil.getInstance().adbCommand2String(commandByDevice);
    }

}
