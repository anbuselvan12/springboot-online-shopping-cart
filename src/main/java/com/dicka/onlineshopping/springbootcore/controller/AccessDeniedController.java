package com.dicka.onlineshopping.springbootcore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/403")
public class AccessDeniedController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView get403AccessDenied(){
        ModelAndView view = new ModelAndView();
        view.addObject("title", "Access Denied !");
        view.setViewName("content/403");
        return view;
    }

}
