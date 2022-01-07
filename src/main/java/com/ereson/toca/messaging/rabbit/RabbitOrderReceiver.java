package com.ereson.toca.messaging.rabbit;

import com.ereson.toca.Order;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitOrderReceiver {

    private RabbitTemplate rabbit;
    private MessageConverter converter;

    @Autowired
    public  RabbitOrderReceiver(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
        this.converter =rabbit.getMessageConverter();
    }

    /**
     * 从队列名为tacocluod.orders的队列中拉取数据，不为空则进行转换
     * @return
     */
    public Order receiveOrder() {
        Message message = rabbit.receive("tacocloud.orders");
        return message != null ? (Order) converter.fromMessage(message) : null;
    }
}
