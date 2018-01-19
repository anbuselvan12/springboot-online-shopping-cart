package com.dicka.onlineshopping.springbootcore.controller;

import com.dicka.onlineshopping.springbootcore.dao.AccountsDao;
import com.dicka.onlineshopping.springbootcore.dao.OrdersDao;
import com.dicka.onlineshopping.springbootcore.dao.ProductDao;
import com.dicka.onlineshopping.springbootcore.entity.Accounts;
import com.dicka.onlineshopping.springbootcore.entity.Product;
import com.dicka.onlineshopping.springbootcore.form.CustomerForm;
import com.dicka.onlineshopping.springbootcore.model.CartModelInfo;
import com.dicka.onlineshopping.springbootcore.model.CustomerModelInfo;
import com.dicka.onlineshopping.springbootcore.model.ProductModelInfo;
import com.dicka.onlineshopping.springbootcore.utils.Utils;
import com.dicka.onlineshopping.springbootcore.validator.CustomerFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private final AccountsDao accountsDao;
    private final ProductDao productDao;
    private final OrdersDao ordersDao;

    @Autowired
    private CustomerFormValidator customerFormValidator;

    @Autowired
    public UserController(AccountsDao accountsDao, ProductDao productDao, OrdersDao ordersDao){
        this.accountsDao=accountsDao;
        this.productDao=productDao;
        this.ordersDao=ordersDao;
    }

    //@InitBinder binding
    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder){
        Object target = dataBinder.getTarget();
        if(target == null){
            return;
        }
        System.out.println("Target = "+target);

        if(target.getClass() == CartModelInfo.class){

        }else if(target.getClass() == CustomerForm.class){
            dataBinder.setValidator(customerFormValidator);
        }
    }

    //home melihat data barang
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

    //cart template
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

    //update quantity cart
    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    public String updateQuantityInCart(HttpServletRequest request, Model model,
                                       @ModelAttribute("cartForm")
                                               CartModelInfo cartForm){

        CartModelInfo cartModelInfo = Utils.getCartInSession(request);
        cartModelInfo.updateQuantity(cartForm);
        return "redirect:/cart";
    }


    //menambahkan item ke cart
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

    //remove atau hapus barang di dalam cart
    @RequestMapping(value = "/removeCartItem")
    public String getRemoveCart(HttpServletRequest request, Model model,
                                @RequestParam(value = "code", defaultValue = "")String code){

        Product product=null;
        if(code!=null && code.length()>0){
            product=productDao.findProduct(Long.parseLong(code));
        }

        if(product!=null){
            CartModelInfo cartModelInfo = Utils.getCartInSession(request);

            ProductModelInfo productModelInfo = new ProductModelInfo(product);

            cartModelInfo.removeProduct(productModelInfo);
        }

        return "redirect:/cart";
    }

    //validation cart customer
    @RequestMapping(value = "/validationCartCustomer", method = RequestMethod.GET)
    public String getValidationCartCustomer(HttpServletRequest request, Model model){

        CartModelInfo cartModelInfo = Utils.getCartInSession(request);

        if(cartModelInfo.isEmpty()){
            return "redirect:/cart";
        }

        CustomerModelInfo customerModelInfo = cartModelInfo.getCustomerModelInfo();

        CustomerForm customerForm = new CustomerForm(customerModelInfo);

        model.addAttribute("title", "Validation Cart");

        model.addAttribute("customerForm", customerForm);

        return "content/validationCart";
    }

    //post validator cart customer
    @RequestMapping(value = "/validationCartCustomer", method = RequestMethod.POST)
    public String cartValidatorCustomer(HttpServletRequest request,
                                        Model model,
                                        @ModelAttribute("customerForm")
                                        @Validated CustomerForm customerForm,
                                        BindingResult result,
                                        final RedirectAttributes redirectAttributes){

        if(result.hasErrors()){
            customerForm.setValid(false);
            model.addAttribute("title", "Validation Cart");
            return "content/validationCart";
        }

        customerForm.setValid(true);
        CartModelInfo cartModelInfo = Utils.getCartInSession(request);
        CustomerModelInfo customerModelInfo = new CustomerModelInfo(customerForm);
        cartModelInfo.setCustomerModelInfo(customerModelInfo);

        return "redirect:/confirmation";
    }

    //confirmation
    @RequestMapping(value = "/confirmation", method = RequestMethod.GET)
    public String cartValidatorCustomerConfirmation(HttpServletRequest request,
                                                    Model model){

        CartModelInfo cartModelInfo = Utils.getCartInSession(request);

        if(cartModelInfo == null || cartModelInfo.isEmpty()){

            return "redirect:/cart";
        }else if(!cartModelInfo.isValidCustomer()){
            return "redirect:/validationCartCustomer";
        }
        model.addAttribute("title", "Confirmation Payment");
        model.addAttribute("myCart", cartModelInfo);
        model.addAttribute("amount", cartModelInfo.getAmountTotal());
        return "content/validationCartCustomerConfirmation";
    }

    @RequestMapping(value = "/sendAndSaved", method = RequestMethod.POST)
    public String saveTheShoppingCart(HttpServletRequest request){
        CartModelInfo cartModelInfo = Utils.getCartInSession(request);

        if(cartModelInfo.isEmpty()){
            return "redirect:/cart";
        }else if(!cartModelInfo.isValidCustomer()){
            return "redirect:/validationCartCustomer";
        }
        try{
            ordersDao.saveOrders(cartModelInfo);
        }catch (Exception e){
            e.printStackTrace();
        }

        //remove session
        Utils.removeCartSession(request);
        return "redirect:/index";
    }
}
