package com.transsnet.more;

import com.transsnet.more.appium.Driver;
import com.transsnet.more.beans.ApkInfo;
import com.transsnet.more.util.AppUtil;
import org.junit.Test;

/**
 * @Class: Test1
 * @Auther: Lance Mone
 * @Date: 2020/11/11 17:51
 * @Description:
 */

public class Test1 {

    @Test
    public void testAppStart() {
        ApkInfo apkInfo = AppUtil.getInstance().getApkInfo();
        Driver.startActivity(apkInfo);
    }
}
