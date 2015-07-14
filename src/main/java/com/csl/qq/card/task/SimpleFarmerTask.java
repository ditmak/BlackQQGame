package com.csl.qq.card.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.dom4j.Element;

import com.csl.execute.TaskExecutor;
import com.csl.util.net.HTTPUtil;

public class SimpleFarmerTask extends BaseTask {
    private static String farmerUrl = "http://mcapp.z.qq.com/nc/cgi-bin/wap_farm_index?sid=";
    private static String fisherUrl = "http://mcapp.z.qq.com/nc/cgi-bin/wap_farm_fish_index?sid=";
    private static String prefix_url = "http://mcapp.z.qq.com/nc/cgi-bin";
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
    public  SimpleFarmerTask(String sid) {
        this.sid=sid;
    }
    @Override
    public void doSomeThing() {
        System.out.println("农场任务开始-----");
        List<Element> aTagListByURL = HTTPUtil.getATagListByURL(farmerUrl+sid);
        doFarmer(aTagListByURL);
        System.out.println("农场end--------");
        System.out.println("鱼农场开始--------");
        aTagListByURL = HTTPUtil.getATagListByURL(fisherUrl+sid);
        doFarmer(aTagListByURL);
        System.out.println("鱼农场end--------");
        TaskExecutor.addTask(this, 5, TimeUnit.MINUTES);
    }
    public static void doFarmer(List<Element> eles){
        for (Element element : eles) {
            String text = element.getTextTrim();
            if(keyList.contains(text)){
                System.out.println(text);
                String url = element.attributeValue("href");
                if(url.startsWith("."))
                    url = url.replace(".", prefix_url);
                List<Element> news= HTTPUtil.getATagListByURL(url);
                if(vali(eles,news))
                doFarmer(news);
                return ;
            }
        }
    }
    private static boolean vali(List<Element> eles,List<Element> news){
        if(news.size()!=eles.size())
            return true;
        int size = news.size()>eles.size()?news.size():eles.size();
        for (int i = 0; i < size; i++) {
            if(!eles.get(i).getTextTrim().equals(news.get(i).getTextTrim()))
                    return true;
        }
        
        return false;
    }

}
