package com.csl.qq.card.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

import com.csl.execute.TaskExecutor;
import com.csl.util.net.HTTPUtil;

public class SimplePastureTasker extends BaseTask {
    private static String pastureURL = "http://mcapp.z.qq.com/mc/cgi-bin/wap_pasture_index?sid=";
    private static List<String> keyList = new ArrayList<String>();
    private static Pattern canPattern  = Pattern.compile("[(](\\d+)[/](\\d+).*?[(](\\d+)[/](\\d+)");
    private static  Pattern woPattern = Pattern.compile("窝\\).*?href=\"(.*?)\">饲养",Pattern.DOTALL);
    private static  Pattern pengPattern = Pattern.compile("棚\\).*?href=\"(.*?)\">饲养",Pattern.DOTALL);
    static {
        keyList.add("生产");
        keyList.add("收获");
        keyList.add("清扫");
        keyList.add("饲养");
    }

    public SimplePastureTasker(String sid) {
        this.sid = sid;
    }

    @Override
    public void doSomeThing() {
        List<Element> aTagListByURL = HTTPUtil.getATagListByURL(pastureURL
                + sid);
        doMainPage(aTagListByURL);
    }

    private void doMainPage(List<Element> eles) {
        boolean flag = false;
        boolean bflag = false;
        for (Element aTag : eles) {
            String text = aTag.getTextTrim();
            String href = aTag.attributeValue("href");
            if (keyList.contains(text)) {
                // static.... 跳过
                if (href.contains("static"))
                    continue;
                // 生产的话，20秒之后添加一个任务
                if (text.equals("生产")) {
                    flag = true;
                }
                if (text.equals("饲养")) {
                    if (href.startsWith("http://mcapp.z.qq.com/mc/cgi-bin/wap_pasture_bag_list")) {
                         toHaveAnimal(href);
                    }
                }
                if(text.equals("收获")){
                    bflag= true;
                }
                // 收获 ..饲养 如果满了会有饲养吗？
                if (href.startsWith("."))
                    href = href
                            .replace(".", "http://mcapp.z.qq.com/mc/cgi-bin");
                HTTPUtil.getURLContent(href);
            }
        }
        if (flag) {
            TaskExecutor.addTask(this, 20, TimeUnit.SECONDS);
        }
        if(bflag){
            TaskExecutor.addTask(this, 3, TimeUnit.SECONDS);
        }

    }

    private void toHaveAnimal(String href) {
        String context = HTTPUtil.getURLContent(href);
        Matcher canMather = canPattern.matcher(context);
        canMather.find();
        int woNum  = getNum(canMather.group(1),canMather.group(2));
        int pengNum = getNum(canMather.group(3),canMather.group(4));
        if(woNum>0)
        {
            Matcher matcher = woPattern.matcher(context);
            if(matcher.find()){
                String url  = matcher.group(1);
                Pattern pattern  = Pattern.compile("num=(\\d+)");
                 matcher = pattern.matcher(url);
                 matcher.find();
                 int num = Integer.parseInt(matcher.group(1));
                 num = woNum>=num?num:woNum;
                 url=matcher.replaceFirst("num="+num);
                 url = url.replace("_info", "");
                 url = url.replaceAll("&amp;", "&");
                 if(url.startsWith("."));
                     url = url.replace(".", "http://mcapp.z.qq.com/mc/cgi-bin");
                 HTTPUtil.getURLContent(url);
                 toHaveAnimal(href);
            }
        }else if(pengNum>0){
            Matcher matcher = pengPattern.matcher(context);
            if(matcher.find()){
                String url  = matcher.group(1);
                Pattern pattern  = Pattern.compile("num=(\\d+)");
                 matcher = pattern.matcher(url);
                 matcher.find();
                 int num = Integer.parseInt(matcher.group(1));
                 num = pengNum>=num?num:pengNum;
                 url=matcher.replaceFirst("num="+num);
                 url = url.replace("_info", "");
                 url = url.replaceAll("&amp;", "&");
                 if(url.startsWith("."));
                     url = url.replace(".", "http://mcapp.z.qq.com/mc/cgi-bin");
                 HTTPUtil.getURLContent(url);
                 toHaveAnimal(href);
            }
        }

    }
    private int getNum(String num,String num2){
        int total = Integer.parseInt(num2);
        int now = Integer.parseInt(num);
        return total-now;
    }
}
