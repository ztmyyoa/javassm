package com.example.demo.SSM_frame.project.service.imp;

import com.example.demo.SSM_frame.project.map.ProductMapper;
import com.example.demo.SSM_frame.project.pojo.product;
import com.example.demo.SSM_frame.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public product getProductById(int productid) {
        return productMapper.selectProductById(productid);
    }

    @Override
    public List<product> getAllProducts() {
        return productMapper.selectAllProducts();
    }

    @Override
    public List<product> getProductsByFarmerId(int farmerId) {
        return productMapper.selectProductsByFarmerId(farmerId);
    }

    @Override
    public void addProduct(product product) {
        productMapper.insertProduct(product);
    }

    @Override
    public void updateProduct(product product) {
        productMapper.updateProduct(product);
    }

    @Override
    public void deleteProduct(int productid) {
        productMapper.deleteProduct(productid);
    }

    @Override
    public List<product> getTop() {
        return productMapper.getTop();
    }

    @Override
    public List<product> searchProducts(String keyword) {
        return productMapper.searchproducts(keyword);
    }

}
