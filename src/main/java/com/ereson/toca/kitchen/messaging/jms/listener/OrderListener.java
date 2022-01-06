package com.ereson.toca.kitchen.messaging.jms.listener;

import com.ereson.toca.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

    private KitchenUI ui;

    @Autowired
    public OrderListener(KitchenUI ui) {
        this.ui = ui;
    }

    /**
     * @JmsListener注解设置监听
     * 监听目的地为tacocloud.order.queue的消息
     * 当消息到达时，receiveOrder方法会被自动调用 将消息中的order作为载荷
     * @param order
     */
    @JmsListener(destination = "tacoclouid.order.queue")
    public void receiveOrder(Order order){
        ui.displayOrder(order);
    }
}
