package com.dicka.onlineshopping.springbootcore.model;

public class OrderDetilsModelInfo {

    private Long idordersdetils;

    private Long productCode;

    private String productName;

    private int quantity;

    private double price;

    private double amount;

    public OrderDetilsModelInfo(Long idordersdetils,
                                Long productCode,
                                String productName,
                                int quantity,
                                double price,
                                double amount){

        this.idordersdetils=idordersdetils;
        this.productCode=productCode;
        this.productName=productName;
        this.quantity=quantity;
        this.price=price;
        this.amount=amount;
    }

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

    public void setProductName(String productName){
        this.productName=productName;
    }

    public String getProductName(){
        return productName;
    }

    public void setProductCode(Long productCode){
        this.productCode=productCode;
    }

    public Long getProductCode(){
        return productCode;
    }

    public void setIdordersdetils(Long idordersdetils){
        this.idordersdetils=idordersdetils;
    }

    public Long getIdordersdetils(){
        return  idordersdetils;
    }
}
