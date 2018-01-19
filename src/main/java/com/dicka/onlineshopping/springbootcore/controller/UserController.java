package com.dicka.onlineshopping.springbootcore.controller;

import com.dicka.onlineshopping.springbootcore.dao.AccountsDao;
import com.dicka.onlineshopping.springbootcore.dao.ProductDao;
import com.dicka.onlineshopping.springbootcore.entity.Accounts;
import com.dicka.onlineshopping.springbootcore.entity.Product;
import com.dicka.onlineshopping.springbootcore.model.CartModelInfo;
import com.dicka.onlineshopping.springbootcore.model.ProductModelInfo;
import com.dicka.onlineshopping.springbootcore.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private final AccountsDao accountsDao;
    private final ProductDao productDao;

    @Autowired
    public UserController(AccountsDao accountsDao, ProductDao productDao){
        this.accountsDao=accountsDao;
        this.productDao=productDao;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView getHome(){
        ModelAndView view = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Accounts accounts = accountsDao.findAccountByUsername(auth.getName());
        view.addObject("activeClass", "active");
        view.addObject("title", "Home");
        view.addObject("who", "Hallo "+accounts.getUsername());
        view.setViewName("content/home");
        return view;
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public ModelAndView getDataCart(HttpServletRequest request){
        ModelAndView view = new ModelAndView();
        CartModelInfo myCart = Utils.getCartInSession(request);
        view.addObject("cartForm", myCart);
        view.addObject("amount", myCart.getAmountTotal());
        view.setViewName("content/cart");
        view.addObject("title", "Your Cart");
        return view;
    }

    @RequestMapping(value = "/addToCart", method = RequestMethod.GET)
    public String getCart(@RequestParam(value = "code", defaultValue = "")String code,
                                HttpServletRequest request){

        Product product = null;
        if(code !=null && code.length()>0){
            product = productDao.findProduct(Long.parseLong(code));
        }

        if(product!=null){
            CartModelInfo cartModelInfo = Utils.getCartInSession(request);
            ProductModelInfo productModelInfo = new ProductModelInfo(product);
            cartModelInfo.addProduct(productModelInfo, 1);
        }

        return "redirect:/cart";
    }
}
