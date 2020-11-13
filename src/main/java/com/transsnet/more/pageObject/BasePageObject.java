package com.transsnet.more.pageObject;

import com.transsnet.more.beans.Locator;
import com.transsnet.more.util.XmlReadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @Class: BasePageObject
 * @Auther: Lance Mone
 * @Date: 2020/11/12 21:42
 * @Description:
 */

@Slf4j
public abstract class BasePageObject {

    protected HashMap<String, Locator> locatorMap;


    /**
     * 构造方法
     */
    public BasePageObject(){}

    protected void getLocatorMap() {
        XmlReadUtil xmlReadUtil = new XmlReadUtil();
        try {
            String pageName = this.getClass().getCanonicalName();
            locatorMap = xmlReadUtil.readUILibraryXML(pageName);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从对象库获取定位信息
     * @param locatorName 对象库名字
     * @return Locator 对象库信息
     */
    public  Locator getLocator(String locatorName) {
        Locator locator;
        /**
         * 在对象库通过对象名字查找定位信息
         */
        locator=locatorMap.get(locatorName);
        /**
         * 加入对象库，找不到该定位信息，就创建一个定位信息
         */
        if(locator==null)
        {
            log.error("没有找到"+locatorName+"页面元素");
        }
        return locator;
    }
}
