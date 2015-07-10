package com.csl.qq.card.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.dom4j.Element;

import com.csl.execute.TaskExecutor;
import com.csl.util.net.HTTPUtil;

public class SimplePastureTasker extends BaseTask{
    private static String pastureURL = "http://mcapp.z.qq.com/nc/cgi-bin/wap_pasture_index?sid=";
    private static List<String> keyList = new ArrayList<String>();
    static{
        keyList.add("生产");
        keyList.add("收获");
        keyList.add("清扫");
    }
    @Override
    public void doSomeThing() {
        System.out.println("牧场开始");
        List<Element> aTagListByURL = HTTPUtil.getATagListByURL(pastureURL+sid);
        doMainPage(aTagListByURL);
        System.out.println("牧场结束");
    }
    private void doMainPage(List<Element> eles){
        for (Element aTag : eles) {
            String text = aTag.getTextTrim();
            String href = aTag.attributeValue("href");
            if(keyList.contains(text)){
                //static.... 跳过
                if(href.contains("static"))
                    continue;
                //生产的话，20秒之后添加一个任务
                if(text.equals("生产")){
                    TaskExecutor.addTask(this, 19, TimeUnit.SECONDS);
                }
                //收获 ..饲养 如果满了会有饲养吗？
                if(href.startsWith("."))
                    href =href.replace(".","http://mcapp.z.qq.com/nc/cgi-bin" );
                HTTPUtil.getURLContent(href);
                            }
        }
        
    }
}
