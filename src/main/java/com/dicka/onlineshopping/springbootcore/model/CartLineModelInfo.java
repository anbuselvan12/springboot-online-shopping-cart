package com.dicka.onlineshopping.springbootcore.model;

public class CartLineModelInfo {

    private ProductModelInfo productModelInfo;

    private int quantity;

    public CartLineModelInfo(){
        this.quantity=0;
    }

    //count amount
    public double getAmount(){
        return productModelInfo.getPrice() * this.quantity;
    }

    public ProductModelInfo getProductModelInfo(){
        return productModelInfo;
    }

    public void setProductModelInfo(ProductModelInfo productModelInfo){
        this.productModelInfo=productModelInfo;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity=quantity;
    }
}
