package com.csl.qq.card.dao.impl.test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.csl.qq.card.task.SimpleFarmerHelperTask;
import com.csl.util.net.HTTPUtil;

public class OperatorDAOImplTest {

        @Test
        public void test7(){
            Map<String,String> map = new HashMap<>();
            map.put("大红花", "xxxxxx");
            System.out.println(map.get("大红花"));
            System.out.println(map.containsKey("大红花"));
        }
    
    
    
        @Test
        public void test6() {
            String url = "http://mcapp.z.qq.com/nc/cgi-bin/wap_farm_seed_plant_list?sid=ARjN47Y5E7xqiOwwcegmSaC4&page=1&g_ut=1&v=0&landid=-1";
            testGetSeeds(url);
        }
        private void testGetSeeds(String url){
            String content = HTTPUtil.getURLContent(url);
            Pattern pattern = Pattern.compile("）(.*?)x.*?href=\"(.*?)\"",Pattern.DOTALL);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()){
                System.out.println(matcher.group(1).trim());
                System.out.println(matcher.group(2));
            }
            int index = content.indexOf("下页");
            if(index!=-1){
                int secondI = content.lastIndexOf("\"", index);
                int fisrtI = content.lastIndexOf("\"",secondI-1);
                url =content.substring(fisrtI+1, secondI);
                url = url.replaceAll("&amp;", "&");
                if(url.startsWith("."))
                    url = url.replace(".", "http://mcapp.z.qq.com/nc/cgi-bin");
                testGetSeeds(url);
            }
        }
      
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
