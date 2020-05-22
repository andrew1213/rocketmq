package com.plansolve;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.plansolve.spring_boot.SpringBootManageApplication;
import com.plansolve.spring_boot.model.database.Balance;
import com.plansolve.spring_boot.service.BalanceService;
import com.plansolve.spring_boot.service.MQConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.rocketmq.client.QueryResult;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** 
 * <br>类 名: BaseTest 
 * <br>描 述: 描述类完成的主要功能 
 * <br>作 者: Andrew
 * <br>创 建： 2015年5月8日
 * <br>版 本：v1.0.0 
 * <br>
 * <br>历 史: (版本) 作者 时间 注释
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootManageApplication.class)
public class BaseTest {
	
	@Autowired
	private MQConsumer mQConsumer;
	
	@Autowired
	private BalanceService balanceService;
	
	@Test
	public void testSave() throws Exception {
		Balance balance = new Balance();
		balance.setUsername("张三");
		balance.setAmount(5000d);
		balance.setUpdateBy("z3");
		balance.setUpdateTime(new Date());
		this.balanceService.insert(balance);
	}
	
	@Test
	public void testUpdate() throws Exception {
		Balance balance = this.balanceService.selectByPrimaryKey("40288a816c95c010016c95c073db0000");
		balance.setAmount(balance.getAmount() + 1000d);
		balance.setUpdateTime(new Date());
		this.balanceService.updateByPrimaryKey(balance);
	}
	
	
	@Test
	public void test1() throws Exception {
		System.out.println(this.mQConsumer);
		long end = new Date().getTime();
		long begin = end - 60 * 1000 * 60 * 24;
		QueryResult qr = this.mQConsumer.queryMessage("topic_pay", "k8", 10, begin, end);
		List<MessageExt> list = qr.getMessageList();
		for(MessageExt me : list){
			Map<String, String> m = me.getProperties();
			System.out.println(m.keySet().toString());
			System.out.println(m.values().toString());
			System.out.println(me.toString());
			System.err.println("内容: " + new String(me.getBody(), "utf-8"));
			System.out.println("Prepared :" + me.getPreparedTransactionOffset());
		}
	}

}
