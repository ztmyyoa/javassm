package com.example.demo.SSM_frame.project.service.imp;

import com.example.demo.SSM_frame.project.map.FarmerMapper;
import com.example.demo.SSM_frame.project.map.UserMapper;
import com.example.demo.SSM_frame.project.pojo.farmer;
import com.example.demo.SSM_frame.project.pojo.user;
import com.example.demo.SSM_frame.project.service.FarmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class FarmerServiceImpl implements FarmerService {

    @Autowired
    private FarmerMapper farmerMapper;

    @Autowired
    private UserMapper userMapper;
    @Override
    public farmer getFarmerById(String username) {
        user user=userMapper.getUserByUsername(username);
        int userId=user.getUserid();
        return farmerMapper.selectFarmerById(userId);
    }

    @Override
    public List<farmer> getAllFarmers() {
        return farmerMapper.selectAllFarmers();
    }

    @Override
    public void updateFarmer(farmer farmer) {
        user user = new user();
        user.setUserid(farmer.getUserid());
        user.setName(farmer.getName());
        user.setAvatar(farmer.getAvatar());
        user.setUsername(farmer.getUsername());
        user.setPhone(farmer.getPhone());
        user.setEmail(farmer.getEmail());
        userMapper.updatefarmer(user);
        farmerMapper.updateFarmer(farmer);
    }

    @Override
    public void deleteFarmer(int userId) {
        System.out.println("即将删除用户：" + userId);  // 添加日志
        farmerMapper.deleteFarmer(userId);
        int deleted = userMapper.deleteUser(userId);
        System.out.println("删除 user 表结果：" + deleted);  // 返回 1 表示成功删除，0 表示未删除
    }
    @Override
    public void addFarmer(farmer farmer) {

        user user = new user();
        user.setName(farmer.getName());
        user.setUsername(farmer.getUsername());
        user.setPassword("123");
        user.setUsertype(user.usertype.农户);
        user.regtime = new Timestamp(System.currentTimeMillis());
        user.setRestatus(1);
        userMapper.insertUser(user);
        user user1 =userMapper.getUserByUsername(farmer.getUsername());
        farmer.setUserid(user1.getUserid());
        farmerMapper.insertFarmer(farmer); // 假设insertFarmer是Mapper中定义的方法
    }
}
