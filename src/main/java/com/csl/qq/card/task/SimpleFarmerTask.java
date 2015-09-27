package com.csl.qq.card.task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

import com.csl.util.io.ByteIOUtils;
import com.csl.util.net.HTTPUtil;

public class SimpleFarmerTask extends BaseTask {
    private static String farmerUrl = "http://mcapp.z.qq.com/nc/cgi-bin/wap_farm_index?sid=";
  //  private static String fisherUrl = "http://mcapp.z.qq.com/nc/cgi-bin/wap_farm_fish_index?sid=";
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
    }
    public  SimpleFarmerTask(String sid) {
        taskName="农场任务";
        this.sid=sid;
        prefix_uri = "http://mcapp.z.qq.com/nc/cgi-bin";
    }
    @Override
    public void doSomeThing() {
        List<Element> aTagListByURL = HTTPUtil.getATagListByURL(farmerUrl+sid);
        doFarmer(aTagListByURL);
        aTagListByURL = HTTPUtil.getATagListByURL(farmerUrl+sid);
        doFarmer(aTagListByURL);
    }
    public  void doFarmer(List<Element> eles){
        for (Element element : eles) {
            String text = element.getTextTrim();
            if(keyList.contains(text)){
                String url = element.attributeValue("href");
                if(text.equals("播种")){
                    plantSeed(url);
                    List<Element> aTagListByURL = HTTPUtil.getATagListByURL(farmerUrl+sid);
                    doFarmer(aTagListByURL);
                    return ;
                }
                List<Element> news= HTTPUtil.getATagListByURL(url);
                doFarmer(news);
                return ;
            }
        }
    }
    private  void plantSeed(String url){
        Map<String, String> seeds = getSeeds(url);
        List<String> expecteds = getSeedsConfig();
        if(seeds.size()<=0)
            return ;
        for (String expected : expecteds) {
           String value = seeds.get(expected.trim());
           if(value!=null){
               System.out.println("种植"+expected);
               getContent(value);
               return ;
           }
        }
        String value=seeds.values().iterator().next();
        getContent(value);
    }
    private Map<String,String> getSeeds(String url){
        String content =getContent(url);
        Pattern pattern = Pattern.compile("）(.*?)x.*?href=\"(.*?)\"",Pattern.DOTALL);
        Matcher matcher = pattern.matcher(content);
        Map<String,String> result = new HashMap<>();
        while (matcher.find()){
            result.put(matcher.group(1).trim(), matcher.group(2).replaceAll("&amp;", "&"));
        }
        int index = content.indexOf("下页");
        if(index!=-1){
            int secondI = content.lastIndexOf("\"", index);
            int fisrtI = content.lastIndexOf("\"",secondI-1);
            url =content.substring(fisrtI+1, secondI);
            result.putAll(getSeeds(url));
        }
        return result;
    }
    private List<String> getSeedsConfig(){
        File file = new File("seed.cfg");
        try {
            return  ByteIOUtils.getStrFromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<String>(0);
        }
    }

}
