package com.stackroute.keepnote.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* Annotate this class with @Aspect and @Component */

@Aspect
@Component
public class LoggingAspect {

	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	/*
	 * Write loggers for each of the methods of controller, any particular method
	 * will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */

	@Before("execution(* com.stackroute.keepnote.controller.CategoryController.addCategory(..))")
	public void logBefore(JoinPoint joinPoint) {
		logger.info("Before " );
	}

	@After("execution(* com.stackroute.keepnote.controller.CategoryController.addCategory(..))")
	public void logAfter(JoinPoint joinPoint) {
		logger.info("After ");
	}

	@AfterReturning(pointcut = "execution(* com.stackroute.keepnote.controller.CategoryController.addCategory(..))", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		logger.info("After " );
		
	}

	@AfterThrowing(pointcut = "execution(* com.stackroute.keepnote.controller.CategoryController.addCategory(..))", throwing = "exception")
	public void logAfterThrowing(JoinPoint joinPoint, Object exception) {
		logger.info("After throwning " );
		
	}

}
