package com.csl.qq.card.task;

import java.util.List;

import org.dom4j.Element;

import com.csl.util.net.HTTPUtil;

public class SimpleFarmerTask extends BaseTask {
    private static String farmerUrl = "http://mcapp.z.qq.com/nc/cgi-bin/wap_farm_index?sid=";
    
    @Override
    public void doSomeThing() {
        System.out.println("任务开始-----");
        List<Element> aTagListByURL = HTTPUtil.getATagListByURL(farmerUrl+sid);
        for (Element element : aTagListByURL) {
            
        }
    }

}
