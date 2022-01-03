package com.ereson.toca;

import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.context.annotation.Bean;

import javax.jms.Destination;

public class Beans {

    /**
     * 设置消息发送的目的地，设置成一个bean
     * @return
     */
    @Bean
    public Destination orderQueue() {
        return new ActiveMQQueue("tacocloud.order.queue");
    }
}
