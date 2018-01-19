package com.dicka.onlineshopping.springbootcore.form;

import com.dicka.onlineshopping.springbootcore.model.CustomerModelInfo;

public class CustomerForm {

    private String name;

    private String address;

    private String email;

    private String phone;

    private boolean valid;

    public CustomerForm(){}

    public CustomerForm(CustomerModelInfo customerModelInfo){
        if(customerModelInfo != null){
            this.name = customerModelInfo.getName();
            this.address = customerModelInfo.getAddress();
            this.email = customerModelInfo.getEmail();
            this.phone = customerModelInfo.getPhone();
        }
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

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

}
