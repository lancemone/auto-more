package com.transsnet.more.beans;

import lombok.Data;

/**
 * @Class: DeviceInfo
 * @Auther: Lance Mone
 * @Date: 2020/11/9 18:19
 * @Description:
 */

@Data
public class DeviceInfo {
    // 设备连接状态
    private String device_state;

    // 设备序列号
    private String device_sn;

    // 设备产品名称
    private String device_product;

    // 设备型号
    private String device_model;

    // 设备名字
    private String device_name;

    // 设备系统版本
    private String device_osVersion;

    // 设备AndroidSDK版本
    private String device_sdkVersion;
}
