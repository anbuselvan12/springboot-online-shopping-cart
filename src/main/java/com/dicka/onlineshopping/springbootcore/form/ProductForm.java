package com.dicka.onlineshopping.springbootcore.form;

import com.dicka.onlineshopping.springbootcore.entity.Product;
import org.springframework.web.multipart.MultipartFile;

public class ProductForm {

    private Long code;
    private String name;
    private double price;
    private boolean newProduct=false;
    private MultipartFile fileData;

    public ProductForm(){
        this.newProduct = true;
    }

    public ProductForm(Product product){
        this.code = product.getIdproduct();
        this.name = product.getName();
        this.price = product.getPrice();
    }

    public void setNewProduct(boolean newProduct){
        this.newProduct = newProduct;
    }

    public boolean getNewProduct(){
        return newProduct;
    }

    public void setFileData(MultipartFile fileData){
        this.fileData=fileData;
    }

    public MultipartFile getFileData() {
        return fileData;
    }

    public void setPrice(double price){
        this.price=price;
    }

    public double getPrice(){
        return price;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public Long getCode(){
        return code;
    }

    public void setCode(Long code){
        this.code=code;
    }
}
