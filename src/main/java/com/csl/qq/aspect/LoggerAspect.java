package com.csl.qq.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;

public class LoggerAspect {
	private final Log log = LogFactory.getLog(getClass());
	public void BeforLogger(JoinPoint jPoint){
		log.debug(jPoint.getArgs());
	}
}
