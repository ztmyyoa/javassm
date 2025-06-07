package com.example.demo.SSM_frame.project.service;

import com.example.demo.SSM_frame.project.pojo.user;

import java.util.List;
import java.util.Map;

public interface UserService {

    user getUserByUsername(String username);
    user getUserById(int userId);
    List<user> getAllUsers();
    void insertUser(user user);
    void updateUser(user user);
    void deleteUser(int userId);
    void auditUser(Integer userId, int restatus);
    List<user> getUsersByRestatus(int restatus);
    Map<String, Integer> getUsersByType();
}
