package com.csl.qq.card.dao.impl.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.csl.qq.card.domain.Operator;
import com.csl.qq.card.service.OperatorService;
import com.csl.qq.card.task.SimpleCardTask;
import com.csl.qq.card.task.SimpleTask;

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
            new Thread(new SimpleCardTask("AS9AWzaHQVxDUuwj9QRq0beE")).start();
            for(;;)
                Thread.sleep(10000);
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
