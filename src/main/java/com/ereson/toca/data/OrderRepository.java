package com.ereson.toca.data;

import com.ereson.toca.Order;
import com.ereson.toca.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    Order save(Order order);

    List<Order> findByDeliveryZip(String deliveryZip);

    List<Order> findOrderByDeliveryZipAndPlacedAtBetween(String deleveryZip, Date stratDate, Date endDate);

    List<Order> findByUserOrderByPlaceAtDesc(User user, Pageable pageable);
}
