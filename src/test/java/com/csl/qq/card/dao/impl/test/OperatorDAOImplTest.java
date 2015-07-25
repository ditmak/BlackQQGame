package com.csl.qq.card.dao.impl.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.csl.qq.card.task.SimpleCardTask;
import com.csl.qq.card.task.SimpleFarmerHelperTask;
import com.csl.util.net.HTTPUtil;

public class OperatorDAOImplTest {

      
        @Test
        public void test5() throws InterruptedException{
            new Thread(new SimpleFarmerHelperTask("ARjN47Y5E7xqiOwwcegmSaC4")).start();
            for(;;)
                Thread.sleep(10000);
        }
        @Test
        public void test4(){
            Pattern canPattern = Pattern.compile("窝\\).*?href=\"(.*?)\">饲养",Pattern.DOTALL);
           Pattern boxsize = Pattern.compile("\\[(\\d+)\\][^0-9]*?(\\d+)?[^0-9]*?已有(\\d+)张.*?(http.*?)\">",Pattern.DOTALL);
           String url = "http://mcapp.z.qq.com/mc/cgi-bin/wap_pasture_bag_list?sid=AS9AWzaHQVxDUuwj9QRq0beE";
           String content = HTTPUtil.getURLContent(url);
           Matcher matcher = canPattern.matcher(content);
           int count =0;
           while(matcher.find()){
              System.out.println("------------"+(++count)+"begin----------");
               System.out.println(matcher.group(1));
               System.out.println("------------"+count+"end----------");
           }

        }
	@Test
	public void test1(){
	    int total = 5;
	    String url = "./wap_pasture_plant_info?sid=AS9AWzaHQVxDUuwj9QRq0beE&amp;g_ut=1&amp;cid=1279&amp;num=90&amp;name=骑行猫&amp;page=1";
	    Pattern pattern  = Pattern.compile("num=(\\d+)");
           Matcher  matcher = pattern.matcher(url);
            matcher.find();
            int num = Integer.parseInt(matcher.group(1));
            num = total>=num?num:total;
            url=matcher.replaceFirst("num="+num);
	}
	public void test2(){
	    
	}

}
