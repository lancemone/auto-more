package com.transsnet.more.interfaces.impl;

import com.transsnet.more.interfaces.ToolsPath;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Class: ToolsPathImpl
 * @Auther: Lance Mone
 * @Date: 2020/11/9 17:43
 * @Description:
 */

public class ToolsPathImpl implements ToolsPath {
    @Override
    public String adb() {
        String os = System.getProperty("os.name").toLowerCase();
        Path adbPath;
        //获取项目根目录
        String rootPath = this.getClass().getResource("/").getPath();
        if (os.contains("windows")) {
            adbPath = Paths.get(rootPath.substring(1), "tools", "adb.exe");
        }else {
            adbPath = Paths.get(rootPath, "tools", "adb");
        }
        return adbPath.toString();
    }

    @Override
    public String os() {
        return System.getProperty("os.name").toLowerCase();
    }

    @Override
    public String aapt() {
        return null;
    }
}
