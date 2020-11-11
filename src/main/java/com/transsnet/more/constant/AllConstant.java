package com.transsnet.more.constant;

import java.nio.file.Paths;

/**
 * @Class: ToolsPath
 * @Auther: Lance Mone
 * @Date: 2020/11/11 11:50
 * @Description: 常量
 */

public class AllConstant {

    public static String rootDir = System.getProperties().getProperty("user.dir");

    public static String toolsDir = Paths.get(rootDir, "tools").toString();

    public static String appDir = Paths.get(rootDir, "app").toString();

}
