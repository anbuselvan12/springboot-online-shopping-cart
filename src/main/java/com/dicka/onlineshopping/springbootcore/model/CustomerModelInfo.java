package com.dicka.onlineshopping.springbootcore.model;

import com.dicka.onlineshopping.springbootcore.form.CustomerForm;

public class CustomerModelInfo {

    private String name;

    private String address;

    private String email;

    private String phone;

    private boolean valid;

    public CustomerModelInfo(CustomerForm customerForm){
        this.name = customerForm.getName();
        this.address = customerForm.getAddress();
        this.email = customerForm.getEmail();
        this.phone = customerForm.getPhone();
        this.valid = customerForm.getValid();
    }

    public void setValid(boolean valid){
        this.valid=valid;
    }

    public boolean getValid(){
        return valid;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }

    public String getPhone(){
        return phone;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public String getEmail(){
        return email;
    }

    public void setAddress(String address){
        this.address=address;
    }

    public String getAddress(){
        return address;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }
}
