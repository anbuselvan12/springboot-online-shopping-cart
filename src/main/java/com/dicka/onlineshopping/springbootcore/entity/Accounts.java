package com.dicka.onlineshopping.springbootcore.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts",
        catalog = "barang")
public class Accounts implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idaccounts;

    @NotNull
    @Length(min = 5, message = "nama depan anda terlalu singkat")
    @NotEmpty(message = "Firstname masih kosong harap diisi")
    @Column(name = "firstname", nullable = false)
    private String firstname;

    @NotNull
    @Length(min = 5, message = "nama terkahir anda terlalu singkat")
    @NotEmpty(message = "Lastname masih kosong harap diisi")
    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Length(min = 5, message = "username terlalu singkat")
    @NotNull
    @NotEmpty(message = "Username masih kosong harap diisi")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "active", nullable = false)
    private int active;

    @NotNull
    @Length(min = 5, message = "password terlalu singkat")
    @NotEmpty(message = "Password masih kosong harap diisi")
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "accounts_roles",
            joinColumns = @JoinColumn(name = "idaccounts", referencedColumnName = "idaccounts"),
            inverseJoinColumns = @JoinColumn(name = "idroles", referencedColumnName = "idroles"))
    private List<Roles> rolesList = new ArrayList<Roles>();

    public int getActive(){
        return active;
    }

    public void setActive(int active){
        this.active=active;
    }

    public List<Roles> getRolesList(){
        return rolesList;
    }

    public void setRolesList(List<Roles> rolesList){
        this.rolesList=rolesList;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getPassword(){
        return password;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username=username;
    }

    public Long getIdaccounts(){
        return idaccounts;
    }

    public void setIdaccounts(Long idaccounts){
        this.idaccounts=idaccounts;
    }

    public String getFirstname(){
        return firstname;
    }

    public void setFirstname(String firstname){
        this.firstname=firstname;
    }

    public String getLastname(){
        return lastname;
    }

    public void setLastname(String lastname){
        this.lastname=lastname;
    }
}
