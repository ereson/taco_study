package com.ereson.toca.data;

import com.ereson.toca.Order;

public interface OrderRepository {

    Order save(Order order);

//    List<Order> findByDeliveryZip(String deliveryZip);
//
//    List<Order> findOrderByDeliveryZipAndPlacedAtBetween(String deleveryZip, Date stratDate, Date endDate);
}
