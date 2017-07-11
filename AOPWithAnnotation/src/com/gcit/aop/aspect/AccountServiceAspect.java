package com.gcit.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AccountServiceAspect {
	
	@Before(value = "execution(* com.gcit.service.impl.AccountServiceImpl.*(..))")
	public void beforeAdvice(JoinPoint joinpoint){
		System.out.println("do something before method starts: " + joinpoint.getSignature().toString());
	}
	
	@After(value = "execution(* com.gcit.service.impl.AccountServiceImpl.*(..))")
	public void afterAdvice(){
		System.out.println("do something after method ends");
	}
	
}
