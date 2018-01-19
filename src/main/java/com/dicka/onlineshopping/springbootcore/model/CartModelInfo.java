package com.dicka.onlineshopping.springbootcore.model;

import java.util.ArrayList;
import java.util.List;

public class CartModelInfo {

    private int orderNum;

    private CustomerModelInfo customerModelInfo;

    private final List<CartLineModelInfo> cartLines = new ArrayList<>();

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
        return cartLines;
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
        //jika jumlah barang kurang dari 0 nol tidak sama dengan 1 !! (validasi)
        if(newQuantity <= 0){
            this.cartLines.remove(lineModelInfo);
        }else{
            lineModelInfo.setQuantity(newQuantity);
        }
    }

    //update product berdasarkan code
    public void updateProduct(Long code, int quantity){
        //{{findByCode}} methode ini ada di dalam class CartModelInfo
        CartLineModelInfo lineModelInfo = this.findByCode(code);

        if(lineModelInfo != null){
            if(quantity <= 0){
                this.cartLines.remove(lineModelInfo);
            }else{
                lineModelInfo.setQuantity(quantity);
            }
        }
    }

    //basket kosong validasi
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
    public void updateQuantity(CartModelInfo cartModelInfo){
        if(cartModelInfo != null){
            List<CartLineModelInfo> lineModelInfos = cartModelInfo.getCartLines();
            for(CartLineModelInfo lineModelInfo: lineModelInfos){
                this.updateProduct(lineModelInfo.getProductModelInfo().getIdproduct(),
                        lineModelInfo.getQuantity());
            }
        }
    }
}
