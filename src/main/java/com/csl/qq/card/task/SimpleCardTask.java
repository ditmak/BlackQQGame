package com.csl.qq.card.task;

import java.util.List;

import org.dom4j.Element;

import com.csl.util.net.HTTPUtil;

public class SimpleCardTask extends BaseTask {
    private static String mainUrl="http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_mainpage?sid=";

    public SimpleCardTask(String sid) {
        this.sid=sid;
    }

    @Override
    public void doSomeThing() {
        List<Element> aTagListByURL = HTTPUtil.getATagListByURL(mainUrl+sid);
        for (Element element : aTagListByURL) {
            if(element.getTextTrim().equals("炼卡")){
                   System.out.println(element.attributeValue("href"));
            }
        }

    }

}
