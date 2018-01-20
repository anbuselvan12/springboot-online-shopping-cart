package com.dicka.onlineshopping.springbootcore.repository;

import com.dicka.onlineshopping.springbootcore.entity.OrdersDetils;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersDetilRepository extends JpaRepository<OrdersDetils, Long>{
}
