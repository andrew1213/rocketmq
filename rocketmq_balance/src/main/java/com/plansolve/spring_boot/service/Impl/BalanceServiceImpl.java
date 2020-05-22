package com.plansolve.spring_boot.service.Impl;

import java.util.Date;
import com.plansolve.spring_boot.model.database.Balance;
import com.plansolve.spring_boot.repository.BalanceRepository;
import com.plansolve.spring_boot.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BalanceServiceImpl implements BalanceService {

	@Autowired
	private BalanceRepository balanceRepository;
	
	public Balance insert(Balance record){
		return balanceRepository.save(record);
	}
	
	public Balance selectByPrimaryKey(String userid){
		return this.balanceRepository.findByIdUser(userid);
	}

	@Transactional
	public Balance updateByPrimaryKey(Balance record){
		return this.balanceRepository.save(record);
	}
	
	@Transactional
	public void updateAmount(String userid, String mode, double money){
		Balance balance = this.selectByPrimaryKey(userid);
		if("IN".equals(mode)){
			balance.setAmount(balance.getAmount() + Math.abs(money));
		} else if("OUT".equals(mode)){
			balance.setAmount(balance.getAmount() - Math.abs(money));
		}
		System.out.println("========开始操作余额账户========");
		balance.setUpdateTime(new Date());
		this.updateByPrimaryKey(balance);
	}
	
}
