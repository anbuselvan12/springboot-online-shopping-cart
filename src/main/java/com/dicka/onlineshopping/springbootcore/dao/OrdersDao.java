package com.dicka.onlineshopping.springbootcore.dao;

import com.dicka.onlineshopping.springbootcore.entity.Orders;
import com.dicka.onlineshopping.springbootcore.entity.OrdersDetils;
import com.dicka.onlineshopping.springbootcore.model.CartModelInfo;
import com.dicka.onlineshopping.springbootcore.model.OrderDetilsModelInfo;
import com.dicka.onlineshopping.springbootcore.model.OrdersModelInfo;

import java.util.List;

public interface OrdersDao {

    void saveOrders(CartModelInfo cartModelInfo);

    Orders findOrders(Long idorders);

    List<Orders> getListOrders();

    List<OrdersDetils> findOneOrders(Long idorders);

    OrdersModelInfo getOrdersInfo(Long idorders);

    List<OrdersDetils> getListOrdersDetils();
}
