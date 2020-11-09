package com.transsnet.more.util;

import com.transsnet.more.beans.DeviceInfo;
import com.transsnet.more.constant.AdbCommand;
import com.transsnet.more.interfaces.impl.ToolsPathImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Class: DevicesUtil
 * @Auther: Lance Mone
 * @Date: 2020/11/9 17:08
 * @Description:
 */

@Slf4j
public class DevicesUtil {

    public static List<DeviceInfo> getConnectedDevices() {
        List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
        ArrayList<String> list = new ArrayList<>();
        Process process;
        BufferedReader reader;
        String line = null;
        String device_tpye;
        try {
            String adbPath = new ToolsPathImpl().adb();
            //执行adb device操作，查看pc当前连接手机或模拟器设备列表
            process = excuteShell(adbPath + AdbCommand.devices);
            if (process != null) {
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    if (line.length() > 1) {
                        list.add(line);
                    }
                }
                if(!list.contains("List of devices attached")) {
                    if (list != null && list.size() > 1) {
                        if(!list.contains("device")){
                            for (int i = 1; i < list.size(); i++) {
                                for (int j = 0; j < list.get(i).split(" ").length; j++) {
                                    //获取手机设备连接状态，目前状态有：device(正常)、offline、unauthorized
                                    device_tpye = list.get(i).split(" ")[j];
                                    //判断当前设备状态是否正常
                                    if(device_tpye.equals("device")){
                                        //获取设备序列号
                                        String device_sn = list.get(i).split(" ")[0];
                                        //获取设备产品名
                                        String device_product = list.get(i).split(" ")[8];
                                        //获取设备型号
                                        String device_model = list.get(i).split(" ")[9];
                                        //获取设备名称
                                        String device_name = list.get(i).split(" ")[10];
                                        log.debug("当前设备序列号:"+ device_sn);
                                        log.debug("当前设备产品名:"+ device_product);
                                        log.debug("当前设备型号:"+ device_model);
                                        log.debug("当前设备名称:"+ device_name);
                                        DeviceInfo deviceInfo = new DeviceInfo();
                                        deviceInfo.setDevice_sn(device_sn);
                                        deviceInfo.setDevice_product(device_product);
                                        deviceInfo.setDevice_model(device_model);
                                        deviceInfo.setDevice_name(device_name);
                                        deviceInfoList.add(deviceInfo);
                                    }
                                }
                            }
                        }
                    }else{
                        System.out.println("当前设备列表没有连接的设备，请检查！");
                    }
                }
            }else{
                System.out.println("当前执行adb命令异常，请检查adb环境！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deviceInfoList;
    }

    /**
     * 执行终端命令
     *
     * @param s 要执行的命令  参数
     *
     */
    public static Process excuteShell(String s) {
        Process proc = null;
        Runtime runtime = Runtime.getRuntime();
        try {
            proc = runtime.exec(s);
        } catch (Exception e) {
            System.out.print("执行命令:" + s + "出错啦！");
            return null;
        }
        return proc;
    }
}
