package com.dicka.onlineshopping.springbootcore.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "orders",
        catalog = "barang")
public class Orders implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idorders;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @NotNull
    @Column(name = "order_num", nullable = false)
    private int orderNum;

    @NotNull
    @Column(name = "amout", nullable = false)
    private double amount;

    @NotNull
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @NotNull
    @Column(name = "customer_adress", nullable = false)
    private String customerAddress;

    @NotNull
    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @NotNull
    @Column(name = "customer_phone", nullable = false)
    private String customerPhone;


    public void setCustomerPhone(String customerPhone){
        this.customerPhone=customerPhone;
    }

    public String getCustomerPhone(){
        return customerPhone;
    }

    public void setCustomerEmail(String customerEmail){
        this.customerEmail=customerEmail;
    }

    public String getCustomerEmail(){
        return customerEmail;
    }

    public void setCustomerAddress(String customerAddress){
        this.customerAddress=customerAddress;
    }

    public String getCustomerAddress(){
        return customerAddress=customerAddress;
    }

    public void setCustomerName(String customerName){
        this.customerName=customerName;
    }

    public String getCustomerName(){
        return customerName;
    }

    public Long getIdorders(){
        return idorders;
    }

    public void setIdorders(Long idorders){
        this.idorders=idorders;
    }

    public Date getOrderDate(){
        return orderDate;
    }

    public void setOrderDate(Date orderDate){
        this.orderDate=orderDate;
    }

    public int getOrderNum(){
        return orderNum;
    }

    public void setOrderNum(int orderNum){
        this.orderNum = orderNum;
    }

    public double getAmount(){
        return amount;
    }

    public void setAmount(double amount){
        this.amount=amount;
    }

}
