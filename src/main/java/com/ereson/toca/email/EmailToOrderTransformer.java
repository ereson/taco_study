package com.ereson.toca.email;

import com.ereson.toca.Order;
import org.springframework.amqp.core.Message;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class EmailToOrderTransformer extends AbstractMailMessageTransformer {

    @Override
    protected AbstractIntegrationMessageBuilder<Order> doTransform(Message mailMessage) {
        Order tacoOrder = processPayload(mailMessage);
        return MessageBuilder.withPayload(tacoOrder);
    }
}
