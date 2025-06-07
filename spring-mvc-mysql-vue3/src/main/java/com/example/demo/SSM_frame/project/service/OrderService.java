package com.example.demo.SSM_frame.project.service;

import com.example.demo.SSM_frame.project.pojo.QuarterlyOrderCount;
import com.example.demo.SSM_frame.project.pojo.orders;

import java.util.List;
import java.util.Map;

public interface OrderService {
    orders getOrderById(int orderid);
    List<orders> getAllOrders();
    List<orders> getOrdersByConsumerId(String usernamev);
    List<orders> getOrdersByProductId(int productid);
    List<orders> getOrdersByFarmerId(String username);
    void addOrder(orders order);
    void updateOrder(orders order);
    void deleteOrder(int orderid);
    public List<Map<String, Object>> getProductSales();
    // 获取每季度订单数量
    public List<QuarterlyOrderCount> getQuarterlyOrderCount();
}
