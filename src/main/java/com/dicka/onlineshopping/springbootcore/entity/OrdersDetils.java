package com.dicka.onlineshopping.springbootcore.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "orders_detils",
        catalog = "barang")
public class OrdersDetils implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idordersdetils;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idorders", nullable = false)
    private Orders orders;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idproduct", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "amount", nullable = false)
    private double amount;


    public void setAmount(double amount){
        this.amount=amount;
    }

    public double getAmount(){
        return amount;
    }

    public void setPrice(double price){
        this.price=price;
    }

    public double getPrice(){
        return price;
    }

    public void setQuantity(int quantity){
        this.quantity=quantity;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setProduct(Product product){
        this.product=product;
    }

    public Product getProduct(){
        return product;
    }

    public void setOrders(Orders orders){
        this.orders=orders;
    }

    public Orders getOrders(){
        return orders;
    }

    public void setIdordersdetils(Long idordersdetils){
        this.idordersdetils=idordersdetils;
    }

    public Long getIdordersdetils(){
        return idordersdetils;
    }
}
