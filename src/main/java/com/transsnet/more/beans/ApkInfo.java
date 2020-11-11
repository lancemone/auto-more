package com.transsnet.more.beans;

import lombok.Data;

/**
 * @Class: ApkInfo
 * @Auther: Lance Mone
 * @Date: 2020/11/11 14:36
 * @Description:
 */

@Data
public class ApkInfo {

    private String apkPath;

    private String apkName;

    private String apkPn;

    private String apkVersion;

    private String apkMainActivity;
}
