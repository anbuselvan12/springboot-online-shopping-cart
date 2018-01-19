package com.dicka.onlineshopping.springbootcore.model;

import com.dicka.onlineshopping.springbootcore.entity.OrdersDetils;

import java.util.Date;
import java.util.List;

public class OrdersModelInfo {

    private Long idorders;

    private Date orderDate;

    private int orderNum;

    private double amount;

    private String customerName;

    private String customerAddress;

    private String customerEmail;

    private String customerPhone;

    private List<OrderDetilsModelInfo> detils;

    public OrdersModelInfo(Long idorders,
                           Date orderDate,
                           int orderNum,
                           double amount,
                           String customerName,
                           String customerAddress,
                           String customerEmail,
                           String customerPhone){

        this.idorders=idorders;
        this.orderDate=orderDate;
        this.orderNum=orderNum;
        this.amount=amount;
        this.customerName=customerName;
        this.customerAddress=customerAddress;
        this.customerEmail=customerEmail;
        this.customerPhone=customerPhone;
    }

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
        return customerAddress;
    }

    public void setCustomerName(String customerName){
        this.customerName=customerName;
    }

    public String getCustomerName(){
        return customerName;
    }

    public void setAmount(double amount){
        this.amount=amount;
    }

    public double getAmount(){
        return amount;
    }

    public void setOrderNum(int orderNum){
        this.orderNum=orderNum;
    }

    public int getOrderNum(){
        return orderNum;
    }

    public void setOrderDate(Date orderDate){
        this.orderDate=orderDate;
    }

    public Date getOrderDate(){
       return orderDate;
    }

    public Long getIdorders(){
        return idorders;
    }

    public void setIdorders(Long idorders){
        this.idorders=idorders;
    }
}
