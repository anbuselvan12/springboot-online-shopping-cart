package com.dicka.onlineshopping.springbootcore.repository;

import com.dicka.onlineshopping.springbootcore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
