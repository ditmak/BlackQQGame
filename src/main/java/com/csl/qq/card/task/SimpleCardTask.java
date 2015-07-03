package com.csl.qq.card.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

import com.csl.util.net.HTTPUtil;

public class SimpleCardTask extends BaseTask {
    private static String mainUrl="http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_mainpage?sid=";
    private static String fireCardUrl="http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_refine?show=1&pageno=1&fuin=0&steal=0";
    private static Pattern firecardPattern = Pattern.compile("\\[(\\d+)\\](.*?)已有(\\d+)张.*?(http.*?)\">",Pattern.DOTALL);
    private static String stealCardUrl="http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_strategy?&steal=1";
    private static Pattern boxsize = Pattern.compile("保险箱</a>.*?\\((\\d+)/(\\d+)\\)",Pattern.DOTALL);
    private static Pattern cardOperator = Pattern.compile("\\d+\\. (.*?)\\[(\\d+)\\].*?(http.*?)\">.*?(http.*?)\">",Pattern.DOTALL);
    private Random random = new Random();
    private String themeid;
    private String themeName;
    public SimpleCardTask(String sid,String themeid,String themeName) {
        this.sid=sid;
        this.themeid = themeid;
        this.themeName = themeName;
    }

    @Override
    public void doSomeThing() {
        System.out.println("任务开始-----");
        List<Element> aTagListByURL = HTTPUtil.getATagListByURL(mainUrl+sid);
        for (Element element : aTagListByURL) {
            String text = element.getTextTrim();
            if(text.equals("放入集卡册")){
                System.out.println("集卡成功");
                getCard(element.attributeValue("href"));
                return;
            }else if(text.startsWith("抽卡")){
                getTimeCard(element.attributeValue("href"));
                return;
            }else if(text.equals("炼卡")){
               fireCard(fireCardUrl +"&sid="+sid+"&tid="+themeid);
               return;
            }else if(text.equals("取卡")){
                getCard(element.attributeValue("href"));
                return;
            }else if(text.equals("偷炉")){
                fireCard(stealCardUrl+"&sid="+sid+"&fuin="+1093695691);
               return;
            }
        }
        System.out.println("end----");

    }
    private void getTimeCard(String url){
        //1.先抽了再说
        String content = HTTPUtil.getURLContent(url);
        settleBox(content,themeName);
        doSomeThing();
    }
    private void settleBox(String content,String themeName){
        int exists=0;
        int total=0;
        int empty=0;
        Matcher matcher = boxsize.matcher(content);
        if(matcher.find()){
           exists= Integer.parseInt(matcher.group(1));
           total = Integer.parseInt(matcher.group(2));
           empty = total - exists;
        }else{
            System.out.println("settleBox someThing is error");
            return;
        }
        matcher = cardOperator.matcher(content);
        while(matcher.find()){
            if(matcher.group(1).startsWith(themeName)||!matcher.group(2).equals("10")){
                if(empty-->0){
                    System.out.println("移动卡到保险箱--->" +matcher.group(1));
                    content=HTTPUtil.getURLContent(matcher.group(4).replaceAll("&amp;", "&"));
                }
            }else{
                System.out.println("卖掉卡--->" +matcher.group(1));
                content=HTTPUtil.getURLContent(matcher.group(3).replaceAll("&amp;", "&"));
            }
        }
    }
    
    
    
    private void getCard(String url) {
        HTTPUtil.getURLContent(url);
        doSomeThing();
    }

    private void fireCard(String url){
        //获取fireCardURl 页面内容
        String content = HTTPUtil.getURLContent(url, null, "GET");   
        Matcher matcher = firecardPattern.matcher(content);
        boolean flag = false;
        List<String> urls = new ArrayList<String>();
        while(matcher.find()){
            String price = matcher.group(1);
            String num = matcher.group(3);
            String fireCardurl = matcher.group(4).replaceAll("&amp;", "&");
            if(num.equals("0")&&!matcher.group(2).contains("正在合成中")){
                HTTPUtil.getURLContent(fireCardurl, null,"GET");
                flag=true;
                break;
            }
            if(price.equals("40")){
                urls.add(fireCardurl);
            }
        }
        if(!flag){
          int index =  random.nextInt(urls.size());
          HTTPUtil.getURLContent(urls.get(index), null , "GET");
        }
        doSomeThing();
    }

}
