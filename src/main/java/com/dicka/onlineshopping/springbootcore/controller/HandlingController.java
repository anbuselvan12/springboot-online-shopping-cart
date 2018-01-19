package com.dicka.onlineshopping.springbootcore.controller;

import com.dicka.onlineshopping.springbootcore.dao.AccountsDao;
import com.dicka.onlineshopping.springbootcore.dao.ProductDao;
import com.dicka.onlineshopping.springbootcore.entity.Accounts;
import com.dicka.onlineshopping.springbootcore.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class HandlingController {

    //login controller isinya yang berkaitan dengan accounts !
    //dan handling controller

    private final ProductDao productDao;
    private final AccountsDao accountsDao;

    @Autowired
    public HandlingController(AccountsDao accountsDao, ProductDao productDao){
        this.accountsDao=accountsDao;
        this.productDao=productDao;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public ModelAndView getLoginPage(){
        ModelAndView view = new ModelAndView();
        view.setViewName("content/login");
        view.addObject("title", "Login Page");
        return view;
    }


    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public ModelAndView getIndex(){
        ModelAndView view = new ModelAndView();
        view.setViewName("index");
        view.addObject("title", "Index");
        view.addObject("productList",  productDao.findAllProduct());
        view.addObject("activeClass", "active");
        return view;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/registrasi")
    public ModelAndView getRegistrasiPage(){
        ModelAndView view = new ModelAndView();
        /**
         * accounts object th:object=${accounts}
         */
        Accounts accounts=new Accounts();
        view.setViewName("content/registrasi");
        view.addObject("accounts", accounts);
        view.addObject("activeClass", "active");
        view.addObject("title", "Registrasi");
        return view;
    }

    //registrasi
    @RequestMapping(value = "/registrasi", method = RequestMethod.POST)
    public ModelAndView createAccounts(@Valid Accounts accounts, BindingResult bindingResult){
        ModelAndView view = new ModelAndView();

        Accounts ifAccounstIsExist= accountsDao.findAccountByUsername(accounts.getUsername());
        if(ifAccounstIsExist != null){
            bindingResult.rejectValue("username", "error.accounts", "maaf username ini sudah terdaftar");
        }

        if(bindingResult.hasErrors()){
            view.addObject("title", "Registrasi");
            view.setViewName("content/registrasi");
        }else{
            accountsDao.createAccounts(accounts);
            view.addObject("valid", "Data berhasil disimpan !");
            view.addObject("accounts", new Accounts());
            view.setViewName("content/registrasi");
        }
        return view;
    }



    //list api
    @RequestMapping(value = "/listaccounts", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Accounts>> listAccounts(){
        return Optional.ofNullable(accountsDao.findAllAccounts())
                .map(list -> new ResponseEntity<>(list, HttpStatus.OK))
                .orElse(new ResponseEntity<List<Accounts>>(HttpStatus.NOT_FOUND));
    }
}
