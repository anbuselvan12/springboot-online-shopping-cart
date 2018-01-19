package com.dicka.onlineshopping.springbootcore.validator;

import com.dicka.onlineshopping.springbootcore.dao.ProductDao;
import com.dicka.onlineshopping.springbootcore.entity.Product;
import com.dicka.onlineshopping.springbootcore.form.ProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProductFormValidator implements Validator {

    private final ProductDao productDao;

    @Autowired
    public ProductFormValidator(ProductDao productDao){
        this.productDao=productDao;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == ProductForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductForm productForm = (ProductForm) target;

        //check field dari product
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.productForm.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price","NotEmpty.productForm.price");

        Long code = productForm.getCode();
        if(code != null && code.longValue() > 0){
            if(code.toString().matches("\\s+")){
                errors.rejectValue("code", "Pattern.productForm.code");
            }else if(productForm.getNewProduct()){
                Product product = productDao.findProduct(code);
                if(product!=null){
                    errors.rejectValue("code", "Duplicate.productForm.code");
                }
            }
        }
    }
}
