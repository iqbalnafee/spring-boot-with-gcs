package com.example.bambergBeverageBox.order.service;

import com.example.bambergBeverageBox.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order,Long> {
}
