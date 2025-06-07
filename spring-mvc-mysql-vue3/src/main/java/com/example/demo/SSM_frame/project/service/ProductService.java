package com.example.demo.SSM_frame.project.service;

import com.example.demo.SSM_frame.project.pojo.product;

import java.util.List;

public interface ProductService {
    product getProductById(int productid);
    List<product> getAllProducts();
    List<product> getProductsByFarmerId(int farmerId);
    void addProduct(product product);
    void updateProduct(product product);
    void deleteProduct(int productid);
    List<product> getTop();
    List<product> searchProducts(String keyword);
}
