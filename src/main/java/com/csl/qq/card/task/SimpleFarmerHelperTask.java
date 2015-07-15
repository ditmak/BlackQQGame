package com.csl.qq.card.task;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.dom4j.Element;

import com.csl.execute.TaskExecutor;
import com.csl.util.net.HTTPUtil;

public class SimpleFarmerHelperTask extends BaseTask{
    private static String helpUrl = "http://mcapp.z.qq.com/nc/cgi-bin/wap_farm_status_list?sid=";
    private static String prefix_url = "http://mcapp.z.qq.com/nc/cgi-bin";
    private static int count =0;
    @Override
    public void doSomeThing() {
        List<Element> aTagListByURL = HTTPUtil.getATagListByURL(helpUrl+sid);
        for (Element element : aTagListByURL) {
            String url  = element.attributeValue("href");
            if(url.startsWith(".")&&url.length()>75){
                url = url.replace(".", prefix_url);
                System.out.println(element.getTextTrim()+"---");
                SimpleFarmerTask.doFarmer(HTTPUtil.getATagListByURL(url));
                if(++count>10){
                    count = 0;
                    break ;
                }
                System.out.println("----"+element.getTextTrim()+"---");
                doSomeThing();
                count = 0;
                break ;
            }
        }
    }
    public SimpleFarmerHelperTask(String sid){
        this.sid =sid;
        taskName="农场好友任务";
    }
}
