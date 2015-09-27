package com.csl.qq.card.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.dom4j.Element;

import com.csl.execute.TaskExecutor;
import com.csl.util.net.HTTPUtil;

public class SimpleFarmerHelperTask extends BaseTask{
    private static String helpUrl = "http://mcapp.z.qq.com/nc/cgi-bin/wap_farm_status_list?sid=";
    private String prefix_uri = "http://mcapp.z.qq.com/nc/cgi-bin";
    private static List<String> keyList = new ArrayList<String>();
    static{
        keyList.add("收获");
        keyList.add("铲除");
        keyList.add("杀虫");
        keyList.add("浇水");
        keyList.add("除草");
        keyList.add("播种");
        keyList.add("种植");
        keyList.add("签到");
        keyList.add("偷取");
        keyList.add("摘取");
        keyList.add("捞鱼");
        keyList.add("养殖");
    }
    private static int count =0;
    @Override
    public void doSomeThing() {
        List<Element> aTagListByURL = HTTPUtil.getATagListByURL(helpUrl+sid);
        for (Element element : aTagListByURL) {
            String url  = element.attributeValue("href");
            if(url.startsWith(".")&&url.length()>75){
                url = url.replace(".", prefix_uri);
                System.out.println(element.getTextTrim()+"---");
                doFarmer(HTTPUtil.getATagListByURL(url));
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
    public  void doFarmer(List<Element> eles){
        for (Element element : eles) {
            String text = element.getTextTrim();
            if(keyList.contains(text)){
                String url = element.attributeValue("href");
                if(url.startsWith("."))
                    url = url.replace(".", prefix_uri);
                List<Element> news= HTTPUtil.getATagListByURL(url);
                doFarmer(news);
                return ;
            }
        }
    }
}
