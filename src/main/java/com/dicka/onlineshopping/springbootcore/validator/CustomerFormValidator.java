package com.dicka.onlineshopping.springbootcore.validator;

import com.dicka.onlineshopping.springbootcore.form.CustomerForm;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CustomerFormValidator implements Validator{

    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == CustomerForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {

        CustomerForm custInfo = (CustomerForm) target;

        //mengecheck fields dari customers
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name","NotEmpty.productForm.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.productForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "NotEmpty.productForm.phone");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.productForm.address");

        if(!emailValidator.isValid(custInfo.getEmail())){
            errors.rejectValue("email", "Pattern.customerForm.email");
        }
    }
}
