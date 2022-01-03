package com.ereson.toca.messaging;

import com.ereson.toca.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;


@Service
public class JmsOrderMessageService implements OrderMessagingService {

    private JmsTemplate jms;

    private Destination orderQueue;

    @Autowired
    public JmsOrderMessageService(JmsTemplate jms, Destination orderQueue) {
        this.jms = jms;
        this.orderQueue = orderQueue;
    }

    /**
     * 只有一个参数的send
     * @param order
    @Override
    public void sendOrder(Order order) {
        jms.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(order);
            }
        });
        //MessageCreator是一个函数式接口，可以使用lambda表达式进行简化
        //需要指定一个默认的目的地
        jms.send(session -> session.createObjectMessage(order));
    }
     */

    /**
     * 指定目的地的send
     * @param order
     */
    public void sendOrder1(Order order) {
        jms.send("tacocloud.order.queue", session -> session.createObjectMessage(order));
    }

    /**
     * 使用convertAndSend，直接将发送的对象转化成Message，不用创建MessageCreator
     * @param order
     */
    @Override
    public void sendOrder(Order order) {
        jms.convertAndSend("tacocloud.order.queue", order);
    }
}
