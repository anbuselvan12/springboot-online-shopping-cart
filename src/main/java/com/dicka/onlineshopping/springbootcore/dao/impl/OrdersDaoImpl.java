package com.dicka.onlineshopping.springbootcore.dao.impl;

import com.dicka.onlineshopping.springbootcore.dao.OrdersDao;
import com.dicka.onlineshopping.springbootcore.entity.Orders;
import com.dicka.onlineshopping.springbootcore.entity.OrdersDetils;
import com.dicka.onlineshopping.springbootcore.entity.Product;
import com.dicka.onlineshopping.springbootcore.model.*;
import com.dicka.onlineshopping.springbootcore.repository.OrderRepository;
import com.dicka.onlineshopping.springbootcore.repository.OrdersDetilRepository;
import com.dicka.onlineshopping.springbootcore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Repository
public class OrdersDaoImpl implements OrdersDao{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrdersDetilRepository ordersDetilRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public OrdersDaoImpl(OrderRepository orderRepository,
                         ProductRepository productRepository,
                         OrdersDetilRepository ordersDetilRepository){

        this.orderRepository=orderRepository;
        this.productRepository=productRepository;
        this.ordersDetilRepository=ordersDetilRepository;
    }

    //orderNum
    private int getMaxOrderNum(){
        String queries = "select max(o.orderNum) from"
                +Orders.class.getName()+"o";
        Query query = entityManager.createQuery(queries, Integer.class);
        Integer value = (Integer) query.getSingleResult();
        if(value == null){
            return 0;
        }
        return value;
    }

    //save orders
    @Override
    public void saveOrders(CartModelInfo cartModelInfo) {

        int ordersNum = 1 + 1;
        Orders orders = new Orders();

        orders.setOrderNum(ordersNum);
        orders.setOrderDate(new Date());
        orders.setAmount(cartModelInfo.getAmountTotal());

        CustomerModelInfo customerModelInfo = cartModelInfo.getCustomerModelInfo();
        orders.setCustomerName(customerModelInfo.getName());
        orders.setCustomerEmail(customerModelInfo.getEmail());
        orders.setCustomerPhone(customerModelInfo.getPhone());
        orders.setCustomerAddress(customerModelInfo.getAddress());

        entityManager.persist(orders);

        //save cart
        List<CartLineModelInfo> lines = cartModelInfo.getCartLines();

        for(CartLineModelInfo line : lines){

            OrdersDetils ordersDetils = new OrdersDetils();
            ordersDetils.setOrders(orders);
            ordersDetils.setAmount(line.getAmount());
            ordersDetils.setPrice(line.getProductModelInfo().getPrice());
            ordersDetils.setQuantity(line.getQuantity());

            Long Code = line.getProductModelInfo().getIdproduct();
            Product product = this.productRepository.findOne(Code);
            ordersDetils.setProduct(product);

            entityManager.persist(ordersDetils);
        }

        cartModelInfo.setOrderNum(ordersNum);
        entityManager.flush();
    }

    @Override
    public Orders findOrders(Long idorders) {
        return orderRepository.findOne(idorders);
    }

    @Override
    public List<Orders> getListOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<OrdersDetils> findOneOrders(Long idorders) {
        String query = "select o from OrdersDetils as o where o.orders.idorders=?";
        return entityManager.createQuery(query).setParameter(1, idorders)
                .getResultList();
    }

    @Override
    public OrdersModelInfo getOrdersInfo(Long idorders) {
        Orders orders = this.findOrders(idorders);
        if(orders == null){
            return null;
        }

        return new OrdersModelInfo(orders.getIdorders(), orders.getOrderDate(),
                orders.getOrderNum(), orders.getAmount(), orders.getCustomerName(),
                orders.getCustomerAddress(), orders.getCustomerEmail(), orders.getCustomerPhone());
    }

    @Override
    public List<OrdersDetils> getListOrdersDetils() {
        return ordersDetilRepository.findAll();
    }
}
