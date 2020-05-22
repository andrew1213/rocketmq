package com.plansolve.spring_boot.service;

import com.plansolve.spring_boot.model.database.Balance;

public interface BalanceService {

	public Balance insert(Balance record);
	
	public Balance selectByPrimaryKey(String userid);
	
	public Balance updateByPrimaryKey(Balance record);

	public void updateAmount(String userid, String mode, double money);
	
}
