package com.ereson.toca.kitchen.messaging.jms;

import com.ereson.toca.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * 从队列中拉取订单
 */
@Component
public class JmsOrderReceiver extends OrderReceiver {

    private JmsTemplate jmsTemplate;
    private MessageConverter messageConverter;

    @Autowired
    public JmsOrderReceiver(JmsTemplate jmsTemplate, MessageConverter messageConverter) {
        this.jmsTemplate = jmsTemplate;
        this.messageConverter = messageConverter;
    }

    public Order receiveOrder() throws JMSException {
        Message message = jmsTemplate.receive("tacocloud.order.queue");
        return (Order) messageConverter.fromMessage(message);
    }
}
