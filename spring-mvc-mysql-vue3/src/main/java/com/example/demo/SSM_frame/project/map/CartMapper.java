package com.example.demo.SSM_frame.project.map;

import com.example.demo.SSM_frame.project.pojo.CartItemDTO;
import com.example.demo.SSM_frame.project.pojo.cart;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CartMapper {
    @Select("SELECT * FROM Cart WHERE cartid = #{cartid}")
    cart selectCartById(int cartid);

    @Select("SELECT * FROM Cart WHERE consumerid = #{consumerid}")
    List<cart> selectcartByConsumerId(int consumerid);
    @Select("SELECT * FROM Cart")
    List<cart> selectAllCarts();
    @Insert("INSERT INTO Cart (consumerid, productid, quantity,totalprice) VALUES (#{consumerid}, #{productid}, #{quantity},#{totalprice})")
    void insertCart(cart cart);

    @Update("UPDATE Cart SET consumerid = #{consumerid}, productid = #{productid}, quantity = #{quantity}, price = #{price},totalprice,image = #{image} WHERE cartid = #{cartid}")
    void updateCart(cart cart);

    @Delete("DELETE FROM Cart WHERE cartid = #{cartid}")
    void deleteCart(int cartid);
    // 删除购物车项
    @Delete("DELETE FROM cart WHERE cartid = #{cartid}")
    void deleteCartItem(int cartid);
    @Select("SELECT c.*,p.productname,p.price,p.image FROM product p JOIN cart c ON c.productid = p.productid WHERE c.consumerid = #{consumerid}")
    List<CartItemDTO> getcartByConsumerId1(int consumerid);
    @Update("UPDATE cart SET quantity = #{quantity} ,totalprice=#{totalprice} WHERE cartid = #{cartid}")
    void updateCartQuantity(@Param("cartid") int cartid, @Param("quantity") int quantity,@Param("totalprice")double totalprice);
    @Delete("DELETE FROM cart WHERE consumerid = #{consumerid}")
    void clearCartByUsername(int consumerid);
}
