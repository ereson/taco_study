package com.ereson.toca.data;

import com.ereson.toca.Order;

public interface OrderRepository {

    Order save(Order order);
}
