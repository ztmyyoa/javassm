package com.example.demo.SSM_frame.project.map;

import com.example.demo.SSM_frame.project.pojo.QuarterlyOrderCount;
import com.example.demo.SSM_frame.project.pojo.orders;
import com.example.demo.SSM_frame.project.pojo.product;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    @Select("SELECT * FROM `orders` WHERE orderid = #{orderId}")
    orders selectOrderById(int orderId);

    @Select("SELECT * FROM `orders`")
    List<orders> selectAllOrders();

    @Select("SELECT * FROM `orders` WHERE consumerid = #{consumerId}")
    List<orders> selectOrdersByConsumerId(int consumerId);

    @Select("SELECT * FROM `orders` WHERE productid = #{productId}")
    List<orders> selectOrdersByProductId(int productId);

    @Select("SELECT * FROM `orders` WHERE farmerid = #{farmerId}")
    List<orders> selectOrdersByFarmerId(int farmerId);
    @Insert("INSERT INTO `orders` (consumerid, productid,farmerid, orderstatus, orderAmount, orderTime, logisticsInfo,quantity) VALUES (#{consumerid}, #{productid}, #{farmerid},#{orderstatus}, #{orderAmount}, #{orderTime}, #{logisticsInfo},#{quantity})")
    void insertOrder(orders order);

    @Update("UPDATE `orders` SET consumerid = #{consumerid}, productid = #{productid}, farmerid = #{farmerid}, orderstatus = #{orderstatus}, orderAmount = #{orderAmount}, orderTime = #{orderTime}, logisticsInfo = #{logisticsInfo}, quantity = #{quantity} WHERE orderid = #{orderid}")
    void updateOrder(orders order);


    @Delete("DELETE FROM `orders` WHERE orderid = #{orderId}")
    void deleteOrder(int orderId);
    @Select("SELECT productid, SUM(quantity) as total_sales FROM orders GROUP BY productid")
    List<Map<String, Object>> selectProductSales();

    @Select("SELECT " +
            "  YEAR(orderTime) AS year, " +
            "  QUARTER(orderTime) AS quarter, " +
            "  SUM(quantity) AS tquantity " +
            "FROM `orders` " +
            "GROUP BY YEAR(orderTime), QUARTER(orderTime) " +
            "ORDER BY year, quarter")
    List<QuarterlyOrderCount> selectQuarterlyOrderCount();


}
