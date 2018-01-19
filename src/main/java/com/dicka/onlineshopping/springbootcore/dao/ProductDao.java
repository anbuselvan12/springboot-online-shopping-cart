package com.dicka.onlineshopping.springbootcore.dao;

import com.dicka.onlineshopping.springbootcore.entity.Product;
import com.dicka.onlineshopping.springbootcore.form.ProductForm;
import com.dicka.onlineshopping.springbootcore.model.ProductModelInfo;

import java.util.List;

public interface ProductDao {

    Product findProduct(Long idproduct);

    ProductModelInfo findProductInfo(Long idproduct);

    void saveProduct(ProductForm productForm);

    void deleteProduct(Product product);

    List<Product> findAllProduct();
}
