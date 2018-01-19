package com.dicka.onlineshopping.springbootcore.model;

import com.dicka.onlineshopping.springbootcore.entity.Product;

public class ProductModelInfo {

    private Long idproduct;

    private String name;

    private double price;

    public ProductModelInfo(){}

    public ProductModelInfo(Product product){
        this.idproduct=product.getIdproduct();
        this.name=product.getName();
        this.price=product.getPrice();
    }

    public ProductModelInfo(Long idproduct, String name, double price){
        this.idproduct=idproduct;
        this.name=name;
        this.price=price;
    }

    public Long getIdproduct(){
        return idproduct;
    }

    public void setIdproduct(Long idproduct){
        this.idproduct=idproduct;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price=price;
    }
}
