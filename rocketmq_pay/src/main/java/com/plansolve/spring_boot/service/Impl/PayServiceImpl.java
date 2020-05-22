package com.plansolve.spring_boot.service.Impl;

import com.plansolve.spring_boot.model.database.Pay;
import com.plansolve.spring_boot.repository.PayRepository;
import com.plansolve.spring_boot.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Service
public class PayServiceImpl implements PayService {

	@Autowired
	private PayRepository payRepository;

	@Autowired
	private PayServiceImpl payService;
	
	public Pay selectByPrimaryKey(String userid) {
		return this.payRepository.findByIdUser(userid);
	}
	
	public Pay insert(Pay record) {
		return this.payRepository.save(record);
	}
	
	public Pay updateByPrimaryKey(Pay record) {
		return this.payRepository.save(record);
	}

	@Transactional
	public void updateAmount(String idUser, String mode, String money){
		double m = Double.parseDouble(money);
		Pay pay = payRepository.findByIdUser(idUser);
		if (null != pay){
			if("IN".equals(mode)){
				pay.setAmount(pay.getAmount() + Math.abs(m));
			} else if("OUT".equals(mode)){
				pay.setAmount(pay.getAmount() - Math.abs(m));
			}
			pay.setUpdateTime(new Date());
			System.out.println("========开始操作余额宝账户========");
			this.updateByPrimaryKey(pay);
		}else{
			System.out.println("========用idUser查询的Pay对象是null========");
		}
		//失败测试：
//		int a = 1/0;
	}
	
	public void updateDetail(Pay record, String detail){
		record.setDetail(detail);
		this.updateByPrimaryKey(record);
	}
	
}
