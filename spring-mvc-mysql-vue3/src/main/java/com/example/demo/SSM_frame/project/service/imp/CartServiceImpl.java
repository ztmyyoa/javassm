package com.example.demo.SSM_frame.project.service.imp;

import com.example.demo.SSM_frame.project.map.CartMapper;
import com.example.demo.SSM_frame.project.map.UserMapper;
import com.example.demo.SSM_frame.project.pojo.CartItemDTO;
import com.example.demo.SSM_frame.project.pojo.cart;
import com.example.demo.SSM_frame.project.pojo.user;
import com.example.demo.SSM_frame.project.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<cart> getAllCarts() {
        return cartMapper.selectAllCarts();
    }
    @Override
    public cart getCartById(int cartid) {
        return cartMapper.selectCartById(cartid);
    }

    @Override
    public List<CartItemDTO> getcartByConsumerId(String username) {
        user user=userMapper.getUserByUsername(username);
        int consumerid=user.getUserid();
        return cartMapper.getcartByConsumerId1(consumerid);
    }

    @Override
    public void addCart(cart cart) {
        cartMapper.insertCart(cart);
    }

    @Override
    public void updateCartQuantity(int cartid, int quantity, double totalprice) {
        cartMapper.updateCartQuantity(cartid, quantity,  totalprice);
    }
    @Override
    public void deleteCartItem(int cartid) {
        cartMapper.deleteCartItem(cartid);
    }

    @Override
    public void clearCartByUsername(String username) {
        user user=userMapper.getUserByUsername(username);
        int consumerid=user.getUserid();
        cartMapper.clearCartByUsername(consumerid);
    }
    @Override
    public void updateCart(cart cart) {
        cartMapper.updateCart(cart);
    }

}
