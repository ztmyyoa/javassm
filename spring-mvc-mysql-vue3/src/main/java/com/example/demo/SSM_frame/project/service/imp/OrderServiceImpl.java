package com.example.demo.SSM_frame.project.service.imp;

import com.example.demo.SSM_frame.project.map.OrderMapper;
import com.example.demo.SSM_frame.project.map.UserMapper;
import com.example.demo.SSM_frame.project.pojo.QuarterlyOrderCount;
import com.example.demo.SSM_frame.project.pojo.orders;
import com.example.demo.SSM_frame.project.pojo.user;
import com.example.demo.SSM_frame.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public orders getOrderById(int orderid) {
        return orderMapper.selectOrderById(orderid);
    }

    @Override
    public List<orders> getAllOrders() {
        return orderMapper.selectAllOrders();
    }

    @Override
    public List<orders> getOrdersByConsumerId(String username) {
        user user=userMapper.getUserByUsername(username);
        int consumerid=user.getUserid();
        return orderMapper.selectOrdersByConsumerId(consumerid);
    }

    @Override
    public List<orders> getOrdersByProductId(int productid) {
        return orderMapper.selectOrdersByProductId(productid);
    }

    @Override
    public List<orders> getOrdersByFarmerId(String username) {
        user user=userMapper.getUserByUsername(username);
        int farmerid=user.getUserid();
       return orderMapper.selectOrdersByFarmerId(farmerid);
    }

    @Override
    public void addOrder(orders order) {
        orderMapper.insertOrder(order);
    }

    @Override
    public void updateOrder(orders order) {
        orderMapper.updateOrder(order);
    }

    @Override
    public void deleteOrder(int orderid) {
        orderMapper.deleteOrder(orderid);
    }

    @Override
    public List<Map<String, Object>> getProductSales() {
        return orderMapper.selectProductSales();
    }
    @Override
    public List<QuarterlyOrderCount> getQuarterlyOrderCount() {
        List<QuarterlyOrderCount> result = orderMapper.selectQuarterlyOrderCount();
        System.out.println("查询结果: " + result); // 调试输出
        return result;
    }
}
