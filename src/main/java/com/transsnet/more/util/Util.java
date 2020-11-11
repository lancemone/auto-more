package com.transsnet.more.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @Class: util
 * @Auther: Lance Mone
 * @Date: 2020/11/11 11:54
 * @Description: 通用工具类
 */

@Slf4j
public class Util {

    public static Boolean isWindows() {
        boolean windows = false;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            windows = true;
        }
        return windows;
    }

    /**
     * 执行终端命令
     *
     * @param s 要执行的命令  参数
     *
     */
    public static Process excuteShell(String s) {
        log.info("终端执行命令: " + s);
        Process proc;
        Runtime runtime = Runtime.getRuntime();
        try {
            proc = runtime.exec(s);
        } catch (Exception e) {
            log.error("执行命令:" + s + "出错啦！");
            return null;
        }
        return proc;
    }
}
