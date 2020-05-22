package com.plansolve.spring_boot.service;

import java.util.List;
import com.plansolve.spring_boot.utils.FastJsonConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.QueryResult;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

@Component
public class MQConsumer {
	
	private final String GROUP_NAME = "SELF_TEST_C_GROUP";

	private final String NAMESRV_ADDR = "192.168.43.164:9876;192.168.43.15:9876";

	private DefaultMQPushConsumer consumer;
	
	@Autowired
	private BalanceService balanceService;
	
	
	public MQConsumer() {
		try {
			this.consumer = new DefaultMQPushConsumer(GROUP_NAME);
			this.consumer.setNamesrvAddr(NAMESRV_ADDR);
			this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
			this.consumer.subscribe("pay", "filter");
			this.consumer.registerMessageListener(new Listener());
			this.consumer.start();
			System.out.println("========consumer start========");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public QueryResult queryMessage(String topic, String key, int maxNum, long begin, long end) throws Exception {
		long current = System.currentTimeMillis();
		return this.consumer.queryMessage(topic, key, maxNum, begin, end);
	}
	
	class Listener implements MessageListenerConcurrently {
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			MessageExt msg = msgs.get(0);
			try {
				String msgId = msg.getMsgId();
				//Message Body
				JSONObject messageBody = FastJsonConvert.convertJSONToObject(new String(msg.getBody(), "utf-8"), JSONObject.class);
				System.out.println("订阅消费端收到消息, msgId : " + msgId + ", body : " + new String(msg.getBody(), "utf-8"));
				String idUser = messageBody.getString("idUser");
				double money = messageBody.getDouble("money");
				String balance_mode = messageBody.getString("balance_mode");
				//业务逻辑处理
				balanceService.updateAmount(idUser, balance_mode, money);
			} catch (Exception e) {
				e.printStackTrace();
				//重试次数为3情况 
				if(msg.getReconsumeTimes() == 3){
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
					//记录日志
				}
				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
			}			
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
	}

	public static void main(String[] args) {
		MQConsumer mqConsumer = new MQConsumer();
	}

}
