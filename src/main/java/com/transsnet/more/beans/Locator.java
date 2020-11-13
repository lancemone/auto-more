package com.transsnet.more.beans;

import com.transsnet.more.constant.Config;

/**
 * @Class: Locator
 * @Auther: Lance Mone
 * @Date: 2020/11/9 14:30
 * @Description: 页面元素定位信息实体类
 */

public class Locator {
    private String value;
    private int timout;
    private String locatorName;
    private ByType type;
    private String text;

    public enum ByType {
        xpath, id, linkText, name, className, cssSelector, partialLinkText, tagName
    }
    public Locator() {
    }

    public Locator(String element) {
        this.value = element;
        this.timout = Config.waitTime;
        this.type = ByType.xpath;

    }
    public Locator(String element, int waitSec) {
        this.timout = waitSec;
        this.value = element;
        this.type = ByType.xpath;
    }
    public Locator(String element, int waitSec, ByType byType) {
        this.timout = waitSec;
        this.value = element;
        this.type = byType;
    }

    public Locator(String element, int waitSec, ByType byType, String text) {
        this.timout = waitSec;
        this.value = element;
        this.type = byType;
        this.text = text;
    }

    public Locator(String element, int waitSec, ByType byType,String text, String locatorName) {
        this.timout = waitSec;
        this.value = element;
        this.type = byType;
        this.text = text;
        this.locatorName=locatorName;
    }
    public String getElement() {
        return value;
    }
    public int getWaitSec() {
        return timout;
    }
    public ByType getType() {
        return type;
    }
    public void setType(ByType byType) {
        this.type = byType;
    }
    public String getLocalorName()
    {
        return locatorName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getTimout() {
        return timout;
    }

    public void setTimout(int timout) {
        this.timout = timout;
    }

    public String getLocatorName() {
        return locatorName;
    }

    public void setLocatorName(String locatorName) {
        this.locatorName = locatorName;
    }

    public void setText(String text) {this.text = text;}

    public String getText() {return text;}
}
