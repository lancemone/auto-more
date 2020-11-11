package com.transsnet.more.constant;

/**
 * @Class: ToolPath
 * @Auther: Lance Mone
 * @Date: 2020/11/9 17:33
 * @Description:
 */

public class AdbCommand {

    public static String devices = "devices -l";

    public static String start = "start-server";

    public static String stop = "stop-server";

    // 获取手机系统版本
    public static String getOsVersion = "shell getprop ro.build.version.release";

    // 获取Android系统api版本
    public static String getOsApiVersion = "shell getprop ro.build.version.sdk";
}
