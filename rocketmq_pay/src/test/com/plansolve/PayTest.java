package com.plansolve;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.plansolve.spring_boot.SpringBootManageApplication;
import com.plansolve.spring_boot.service.MQProducer;
import com.plansolve.spring_boot.utils.FastJsonConvert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.QueryResult;
import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootManageApplication.class)
public class PayTest {

	@Autowired
	private MQProducer mQProducer;
	
	@Autowired
	private LocalTransactionExecuter transactionExecuterImpl;
	
	@Test
	public void test1() {
		try {
			System.out.println(this.mQProducer);
			System.out.println(this.transactionExecuterImpl);
			
			//构造消息数据
			Message message = new Message();
			//主题
			message.setTopic("pay");
			//子标签
			message.setTags("filter");
			//key
			String uuid = UUID.randomUUID().toString();
			System.out.println("key: " + uuid);
			message.setKeys(uuid);
			JSONObject body = new JSONObject();
			body.put("idUser", "40288a816c958b6c016c958c6a700000");
			body.put("money", "500");
			body.put("pay_mode", "OUT");
			body.put("balance_mode", "IN");
			message.setBody(FastJsonConvert.convertObjectToJSON(body).getBytes());
			
			//添加参数
			Map<String, Object> transactionMapArgs = new HashMap<String, Object>();
			
			this.mQProducer.sendTransactionMessage(message, this.transactionExecuterImpl, transactionMapArgs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void test2() throws Exception {
		long end = new Date().getTime();
		long begin = end - 60 * 1000 * 60 * 24;
		QueryResult qr = this.mQProducer.queryMessage("pay", "6e4cefbb-5216-445f-9027-d96dd54a4afb", 10, begin, end);
		List<MessageExt> list = qr.getMessageList();
		for(MessageExt me : list){
			
			Map<String, String> m = me.getProperties();
			System.out.println(m.keySet().toString());
			System.out.println(m.values().toString());
			System.out.println(me.toString());
			System.err.println("内容: " + new String(me.getBody(), "utf-8"));
			System.out.println("Prepared :" + me.getPreparedTransactionOffset());
			LocalTransactionState ls = this.mQProducer.check(me);
			System.out.println(ls);
			//this.mQProducer.getTransactionCheckListener()
		}
		//System.out.println("qr: " + qr.toString());
		//C0A8016F00002A9F0000000000034842
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
