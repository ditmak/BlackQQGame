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
           Pattern boxsize = Pattern.compile("\\[(\\d+)\\][^0-9]*?(\\d+)?[^0-9]*?已有(\\d+)张.*?(http.*?)\">",Pattern.DOTALL);
           String url = "http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_strategy?sid=AS9AWzaHQVxDUuwj9QRq0beE";
           String content = HTTPUtil.getURLContent(url);
           Matcher matcher = boxsize.matcher(content);
           int count =0;
           while(matcher.find()){
              System.out.println("------------"+(++count)+"begin----------");
               System.out.println(matcher.group(1));
               System.out.println(matcher.group(2));
               System.out.println(matcher.group(3));
               System.out.println(matcher.group(4));
               System.out.println("------------"+count+"end----------");
           }
        }
	@Test
	public void test1(){
		System.out.println(Integer.highestOneBit(101));
	}

}
