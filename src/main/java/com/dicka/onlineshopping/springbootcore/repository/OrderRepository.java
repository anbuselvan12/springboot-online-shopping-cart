package com.dicka.onlineshopping.springbootcore.repository;

import com.dicka.onlineshopping.springbootcore.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long>{
}
