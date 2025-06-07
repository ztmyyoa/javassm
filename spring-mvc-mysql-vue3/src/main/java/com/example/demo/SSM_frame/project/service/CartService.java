package com.example.demo.SSM_frame.project.service;

import com.example.demo.SSM_frame.project.pojo.CartItemDTO;
import com.example.demo.SSM_frame.project.pojo.cart;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    cart getCartById(int cartid);
    List<CartItemDTO> getcartByConsumerId(String username);
    void addCart(cart cart);
    void updateCartQuantity(int cartid, int quantity, double totalprice);
    void updateCart(cart cart);
    void deleteCartItem(int cartid);
    List<cart> getAllCarts();
    void clearCartByUsername(String username);
}
