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
    private static Pattern firecardPattern = Pattern.compile("\\[(\\d+)\\].*?已有(\\d+)张.*?(http.*?)\">",Pattern.DOTALL);
    private static String stealCardUrl="http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_strategy?&steal=1";
    private Random random = new Random();
    public SimpleCardTask(String sid) {
        this.sid=sid;
    }

    @Override
    public void doSomeThing() {
        List<Element> aTagListByURL = HTTPUtil.getATagListByURL(mainUrl+sid);
        for (Element element : aTagListByURL) {
            if(element.getTextTrim().equals("炼卡")){
               fireCard(fireCardUrl +"&sid="+sid+"&tid="+393);
               return;
            }else if(element.getTextTrim().equals("取卡")){
                getCard(element.attributeValue("href"));
                return;
            }else if(element.getTextTrim().equals("偷炉")){
                fireCard(stealCardUrl+"&sid="+sid+"&fuin="+1093695691);
               return;
            }
        }
        System.out.println("end----");

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
            String num = matcher.group(2);
            String fireCardurl = matcher.group(3).replaceAll("&ampl", "&");
            if(num.equals("0")){
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
