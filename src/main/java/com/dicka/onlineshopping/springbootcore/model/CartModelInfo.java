package com.dicka.onlineshopping.springbootcore.model;

import java.util.ArrayList;
import java.util.List;

public class CartModelInfo {

    private int orderNum;

    private CustomerModelInfo customerModelInfo;

    private final List<CartLineModelInfo> cartLines = new ArrayList<CartLineModelInfo>();

    public CartModelInfo(){}

    public int getOrderNum(){
        return orderNum;
    }

    public void setOrderNum(int orderNum){
        this.orderNum=orderNum;
    }

    public CustomerModelInfo getCustomerModelInfo(){
        return customerModelInfo;
    }

    public void setCustomerModelInfo(CustomerModelInfo customerModelInfo){
        this.customerModelInfo=customerModelInfo;
    }

    public List<CartLineModelInfo> getCartLines(){
        return this.cartLines;
    }


    //cart line mencari berdasarkan kode product di dalam basket
    private CartLineModelInfo findByCode(Long code){
        for(CartLineModelInfo line : this.cartLines){
            if(line.getProductModelInfo().getIdproduct().equals(code)){
                return line;
            }
        }
        return null;
    }

    //validate
    public void validate(){}

    //add product to cart ke keranjang basket
    public void addProduct(ProductModelInfo productModelInfo, int quantity){
        CartLineModelInfo lineModelInfo = this.findByCode(productModelInfo.getIdproduct());

        if(lineModelInfo == null){
            lineModelInfo = new CartLineModelInfo();
            lineModelInfo.setQuantity(0);
            lineModelInfo.setProductModelInfo(productModelInfo);
            this.cartLines.add(lineModelInfo);
        }

        int newQuantity = lineModelInfo.getQuantity() + quantity;
        if(newQuantity <= 0){
            this.cartLines.remove(lineModelInfo);
        }else{
            lineModelInfo.setQuantity(newQuantity);
        }
    }

    //update product berdasarkan code
    public void updateProduct(Long code, int quantity){
        CartLineModelInfo line = this.findByCode(code);

        if(line != null){
            if(quantity<=0){
                this.cartLines.remove(line);
            }else{
                line.setQuantity(quantity);
            }
        }
    }

    //jika cart kosong, customer di direct ke page cart
    public boolean isEmpty(){
        return this.cartLines.isEmpty();
    }

    //remove product dari basket
    public void removeProduct(ProductModelInfo productModelInfo){
        CartLineModelInfo lineModelInfo = this.findByCode(productModelInfo.getIdproduct());
        if(lineModelInfo != null){
            this.cartLines.remove(lineModelInfo);
        }
    }

    //valid customer
    public boolean isValidCustomer(){
        return this.customerModelInfo != null && this.customerModelInfo.getValid();
    }


    //total price
    public int getQuantityTotal(){
        int quantity = 0;
        for(CartLineModelInfo lineModelInfo : this.cartLines){
            quantity += lineModelInfo.getQuantity();
        }
        return quantity;
    }

    //total amount
    public double getAmountTotal(){
        double total = 0.0;
        for(CartLineModelInfo lineModelInfo : this.cartLines){
            total += lineModelInfo.getAmount();
        }
        return total;
    }

    //how to update quantity ?
    public void updateQuantity(CartModelInfo cartForm){
        if(cartForm != null){
            List<CartLineModelInfo> lines = cartForm.getCartLines();
            for(CartLineModelInfo line : lines){
                this.updateProduct(line.getProductModelInfo().getIdproduct(),
                        line.getQuantity());
            }
        }
    }
}
