package com.plansolve.spring_boot.service;

import java.util.Map;
import com.plansolve.spring_boot.utils.FastJsonConvert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.common.message.Message;


/**
 * 执行本地事务，由客户端回调
 */

//@Scope("prototype")
@Component("transactionExecuterImpl")
public class TransactionExecuterImpl implements LocalTransactionExecuter {

    @Autowired
    private PayService payService;

    public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {
        System.out.println("注入的payservice对象=" + payService);
        try {
            //Message Body
            JSONObject messageBody = FastJsonConvert.convertJSONToObject(new String(msg.getBody(), "utf-8"), JSONObject.class);
            //Transaction MapArgs
            Map<String, Object> mapArgs = (Map<String, Object>) arg;

            // --------------------IN PUT---------------------- //
            System.out.println("message body = " + messageBody);
            System.out.println("message mapArgs = " + mapArgs);
            System.out.println("message tag = " + msg.getTags());
            // --------------------IN PUT---------------------- //

            //userid
            String idUser = messageBody.getString("idUser");
            //money
            String money = messageBody.getString("money");
            //mode
            String pay_mode = messageBody.getString("pay_mode");
            if (null != idUser && StringUtils.isNotBlank(pay_mode) && StringUtils.isNotBlank(money)) {
                //持久化数据
                this.payService.updateAmount(idUser, pay_mode, money);
                //成功通知MQ消息变更 该消息变为：<确认发送>
                return LocalTransactionState.COMMIT_MESSAGE;
            } else {
                System.out.println("========messageBody获取数据有null值========");
            }
            //return LocalTransactionState.UNKNOW;
        } catch (Exception e) {
            System.out.println("========抛了异常所以走了回滚========");
            e.printStackTrace();
            //失败则不通知MQ 该消息一直处于：<暂缓发送>
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return LocalTransactionState.UNKNOW;
    }

}