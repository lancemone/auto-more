package com.transsnet.more.util;

import com.transsnet.more.constant.AllConstant;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @Class: AdbUtil
 * @Auther: Lance Mone
 * @Date: 2020/11/10 17:27
 * @Description: ADB命令工具类
 */

@Slf4j
public class AdbUtil {

    private static AdbUtil adbUtil;

    private AdbUtil() {}

    public static synchronized AdbUtil getInstance() {
        if (adbUtil == null) {
            adbUtil = new AdbUtil();
        }
        return adbUtil;
    }


    public InputStream adbCommand(String s) {
        String command = adbPath() + " " + s;
        InputStream inputStream = null;
        Process proc = Util.excuteShell(command);
        if (proc != null) {
            inputStream = proc.getInputStream();
        }
        return inputStream;
    }

    public String adbCommand2String(String s) {
        String result = null;
        String line;
        StringBuffer buffer = new StringBuffer();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = adbCommand(s);
            if (inputStream != null) {
                reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                    }
                result = Objects.requireNonNull(buffer).toString();
                }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (reader != null) {
                    reader.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private String adbPath() {
        Path adbPath;
        if (Util.isWindows()) {
            adbPath = Paths.get(AllConstant.toolsDir, "adb.exe");
        }else {
            adbPath = Paths.get(AllConstant.toolsDir, "adb");
        }
        return adbPath.toString();
    }
}
