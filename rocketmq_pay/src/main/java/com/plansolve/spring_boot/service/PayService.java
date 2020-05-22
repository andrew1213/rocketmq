package com.plansolve.spring_boot.service;

import com.plansolve.spring_boot.model.database.Pay;

public interface PayService {
	
	public Pay selectByPrimaryKey(String userid);
	
	public Pay insert(Pay record);
	
	public Pay updateByPrimaryKey(Pay record);

	
	public void updateAmount(String idUser, String mode, String money);
	
	public void updateDetail(Pay record, String detail);
	
}
