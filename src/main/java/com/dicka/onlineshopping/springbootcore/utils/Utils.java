package com.dicka.onlineshopping.springbootcore.utils;

import com.dicka.onlineshopping.springbootcore.model.CartModelInfo;

import javax.servlet.http.HttpServletRequest;

public class Utils {

    public static CartModelInfo getCartInSession(HttpServletRequest request){

        //mengambil session name "myCart"
        CartModelInfo cartModelInfo = (CartModelInfo)
                request.getSession().getAttribute("myCart");


        if(cartModelInfo == null){
            cartModelInfo = new CartModelInfo();

            request.getSession().setAttribute("myCart", cartModelInfo);
        }
        return cartModelInfo;
    }

    //menghapus cart session "myCart"
    public static void removeCartSession(HttpServletRequest request){
        request.getSession().removeAttribute("myCart");
    }

    //menampilkan data history "lastOrderCart"
    public static void storeLastOrderCartInSession(HttpServletRequest request, CartModelInfo cartModelInfo){
        request.getSession().setAttribute("lastOrderCart", cartModelInfo);
    }

    //data history cart
    public static CartModelInfo getLastOrderCartInSession(HttpServletRequest request){
        return (CartModelInfo) request.getSession().getAttribute("lastOrderCart");
    }
}
