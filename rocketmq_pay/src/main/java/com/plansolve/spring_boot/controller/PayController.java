package com.plansolve.spring_boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.common.message.Message;
import com.plansolve.spring_boot.model.database.Pay;
import com.plansolve.spring_boot.service.MQProducer;
import com.plansolve.spring_boot.service.PayService;
import com.plansolve.spring_boot.utils.FastJsonConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/rocketMQ")
public class PayController {

    @Autowired
    private PayService payService;

    @Autowired
    private MQProducer mQProducer;

    @Autowired
    private LocalTransactionExecuter transactionExecuterImpl;

    @RequestMapping("/account/add")
    public Pay add(){
        Pay pay = new Pay();
        pay.setUsername("里斯");
        pay.setAmount(5000d);
        pay.setDetail("0");
        pay.setUpdateBy("l4");
        pay.setUpdateTime(new Date());
        Pay insert = payService.insert(pay);
        return insert;
    }

    @RequestMapping("/transaction/pay")
    public String pay(){
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
            message.setKeys(uuid);
            JSONObject body = new JSONObject();
            body.put("idUser", "40288a816c958b6c016c958c6a700000");
            body.put("money", "500");
            body.put("pay_mode", "OUT");
            body.put("balance_mode", "IN");
            message.setBody(FastJsonConvert.convertObjectToJSON(body).getBytes());
            //添加参数
            Map<String, Object> transactionMapArgs = new HashMap<String, Object>();
            // 发送消息
            this.mQProducer.sendTransactionMessage(message, this.transactionExecuterImpl, transactionMapArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "付款申请已发送";
    }

}
