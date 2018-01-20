package com.dicka.onlineshopping.springbootcore.controller;

import com.dicka.onlineshopping.springbootcore.dao.AccountsDao;
import com.dicka.onlineshopping.springbootcore.dao.OrdersDao;
import com.dicka.onlineshopping.springbootcore.dao.ProductDao;
import com.dicka.onlineshopping.springbootcore.entity.Accounts;
import com.dicka.onlineshopping.springbootcore.entity.Orders;
import com.dicka.onlineshopping.springbootcore.entity.OrdersDetils;
import com.dicka.onlineshopping.springbootcore.entity.Product;
import com.dicka.onlineshopping.springbootcore.form.ProductForm;
import com.dicka.onlineshopping.springbootcore.validator.ProductFormValidator;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.lang.exception.ExceptionUtils;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class AdminController {

    private final ProductDao productDao;
    private final AccountsDao accountsDao;
    private final OrdersDao ordersDao;
    //form validator
    @Autowired
    private ProductFormValidator productFormValidator;

    @Autowired
    public AdminController(ProductDao productDao,
                           AccountsDao accountsDao,
                           OrdersDao ordersDao){
        this.productDao=productDao;
        this.accountsDao=accountsDao;
        this.ordersDao=ordersDao;
    }

    @InitBinder
    public void myInitBindir(WebDataBinder dataBinder){
        Object target = dataBinder.getTarget();
        if(target == null){
            return;
        }
        System.out.println("Target="+target);

        if(target.getClass() == ProductForm.class){
            dataBinder.setValidator(productFormValidator);
        }
    }

    //handling gambar
    @RequestMapping(value = {"/productImage"}, method = RequestMethod.GET)
    public void productImage(HttpServletRequest request, HttpServletResponse response,
                             Model model, @RequestParam("code")Long code)throws IOException{

        Product product = null;
        if(code!=null){
            product=this.productDao.findProduct(code);
        }
        if(product!=null && product.getImage()!=null){
            response.setContentType("image/jpeg, image/jpeg ,image/png, image/gif");
            response.getOutputStream().write(product.getImage());
        }
        response.getOutputStream().close();
    }

    //data cart
    @RequestMapping(value = "/adminCart", method = RequestMethod.GET)
    public ModelAndView getDataCart(){
        ModelAndView view = new ModelAndView();
        view.addObject("title", "Data Cart Customer");
        view.addObject("datas", ordersDao.getListOrders());
        view.setViewName("content/dataCart");
        return view;
    }

    @RequestMapping(value = "/dataDetils", method = RequestMethod.GET)
    public String getDataCartOrdersDetils(@RequestParam(value = "code", defaultValue = "")
                                          String code, Model model){

        model.addAttribute("title", "Data Detils");
        Orders orders = ordersDao.findOrders(Long.parseLong(code));
        List<OrdersDetils> ordersDetils = ordersDao.findOneOrders(Long.parseLong(code));
        model.addAttribute("detils", ordersDetils);
        model.addAttribute("orders", orders);
        return "content/dataCartDetils";
    }

    //list product
    @RequestMapping(value = "/adminProduct",method = RequestMethod.GET)
    public ModelAndView getPageAdmin(){
        ModelAndView view = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Accounts accounts = accountsDao.findAccountByUsername(auth.getName());
        view.addObject("activeClass", "active");
        view.setViewName("content/product");
        view.addObject("datalogin",
                "Data Product | Username :  " + accounts.getUsername());
        view.addObject("products", productDao.findAllProduct());
        view.addObject("title", "Data Product");
        return view;
    }

    //menuju halaman create product
    @RequestMapping(value = "/adminProductCreate", method = RequestMethod.GET)
    public String getManaPoduct(Model model,
                                @RequestParam(value = "code", defaultValue = "")
                                String code){

        ProductForm productForm = null;

        if(code!=null && code.length()>0){
            Product product = productDao.findProduct(Long.parseLong(code));
            if(product!=null){
                productForm = new ProductForm(product);
            }
        }
        if(productForm == null){
            productForm = new ProductForm();
            productForm.setNewProduct(true);
        }
        model.addAttribute("title", "Manage Product");
        model.addAttribute("productForm", productForm);
        return "content/createProduct";
    }


    //create product
    @RequestMapping(value = "/adminProductCreate", method = RequestMethod.POST)
    public String getCreateProduct(@ModelAttribute("productForm")
                                   @Validated ProductForm productForm,
                                   BindingResult result,
                                   Model model,
                                   final RedirectAttributes redirectAttributes){

        if(result.hasErrors()){
            return "content/createProduct";
        }
        try{
           productDao.saveProduct(productForm);
        }catch (Exception e){
            e.printStackTrace();
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            model.addAttribute("title", "Manage Product");
            String message =rootCause.getMessage();
            model.addAttribute("errorMessage", message);
            return "content/createProduct";
        }
        return "redirect:/adminProduct";
    }

    //delete product
    @RequestMapping(value = "/adminDeleteProduct", method = RequestMethod.GET)
    public String deleteProduct(@RequestParam(value = "code", defaultValue = "")String code){

        Product product = productDao.findProduct(Long.parseLong(code));
        productDao.deleteProduct(product);
        return "redirect:/adminProduct";
    }

    //show product
    @RequestMapping(value = "/adminShowProduct", method = RequestMethod.GET)
    public String getShowProduct(@RequestParam(value = "code", defaultValue = "")String code,
                                 Model model){
        ProductForm productForm = null;

        if(code !=null && code.length() > 0){
            Product product = productDao.findProduct(Long.parseLong(code));
            if(product!=null){
                productForm = new ProductForm(product);
            }
        }
        if(productForm == null){
            productForm = new ProductForm();
            productForm.setNewProduct(true);
        }
        model.addAttribute("productForm", productForm);
        return "content/createProduct";
    }
}
