package com.transsnet.more.util;

import com.transsnet.more.beans.ApkInfo;
import com.transsnet.more.constant.AllConstant;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @Class: AppUtil
 * @Auther: Lance Mone
 * @Date: 2020/11/11 15:25
 * @Description: 获取app包详细信息
 */

@Slf4j
public class AppUtil {

    private static AppUtil appUtil;
    private static final String PACKAGE = "package";
    private static final String APPLICATION_LABEL = "application-label:";
    public static final String VERSION_NAME = "versionName";
    public static final String LAUNCHABLE_ACTIVITY = "launchable";
    private static final String SPLIT_REGEX = "(: )|(=')|(' )|'";

    private AppUtil() {}

    public static synchronized AppUtil getInstance() {
        if (appUtil == null) {
            appUtil = new AppUtil();
        }
        return appUtil;
    }

    public ApkInfo getApkInfo() {
        Process process = null;
        ApkInfo apkInfo = new ApkInfo();
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            log.info("开始解析APK文件");
            process = Util.excuteShell( aaptPath() + " d" +  " badging " + appPath());
            inputStream = process.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            String temp = null;
            while ((temp = bufferedReader.readLine()) != null) {
                setApkInfo(apkInfo, temp);
            }
            apkInfo.setApkPath(appPath());
            return apkInfo;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (process != null) {
                process.destroy();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void setApkInfo(ApkInfo apkInfo, String source) {
        if (source.startsWith(PACKAGE)) {
            log.debug(source);
            String[] pack = source.split(SPLIT_REGEX);
            apkInfo.setApkPn(pack[2]);
            apkInfo.setApkVersion(String.format("%s-%s", pack[6], pack[4]));
        }else if (source.startsWith(APPLICATION_LABEL)){
            log.debug(source);
            apkInfo.setApkName(source.split(":")[1]);
        }else if (source.startsWith(LAUNCHABLE_ACTIVITY)){
            log.debug(source);
            String[] la = source.split(SPLIT_REGEX);
            apkInfo.setApkMainActivity(la[2]);
        }
    }

    private String appPath() {
        File appDir = new File(AllConstant.appDir);
        String path = null;
        File[] apps = appDir.listFiles();
        if (apps != null) {
            // 按照文件最后修改日期倒序排序
            Arrays.sort(apps, new Comparator<File>() {
                @Override
                public int compare(File file1, File file2) {
                    Long result = file2.lastModified() - file1.lastModified();
                    //先将Long的差值算出，然后视该值是否会超越int的最大值，然后返回不同结果
                    return result>=Integer.MAX_VALUE?Integer.MAX_VALUE:result.intValue();
                }
            });
            path = Paths.get(AllConstant.appDir, apps[0].getName()).toString();
        }else {
            log.error("未找到app文件");
        }
        log.info("app文件路径: " + path);
        return path;
    }

    private String aaptPath() {
        Path aaptPath;
        if (Util.isWindows()) {
            aaptPath = Paths.get(AllConstant.toolsDir, "aapt2.exe");
        }else {
            aaptPath = Paths.get(AllConstant.toolsDir, "aapt2");
        }
        return aaptPath.toString();
    }
}
