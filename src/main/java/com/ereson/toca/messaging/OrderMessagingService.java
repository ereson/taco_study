package com.ereson.toca.messaging;

import com.ereson.toca.Order;

public interface OrderMessagingService {

    void sendOrder(Order order);
}
