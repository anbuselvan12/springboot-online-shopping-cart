package com.dicka.onlineshopping.springbootcore.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "roles",
        catalog = "barang")
public class Roles implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idroles;

    @NotNull
    @Column(name = "roles", nullable = false)
    private String roles;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    public Long getIdroles(){
        return idroles;
    }

    public void setIdroles(Long idroles){
        this.idroles=idroles;
    }

    public String getRoles(){
        return roles;
    }

    public void setRoles(String roles){
        this.roles=roles;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description=description;
    }
}
