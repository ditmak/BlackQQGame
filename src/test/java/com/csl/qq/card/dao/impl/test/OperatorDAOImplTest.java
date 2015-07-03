package com.csl.qq.card.dao.impl.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.csl.qq.card.domain.Operator;
import com.csl.qq.card.service.OperatorService;
import com.csl.qq.card.task.SimpleCardTask;
import com.csl.qq.card.task.SimpleTask;
import com.csl.util.net.HTTPUtil;

public class OperatorDAOImplTest {
	public static ApplicationContext context;
	//@BeforeClass
	public static void setUp(){
		context = new ClassPathXmlApplicationContext("spring/application-context.xml");
	}
	@Test
	public void test2() throws InterruptedException{
	    new Thread(new SimpleTask("AS9AWzaHQVxDUuwj9QRq0beE", 63)).start();
	    for(;;)
	        Thread.sleep(10000);
	}
        @Test
        public void test3() throws InterruptedException{
            new Thread(new SimpleCardTask("AS9AWzaHQVxDUuwj9QRq0beE","185","橡皮泥的记忆")).start();
            for(;;)
                Thread.sleep(10000);
        }
        @Test
        public void test4(){
           Pattern boxsize = Pattern.compile("\\[(\\d+)\\](.*?)已有(\\d+)张.*?(http.*?)\">",Pattern.DOTALL);
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
	public void test() {
		OperatorService serivce = (OperatorService) context.getBean("operatorService");
		Operator operator = new Operator();
		operator.setName("测试123");
		operator.setUrl("www.orsoon.com");
		serivce.saveOperator(operator);
	}
	@Test
	public void test1(){
		System.out.println(Integer.highestOneBit(101));
	}

}
