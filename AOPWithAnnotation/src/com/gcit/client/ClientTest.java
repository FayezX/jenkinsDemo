package com.gcit.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gcit.model.Account;
import com.gcit.service.impl.AccountService;
import com.gcit.service.impl.AccountServiceImpl;

public class ClientTest {

	public static void main(String[] args) {

		ApplicationContext ctx= new ClassPathXmlApplicationContext("Beans.xml");
		
		AccountService accountService = ctx.getBean("accountServicee", AccountServiceImpl.class);
		
		accountService.updateAccountBalance(new Account("1234", "Transfer"), 20);
		((AbstractApplicationContext) ctx).close();
	}
}
