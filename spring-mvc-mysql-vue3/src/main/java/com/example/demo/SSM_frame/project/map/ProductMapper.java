package com.example.demo.SSM_frame.project.map;

import com.example.demo.SSM_frame.project.pojo.product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Select("SELECT * FROM Product WHERE productid = #{productid}")
    product selectProductById(int productid);

    @Select("SELECT * FROM product")
    List<product> selectAllProducts();

    @Select("SELECT * FROM product WHERE farmerId = #{farmerId}")
    List<product> selectProductsByFarmerId(int farmerId);

    @Insert("INSERT INTO product (productname, price, stock, description, farmerId, image) VALUES (#{productname}, #{price}, #{stock}, #{description}, #{farmerId}, #{image})")
    void insertProduct(product product);

    @Update("UPDATE product SET productname = #{productname}, price = #{price}, stock = #{stock}, description = #{description}, farmerId = #{farmerId} WHERE productid = #{productid}")
    void updateProduct(product product);

    @Delete("DELETE FROM product WHERE productid = #{productid}")
    void deleteProduct(int productid);
    @Select(" SELECT p.* FROM product p JOIN ( SELECT productid, SUM(quantity) AS total_sold FROM orders GROUP BY productid ORDER BY total_sold DESC LIMIT 4) top_products ON p.productid = top_products.productid")
    List<product> getTop();
    @Select("SELECT * FROM product WHERE productname LIKE CONCAT('%', #{keyword}, '%') OR type LIKE CONCAT('%', #{keyword}, '%')")
    List<product> searchproducts(String keyword);

}
