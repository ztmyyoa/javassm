package com.example.demo.SSM_frame.project.control;

import com.example.demo.SSM_frame.project.map.UserMapper;
import com.example.demo.SSM_frame.project.pojo.consumer;
import com.example.demo.SSM_frame.project.pojo.product;
import com.example.demo.SSM_frame.project.pojo.user;
import com.example.demo.SSM_frame.project.service.ProductService;
import com.example.demo.SSM_frame.project.utils.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    @Autowired
    private OssUtil ossUtil;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/{productid}")
    public ResponseEntity<product> getProductById(@PathVariable int productid) {
        product product = productService.getProductById(productid);
            if (product.getImage() != null && !product.getImage().isEmpty()) {
                String signedUrl = ossUtil.generatePrivateImageUrl(
                        product.getImage(),
                        36000 // 有效时间：1小时
                );
                product.setImage(signedUrl);
            }
        return ResponseEntity.ok(product);
    }

    @GetMapping("/all")
    public ResponseEntity<List<product>> getAllProducts() {
        List<product> products = productService.getAllProducts();
        for (product product : products){
            if (product.getImage() != null && !product.getImage().isEmpty()) {
                String signedUrl = ossUtil.generatePrivateImageUrl(
                        product.getImage(),
                        36000 // 有效时间：1小时
                );
                product.setImage(signedUrl);
            }
        }
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(products);
    }
    @GetMapping("/consumerall")
    public ResponseEntity<List<product>> getAllProducts1() {
        List<product> products = productService.getAllProducts();
        for (product product : products){
            if (product.getImage() != null && !product.getImage().isEmpty()) {
                String signedUrl = ossUtil.generatePrivateImageUrl(
                        product.getImage(),
                        36000 // 有效时间：1小时
                );
                product.setImage(signedUrl);
            }
        }
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(products);
    }

    @PostMapping("/add")
    public ResponseEntity<product> addProduct(@RequestBody product product) {
        productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/update")
    public ResponseEntity<product> updateProduct(@RequestBody product product) {
        productService.updateProduct(product);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/delete/{productid}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int productid) {
        productService.deleteProduct(productid);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("getbyfarmer")
    public ResponseEntity<List<product>> getProductsByFarmerId(@RequestParam String username) {
        user user=userMapper.getUserByUsername(username);
        int userId=user.getUserid();
        List<product> products = productService.getProductsByFarmerId(userId);
        for (product product : products){
            if (product.getImage() != null && !product.getImage().isEmpty()) {
                String signedUrl = ossUtil.generatePrivateImageUrl(
                        product.getImage(),
                        36000 // 有效时间：1小时
                );
                product.setImage(signedUrl);
            }
        }
        if (products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(products);
    }
    @GetMapping("/search")
    public ResponseEntity<List<product>> searchProducts(@RequestParam("keyword") String keyword) {
       List<product> products = productService.searchProducts(keyword);
        for (product product : products){
            if (product.getImage() != null && !product.getImage().isEmpty()) {
                String signedUrl = ossUtil.generatePrivateImageUrl(
                        product.getImage(),
                        36000 // 有效时间：1小时
                );
                product.setImage(signedUrl);
            }
        }
        return ResponseEntity.ok(products);
    }
    @GetMapping("/gettop")
    public ResponseEntity<List<product>> getTop() {
        List<product> products = productService.getTop();
        for (product product : products){
            if (product.getImage() != null && !product.getImage().isEmpty()) {
                String signedUrl = ossUtil.generatePrivateImageUrl(
                        product.getImage(),
                        36000 // 有效时间：1小时
                );
                product.setImage(signedUrl);
            }
        }
        if (products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(products);
    }
}
