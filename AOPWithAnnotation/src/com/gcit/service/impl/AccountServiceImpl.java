package com.gcit.service.impl;

import org.springframework.stereotype.Service;

//import com.infotech.aop.aspect.Auditable;
//import com.infotech.aop.aspect.Auditable.AuditDestination;

import com.gcit.model.Account;

@Service("accountServicee")
public class AccountServiceImpl implements AccountService {

	/* (non-Javadoc)
	 * @see com.infotech.service.impl.AccountService#updateAccountBalance(com.infotech.model.Account, java.lang.Long)
	 */
	@Override
	public void updateAccountBalance(Account account,Integer amount){
		System.out.println("Account No:"+account.getAccountNumber()+", Amount:"+amount);
	}
}
