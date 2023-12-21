package com.example.bambergBeverageBox.order.service;

import com.example.bambergBeverageBox.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
