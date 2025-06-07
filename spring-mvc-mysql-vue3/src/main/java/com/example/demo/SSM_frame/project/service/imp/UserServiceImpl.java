package com.example.demo.SSM_frame.project.service.imp;

import com.example.demo.SSM_frame.project.map.UserMapper;
import com.example.demo.SSM_frame.project.pojo.user;
import com.example.demo.SSM_frame.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public user getUserById(int userId) {
        return userMapper.selectUserById(userId);
    }

    @Override
    public List<user> getAllUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    public void insertUser(user user) {
        userMapper.insertUser(user);
    }

    @Override
    public void updateUser(user user) {
        userMapper.updateUser(user);
    }

    @Override
    public void deleteUser(int userId) {
        userMapper.deleteUser(userId);
    }


    @Override
    public user getUserByUsername(String username) {
        return userMapper.getUserByUsername(username); // 通过用户名查询用户
    }
    @Override
    public void auditUser(Integer userId, int restatus) {
        userMapper.auditUser(userId, restatus);
    }
    @Override
    public List<user> getUsersByRestatus(int restatus) {
        return userMapper.selectUsersByRestatus(restatus);
    }

    @Override
    public Map<String, Integer> getUsersByType() {
        List<Map<String, Object>> rawData = userMapper.countUsersByRole();
        Map<String, Integer> result = new HashMap<>();
        for (Map<String, Object> row : rawData) {
            String role = row.get("usertype").toString();
            int count = ((Number) row.get("count")).intValue();
            result.put(role, count);
        }
        return result;
    }
}
