package com.dicka.onlineshopping.springbootcore.dao.impl;

import com.dicka.onlineshopping.springbootcore.dao.ProductDao;
import com.dicka.onlineshopping.springbootcore.entity.Product;
import com.dicka.onlineshopping.springbootcore.form.ProductForm;
import com.dicka.onlineshopping.springbootcore.model.ProductModelInfo;
import com.dicka.onlineshopping.springbootcore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Repository
public class ProductDaoImpl implements ProductDao {

    private final ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ProductDaoImpl(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    //find product berdasarkan kode
    @Override
    public Product findProduct(Long idproduct) {
        return productRepository.findOne(idproduct);
    }


    //menampilkan informasi product hanya berdasarkan nama, harga dan kode
    @Override
    public ProductModelInfo findProductInfo(Long idproduct) {
        Product product = this.findProduct(idproduct);
        if(product == null){
            return null;
        }
        return new ProductModelInfo(product.getIdproduct(),
                product.getName(),
                product.getPrice());
    }

    //menyimpan data product
    @Override
    public void saveProduct(ProductForm productForm) {

        Long code = productForm.getCode();
        Product product = null;
        boolean isNew = false;

        //check apakah idproduct sudah ada
        if(code != null){
            product=this.findProduct(code);
        }
        if(product == null){
            isNew=true;
            product = new Product();
            product.setCreateDate(new Date());
        }

        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());

        //handling gambar
        if(productForm.getFileData()!=null){
            byte[] images = null;
            try{
                images = productForm.getFileData().getBytes();
            }catch (IOException e){
                e.printStackTrace();
            }
            if(images != null && images.length > 0){
                product.setImage(images);
            }
        }
        //jika baru maka save data
        if(isNew){
            productRepository.save(product);
        }else if(isNew = false){
            entityManager.contains(productForm);
            productForm = entityManager.merge(productForm);
            entityManager.flush();
        }
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }


    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }
}
