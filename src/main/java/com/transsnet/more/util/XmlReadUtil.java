package com.transsnet.more.util;

import com.transsnet.more.beans.Locator;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import com.transsnet.more.beans.Locator.*;

/**
 * @Class: XmlReadUtil
 * @Auther: Lance Mone
 * @Date: 2020/11/6 21:40
 * @Description: UILibrary对象库读取工具类
 */

@Slf4j
public class XmlReadUtil {

    public HashMap<String, Locator> readUILibraryXML(String pageName) {
        log.debug("开始解析UILibrary.xml元素对象库");
        log.debug("开始读取pageName内容");
        HashMap<String, Locator> locatorHashMap = new HashMap<>();
        try {
            String uiLibraryPath = new Object() {
                public String getPath() {
                    return Objects.requireNonNull(this.getClass().getClassLoader().getResource("UILibrary.xml")).getPath();
                }
            }.getPath();
            log.debug("uiLibraryPath: " + uiLibraryPath);
            File file = new File(uiLibraryPath);
            if (!file.exists()) {
                throw new IOException("Not Find " + uiLibraryPath);
            }
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            Element element = document.getRootElement();
            for (Iterator<?> el = element.elementIterator(); el.hasNext();) {
                Element page = (Element) el.next();
                if (page.attribute(0).getValue().equalsIgnoreCase(pageName)) {
                    for (Iterator<?> pn = page.elementIterator(); pn.hasNext();) {
                        log.debug("获取pageName" + pn);
                        String type = null;
                        String timeOut = "5";
                        String value = null;
                        String text = null;
                        String locatorName;
                        Element locator = (Element) pn.next();
                        //获取元素名
                        locatorName = locator.getText();
                        log.debug("读取" + locatorName + "详细信息");
                        for (Iterator<?> ln = locator.attributeIterator(); ln.hasNext();) {
                            Attribute attribute = (Attribute)ln.next();
                            switch (attribute.getName()) {
                                case "type":
                                    type = attribute.getValue();
                                    log.debug("读取定位方式:" + type);
                                    break;
                                case "timeout":
                                    if (!attribute.getValue().equals("")) {
                                        timeOut = attribute.getValue();
                                    }
                                    log.debug("读取元素等待时间 ：" + timeOut);
                                    break;
                                case "value":
                                    value = attribute.getValue();
                                    log.debug("读取定位内容：" + value);
                                    break;
                                case "text":
                                    text = attribute.getValue();
                                    log.debug("读取元素的文本: " + text);
                                    break;
                            }
                        }
                        Locator temp = new Locator(Objects.requireNonNull(value).trim(), Integer.parseInt(timeOut), getByType(type), text, locatorName.trim());
                        log.debug(locatorName + "内容读取完成");
                        locatorHashMap.put(locatorName, temp);
                    }
                }
            }
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        log.debug("解析UILibrary.xml对象库完毕\n");
        return locatorHashMap;
    }

    /**
     * @param type: 定位元素的类型
     */
    public static ByType getByType(String type) {
        ByType byType = null;
        if (type == null || type.equalsIgnoreCase("xpath")) {
            byType = ByType.xpath;
        } else if (type.equalsIgnoreCase("id")) {
            byType = ByType.id;
        } else if (type.equalsIgnoreCase("linkText")) {
            byType = ByType.linkText;
        } else if (type.equalsIgnoreCase("name")) {
            byType = ByType.name;
        } else if (type.equalsIgnoreCase("className")) {
            byType = ByType.className;
        } else if (type.equalsIgnoreCase("cssSelector")) {
            byType = ByType.cssSelector;
        } else if (type.equalsIgnoreCase("partialLinkText")) {
            byType = ByType.partialLinkText;
        } else if (type.equalsIgnoreCase("tagName")) {
            byType = ByType.tagName;
        }
        return byType;
    }

    public static  String getTestngParametersValue(String path,String ParametersName) throws DocumentException, IOException
    {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("Not find " + path);

        }
        String value=null;
        SAXReader reader = new SAXReader();
        Document  document = reader.read(file);
        Element root = document.getRootElement();
        for (Iterator<?> i = root.elementIterator(); i.hasNext();)
        {
            Element page = (Element) i.next();
            if(page.attributeCount()>0)
            {
                if (page.attribute(0).getValue().equalsIgnoreCase(ParametersName))
                {
                    value=page.attribute(1).getValue();
                    log.debug(ParametersName + "value: " + value + "\n");
                }
            }
        }
        return value;

    }
}
